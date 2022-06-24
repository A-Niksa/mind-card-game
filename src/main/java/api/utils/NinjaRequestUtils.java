package api.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.ninjahandling.NinjaRequest;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

public class NinjaRequestUtils {
    public static boolean ninjaRequestHasBeenCompleted(int gameId) {
        Game game = GameManager.getGameById(gameId);
        NinjaRequest request = game.getNinjaHandler().getNinjaRequestsStack().poll();
        if (request == null) {
            return false;
        }

        if (!request.allHumanVotesHaveBeenCast()) {
            game.getNinjaHandler().getNinjaRequestsStack().offer(request);
            return false;
        }

        return request.allHumansHaveAgreedOnNinjaRequest();
    }

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
