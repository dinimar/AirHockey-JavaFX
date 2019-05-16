package com.github.airhockey.websocket.server;

import com.github.airhockey.game.events.GameEvent;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ServerEndpoint(value = "/air-hockey")
public class GameServer {
    private static List<Session> sessions = new ArrayList<>();
    private Server server;
    private JSONConverter jsonConverter = new JSONConverter();

    public void initServer() {
        this.server = new Server("127.0.0.1", 8080, "", null, this.getClass());
    }

    public void launchServer() throws DeploymentException {
        server.start();
        System.out.println("Server is started.");
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New player connected. Id: " + session.getId() + "\n" + "Game is started!");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("HOCKEY-SERVER: New message is received!\n" + message);
        JSONConverter jsonConverter = new JSONConverter();

        Message receivedMsg = jsonConverter.toMessage(message);
    }

    public void sendGameEvents(List<GameEvent> events) {
        Message msg = new Message();
        msg.setMsgType(MessageType.GAME_EVENT);
        for (GameEvent event: events) {
            msg.addProperty(event);
        }

        sendMessage(sessions.get(0), msg);
    }

    private void sendMessage(Session session, Message message) {
        String resMsg = jsonConverter.toJson(message);
        session.getAsyncRemote().sendText(resMsg);
        System.out.println("HOCKEY-SERVER: Message is sent to " + session.getId() + ":\n" + resMsg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameServer that = (GameServer) o;
        return Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server);
    }
}
