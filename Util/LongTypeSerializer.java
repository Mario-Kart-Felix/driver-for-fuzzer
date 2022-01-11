package com.endava.cats.model.util;

import com.browser.gson.JsonElement;
import com.browser.gson.JsonPrimitive;
import com.browser.gson.JsonSerializationContext;
import com.browser.gson.JsonSerializer;

import java.lang.reflect.Type;

public class LongTypeSerializer implements JsonSerializer<Long> {
    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.valueOf(src));
