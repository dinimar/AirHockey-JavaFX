package com.github.airhockey.websocket.client;

import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientMessageHandler {
    @Autowired
    private JSONConverter jsonConverter;

//    protected Object createResponse() {
//        Player opponent = null;
//
//        if (lastReceivedMsg.getMsgType().equals(MessageType.OPPONENT_INFO)) {
//            opponent = (Player) lastReceivedMsg.getProperties().get(0);
//        }
//
//        return opponent;
//    }

    protected Message createResponse(String receivedMessage) {
        Message req = parseMessage(receivedMessage);
        Message resp = new Message();

        resp.setMsgType(req.getMsgType());

//        if (req.getMsgType().equals(MessageType.OPPONENT_INFO)) {
//            properties = (LinkedTreeMap) req.getProperties().get(0);
//        }

        return resp;
    }

//    protected void receiveMessage(String message) {
//        lastReceivedMsg = jsonConverter.toMessage(message);
//    }
    protected Message parseMessage(String message) {
        return jsonConverter.toMessage(message);
    }
}
