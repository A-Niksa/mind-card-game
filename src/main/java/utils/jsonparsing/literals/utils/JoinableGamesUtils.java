package utils.jsonparsing.literals.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import utils.jsonparsing.literals.dataeggs.joinablegames.JoinableGamesEgg;

import java.util.Map;

public class JoinableGamesUtils {
    public static JoinableGamesEgg getJoinableGamesDataEgg() {
        JoinableGamesEgg dataEgg = new JoinableGamesEgg();

        Map<Integer, Game> gamesMap = GameManager.getGamesMap();
        for (Game game : gamesMap.values()) {
            dataEgg.addJoinableGame(game);
        }

        return dataEgg;
    }
}
