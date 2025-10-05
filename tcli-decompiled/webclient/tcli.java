/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.config.FeignConfigObserver
 *  com.sap.b1.infra.share.feign.FeignConfiguration
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
 *  org.springframework.context.annotation.ComponentScan
 *  org.springframework.context.annotation.Import
 *  webclient.tcli
 */
package webclient;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.config.FeignConfigObserver;
import com.sap.b1.infra.share.feign.FeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude={UserDetailsServiceAutoConfiguration.class})
@ComponentScan(value={"com.sap.b1.tcli.config", "com.sap.b1"})
@Import(value={FeignConfiguration.class, FeignConfigObserver.class})
public class tcli {
    public static void main(String[] args) {
        Bootstrap.getInstance();
        SpringApplication.run(tcli.class, (String[])args);
    }
}

