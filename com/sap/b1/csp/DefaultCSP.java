/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.csp.Debuggable
 *  com.sap.b1.csp.DefaultCSP
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.csp;

import com.sap.b1.csp.Debuggable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value="DefaultCSP")
public class DefaultCSP
extends Debuggable {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCSP.class);
    private static final String WEB_CLIENT_CSP_DEVELOPER_MODE = "WEB_CLIENT_CSP_DEVELOPER_MODE";
    private static final String WEB_CLIENT_CSP_DEBUG_SERVER = "WEB_CLIENT_CSP_DEBUG_SERVER";
    private static final String WEB_CLIENT_CSP = "WEB_CLIENT_CSP";

    public String generateCSP(String csp) {
        this.checkDeveloperMode();
        Boolean developerMode = (Boolean)this.session.getAttribute(WEB_CLIENT_CSP_DEVELOPER_MODE);
        String debugServer = (String)this.session.getAttribute(WEB_CLIENT_CSP_DEBUG_SERVER);
        if (StringUtils.isEmpty((CharSequence)csp)) {
            this.session.getAttributes().put(WEB_CLIENT_CSP, "");
        } else if (Boolean.TRUE.equals(developerMode) && StringUtils.isNotEmpty((CharSequence)debugServer)) {
            this.session.getAttributes().put(WEB_CLIENT_CSP, this.addDevServerToCsp(csp, debugServer));
        } else {
            this.session.getAttributes().put(WEB_CLIENT_CSP, csp);
        }
        logger.info("set session csp: " + this.session.getAttribute(WEB_CLIENT_CSP));
        return (String)this.session.getAttribute(WEB_CLIENT_CSP);
    }

    private void checkDeveloperMode() {
        if (this.isRequireDebug.booleanValue()) {
            this.session.getAttributes().put(WEB_CLIENT_CSP_DEVELOPER_MODE, this.devModeEnabled);
            logger.info("set session developer mode: " + this.session.getAttribute(WEB_CLIENT_CSP_DEVELOPER_MODE));
            if (Boolean.TRUE.equals(this.devModeEnabled)) {
                this.session.getAttributes().remove(WEB_CLIENT_CSP_DEBUG_SERVER);
                if (StringUtils.isNotEmpty((CharSequence)this.debugServer)) {
                    this.session.getAttributes().put(WEB_CLIENT_CSP_DEBUG_SERVER, this.debugServer);
                }
                return;
            }
        }
        this.session.getAttributes().remove(WEB_CLIENT_CSP_DEVELOPER_MODE);
        this.session.getAttributes().remove(WEB_CLIENT_CSP_DEBUG_SERVER);
    }
}

