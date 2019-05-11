package com.github.airhockey.websocket.server;

import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.springframework.beans.factory.annotation.Autowired;


public class ServerMessageHandler {
    @Autowired
    private JSONConverter jsonConverter;
    private Message lastReceivedMsg;

    protected Message createResponse() {
        Message msg = new Message();
        msg.setMsgType(lastReceivedMsg.getMsgType());

//        if (lastReceivedMsg.getMsgType().equals(MessageType.OPPONENT_INFO)) {
//            opponent = (Player) lastReceivedMsg.getProperties().get(0);
//        }

        return msg;
    }

    protected void receiveMessage(String message) {
        lastReceivedMsg = jsonConverter.toMessage(message);
    }
}