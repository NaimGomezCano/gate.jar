/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.CookieConfig
 *  com.sap.b1.gate.MyCookieConsumer
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.server.reactive.HttpHandler
 *  org.springframework.web.server.adapter.HttpWebHandlerAdapter
 *  org.springframework.web.server.session.CookieWebSessionIdResolver
 *  org.springframework.web.server.session.DefaultWebSessionManager
 *  org.springframework.web.server.session.WebSessionIdResolver
 *  org.springframework.web.server.session.WebSessionManager
 */
package com.sap.b1.gate;

import com.sap.b1.gate.MyCookieConsumer;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionIdResolver;
import org.springframework.web.server.session.WebSessionManager;

@Configuration
@ConditionalOnProperty(name={"server.ssl.enabled"}, havingValue="true")
class CookieConfig {
    CookieConfig() {
    }

    @Autowired(required=false)
    public void setCookieName(HttpHandler httpHandler) {
        if (httpHandler == null) {
            return;
        }
        if (!(httpHandler instanceof HttpWebHandlerAdapter)) {
            return;
        }
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        CookieWebSessionIdResolver sessionIdResolver = new CookieWebSessionIdResolver();
        sessionIdResolver.addCookieInitializer((Consumer)new MyCookieConsumer());
        sessionManager.setSessionIdResolver((WebSessionIdResolver)sessionIdResolver);
        ((HttpWebHandlerAdapter)httpHandler).setSessionManager((WebSessionManager)sessionManager);
    }
}

