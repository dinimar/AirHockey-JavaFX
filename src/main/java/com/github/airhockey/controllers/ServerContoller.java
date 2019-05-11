package com.github.airhockey.controllers;

import com.github.airhockey.config.ServerConfig;
import com.github.airhockey.websocket.server.GameServer;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.websocket.DeploymentException;

public class ServerContoller {
    private static GameServer gameServer;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
        gameServer = context.getBean(GameServer.class);

        try {
            // Launch server
            gameServer.initServer();
            gameServer.launchServer();

            while (true) {}

        } catch (DeploymentException ex) {
            Alert deployExAl = new Alert(Alert.AlertType.ERROR, "Some bugs during deployment");
            deployExAl.showAndWait();
        }
    }
}
