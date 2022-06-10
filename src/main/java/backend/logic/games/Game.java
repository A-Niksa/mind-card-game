package backend.logic.games;

import backend.logic.games.actionlogger.Action;
import backend.logic.games.actionlogger.ActionLogger;
import backend.logic.games.components.Deck;
import backend.logic.games.components.DroppingGround;
import backend.logic.games.components.JudgeUtils;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Player;
import backend.logic.models.players.bots.Bot;
import backend.logic.models.players.bots.BotGenerationUtils;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int globalGameId = 0;

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

    public Game(int numberOfBots, int hostHumanId) {
        this.numberOfBots = numberOfBots;
        this.hostHumanId = hostHumanId;

        generateGameId();
        initializeLists();
        currentRound = 1;

        gameHasBeenStarted = false;
        gameHasEnded = false;
        gameHasResultedInLoss = false;
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
    }

    public synchronized void dropCard(int playerId, NumberedCard cardToDrop) {
        if (!JudgeUtils.cardToDropIsTheMinOfAllPlayers(this, cardToDrop)) {
            removeOneHealthCardFromDeck();
            if (JudgeUtils.checkIfPlayersHaveLost(this)) {
                return;
            }

            resetComponents();
            goToNextRound();
            return;
        }

        droppingGround.dropCardOnGround(cardToDrop);
        actionLogger.addAction(new Action()); // logs timestamp of the latest action

        if (JudgeUtils.allCardsHaveBeenDropped(this)) {
            resetComponents();
            goToNextRound();
        }
    }

    public void dropCardByHuman(int playerId, int numberOfCardToDrop) {

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

    private Player getPlayerById(int playerId) {
        for (Player player : playersList) {
            if (playerId == player.getPlayerId()) {
                return player;
            }
        }

        return null;
    }

    private void initializeGame() {
        initializeBots();
        startBotThreads();
    }

    private void initializeGameComponents() {
        deck = new Deck(getNumberOfPlayers());
        droppingGround = new DroppingGround();
        actionLogger = new ActionLogger();
    }

    private void startBotThreads() {
        for (Thread thread : botThreadsList) {
            thread.start();
        }
    }

    private void initializeBots() {
        ArrayList<Bot> botsList = BotGenerationUtils.getSomeBots(numberOfBots, deck, currentRound, gameId);
        playersList.addAll(botsList);

        connectThreadsToBots(botsList);
    }

    private void connectThreadsToBots(ArrayList<Bot> botsList) {
        for (int i = 0; i < numberOfBots; i++) {
            Thread thread = new Thread(botsList.get(i));
            botThreadsList.add(thread);
        }
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

    public DroppingGround getDroppingGround() {
        return droppingGround;
    }

    public int getNumberOfPlayers() {
        return playersList.size();
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }

    public int getGameId() {
        return gameId;
    }
}