package com.github.airhockey.websocket.client;


import com.github.airhockey.config.RootConfig;
import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONUtils;
import javafx.scene.control.Alert;
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

    public void sendMessage(Message message) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        JSONUtils jsonUtils = context.getBean(JSONUtils.class);

        String resMsg = jsonUtils.toJson(message);
        this.userSession.getAsyncRemote().sendText(resMsg);
    }
}
