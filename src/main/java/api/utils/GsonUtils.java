package api.utils;

import api.dataeggs.MakingMoveEgg;
import api.dataeggs.NewGameEgg;
import api.dataeggs.gamestate.GameStateEgg;
import api.dataeggs.joinablegames.JoinableGamesEgg;
import api.dataeggs.ninjarequest.NinjaMoveEgg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
