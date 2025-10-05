/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.server.MyWebSocketConfigurer
 *  com.sap.b1.notification.server.WebSocketHandler
 *  com.sap.b1.notification.server.WebSocketHandshakeInterceptor
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
 *  org.springframework.web.socket.WebSocketHandler
 *  org.springframework.web.socket.config.annotation.EnableWebSocket
 *  org.springframework.web.socket.config.annotation.WebSocketConfigurer
 *  org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
 *  org.springframework.web.socket.server.HandshakeInterceptor
 */
package com.sap.b1.notification.server;

import com.sap.b1.notification.server.WebSocketHandler;
import com.sap.b1.notification.server.WebSocketHandshakeInterceptor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class MyWebSocketConfigurer
implements WebSocketConfigurer {
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }

    @Bean
    public TaskScheduler getTaskScheduler() {
        return new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler((org.springframework.web.socket.WebSocketHandler)this.webSocketHandler(), new String[]{"/notification.svc/websocket"}).addInterceptors(new HandshakeInterceptor[]{new WebSocketHandshakeInterceptor()}).setAllowedOrigins(new String[]{"*"});
    }
}

