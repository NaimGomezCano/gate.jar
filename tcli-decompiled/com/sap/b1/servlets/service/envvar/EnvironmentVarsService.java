/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.envvar.EnvironmentVars
 *  com.sap.b1.servlets.service.envvar.EnvironmentVarsService
 *  com.sap.b1.spring.RequestMappingTx
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.envvar;

import com.sap.b1.servlets.service.envvar.EnvironmentVars;
import com.sap.b1.spring.RequestMappingTx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentVarsService {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentVarsService.class);

    @RequestMappingTx(path={"/system/envvars.svc"})
    public EnvironmentVars get() {
        EnvironmentVars envVars = new EnvironmentVars();
        try {
            envVars.googleMapAPIKey = System.getenv("GOOGLE_MAP_API_KEY");
            envVars.googleMapClientId = System.getenv("GOOGLE_MAP_CLIENT_ID");
        }
        catch (NullPointerException | SecurityException e) {
            logger.warn(e.getMessage());
        }
        return envVars;
    }
}

