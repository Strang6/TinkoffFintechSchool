package com.strang6.tinkofffintechschool;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Strang6 on 27.11.2017.
 */

public class NewsDeserializer implements JsonDeserializer<News> {
    @Override
    public News deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        News news = null;
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            String text = object.get("text").getAsString();
            long milliseconds = object.get("publicationDate").getAsJsonObject().get("milliseconds").getAsLong();
            news = new News(text, new Date(milliseconds));
        }
        return news;
    }
}
