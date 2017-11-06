package com.strang6.tinkofffintechschool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class DateSerializer implements JsonSerializer<DateExample> {

    @Override
    public JsonElement serialize(DateExample src, Type typeOfSrc, JsonSerializationContext context) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(src.getDate());
        //JsonObject result = new JsonObject();
        //result.addProperty("date", date);
        return new JsonPrimitive(date);
    }
}
