package utils.jsonparsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;
import utils.jsonparsing.literals.dataeggs.MakingMoveEgg;
import utils.jsonparsing.literals.dataeggs.NewGameEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.EmojiEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.GameStateEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.HandEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.NinjaRequestEgg;
import utils.jsonparsing.literals.dataeggs.joinablegames.JoinableGamesEgg;
import utils.jsonparsing.literals.dataeggs.ninjarequest.DroppedCardEgg;

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
