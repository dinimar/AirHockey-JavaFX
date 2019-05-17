package com.github.airhockey.websocket.client;


import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.events.*;
import com.github.airhockey.websocket.exceptions.OpponentNotConnectedException;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
        Gson gson = new Gson();

        Message receivedMsg = messageHandler.parseMessage(message);
        System.out.println("New message received:" + jsonConverter.toJson(message));

        List<Object> props = receivedMsg.getProperties();

        switch (receivedMsg.getMsgType()) {
            case CURSOR_MOVE:
                for (Object obj : props) {
                    LinkedTreeMap map = (LinkedTreeMap) obj;
                    String json = jsonConverter.toJson(map);

                    gameProcess.addEvent(gson.fromJson(json, CursorMove.class));
                }
            case GOAL_EVENT:
                for (Object obj : props) {
                    LinkedTreeMap map = (LinkedTreeMap) obj;
                    String json = jsonConverter.toJson(map);

                    gameProcess.addEvent(gson.fromJson(json, GoalEvent.class));
                }
            case START_GAME_EVENT:
                for (Object obj : props) {
                    LinkedTreeMap map = (LinkedTreeMap) obj;
                    String json = jsonConverter.toJson(map);

                    gameProcess.addEvent(gson.fromJson(json, StartGameEvent.class));
                }
            case DYNAMIC_OBJECT_MOVE:
                for (Object obj : props) {
                    LinkedTreeMap map = (LinkedTreeMap) obj;
                    String json = jsonConverter.toJson(map);

                    gameProcess.addEvent(gson.fromJson(json, DynamicObjectMove.class));
                }
        }
    }

    public void sendGameEvents(List<GameEvent> events) {
        Message msg = new Message();

        for (GameEvent event : events) {
            if (event.getClass().equals(CursorMove.class)) {
                msg.setMsgType(MessageType.CURSOR_MOVE);
            } else if (event.getClass().equals(GoalEvent.class)) {
                msg.setMsgType(MessageType.GOAL_EVENT);
            } else if (event.getClass().equals(StartGameEvent.class)) {
                msg.setMsgType(MessageType.START_GAME_EVENT);
            } else if (event.getClass().equals(DynamicObjectMove.class)) {
                msg.setMsgType(MessageType.DYNAMIC_OBJECT_MOVE);
            }

            msg.addProperty(event);
        }

        sendMessage(msg);
    }

    private void sendMessage(Message message) {
        String resMsg = jsonConverter.toJson(message);
        this.userSession.getAsyncRemote().sendText(resMsg);
    }

    public void setGameProcess(GameProcess gameProcess) {
        this.gameProcess = gameProcess;
    }
}
