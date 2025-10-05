/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.gate.AppGate
 *  com.sap.encryption.util.EncryptionTool
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.context.annotation.ComponentScan
 */
package com.sap.b1.gate;

import com.sap.b1.boot.Bootstrap;
import com.sap.encryption.util.EncryptionTool;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * Exception performing whole class analysis ignored.
 */
@SpringBootApplication
@ComponentScan(value={"com.sap.b1"})
public class AppGate {
    private static final Logger logger = LoggerFactory.getLogger(AppGate.class);

    public static void main(String[] args) {
        String prop = "reactor.netty.http.server.accessLogEnabled";
        if (System.getProperty(prop) == null) {
            System.setProperty(prop, "true");
        }
        String passwordParam = "--server.ssl.key-store-encrypted-password";
        String keyStorePassword = null;
        for (String arg : args) {
            int indexParam = arg.indexOf(passwordParam);
            if (indexParam < 0) continue;
            keyStorePassword = arg.substring(passwordParam.length() + 1);
            break;
        }
        if (StringUtils.isBlank(keyStorePassword)) {
            passwordParam = "server_ssl_key_store_encrypted_password";
            keyStorePassword = System.getenv(passwordParam);
        }
        Bootstrap.getInstance();
        if (keyStorePassword != null) {
            AppGate.tidyEnvironment();
            EncryptionTool encryptionTool = new EncryptionTool("webclients");
            String password = encryptionTool.decryption(keyStorePassword);
            System.setProperty("javax.net.ssl.keyStorePassword", password);
        }
        SpringApplication.run(AppGate.class, (String[])args);
    }

    private static void tidyEnvironment() {
        try {
            AppGate.cleanEnvironment();
        }
        catch (Exception e) {
            logger.error("tidyEnvironment", (Throwable)e);
        }
    }

    private static void cleanEnvironment() {
        String dir = System.getProperty("java.io.tmpdir");
        String suffixWin = "sapcrypto";
        String suffixLinux = "libsapcrypto";
        String prefix = "jni";
        File directory = new File(dir);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String fileName;
            if (!file.isFile() || !(fileName = file.getName()).startsWith("jni") || !fileName.contains("libsapcrypto") && !fileName.contains("sapcrypto")) continue;
            boolean bl = file.delete();
        }
    }
}

