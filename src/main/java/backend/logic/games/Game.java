package backend.logic.games;

import backend.logic.games.actionlogger.Action;
import backend.logic.games.actionlogger.ActionLogger;
import backend.logic.games.components.Deck;
import backend.logic.games.components.DroppingGround;
import backend.logic.games.components.Hand;
import backend.logic.games.components.JudgeUtils;
import backend.logic.games.components.ninjahandling.CardAndPlayerTuple;
import backend.logic.games.components.ninjahandling.NinjaHandler;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;
import backend.logic.models.players.bots.Bot;
import backend.logic.models.players.bots.BotGenerationUtils;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int globalGameId = 0;

    private int totalNumberOfPlayers;
    private boolean gameHasBeenCanceled;
    private boolean gameHasBeenStarted;
    private boolean gameHasEnded;
    private boolean gameHasResultedInLoss;
    private int gameId;
    private int currentRound;
    private int numberOfBots;
    private int hostHumanId;
    private List<Player> playersList;
    private List<Thread> botThreadsList;
    private Deck deck;
    private DroppingGround droppingGround;
    private ActionLogger actionLogger;
    private NinjaHandler ninjaHandler;

    public Game(int numberOfBots, int hostHumanId) {
        this.numberOfBots = numberOfBots;
        this.hostHumanId = hostHumanId;

        generateGameId();
        initializeLists();
        currentRound = 1;

        gameHasBeenCanceled = false;
        gameHasBeenStarted = false;
        gameHasEnded = false;
        gameHasResultedInLoss = false;

        initializePlayers();
    }

    private void initializePlayers() {
        initializeHost();
        initializeBots();
    }

    private void initializeHost() {
        Human hostHuman = GameManager.removePlayerFromLobbyById(hostHumanId);
        playersList.add(hostHuman);
    }

    public void addHuman(int playerId) {
        int numberOfHumans = getNumberOfPlayers() - numberOfBots;
        if (numberOfHumans >= 4) {
            return;
        }

        Human human = GameManager.removePlayerFromLobbyById(playerId);
        if (!gameHasBeenStarted) {
            playersList.add(human);
        } else {
            replaceBotWithHuman(human);
        }
    }

    public void replaceBotWithHuman(Human human) {
        if (numberOfBots == 0) {
            return;
        }

        Bot bot = getFirstBotInList();
        Hand hand = bot.getHand();
        removePlayer(bot.getPlayerId());
        numberOfBots--;

        restartThreads();

        human.setHand(hand);
        playersList.add(human);
        int numberOfHumans = playersList.size() - numberOfBots;
        ninjaHandler.setNumberOfHumansInGame(numberOfHumans);
    }

    private Bot getFirstBotInList() {
        List<Bot> botsList = BotGenerationUtils.getBotsList(playersList);
        if (botsList.isEmpty()) {
            return null;
        }

        return botsList.get(0);
    }

    public void disconnectHuman(int playerId) {
        Player player = getPlayer(playerId);
        int updatedNumberOfHumans = getNumberOfPlayers() - numberOfBots - 1;
        ninjaHandler.setNumberOfHumansInGame(updatedNumberOfHumans);
        removePlayer(playerId);

        numberOfBots++;
        addBot(player.getHand());
    }

    private Player getPlayer(int playerId) {
        for (Player player : playersList) {
            if (playerId == player.getPlayerId()) {
                return player;
            }
        }

        return null;
    }

    private void removePlayer(int playerId) {
        Player player;
        for (int i = 0; i < playersList.size(); i++) {
            player = playersList.get(i);
            if (playerId == player.getPlayerId()) {
                playersList.remove(i);
                return;
            }
        }
    }

    private void addBot(Hand hand) {
        Bot bot = BotGenerationUtils.getARandomBot(gameId);
        bot.setHand(hand);
        playersList.add(bot);

        Thread thread = new Thread(bot);
        botThreadsList.add(thread);
        thread.start();
    }

    private void generateGameId() {
        gameId = globalGameId;
        globalGameId++;
    }

    private void initializeLists() {
        playersList = new ArrayList<>();
        botThreadsList = new ArrayList<>();
    }

    public void startGame() {
        // this class is 'lazy', in the sense that most components will be initialized if we start the game by this method
        initializeGame();
        gameHasBeenStarted = true;
        totalNumberOfPlayers = playersList.size();
    }

    public synchronized void dropCard(int playerId, NumberedCard cardToDrop) {
        if (!JudgeUtils.cardToDropIsTheMinOfAllPlayers(this, cardToDrop)) {
            actionLogger.logLossOfHealthCard();
            // dummy
            removeOneHealthCardFromDeck();
            if (JudgeUtils.checkIfPlayersHaveLost(this)) {
                return;
            }

            restartThreads();
            JudgeUtils.scorchSmallerCards(this, cardToDrop);
        }

        droppingGround.dropCardOnGround(cardToDrop);
        actionLogger.addAction(new Action(playerId)); // logs timestamp of the latest action

        if (JudgeUtils.allCardsHaveBeenDropped(this)) {
            resetComponents();
            goToNextRound();
        }
    }

    public void castVoteForNinjaRequest(Human human, boolean agreesWithRequest) {
        ninjaHandler.addRequest(human, agreesWithRequest);
    }

    public void dropNinjaCard() {
        restartThreads();
        List<CardAndPlayerTuple> smallestCardsList = ninjaHandler.carryOutRequestAndReturnDroppedCards();
        ninjaHandler.setSmallestCardsList(smallestCardsList);
        ninjaHandler.setShouldShowSmallestCards(true);
        deck.removeOneNinjaCard();

        if (JudgeUtils.allCardsHaveBeenDropped(this)) {
            resetComponents();
            goToNextRound();
        }
    }

    private void removeOneHealthCardFromDeck() {
        deck.removeOneHealthCard();
    }

    private void goToNextRound() {
        if (currentRound >= 12) {
            return;
        }

        currentRound++;
        deck.resetDeck();
        BotGenerationUtils.giveHandsToPlayersFromDeck(playersList, deck, currentRound);
    }

    private void resetComponents() {
        actionLogger.clear();
        droppingGround.clear();
    }

    private void initializeGame() {
        initializeGameComponents();
        startBotThreads();
    }

    private void initializeGameComponents() {
        droppingGround = new DroppingGround();
        actionLogger = new ActionLogger(gameId);
        deck = new Deck(getNumberOfPlayers());
        BotGenerationUtils.giveHandsToPlayersFromDeck(playersList, deck, currentRound);

        int numberOfHumans = getNumberOfPlayers() - numberOfBots;
        ninjaHandler = new NinjaHandler(gameId, numberOfHumans, playersList);
    }

    private void startBotThreads() {
        for (Thread thread : botThreadsList) {
            thread.start();
        }
    }

    private void initializeBots() {
        ArrayList<Bot> botsList = BotGenerationUtils.getSomeBots(numberOfBots, gameId);
        playersList.addAll(botsList);

        connectThreadsToBots(botsList);
    }

    private void connectThreadsToBots(ArrayList<Bot> botsList) {
        for (int i = 0; i < numberOfBots; i++) {
            Thread thread = new Thread(botsList.get(i));
            botThreadsList.add(thread);
        }
    }

    public void restartThreads() {
        interruptThreads();
        reconnectThreadsToBots();
    }

    public void interruptThreads() {
        for (Thread thread : botThreadsList) {
            thread.interrupt();
        }
    }

    private void reconnectThreadsToBots() {
        botThreadsList.clear();

        ArrayList<Bot> botsList = BotGenerationUtils.getBotsList(playersList);
        connectThreadsToBots(botsList);
        startBotThreads();
    }

    public boolean hasHealthCardsLeft() {
        return deck.getHealthCardsList().size() > 0;
    }

    public void setGameHasEnded(boolean gameHasEnded) {
        this.gameHasEnded = gameHasEnded;
    }

    public void setGameHasResultedInLoss(boolean gameHasResultedInLoss) {
        this.gameHasResultedInLoss = gameHasResultedInLoss;
    }

    public long getLatestActionTimeDifference() {
        return actionLogger.getLatestTimeDifference();
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public Deck getDeck() {
        return deck;
    }

    public NinjaHandler getNinjaHandler() {
        return ninjaHandler;
    }

    public DroppingGround getDroppingGround() {
        return droppingGround;
    }

    public int getNumberOfPlayers() {
        return playersList.size();
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }

    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    public int getGameId() {
        return gameId;
    }

    public boolean gameHasBeenStarted() {
        return gameHasBeenStarted;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getHostHumanId() {
        return hostHumanId;
    }

    public ActionLogger getActionLogger() {
        return actionLogger;
    }
}