/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.meta.tile.Tile
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.apiversion.PackageVersion
 */
package com.sap.b1.thin.extn.apiversion;

import com.sap.b1.infra.meta.tile.Tile;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import java.util.List;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum PackageVersion {
    V1/* Unavailable Anonymous Inner Class!! */;


    public abstract void toTileList(List<Tile> var1, SLDExtensionMeta var2, boolean var3);

    public abstract void toWhiteList(List<String> var1, SLDExtensionMeta var2);
}

