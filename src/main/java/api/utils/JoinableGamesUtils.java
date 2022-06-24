package api.utils;

import api.dataeggs.joinablegames.JoinableGamesEgg;
import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.models.players.Player;

import java.util.List;
import java.util.Map;

public class JoinableGamesUtils {
    public static JoinableGamesEgg getJoinableGamesDataEgg() {
        JoinableGamesEgg dataEgg = new JoinableGamesEgg();

        Map<Integer, Game> gamesMap = GameManager.getGamesMap();
        for (Game game : gamesMap.values()) {
            if (gameIsJoinable(game)) {
                dataEgg.addJoinableGame(game);
            }
        }

        return dataEgg;
    }

    private static boolean gameIsJoinable(Game game) {
        return getNumberOfHumans(game) < 4;
    }

    private static int getNumberOfHumans(Game game) {
        List<Player> playersList = game.getPlayersList();

        int numberOfHumans = 0;
        for (Player player : playersList) {
            if (!player.isBot()) {
                numberOfHumans++;
            }
        }

        return numberOfHumans;
    }
}
