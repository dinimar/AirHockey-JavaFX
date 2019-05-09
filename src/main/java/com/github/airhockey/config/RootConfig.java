package com.github.airhockey.config;

import com.github.airhockey.controllers.ScreenController;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import com.github.airhockey.websocket.server.GameServer;
import com.github.airhockey.websocket.utils.JSONUtils;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public GameServer gameServer() {
        return new GameServer();
    }

    @Bean
    public GameClientEndpoint gameClientEndpoint() {
        return new GameClientEndpoint();
    }

    @Bean
    public JSONUtils jsonUtils() {
        return new JSONUtils();
    }

    @Bean
    public ScreenController sceneController() {
        Scene scene = new Scene(new Pane(), 480, 640);
        return new ScreenController(scene);
    }
}
