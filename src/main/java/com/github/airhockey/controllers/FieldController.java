package com.github.airhockey.controllers;

import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import com.github.airhockey.websocket.exceptions.OpponentNotConnectedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class FieldController {
    private GameClientEndpoint client;
    private Player player;
    @FXML
    private Label ownNickname;
    @FXML
    private Label opponentNickname;
    @FXML
    protected void getOpponentNickname(ActionEvent event) {
        try {
            setOpponentNickname(client.getOpponentInfo(player));
        } catch (OpponentNotConnectedException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Your opponent is not connected yet.");
            alert.showAndWait();
        }
    }

    protected void setOwnNickname(String nickname) {
        ownNickname.setText(nickname);
    }

    protected void setOpponentNickname(String nickname) {
        opponentNickname.setText(nickname);
    }

    protected void setClient(GameClientEndpoint client) {
        this.client = client;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }
}
