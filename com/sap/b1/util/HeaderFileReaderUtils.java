/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.headers.config.HeaderFilterPropsConfig
 *  com.sap.b1.util.HeaderFileReaderUtils
 *  jakarta.annotation.PostConstruct
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.util;

import com.sap.b1.headers.config.HeaderFilterPropsConfig;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class HeaderFileReaderUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFileReaderUtils.class);
    public static final Map<String, Set<String>> rspHeaderKeySetMap = new HashMap();
    public static final Map<String, Set<String>> reqHeaderKeySetMap = new HashMap();
    private String pathPrefix;
    @Autowired
    private HeaderFilterPropsConfig headerFilterPropsConfig;

    @PostConstruct
    public void init() throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            this.pathPrefix = System.getProperty("user.dir") + File.separator + this.headerFilterPropsConfig.getPath_prefix();
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            this.pathPrefix = System.getProperty("user.dir") + File.separator + this.headerFilterPropsConfig.getPath_prefix();
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + osName);
        }
        LOGGER.info("pathPrefix: {}", (Object)this.pathPrefix);
        this.loadHeaderKeys();
    }

    private void addHeaderFileToMap(String fileName, Map<String, Set<String>> headerKeySetMap, String serviceKey) throws IOException {
        if (fileName != null) {
            Set headerKeySet = HeaderFileReaderUtils.readLinesAsSet((String)(this.pathPrefix + File.separator + fileName));
            headerKeySetMap.put(serviceKey, headerKeySet);
            LOGGER.info("Add header file: {}", (Object)(this.pathPrefix + File.separator + fileName));
        }
    }

    public static Set<String> readLinesAsSet(String filePath) throws IOException {
        Path path = Paths.get(filePath, new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return Collections.emptySet();
        }
        TreeSet<String> headerSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        headerSet.addAll(Files.readAllLines(path));
        return headerSet;
    }

    private void loadHeaderKeys() throws IOException {
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getAuth_req_file_name(), reqHeaderKeySetMap, "auth");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getAuth_rsp_file_name(), rspHeaderKeySetMap, "auth");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getNoti_req_file_name(), reqHeaderKeySetMap, "noti");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getNoti_rsp_file_name(), rspHeaderKeySetMap, "noti");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getAnly_req_file_name(), reqHeaderKeySetMap, "anly");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getAnly_rsp_file_name(), rspHeaderKeySetMap, "anly");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getB1ah_req_file_name(), reqHeaderKeySetMap, "b1ah");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getB1ah_rsp_file_name(), rspHeaderKeySetMap, "b1ah");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getB1s_req_file_name(), reqHeaderKeySetMap, "b1s");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getB1s_rsp_file_name(), rspHeaderKeySetMap, "b1s");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getCrpt_req_file_name(), reqHeaderKeySetMap, "crpt");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getCrpt_rsp_file_name(), rspHeaderKeySetMap, "crpt");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getExtn_req_file_name(), reqHeaderKeySetMap, "extn");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getExtn_rsp_file_name(), rspHeaderKeySetMap, "extn");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getSvcl_req_file_name(), reqHeaderKeySetMap, "svcl");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getSvcl_rsp_file_name(), rspHeaderKeySetMap, "svcl");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getTcli_req_file_name(), reqHeaderKeySetMap, "tcli");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getTcli_rsp_file_name(), rspHeaderKeySetMap, "tcli");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getTcli_ui_req_file_name(), reqHeaderKeySetMap, "tcli-ui");
        this.addHeaderFileToMap(this.headerFilterPropsConfig.getTcli_ui_rsp_file_name(), rspHeaderKeySetMap, "tcli-ui");
    }
}

