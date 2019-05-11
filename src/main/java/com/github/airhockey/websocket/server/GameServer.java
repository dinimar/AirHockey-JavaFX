package com.github.airhockey.websocket.server;

import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import org.glassfish.tyrus.server.Server;
import org.springframework.beans.factory.annotation.Autowired;

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
    private static List<Player> playerList = new ArrayList<>();
    private ServerMessageHandler messageHandler = new ServerMessageHandler();

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
        System.out.println("HOCKEY-SERVER: New message is received!\n"+message);
        JSONConverter jsonConverter = new JSONConverter();

        Message receivedMsg = jsonConverter.toMessage(message);

        if (receivedMsg.getMsgType().equals(MessageType.PLAYER_INFO)) {
            for(Player player: playerList) {
                if (player.getSession().equals(session)) {
                    LinkedTreeMap playerInfo = (LinkedTreeMap) receivedMsg.getProperties().get(0);
                    player.setNickname((String) playerInfo.get("nickname"));
                }
            }
        } else if (receivedMsg.getMsgType().equals(MessageType.OPPONENT_INFO)) {
            Message clientResp = messageHandler.createResponse(message);

            for(Player player: playerList) {
                if (!player.getSession().equals(session)) {
                    Player opponent = new Player(player.getNickname());
                    clientResp.addProperty(opponent);
                    String resMsg = jsonConverter.toJson(clientResp);

                    session.getAsyncRemote().sendText(resMsg);
                    System.out.println("HOCKEY-SERVER: Message sent:\n"+resMsg);
                }
            }
        }
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
