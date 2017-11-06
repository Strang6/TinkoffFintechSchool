package com.strang6.tinkofffintechschool;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class StateExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == State.class && f.getName().equals("capital"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
