package utils.jsonparsing.literals.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.jsonparsing.literals.dataeggs.MakingMoveEgg;
import utils.jsonparsing.literals.dataeggs.NewGameEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.GameStateEgg;
import utils.jsonparsing.literals.dataeggs.joinablegames.JoinableGamesEgg;
import utils.jsonparsing.literals.dataeggs.ninjarequest.NinjaMoveEgg;

public class GsonUtils {
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public static String getJsonString(MakingMoveEgg dataEgg) {
        return gson.toJson(dataEgg);
    }

    public static String getJsonString(JoinableGamesEgg dataEgg) {
        return gson.toJson(dataEgg);
    }

    public static String getJsonString(NewGameEgg dataEgg) {
        return gson.toJson(dataEgg);
    }

    public static String getJsonString(GameStateEgg dataEgg) {
        return gson.toJson(dataEgg);
    }

    public static String getJsonString(NinjaMoveEgg dataEgg) {
        return gson.toJson(dataEgg);
    }
}
