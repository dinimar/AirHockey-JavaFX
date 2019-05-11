package com.github.airhockey.controllers;

import com.github.airhockey.config.RootConfig;
import com.github.airhockey.config.ViewConfig;
import com.github.airhockey.entities.Player;
import com.github.airhockey.services.ViewResolver;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import com.github.airhockey.websocket.server.GameServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class GameController extends Application {
    private static GameClientEndpoint client;
    private static ViewResolver viewResolver;
    @FXML
    private TextField nicknameField;
    @FXML
    private Button startButton;


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class, ViewConfig.class);
        GameController.client = context.getBean(GameClientEndpoint.class);
        GameController.viewResolver = context.getBean(ViewResolver.class);
        launch(args);
    }

    @FXML
    protected void handleStartButtonAction(ActionEvent event) {
        // Connect client to server
        Player player = new Player(nicknameField.getCharacters().toString());
        client.connectToServer("ws://127.0.0.1:8080/air-hockey", player);
        // Close current window
        Stage signInStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        signInStage.close();

        // Load view for hockeyField
        FXMLLoader loader = new FXMLLoader(viewResolver.getView("hockeyField.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Alert loaderAlert = new Alert(Alert.AlertType.ERROR, "Cannot load view");
            loaderAlert.showAndWait();
        }
        // Init controller
        FieldController fieldCtrl = loader.getController();
        fieldCtrl.setOwnNickname(player.getNickname());

        // Show hockeyField scene in new window
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 480, 640));
        stage.setTitle("Hockey field");
        stage.show();
    }

    @Override
    public void start(Stage stage) {
        try {
            // Change default behaviout to prevent code of windows closing
            // Platform.setImplicitExit(false);

            // Init views
            viewResolver.init();
            // Show signIn scene
            Parent root = FXMLLoader.load(viewResolver.getView("signIn.fxml"));
            stage.setScene(new Scene(root, 480, 640));
            stage.setTitle("Air Hockey");
            stage.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "FXML file was not found");
            alert.showAndWait();
        }
    }
}
