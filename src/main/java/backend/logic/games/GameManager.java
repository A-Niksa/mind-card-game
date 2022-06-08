package backend.logic.games;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager manager;

    private List<Game> gamesList;

    private GameManager() {
        gamesList = new ArrayList<>();
    }

    private static GameManager getInstance() {
        if (manager == null) {
            manager = new GameManager();
        }

        return manager;
    }

    public static void addGame(Game game) {
        getInstance().gamesList.add(game);
    }

    public static boolean gameHasHealthCardsLeft(int gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            return false;
        }

        return game.hasHealthCardsLeft();
    }

    private static Game getGameById(int gameId) {
        for (Game game : getInstance().gamesList){
            if (gameId == game.getGameId()) {
                return game;
            }
        }

        return null;
    }
}
