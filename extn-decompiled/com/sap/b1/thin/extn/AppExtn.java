/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.thin.extn.AppExtn
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
 *  org.springframework.context.annotation.ComponentScan
 */
package com.sap.b1.thin.extn;

import com.sap.b1.boot.Bootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={UserDetailsServiceAutoConfiguration.class})
@ComponentScan(value={"com.sap.b1"})
public class AppExtn {
    public static void main(String[] args) {
        Bootstrap.getInstance();
        SpringApplication.run(AppExtn.class, (String[])args);
    }
}

