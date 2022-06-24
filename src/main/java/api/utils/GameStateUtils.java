package api.utils;

import api.dataeggs.gamestate.Emoji;
import api.dataeggs.gamestate.EmojiEgg;
import api.dataeggs.gamestate.HandEgg;
import api.dataeggs.gamestate.NinjaRequestEgg;
import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.games.components.ninjahandling.NinjaHandler;
import backend.logic.games.components.ninjahandling.NinjaRequest;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class GameStateUtils {
    public static EmojiEgg getEmojiById(Game game, int playerId) {
        Human human = (Human) getPlayerById(game, playerId);
        return new EmojiEgg(playerId, human.getSelectedEmoji());
    }

    public static ArrayList<NinjaRequestEgg> getNinjaRequestsList(Game game) {
        ArrayList<NinjaRequestEgg> ninjaRequestEggsList = new ArrayList<>();

        NinjaHandler handler = game.getNinjaHandler();
        Deque<NinjaRequest> ninjaRequestsStack = handler.getNinjaRequestsStack();
        NinjaRequest ninjaRequest = ninjaRequestsStack.peek();
        if (ninjaRequest == null) {
            return ninjaRequestEggsList;
        }

        for (Map.Entry<Human, NinjaRequestStatus> entry : ninjaRequest.getHumanVotesMap().entrySet()) {
            NinjaRequestEgg requestEgg = new NinjaRequestEgg(entry.getKey().getPlayerId(),
                    entry.getValue());
            ninjaRequestEggsList.add(requestEgg);
        }

        return ninjaRequestEggsList;
    }

    public static void setEmojiById(Game game, int playerId, Emoji emoji) {
        Player player = getPlayerById(game, playerId);
        if (!player.isBot()) {
            ((Human) player).setSelectedEmoji(emoji);
        }
    }

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

    public static Emoji stringToEnum(String emojiString) {
        Emoji emoji;
        if (emojiString.toLowerCase().equals("ANGRY".toLowerCase())) {
            emoji = Emoji.ANGRY;
        } else if (emojiString.toLowerCase().equals("LAUGHING".toLowerCase())) {
            emoji = Emoji.LAUGHING;
        } else if (emojiString.toLowerCase().equals("LOVING".toLowerCase())) {
            emoji = Emoji.LOVING;
        } else if (emojiString.toLowerCase().equals("CRYING".toLowerCase())) {
            emoji = Emoji.CRYING;
        } else {
            emoji = Emoji.NOTHING;
        }
        return emoji;
    }
}
