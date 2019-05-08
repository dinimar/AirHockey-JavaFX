package com.github.airhockey.websocket.client;


import com.github.airhockey.entities.Player;
import com.google.gson.Gson;
import javafx.scene.control.Alert;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class GameClientEndpoint {
    private Session userSession;
    private WebSocketContainer container;


    public GameClientEndpoint() {
        try {
            this.container = ContainerProvider.getWebSocketContainer();
        } catch (Exception e) {
        }
    }

    public void connectToServer(String endpointURI, Player player) {
        try {
            container.connectToServer(this, new URI(endpointURI));
            Gson gson = new Gson();
            this.sendMessage(gson.toJson(player));
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
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
}
