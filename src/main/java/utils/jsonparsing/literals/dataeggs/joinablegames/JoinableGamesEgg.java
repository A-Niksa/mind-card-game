package utils.jsonparsing.literals.dataeggs.joinablegames;

import backend.logic.games.Game;
import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;

import java.util.ArrayList;
import java.util.List;

public class JoinableGamesEgg extends DataEgg {
    private List<JoinableGame> joinableGamesList;

    public JoinableGamesEgg() {
        super(DataEggType.JOINABLE_GAMES_EGG);
        joinableGamesList = new ArrayList<>();
    }

    public void addJoinableGame(Game game) {
        int gameId = game.getGameId();
        int numberOfBots = game.getNumberOfBots();
        int numberOfFreePlayers = 4 - game.getNumberOfPlayers();
        JoinableGame joinableGame = new JoinableGame(gameId, numberOfFreePlayers, numberOfBots);

        joinableGamesList.add(joinableGame);
    }

    public List<JoinableGame> getJoinableGamesList() {
        return joinableGamesList;
    }
}
