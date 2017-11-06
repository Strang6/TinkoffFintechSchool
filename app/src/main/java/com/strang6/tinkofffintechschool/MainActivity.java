package com.strang6.tinkofffintechschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void task1OnClick(View view) {
        // Serializer
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(State.class, new StateSerializer())
                .create();
        State state = new State("North Korea");
        state.setCapital("Pyongyang");
        state.setOfficialLanguage("Korean");
        state.setArea(120540);
        state.setPopulation(25368620);
        Log.d(TAG, gson.toJson(state));

        // ExposeAnnotation
        Gson exposeGson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        User user = new User("Иван", "Иванов", "8-936-562-15-20");
        Log.d(TAG, exposeGson.toJson(user));

        //ExclusionStrategy
        Gson exclusionStrategyGson = new GsonBuilder()
                .setPrettyPrinting()
                .setExclusionStrategies(new StateExclusionStrategy())
                .create();
        Log.d(TAG, exclusionStrategyGson.toJson(state));
    }

    public void task2OnClick(View view) {
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new MapDeserializer())
                .create();
        String gsonString = "{\"name\":\"name\",\"any_map\":{\"a\":\"55\",\"b\":\"85\",\"c\":\"56\"}}";
        Map<String, Integer> map = gson.fromJson(gsonString, type);
        Log.d(TAG, map.getClass().toString());
        Log.d(TAG, map.toString());
    }

    public void task3OnClick(View view) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BigDecimal.class, new MoneyAmountDeserializer())
                .create();
        String gsonString = "{\"money_amount\":\"2444,88\"}";
        BigDecimal moneyAmount = gson.fromJson(gsonString, BigDecimal.class);
        Log.d(TAG, moneyAmount.toString());
    }

    public void task4OnClick(View view) {
        DateExample dateExample = new DateExample(new Date());
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(DateExample.class, new DateSerializer())
                .create();
        Log.d(TAG, gson.toJson(dateExample));
    }
}
