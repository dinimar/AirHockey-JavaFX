package com.github.airhockey.controllers;

import com.github.airhockey.config.RootConfig;
import com.github.airhockey.game.GameProcess;
import com.github.airhockey.game.events.javafx.GroupEventProcessingProvider;
import com.github.airhockey.game.render.javafx.GroupRenderProvider;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GameController extends Application {
    private static GameClientEndpoint client;
    private final String groupId = "group";
    private final String labelId = "label";

    protected static <T> T getNodeById(ObservableList<Node> nodes, String id, Class<T> nodeClass) {
        if (nodes == null) {
            return null;
        }
        for (Node node : nodes) {
            if (node != null && node.getId() != null && node.getClass().equals(nodeClass) && node.getId().equals(id)) {
                return nodeClass.cast(node);
            }
            if (Parent.class.isAssignableFrom(node.getClass())) {
                T finded = getNodeById(((Parent) node).getChildrenUnmodifiable(), id, nodeClass);
                if (finded != null) {
                    return finded;
                }
            }
        }
        return null;
    }

//    private void connectToGame() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
//        client = context.getBean(GameClientEndpoint.class);
//    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

        Parent root = new VBox();
        try {
            root = FXMLLoader.load(getClass().getResource("../../../../gameLayout.fxml"));
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "FXML file was not found");
            alert.showAndWait();
            return;
        }
        Scene scene = new Scene(root, 800, 500);

        Group group = GameController.getNodeById(root.getChildrenUnmodifiable(), groupId, Group.class);
        if (group == null) {
            // TODO обработка ошибок
            System.err.println("Group not found");
            return;
        }
        Label label = GameController.getNodeById(root.getChildrenUnmodifiable(), labelId, Label.class);
        if (label == null) {
            // TODO обработка ошибок
            System.err.println("Score label not found");
            return;
        }

        GameProcess gameProcess = new GameProcess();
        GroupRenderProvider provider = new GroupRenderProvider(group, gameProcess, label);
        GroupEventProcessingProvider processingProvider = new GroupEventProcessingProvider(group, gameProcess);
        processingProvider.startEventProcessing();
//        provider.render(gameProcess.getRenderableObject());
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                gameProcess.compute();
                provider.render(gameProcess.getRenderableObjects());
            }
        }.start();

        stage.setMinHeight(700);
        stage.setMinWidth(900);
        stage.setTitle("Air hockey");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();
    }
}
