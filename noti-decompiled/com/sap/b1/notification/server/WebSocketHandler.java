/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.cache.CacheObject
 *  com.sap.b1.notification.server.WebSocketHandler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.socket.CloseStatus
 *  org.springframework.web.socket.TextMessage
 *  org.springframework.web.socket.WebSocketMessage
 *  org.springframework.web.socket.WebSocketSession
 *  org.springframework.web.socket.handler.AbstractWebSocketHandler
 */
package com.sap.b1.notification.server;

import com.sap.b1.notification.cache.CacheObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class WebSocketHandler
extends AbstractWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    @Autowired
    CacheObject cacheObject;

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage((WebSocketMessage)message);
    }

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.cacheObject.addWebSocket(session);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.cacheObject.removeWebSocket(session);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error(exception.getMessage(), exception);
        if (!session.isOpen()) {
            this.cacheObject.removeWebSocket(session);
        }
    }
}

