package com.github.airhockey.config;

import com.github.airhockey.websocket.server.GameServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public GameServer gameServer() {
        return new GameServer();
    }
}
