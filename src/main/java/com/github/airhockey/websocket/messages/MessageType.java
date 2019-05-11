package com.github.airhockey.websocket.messages;

public enum MessageType {
    PLAYER_INFO(0x01), OPPONENT_INFO(0x02);

    private int code;

    MessageType(int code) {
        this.code = code;
    }
}
