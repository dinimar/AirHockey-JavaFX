package com.github.airhockey.websocket.server;

import com.github.airhockey.config.MessageConfig;
import com.github.airhockey.config.RootConfig;
import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ServerMessageHandler {
    private JSONConverter jsonConverter;
    private Message lastReceivedMsg;

    public ServerMessageHandler() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(MessageConfig.class);
//        jsonConverter = context.getBean(JSONConverter.class);
    }

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