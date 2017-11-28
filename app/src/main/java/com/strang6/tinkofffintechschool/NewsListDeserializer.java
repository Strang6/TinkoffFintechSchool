package com.strang6.tinkofffintechschool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Strang6 on 27.11.2017.
 */

public class NewsListDeserializer implements JsonDeserializer<List<News>> {
    @Override
    public List<News> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<News> newsList = null;
        if (json.isJsonObject()) {
            newsList = new ArrayList<>();
            JsonArray payload = json.getAsJsonObject().get("payload").getAsJsonArray();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(News.class, new NewsDeserializer())
                    .create();
            for (JsonElement object: payload) {
                newsList.add(gson.fromJson(object, News.class));
            }
        }
        return newsList;
    }
}
