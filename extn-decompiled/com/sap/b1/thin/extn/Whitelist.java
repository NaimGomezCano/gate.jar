/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.SLDExtensionUtils
 *  com.sap.b1.thin.extn.Whitelist
 *  com.sap.b1.thin.extn.apiversion.PackageVersion
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package com.sap.b1.thin.extn;

import com.google.gson.JsonSyntaxException;
import com.sap.b1.boot.Bootstrap;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.SLDExtensionUtils;
import com.sap.b1.thin.extn.apiversion.PackageVersion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Whitelist {
    public List<String> getDomains() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userCode = authentication.getName();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        String schema = userDetails.getSchema();
        ArrayList<String> stringList = new ArrayList<String>();
        SLDExtensionMeta[] sldExt = new SLDExtensionMeta[]{};
        try {
            String dbInstanceName = Bootstrap.getInstance().getSldClient().getDatabaseInstance().getName();
            for (SLDExtensionMeta addon : sldExt = SLDExtensionUtils.retrieveExtensionsbyUserPreference((String)dbInstanceName, (String)schema, (String)userCode)) {
                PackageVersion packageVersion = PackageVersion.V1;
                packageVersion.toWhiteList(stringList, addon);
            }
        }
        catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return stringList;
    }
}

