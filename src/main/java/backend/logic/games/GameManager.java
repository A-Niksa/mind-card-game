package backend.logic.games;

import backend.logic.games.components.ninjahandling.CardAndPlayerTuple;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private static GameManager manager;

    private Map<Integer, Game> gamesMap;

    private GameManager() {
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

    public static void joinGame(int gameId, Player player) {
        Game game = getGameById(gameId);
        if (game == null) {
            return;
        }

        game.addPlayer(player);
    }

    public static boolean canJoinGame(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return false;
        }

        return !game.gameHasBeenStarted();
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

    public static ArrayList<CardAndPlayerTuple> dropNinjaCardInGame(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return new ArrayList<>();
        }

        return game.dropNinjaCard();
    }

    public static Game getGameById(int gameId) {
        return getInstance().gamesMap.getOrDefault(gameId, null);
    }

    public static Map<Integer, Game> getGamesMap() {
        return getInstance().gamesMap;
    }
}
