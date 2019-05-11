package com.github.airhockey.config;

import com.github.airhockey.services.ViewResolver;
import com.github.airhockey.websocket.client.ClientMessageHandler;
import com.github.airhockey.websocket.client.GameClientEndpoint;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public GameClientEndpoint gameClientEndpoint() {
        return new GameClientEndpoint();
    }

    @Bean
    public ClientMessageHandler clientMessageHandler() {
        return new ClientMessageHandler();
    }

    @Bean
    public ViewResolver viewResolver() {
        return new ViewResolver();
    }

    @Bean
    public JSONConverter jsonConverter() {
        return new JSONConverter();
    }
}
