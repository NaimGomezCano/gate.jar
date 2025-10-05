/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.context.annotation.ComponentScan
 *  org.springframework.data.jpa.repository.config.EnableJpaRepositories
 *  org.springframework.scheduling.annotation.EnableScheduling
 *  webclient.noti
 */
package webclient;

import com.sap.b1.boot.Bootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(value={"com.sap.b1"})
@EnableScheduling
@EnableJpaRepositories(value={"gen.dao"})
public class noti {
    public static void main(String[] args) {
        Bootstrap.getInstance();
        SpringApplication.run(noti.class, (String[])args);
    }
}

