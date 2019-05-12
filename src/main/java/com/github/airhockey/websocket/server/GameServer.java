package com.github.airhockey.websocket.server;

import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.messages.Message;
import com.github.airhockey.websocket.messages.MessageType;
import com.github.airhockey.websocket.utils.JSONConverter;
import com.google.gson.internal.LinkedTreeMap;
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
    private static List<Player> playerList = new ArrayList<>();
    private Server server;
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
        System.out.println("HOCKEY-SERVER: New message is received!\n" + message);
        JSONConverter jsonConverter = new JSONConverter();

        Message receivedMsg = jsonConverter.toMessage(message);

        if (receivedMsg.getMsgType().equals(MessageType.PLAYER_INFO)) {
            for (Player player : playerList) {
                if (player.getSession().equals(session)) {
                    LinkedTreeMap playerInfo = (LinkedTreeMap) receivedMsg.getProperties().get(0);
                    player.setNickname((String) playerInfo.get("nickname"));
                }
            }

            if (checkPullState()) {
                sendOpponentNickname();
            }
        }
    }

    private boolean checkPullState() {
        return playerList.size() == 2 && playerList.get(1).getNickname() != null;
    }

    private void sendOpponentNickname() {
        Player player1 = null;
        Player player2 = null;

        JSONConverter jsonConverter = new JSONConverter();
        player1 = playerList.get(0);
        player2 = playerList.get(1);

        Player opponentFor1 = new Player(player2.getNickname());
        Player opponentFor2 = new Player(player1.getNickname());

        Message msgToPlayer1 = new Message();
        msgToPlayer1.setMsgType(MessageType.OPPONENT_INFO);
        msgToPlayer1.addProperty(opponentFor1);

        Message msgToPlayer2 = new Message();
        msgToPlayer2.setMsgType(MessageType.OPPONENT_INFO);
        msgToPlayer2.addProperty(opponentFor2);

        String resMsg1 = jsonConverter.toJson(msgToPlayer1);
        String resMsg2 = jsonConverter.toJson(msgToPlayer2);

        sendMessage(player1.getSession(), resMsg1);
        sendMessage(player2.getSession(), resMsg2);
    }

    private void sendMessage(Session session, String msg) {
        session.getAsyncRemote().sendText(msg);
        System.out.println("HOCKEY-SERVER: Message is sent to " + session.getId() + ":\n" + msg);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        GameServer.playerList = playerList;
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
