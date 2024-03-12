package com.qingyou.auth.abac.serialize;

public interface JsonSerializer {
    <T> T fromJson(String json, Class<T> clazz);
    <T> String toJson(T object);
}
