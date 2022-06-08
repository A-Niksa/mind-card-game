package backend.logic.games;

import backend.logic.games.actionlogger.ActionLogger;
import backend.logic.games.components.Deck;
import backend.logic.models.cards.Card;
import backend.logic.models.players.Player;
import backend.logic.models.players.bots.Bot;
import backend.logic.models.players.bots.BotGenerationUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Game {
    private static int globalGameId = 0;

    private boolean gameHasBeenStarted;
    private int gameId;
    private int currentRound;
    private int numberOfBots;
    private int hostHumanId;
    private Deque<Card> cardsStack;
    private List<Player> playersList;
    private List<Thread> botThreadsList;
    private Deck deck;
    private ActionLogger actionLogger;

    public Game(int numberOfBots, int hostHumanId) {
        this.numberOfBots = numberOfBots;
        this.hostHumanId = hostHumanId;

        generateGameId();
        initializeLists();
        cardsStack = new ArrayDeque<>();
        gameHasBeenStarted = false;
        currentRound = 1;
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
        initializeGameComponents();
        gameHasBeenStarted = true;
    }

    public void dropCard(int playerId, int numberOfCardToDrop) {
        // TODO
    }

    private void initializeGameComponents() {
        deck = new Deck(getNumberOfPlayers());
        initializeBots();
        startBotThreads();
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

    public long getLatestActionTimeDifference() {
        return actionLogger.getLatestTimeDifference();
    }

    public int getNumberOfPlayers() {
        return playersList.size();
    }

    public int getGameId() {
        return gameId;
    }
}