package api.utils;

import api.dataeggs.joinablegames.JoinableGame;
import api.dataeggs.joinablegames.JoinableGamesDataEgg;
import backend.logic.games.Game;
import backend.logic.games.GameManager;

import java.util.Map;

public class JoinableGamesUtils {
    public static JoinableGamesDataEgg getJoinableGamesDataEgg() {
        JoinableGamesDataEgg dataEgg = new JoinableGamesDataEgg();

        Map<Integer, Game> gamesMap = GameManager.getGamesMap();
        for (Game game : gamesMap.values()) {
            dataEgg.addJoinableGame(game);
        }

        return dataEgg;
    }
}
