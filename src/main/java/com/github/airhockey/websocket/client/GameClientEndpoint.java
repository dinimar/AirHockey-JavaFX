package com.github.airhockey.websocket.client;

import com.github.airhockey.websocket.utils.MessageHandler;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class GameClientEndpoint {
    private Session userSession;
    private MessageHandler messageHandler = new MessageHandler();

    public GameClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public void onMessage(String message) { }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
}
