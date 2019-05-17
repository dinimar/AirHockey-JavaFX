package com.github.airhockey.websocket.server;

import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.utils.JSONConverter;


public class ServerMessageHandler {
    private JSONConverter jsonConverter = new JSONConverter();

    protected Message createResponse(String receivedMessage) {
        Message req = parseMessage(receivedMessage);
        Message resp = new Message();

        resp.setMsgType(req.getMsgType());

//        if (req.getMsgType().equals(MessageType.OPPONENT_INFO)) {
//            properties = (LinkedTreeMap) req.getProperties().get(0);
//        }

        return resp;
    }

    private Message parseMessage(String message) {
        return jsonConverter.toMessage(message);
    }
}