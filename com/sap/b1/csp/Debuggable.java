/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.csp.CSPStrategy
 *  com.sap.b1.csp.Debuggable
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.server.WebSession
 */
package com.sap.b1.csp;

import com.sap.b1.csp.CSPStrategy;
import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.WebSession;

public abstract class Debuggable
implements CSPStrategy {
    protected Boolean isRequireDebug = false;
    protected String debugServer = null;
    protected Boolean devModeEnabled = false;
    protected WebSession session = null;

    public void setDebugServer(String debugServer) {
        if (StringUtils.isNotEmpty((CharSequence)debugServer) && debugServer.matches("^[^\\s;']+$")) {
            this.debugServer = debugServer.endsWith("/") ? StringUtils.removeEnd((String)debugServer, (String)"/") : debugServer;
        }
    }

    public void setRequireDebug(String requireDebug) {
        this.isRequireDebug = "true".equalsIgnoreCase(requireDebug);
    }

    public void init(WebSession session, Boolean devModeEnabled, String debugServer, String requireDebug) {
        this.session = session;
        this.devModeEnabled = devModeEnabled;
        this.setRequireDebug(requireDebug);
        if (Boolean.TRUE.equals(devModeEnabled)) {
            this.setDebugServer(debugServer);
        }
    }

    private Map<String, List<String>> parseCSP(String csp) {
        String[] parts = StringUtils.split((String)csp, (String)";");
        HashMap<String, List<String>> info = new HashMap<String, List<String>>();
        Arrays.stream(parts).forEach(item -> {
            item = item.trim();
        });
        for (String part : parts) {
            String[] items = StringUtils.split((String)part, (String)" ");
            Arrays.stream(items).forEach(item -> {
                item = item.trim();
            });
            if (items.length <= 0 || !StringUtils.isNotEmpty((CharSequence)items[0])) continue;
            info.put(items[0], Arrays.stream(Arrays.copyOfRange(items, 1, items.length)).collect(Collectors.toList()));
        }
        return info;
    }

    private String buildCSP(Map<String, List<String>> info) {
        String result = "";
        if (info != null) {
            ArrayList<CallSite> temp = new ArrayList<CallSite>();
            for (Map.Entry<String, List<String>> entry : info.entrySet()) {
                temp.add((CallSite)((Object)(entry.getKey() + " " + StringUtils.join((Iterable)entry.getValue(), (String)" "))));
            }
            result = StringUtils.join(temp, (String)";");
        }
        return result;
    }

    protected String addDevServerToCsp(String csp, String debugServer) {
        String result = csp;
        if (StringUtils.isNotEmpty((CharSequence)csp)) {
            Map settingsMap = this.parseCSP(csp);
            if (settingsMap.containsKey("connect-src")) {
                List settings = (List)settingsMap.get("connect-src");
                settings.add(debugServer);
            } else if (settingsMap.containsKey("default-src")) {
                List settings = (List)settingsMap.get("default-src");
                settings.add(debugServer);
            }
            result = this.buildCSP(settingsMap);
        }
        return result;
    }
}

