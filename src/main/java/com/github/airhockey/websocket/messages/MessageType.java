package com.github.airhockey.websocket.messages;

public enum MessageType {
    PLAYER_INFO(0x01), OPPONENT_INFO(0x02), GAME_EVENT(0x04);

    private int code;

    MessageType(int code) {
        this.code = code;
    }
}
