package com.qingyou.auth.abac.serialize;

import com.google.gson.Gson;

public class GsonSerializer implements JsonSerializer {

    private final static Gson gson = new Gson();

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> String toJson(T object) {
        return gson.toJson(object);
    }
}
