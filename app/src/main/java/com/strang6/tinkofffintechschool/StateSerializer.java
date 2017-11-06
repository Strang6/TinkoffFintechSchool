package com.strang6.tinkofffintechschool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class StateSerializer implements JsonSerializer<State> {
    @Override
    public JsonElement serialize(State src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("name", src.getName());
        result.addProperty("capital", src.getCapital());
        result.addProperty("area", src.getArea());
        result.addProperty("population", src.getPopulation());
        return result;
    }
}
