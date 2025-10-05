/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.i18n.I18nResourceLoader
 *  com.sap.b1.tcli.resource.domain.PropFiles
 *  com.sap.b1.tcli.resource.domain.ZipFiles
 *  org.apache.commons.io.IOUtils
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.gate.i18n;

import com.sap.b1.tcli.resource.domain.PropFiles;
import com.sap.b1.tcli.resource.domain.ZipFiles;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class I18nResourceLoader {
    ZipFiles zipFiles = new ZipFiles();

    public HashMap<String, String> getLang(String langCode) throws Exception {
        PropFiles rt = (PropFiles)this.zipFiles.get((Object)langCode);
        if (rt == null) {
            rt = this.load(langCode);
            this.zipFiles.put((Object)langCode, (Object)rt);
        }
        return rt;
    }

    private PropFiles load(String langCode) throws IOException {
        PropFiles rt = new PropFiles();
        ZipFile zipFile = new ZipFile("./i18n/" + langCode + ".zip");
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = "/" + entry.getName();
            if (!name.startsWith("/webx/properties/")) continue;
            InputStream stream = zipFile.getInputStream(entry);
            String result = IOUtils.toString((InputStream)stream, (Charset)StandardCharsets.UTF_8);
            String key = "/webx/" + name.substring("/webx/properties/".length());
            rt.put((Object)key, (Object)result);
            stream.close();
        }
        zipFile.close();
        return rt;
    }
}

