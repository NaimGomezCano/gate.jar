/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.TypeAdapter
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.SLDExtensionUtils
 *  com.sap.b1.thin.extn.UIExtension
 *  com.sap.b1.thin.extn.UIExtensionServlet
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.ResponseEntity
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.extn;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.sap.b1.boot.Bootstrap;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.SLDExtensionUtils;
import com.sap.b1.thin.extn.UIExtension;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UIExtensionServlet {
    private Logger logger = LoggerFactory.getLogger(UIExtensionServlet.class);

    @RequestMapping(path={"/ui-extensions.svc"})
    protected ResponseEntity<String> service() throws IOException {
        UIExtension[] extensions = null;
        SLDExtensionMeta[] extMetas = null;
        String loadErrorInfo = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userCode = authentication.getName();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        String schema = userDetails.getSchema();
        try {
            String dbInstanceName = Bootstrap.getInstance().getSldClient().getDatabaseInstance().getName();
            if (dbInstanceName.contains("(local)")) {
                this.logger.info("Get db instance name failed.  The get value is : {0}", (Object)dbInstanceName);
            }
            extMetas = SLDExtensionUtils.retrieveExtensionsbyUserPreference((String)dbInstanceName, (String)schema, (String)userCode);
            extensions = SLDExtensionUtils.cacheUIExtensions((SLDExtensionMeta[])extMetas);
            Object strResponse = "";
            if (extensions == null) {
                strResponse = "Errors on accessing B1 System Landscape Directory(SLD). \n " + loadErrorInfo;
            } else {
                this.logger.info("Retrieved {} UI extensions.", (Object)extensions.length);
                Gson gson = new Gson();
                TypeAdapter adapter = gson.getAdapter(UIExtension[].class);
                JsonElement jsonElement = adapter.toJsonTree((Object)extensions);
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("applications", jsonElement);
                strResponse = jsonObject.toString();
            }
            return ResponseEntity.ok().body(strResponse);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return ResponseEntity.status((int)500).body((Object)"Internal Error");
        }
    }
}

