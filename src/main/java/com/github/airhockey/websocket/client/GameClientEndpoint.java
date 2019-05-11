package com.github.airhockey.websocket.client;


import com.github.airhockey.entities.Player;
//import com.github.airhockey.websocket.exceptions.OpponentNotConnectedException;
import com.github.airhockey.websocket.exceptions.OpponentNotConnectedException;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import com.google.gson.internal.LinkedTreeMap;
import com.sun.xml.internal.ws.handler.ClientMessageHandlerTube;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class GameClientEndpoint {
    private Session userSession;
    private WebSocketContainer container;
    @Autowired
    private JSONConverter jsonConverter;
    @Autowired
    private ClientMessageHandler messageHandler;
    private String lastReceivedMessage;

    public GameClientEndpoint() {
        try {
            this.container = ContainerProvider.getWebSocketContainer();
        } catch (Exception e) {
        }
    }

    public void connectToServer(String endpointURI, Player player) {
        try {
            container.connectToServer(this, new URI(endpointURI));

            // Create new message
            Message msg = new Message();
            msg.setMsgType(MessageType.PLAYER_INFO);
//            msg.setSession(userSession);
            msg.addProperty(player);

            this.sendMessage(msg);

        } catch (URISyntaxException ex) {
            Alert warn = new Alert(Alert.AlertType.WARNING, "Wrong server address");
            warn.showAndWait();
        } catch (DeploymentException ex) {
            Alert warn = new Alert(Alert.AlertType.WARNING, "Cannot connect to server");
            warn.showAndWait();
        } catch (IOException ex) {
        }
    }

    public String getOpponentInfo(Player fromPlayer) throws OpponentNotConnectedException {
        Message msg = new Message();
        msg.setMsgType(MessageType.OPPONENT_INFO);
        msg.addProperty(fromPlayer);

        this.sendMessage(msg);

        String oppNick = null;

        if (lastReceivedMessage != null) {
            LinkedTreeMap msgProps = (LinkedTreeMap)messageHandler.parseMessage(lastReceivedMessage).getProperties().get(0);
            oppNick = (String) msgProps.get("nickname");
        }

        return oppNick;
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        lastReceivedMessage = message;
//        messageHandler.receiveMessage(message);
    }

    public void sendMessage(Message message) {
        String resMsg = jsonConverter.toJson(message);
        this.userSession.getAsyncRemote().sendText(resMsg);
    }
}
