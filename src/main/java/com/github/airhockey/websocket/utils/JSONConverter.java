package com.github.airhockey.websocket.utils;

import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.google.gson.Gson;

public class JSONConverter {
    private Gson gson = new Gson();

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public Message toMessage(String message) {
        return gson.fromJson(message, Message.class);
    }

    public MessageType getMessageType(String message) {
        return toMessage(message).getMsgType();
    }
}
