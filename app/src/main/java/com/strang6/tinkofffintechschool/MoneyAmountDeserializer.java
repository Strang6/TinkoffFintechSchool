package com.strang6.tinkofffintechschool;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class MoneyAmountDeserializer implements JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            String moneyAmount = json.getAsJsonObject().get("money_amount").getAsString();
            moneyAmount = moneyAmount.replace(',', '.');
            return new BigDecimal(moneyAmount);
        } else
            return null;
    }
}
