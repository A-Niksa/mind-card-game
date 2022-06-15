package backend.logic.games;

import backend.logic.games.components.ninjahandling.CardAndPlayerTuple;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Human;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private static GameManager manager;

    private Lobby lobby;
    private Map<Integer, Game> gamesMap;

    private GameManager() {
        lobby = new Lobby();
        gamesMap = new HashMap<>();
    }

    private static GameManager getInstance() {
        if (manager == null) {
            manager = new GameManager();
        }

        return manager;
    }

    public static void addGame(Game game) {
        getInstance().gamesMap.put(game.getGameId(), game);
    }

    public static boolean gameHasHealthCardsLeft(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return false;
        }

        return game.hasHealthCardsLeft();
    }

    public static Human createNewHumanInLobby() {
        Human human = new Human();
        getInstance().lobby.addHuman(human);
        return human;
    }

    public static Human removePlayerFromLobbyById(int playerId) {
        Human human = getInstance().lobby.removeHumanById(playerId);
        return human;
    }

    public static void joinGame(int gameId, int playerId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return;
        }

        game.addHuman(playerId);
    }

    public static void restartGameThreads(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return;
        }

        game.restartThreads();
    }

    public static boolean canJoinGame(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return false;
        }

        boolean gameIsFull = game.getNumberOfPlayers() >= 4;
        return !game.gameHasBeenStarted() && !gameIsFull;
    }

    public static boolean thereHasBeenANinjaRequest(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return false;
        }

        return game.getNinjaHandler().thereHasBeenARequest();
    }

    public static boolean startGameById(int gameId) {
        // returns a boolean indicating whether the setting operation was successful
        Game game = getGameById(gameId);
        game.startGame();
        return true;
    }

    public static long getLatestActionTimeDifferenceOfGame(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return -1;
        }

        return game.getLatestActionTimeDifference();
    }

    public static void dropCardInGame(int gameId, int playerId, NumberedCard cardToDrop) {
        Game game = getGameById(gameId);
        if (game == null) {
            return;
        }

        game.dropCard(playerId, cardToDrop);
    }

    public static void castVoteForNinjaRequestInGame(int gameId, Human human, boolean agreesWithRequest) {
        Game game = getGameById(gameId);
        if (game == null) {
            return;
        }

        game.castVoteForNinjaRequest(human, agreesWithRequest);
    }

    public static void dropNinjaCardInGame(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return;
        }

        game.dropNinjaCard();
    }

    public static Game getGameById(int gameId) {
        return getInstance().gamesMap.getOrDefault(gameId, null);
    }

    public static Map<Integer, Game> getGamesMap() {
        return getInstance().gamesMap;
    }
}
