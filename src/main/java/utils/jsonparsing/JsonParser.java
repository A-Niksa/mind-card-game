package utils.jsonparsing;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;
import api.dataeggs.MakingMoveEgg;
import api.dataeggs.NewGameEgg;
import api.dataeggs.gamestate.EmojiEgg;
import api.dataeggs.gamestate.GameStateEgg;
import api.dataeggs.gamestate.HandEgg;
import api.dataeggs.gamestate.NinjaRequestEgg;
import api.dataeggs.joinablegames.JoinableGamesEgg;
import api.dataeggs.ninjarequest.DroppedCardEgg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {
    private static JsonParser parser;

    private GsonBuilder builder;
    private Gson gson;

    private JsonParser() {
        builder = new GsonBuilder();
        gson = builder.create();
    }

    private static JsonParser getInstance() {
        if (parser == null) parser = new JsonParser();
        return parser;
    }

    private static Gson gson() {
        return getInstance().gson;
    }

    public static DataEgg parseToDataEgg(String jsonString, DataEggType dataEggType) {
        return gson().fromJson(jsonString, getClassLiteral(dataEggType));
    }

    private static Class<? extends DataEgg> getClassLiteral(DataEggType dataEggType) { // ?
        switch (dataEggType) {
            case MAKING_MOVE_EGG:
                return MakingMoveEgg.class;
            case NEW_GAME_EGG:
                return NewGameEgg.class;
            case JOINABLE_GAMES_EGG:
                return JoinableGamesEgg.class;
            case GAME_STATE_EGG:
                return GameStateEgg.class;
            case HAND_EGG:
                return HandEgg.class;
            case DROPPED_CARD_EGG:
                return DroppedCardEgg.class;
            case EMOJI_EGG:
                return EmojiEgg.class;
            case NINJA_REQUEST_EGG:
                return NinjaRequestEgg.class;
        }

        return null;
    }
}
