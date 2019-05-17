package com.github.airhockey.websocket.client;


import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.events.CursorMove;
import com.github.airhockey.game.events.GameEvent;
import com.github.airhockey.websocket.exceptions.OpponentNotConnectedException;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.utils.JSONConverter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ClientEndpoint
public class GameClientEndpoint {
    private Session userSession;
    private WebSocketContainer container;
    @Autowired
    private JSONConverter jsonConverter;
    @Autowired
    private ClientMessageHandler messageHandler;
    private GameProcess gameProcess;

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
            case GAME_EVENT:
                List<Object> props = receivedMsg.getProperties();
                Gson gson = new Gson();

                for (Object obj: props) {
                    LinkedTreeMap map = (LinkedTreeMap) obj;
                    String json = jsonConverter.toJson(map);
                    gameProcess.addEvent(gson.fromJson(json, CursorMove.class));
                }
        }
    }

    private void sendMessage(Message message) {
        String resMsg = jsonConverter.toJson(message);
        this.userSession.getAsyncRemote().sendText(resMsg);
    }

    public void setGameProcess(GameProcess gameProcess) {
        this.gameProcess = gameProcess;
    }
}
