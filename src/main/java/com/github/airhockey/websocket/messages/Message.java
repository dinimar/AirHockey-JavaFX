package com.github.airhockey.websocket.messages;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message {
    private MessageType msgType;
    private List<Object> properties = new ArrayList<>();

    public MessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }

    public List<Object> getProperties() {
        return properties;
    }

    public void setProperties(List<Object> properties) {
        this.properties = properties;
    }

    public void addProperty(Object object) {
        properties.add(object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return msgType == message.msgType &&
                Objects.equals(properties, message.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgType, properties);
    }
}