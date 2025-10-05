/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.infra.meta.tile.Tile
 *  com.sap.b1.infra.share.feign.extn.AddonService
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.thin.extn.AddonServlet
 *  com.sap.b1.thin.extn.CachedScpProxy
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.SLDExtensionUtils
 *  com.sap.b1.thin.extn.apiversion.PackageVersion
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.extn;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.infra.meta.tile.Tile;
import com.sap.b1.infra.share.feign.extn.AddonService;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.thin.extn.CachedScpProxy;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.SLDExtensionUtils;
import com.sap.b1.thin.extn.apiversion.PackageVersion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddonServlet
implements AddonService {
    private Logger logger = LoggerFactory.getLogger(AddonServlet.class);

    @RequestMapping(path={"/webclient-addons.svc"})
    public List<Tile> getAddons() throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userCode = authentication.getName();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        String schema = userDetails.getSchema();
        ArrayList<Tile> fioriAppTile = new ArrayList<Tile>();
        SLDExtensionMeta[] assignedExt = new SLDExtensionMeta[]{};
        SLDExtensionMeta[] allExt = new SLDExtensionMeta[]{};
        try {
            String dbInstanceName = Bootstrap.getInstance().getSldClient().getDatabaseInstance().getName();
            allExt = SLDExtensionUtils.retrieveDeployedExtensions();
            assignedExt = SLDExtensionUtils.retrieveExtensionsbyUserPreference((String)dbInstanceName, (String)schema, (String)userCode);
            CachedScpProxy.getInstance().manageAddonCache(allExt, assignedExt, true);
            for (SLDExtensionMeta addon : assignedExt) {
                PackageVersion packageVersion = PackageVersion.V1;
                packageVersion.toTileList(fioriAppTile, addon, false);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return fioriAppTile;
    }
}

