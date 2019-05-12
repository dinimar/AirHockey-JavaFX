package com.github.airhockey.websocket.client;


import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import com.google.gson.internal.LinkedTreeMap;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

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
    private String opponentNickname;

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
        Message receivedMsg = messageHandler.parseMessage(message);

        if (receivedMsg.getMsgType().equals(MessageType.OPPONENT_INFO)) {
            LinkedTreeMap opponent = (LinkedTreeMap) receivedMsg.getProperties().get(0);
            opponentNickname = (String) opponent.get("nickname");
        }
    }

    public String getOpponentNickname() {
        return opponentNickname;
    }

    public void sendMessage(Message message) {
        String resMsg = jsonConverter.toJson(message);
        this.userSession.getAsyncRemote().sendText(resMsg);
    }
}
