package utils.jsonparsing.literals.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.games.components.ninjahandling.NinjaHandler;
import backend.logic.games.components.ninjahandling.NinjaRequest;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;
import utils.jsonparsing.literals.dataeggs.gamestate.Emoji;
import utils.jsonparsing.literals.dataeggs.gamestate.EmojiEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.HandEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.NinjaRequestEgg;
import utils.jsonparsing.literals.dataeggs.ninjarequest.NinjaRequestStatus;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class GameStateUtils {
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

    public static Emoji stringToEnum(String  emojiString){
        Emoji emoji;
        if(emojiString.toLowerCase().equals("ANGRY".toLowerCase())){
            emoji = Emoji.ANGRY;
        }
        else if(emojiString.toLowerCase().equals("LAUGHING".toLowerCase())){
            emoji = Emoji.LAUGHING;
        }
        else if(emojiString.toLowerCase().equals("LOVING".toLowerCase())){
            emoji = Emoji.LOVING;
        }
        else if(emojiString.toLowerCase().equals("CRYING".toLowerCase())){
            emoji = Emoji.CRYING;
        }
        else{
            emoji = Emoji.NOTHING;
        }
        return emoji;
    }
}
