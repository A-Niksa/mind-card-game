package api.utils;

import api.dataeggs.MakingMoveDataEgg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    public static String getJsonString(MakingMoveDataEgg dataEgg) {
        return gson.toJson(dataEgg);
    }
}
