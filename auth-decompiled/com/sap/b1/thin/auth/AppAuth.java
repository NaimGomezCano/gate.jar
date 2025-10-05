/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.infra.share.web.Keys
 *  com.sap.b1.thin.auth.AppAuth
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.context.annotation.ComponentScan
 *  org.springframework.context.annotation.Import
 */
package com.sap.b1.thin.auth;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.infra.share.web.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(value={"com.sap.b1"})
@Import(value={Keys.class})
public class AppAuth {
    public static void main(String[] args) {
        Bootstrap.getInstance();
        SpringApplication.run(AppAuth.class, (String[])args);
    }
}

