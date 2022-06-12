package api.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

public class NinjaRequestUtils {
    public static Human getHumanById(int gameId, int playerId) {
        Game game = GameManager.getGameById(gameId);
        for (Player player : game.getPlayersList()) {
            if (playerId == player.getPlayerId() && !player.isBot()) {
                return (Human) player;
            }
        }

        return null;
    }
}
