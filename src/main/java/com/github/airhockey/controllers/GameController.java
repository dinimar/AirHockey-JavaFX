package com.github.airhockey.controllers;

import com.github.airhockey.config.RootConfig;
import com.github.airhockey.entities.Player;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import com.github.airhockey.websocket.server.GameServer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.websocket.DeploymentException;

public class GameController extends Application {
    private static ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
    private static GameClientEndpoint client = context.getBean(GameClientEndpoint.class);
    private static ScreenController scController = context.getBean(ScreenController.class);
    @FXML
    private Button startButton;
    @FXML
    private TextField nicknameField;

    public static void main(String[] args) {
        GameServer gameServer = context.getBean(GameServer.class);

        try {
            // Launch server
            gameServer.initServer();
            gameServer.launchServer();

            // Launch game window
            launch(args);
        } catch (DeploymentException ex) {
            Alert deployExAl = new Alert(Alert.AlertType.ERROR, "Some bugs during deployment");
            deployExAl.showAndWait();
        }
    }

    @FXML
    protected void handleStartButtonAction(ActionEvent event) {
        // Connect client to server
        Player player = new Player(nicknameField.getCharacters().toString());
        client.connectToServer("ws://127.0.0.1:8080/air-hockey", player);
        scController.activate("hockeyField.fxml");
    }

    @Override
    public void start(Stage stage) {
        try {
            scController.init();
            scController.activate("signIn.fxml");

            stage.setScene(scController.getMain());
            stage.setTitle("Air Hockey");
            stage.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "FXML file was not found");
            alert.showAndWait();
        }
    }

}
