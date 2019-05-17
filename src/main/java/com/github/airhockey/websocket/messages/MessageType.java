package com.github.airhockey.websocket.messages;

public enum MessageType {
    PLAYER_INFO(0x01), OPPONENT_INFO(0x02), CURSOR_MOVE(0x04), GOAL_EVENT(0x08),
    START_GAME_EVENT(0x16), DYNAMIC_OBJECT_MOVE(0x32);

    private int code;

    MessageType(int code) {
        this.code = code;
    }
}
