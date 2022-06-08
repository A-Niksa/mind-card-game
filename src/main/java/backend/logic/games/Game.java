package backend.logic.games;

import backend.logic.games.components.Deck;
import backend.logic.models.cards.HealthCard;
import backend.logic.models.cards.NinjaCard;
import backend.logic.models.players.Player;
import backend.logic.models.players.bots.Bot;
import backend.logic.models.players.bots.BotGenerationUtils;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int globalGameId = 0;

    private boolean gameHasBeenStarted;
    private int gameId;
    private int currentRound;
    private int numberOfBots;
    private int hostHumanId;
    private List<Player> playersList;
    private List<Thread> botThreadsList;
    private Deck deck;

    public Game(int gameId, int numberOfBots, int hostHumanId) {
        this.gameId = gameId;
        this.numberOfBots = numberOfBots;
        this.hostHumanId = hostHumanId;

        gameHasBeenStarted = false;
        currentRound = 1;
    }

    private void initializeArrays() {
        playersList = new ArrayList<>();
        botThreadsList = new ArrayList<>();
    }

    public void startGame() {
        // this class is 'lazy', in the sense that most components will be initialized if we start the game by this method
        initializeGameComponents();
        gameHasBeenStarted = true;
    }

    private void initializeGameComponents() {
        deck = new Deck(getNumberOfPlayers());
        initializeBots();
    }

    private void initializeBots() {
        ArrayList<Bot> botsList = BotGenerationUtils.getSomeBots(numberOfBots, deck, currentRound);
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

    public int getNumberOfPlayers() {
        return playersList.size();
    }

    public int getGameId() {
        return gameId;
    }
}