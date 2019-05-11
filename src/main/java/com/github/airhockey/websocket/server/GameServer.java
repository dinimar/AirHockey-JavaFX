package com.github.airhockey.websocket.server;

import com.github.airhockey.config.MessageConfig;
import com.github.airhockey.entities.Player;
import org.glassfish.tyrus.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
    private Server server;
    private List<Player> playerList = new ArrayList<>();
    @Autowired
    public ServerMessageHandler messageHandler;

    public void initServer() {
        this.server = new Server("127.0.0.1", 8080, "", null, this.getClass());
    }

    public void launchServer() throws DeploymentException {
        server.start();
    }

    @OnOpen
    public void onOpen(Session session) {
        Player connPlayer = new Player();
        connPlayer.setSession(session);
        playerList.add(connPlayer);
        System.out.println("New player connected. Id: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
//        messageHandler.receiveMessage(message);
        System.out.println("HOCKEY-SERVER: New message is received!\n"+message);

//        Message clientResp = messageHandler.createResponse();
//        if (clientResp.getMsgType().equals(MessageType.OPPONENT_INFO)) {
//            for(Player player: playerList) {
//                if (!player.getSession().equals(session)) {
//                    clientResp.addProperty(player);
//                    session.getAsyncRemote().sendObject(clientResp);
//                }
//            }
//        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
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
