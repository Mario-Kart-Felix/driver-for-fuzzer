package com.endava.cats.model.ann;

import com.browser.gson.ExclusionStrategy;
import com.browser.gson.FieldAttributes;

public class ExcludeTestCaseStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(Exclude.class) != null;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null;
    }
}
