package com.github.airhockey.controllers;

import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FieldController {
    private static GameClientEndpoint client;
    private Player player;
    @FXML
    private Label ownNickname;
    @FXML
    private Label opponentNickname;

    public static void setClient(GameClientEndpoint client) {
        FieldController.client = client;
    }

    public void getOpponentNickname() {
        if (client.getOpponentNickname() != null) {
            setOpponentNickname(client.getOpponentNickname());
        }
    }

    public void setOwnNickname(String nickname) {
        ownNickname.setText(nickname);
    }

    public void setOpponentNickname(String nickname) {
        opponentNickname.setText(nickname);
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }
}
