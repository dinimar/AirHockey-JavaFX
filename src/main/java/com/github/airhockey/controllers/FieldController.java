package com.github.airhockey.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FieldController {
    @FXML
    private Label ownNickname;

    protected void setOwnNickname(String nickname) {
        ownNickname.setText(nickname);
    }
}
