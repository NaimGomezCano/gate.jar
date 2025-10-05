/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.server.WebSocketHandshakeInterceptor
 *  org.springframework.http.server.ServerHttpRequest
 *  org.springframework.http.server.ServerHttpResponse
 *  org.springframework.web.socket.WebSocketHandler
 *  org.springframework.web.socket.server.HandshakeInterceptor
 */
package com.sap.b1.notification.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WebSocketHandshakeInterceptor
implements HandshakeInterceptor {
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        List protocol = request.getHeaders().get((Object)"Sec-WebSocket-Protocol");
        if (protocol != null && protocol.size() != 0) {
            String regex = "[a-zA-Z\\d.+-]+";
            Pattern compiledPattern = Pattern.compile(regex);
            ArrayList<String> protocolMatched = new ArrayList<String>();
            for (String sub : protocol) {
                if (!compiledPattern.matcher(sub).matches()) continue;
                protocolMatched.add(sub);
            }
            if (protocolMatched.size() != 0) {
                response.getHeaders().put("Sec-WebSocket-Protocol", protocolMatched);
            }
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}

