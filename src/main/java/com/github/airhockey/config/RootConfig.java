package com.github.airhockey.config;

import com.github.airhockey.services.ViewResolver;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import com.github.airhockey.websocket.server.GameServer;
import com.github.airhockey.websocket.server.ServerMessageHandler;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public GameClientEndpoint gameClientEndpoint() {
        return new GameClientEndpoint();
    }

    @Bean
    public JSONConverter jsonConverter() {
        return new JSONConverter();
    }

    @Bean
    public ViewResolver viewResolver() {
        return new ViewResolver();
    }
}
