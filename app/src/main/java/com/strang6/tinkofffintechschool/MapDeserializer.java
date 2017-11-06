package com.strang6.tinkofffintechschool;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class MapDeserializer implements JsonDeserializer<Map<String, Integer>> {
    @Override
    public Map<String, Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject().get("any_map").getAsJsonObject();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            Gson gson = new Gson();
            Map<String, Integer> map = gson.fromJson(object, type);
            return map;
        } else
            return null;
    }
}
