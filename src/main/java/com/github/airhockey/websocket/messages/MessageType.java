package com.github.airhockey.websocket.messages;

public enum MessageType {
    PLAYER_INFO(0x01), OPPONENT_INFO(0x02), CURSOR_MOVE(0x04), SET_SCORE_EVENT(0x08);

    private int code;

    MessageType(int code) {
        this.code = code;
    }
}
