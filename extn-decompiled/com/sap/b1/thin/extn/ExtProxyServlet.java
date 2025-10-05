/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.infra.meta.tile.Tile
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.sdk.logger.B1LoggerFactory
 *  com.sap.b1.sdk.logger.audit.AuditMessage
 *  com.sap.b1.sdk.logger.audit.SecurityEvent
 *  com.sap.b1.sdk.logger.audit.SecurityEventCategory$SubCategory
 *  com.sap.b1.sdk.oidc.core.AppMetaProviderRegister
 *  com.sap.b1.sdk.oidc.web.WebUtils
 *  com.sap.b1.thin.extn.ExtProxyServlet
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.SLDExtensionUtils
 *  com.sap.b1.thin.extn.apiversion.PackageVersion
 *  jakarta.servlet.http.HttpServletRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.ResponseEntity
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.sap.b1.thin.extn;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.infra.meta.tile.Tile;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.sdk.logger.B1LoggerFactory;
import com.sap.b1.sdk.logger.audit.AuditMessage;
import com.sap.b1.sdk.logger.audit.SecurityEvent;
import com.sap.b1.sdk.logger.audit.SecurityEventCategory;
import com.sap.b1.sdk.oidc.core.AppMetaProviderRegister;
import com.sap.b1.sdk.oidc.web.WebUtils;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.SLDExtensionUtils;
import com.sap.b1.thin.extn.apiversion.PackageVersion;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class ExtProxyServlet {
    private static final Logger logger = LoggerFactory.getLogger(ExtProxyServlet.class);

    @RequestMapping(value={"/app/{id}/{module}/{index}"}, method={RequestMethod.GET})
    protected ResponseEntity<Tile> handleModuleTileRequest(@PathVariable(value="id", required=false) String id, @PathVariable(value="module", required=false) String module, @PathVariable(value="index", required=false) String index) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userCode = authentication.getName();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        String schema = userDetails.getSchema();
        String dbInstanceName = Bootstrap.getInstance().getSldClient().getDatabaseInstance().getName();
        SLDExtensionMeta[] sldExt = new SLDExtensionMeta[]{};
        sldExt = SLDExtensionUtils.retrieveExtensionsbyUserPreference((String)dbInstanceName, (String)schema, (String)userCode);
        ArrayList AppTiles = new ArrayList();
        for (SLDExtensionMeta addon : sldExt) {
            if (!addon.getID().toString().equals(id)) continue;
            PackageVersion.V1.toTileList(AppTiles, addon, true);
            break;
        }
        if (AppTiles.size() > 0) {
            int tileIndex = 1;
            for (Tile t : AppTiles) {
                if (t.componentId.equals(id + "-" + module) && Integer.toString(tileIndex).equals(index) && !t.linkMethod.equals("NewWindow")) {
                    return ResponseEntity.ok((Object)t);
                }
                ++tileIndex;
            }
        } else {
            SecurityEvent auditMessage = new SecurityEvent(SecurityEventCategory.SubCategory.GENERAL);
            String message = String.format("User is not authorized to access extension: id:%s", id);
            auditMessage.setMessage(message);
            auditMessage.setCompanySchema(schema);
            auditMessage.setTenantId(userDetails.getCompanyDbId());
            auditMessage.setUserName(userDetails.getUsername());
            auditMessage.setB1UserCode(userCode);
            auditMessage.setTimestamp(new Date());
            auditMessage.setComponentName(AppMetaProviderRegister.getAppMetaProvider().getAppConfig().getSld().getComponentType());
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                auditMessage.setSourceIp(WebUtils.getClientIpAddress((HttpServletRequest)request));
            }
            B1LoggerFactory.getAuditLogger().audit((AuditMessage)auditMessage);
        }
        return ResponseEntity.status((int)500).body(null);
    }
}

