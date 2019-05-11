package com.github.airhockey.config;

import com.github.airhockey.websocket.server.GameServer;
import com.github.airhockey.websocket.server.ServerMessageHandler;
import com.github.airhockey.websocket.utils.JSONConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Bean
    public GameServer gameServer() {
        return new GameServer();
    }

//    @Bean
//    public ServerMessageHandler serverMessageHandler(){
//        return new ServerMessageHandler();
//    }
//
//    @Bean
//    public JSONConverter jsonConverter() {
//        return new JSONConverter();
//    }
}
