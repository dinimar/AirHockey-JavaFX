package com.github.airhockey.controllers;

import com.github.airhockey.config.RootConfig;
import com.github.airhockey.game.GameProccess;
import com.github.airhockey.game.render.javafx.GroupRenderProvider;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.Thread.sleep;

@Component
public class GameController extends Application {

    private final String groupId = "group";

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

//        VBox root = new VBox();
        Parent root = new VBox();
        try {
            root = FXMLLoader.load(getClass().getResource("../../../../gameLayout.fxml"));
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "FXML file was not found");
            alert.showAndWait();
            return;
        }
        Scene scene = new Scene(root, 800, 500);

        Group group = null;
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node.getId() != null && node.getId().equals(this.groupId)) {
                group = (Group) node;
            }
        }
        if (group == null) {
            // TODO обработка ошибок
            System.err.println("Group not found");
            return;
        }

        GameProccess gameProccess = new GameProccess();
        GroupRenderProvider provider = new GroupRenderProvider(group, gameProccess);
//        provider.render(gameProccess.getRenderableObject());
        new AnimationTimer(){

            @Override
            public void handle(long now) {
                provider.render(gameProccess.getRenderableObject());
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
