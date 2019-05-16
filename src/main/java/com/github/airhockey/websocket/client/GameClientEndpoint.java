package com.github.airhockey.websocket.client;


import com.github.airhockey.websocket.exceptions.OpponentNotConnectedException;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.data.Position;
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
    private Position puckPos;

    public GameClientEndpoint() {
        try {
            this.container = ContainerProvider.getWebSocketContainer();
        } catch (Exception e) {
        }
    }

    public void connectToServer(String endpointURI) throws OpponentNotConnectedException {
        try {
            container.connectToServer(this, new URI(endpointURI));
            System.out.println("Client is initialized.");
        } catch (URISyntaxException ex) {
            Alert warn = new Alert(Alert.AlertType.WARNING, "Wrong server address");
            warn.showAndWait();
        } catch (DeploymentException ex) {
            throw new OpponentNotConnectedException();
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
        System.out.println("New message received:" + jsonConverter.toJson(message));

        switch (receivedMsg.getMsgType()) {
            case PUCK_POS:
                LinkedTreeMap position = (LinkedTreeMap) receivedMsg.getProperties().get(0);
                double x = (double) position.get("x");
                double y = (double) position.get("y");

                puckPos = new Position((float) x, (float) y);
        }
    }

    private void sendMessage(Message message) {
        String resMsg = jsonConverter.toJson(message);
        this.userSession.getAsyncRemote().sendText(resMsg);
    }
}
