package utils.jsonparsing.literals.utils;

import backend.logic.games.Game;
import backend.logic.games.components.Hand;
import backend.logic.models.players.Player;
import utils.jsonparsing.literals.dataeggs.gamestate.HandDataEgg;

import java.util.ArrayList;
import java.util.List;

public class GameStateUtils {
    public static Hand getHandById(Game game, int playerId) {
        Player player = getPlayerById(game, playerId);
        return player.getHand();
    }

    private static Player getPlayerById(Game game, int playerId) {
        List<Player> playersList = game.getPlayersList();
        for (Player player : playersList) {
            if (playerId == player.getPlayerId()) {
                return player;
            }
        }

        return null;
    }

    public static List<HandDataEgg> getHandsOfPlayersOtherThanCurrentById(Game game, int playerId) {
        ArrayList<HandDataEgg> handDataEggsList = new ArrayList<>();

        List<Player> playersList = game.getPlayersList();
        for (Player player : playersList) {
            if (playerId != player.getPlayerId()) {
                HandDataEgg handDataEgg = new HandDataEgg(player.getPlayerId(), player.getHand());
            }
        }

        return handDataEggsList;
    }
}
