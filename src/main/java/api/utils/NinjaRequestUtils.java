package api.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.Deck;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

public class NinjaRequestUtils {
    public static Human createHumanPlayer(int gameId) {
        Game game = GameManager.getGameById(gameId);
        Deck deck = game.getDeck();
        Human human = new Human(deck.getRandomHand(game.getCurrentRound()));
        return human;
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
