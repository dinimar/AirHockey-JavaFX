package com.github.airhockey.websocket.utils;

import com.google.gson.Gson;

public class JSONUtils {
    private Gson gson = new Gson();

    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
