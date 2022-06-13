package api.utils;

import api.dataeggs.gamestate.HandEgg;
import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.games.components.ninjahandling.NinjaHandler;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameStateUtils {
    public static NinjaRequestStatus getNinjaRequestStatus(int gameId) {
        Game game = GameManager.getGameById(gameId);
        NinjaHandler ninjaHandler = game.getNinjaHandler();
        return ninjaHandler.getRequestStatus();
    }

    public static boolean thereHasBeenANinjaRequestInGame(int gameId) {
        Game game = GameManager.getGameById(gameId);
        NinjaHandler ninjaHandler = game.getNinjaHandler();
        return ninjaHandler.thereHasBeenARequest();
    }

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

    public static List<HandEgg> getHandsOfPlayersOtherThanCurrentById(Game game, int playerId) {
        ArrayList<HandEgg> handEggsList = new ArrayList<>();

        List<Player> playersList = game.getPlayersList();
        for (Player player : playersList) {
            if (playerId != player.getPlayerId()) {
                HandEgg handEgg = new HandEgg(player.getPlayerId(), player.getHand());
                handEggsList.add(handEgg);

            }
        }

        return handEggsList;
    }
}
