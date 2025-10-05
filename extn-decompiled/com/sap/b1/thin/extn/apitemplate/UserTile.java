/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.meta.tile.FlpTileDynamicContent
 *  com.sap.b1.thin.extn.apitemplate.UserTile
 */
package com.sap.b1.thin.extn.apitemplate;

import com.sap.b1.infra.meta.tile.FlpTileDynamicContent;

public class UserTile {
    private String name;
    private String folder;
    private String url;
    private String linkmethod;
    private String image;
    private String size;
    private String icon;
    private String text;
    private String subtitle;
    public FlpTileDynamicContent dynamicContent;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinkmethod() {
        return this.linkmethod;
    }

    public void setLinkmethod(String linkmethod) {
        this.linkmethod = linkmethod;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public FlpTileDynamicContent getDynamicContent() {
        return this.dynamicContent;
    }

    public void setDynamicContent(FlpTileDynamicContent dynamicContent) {
        this.dynamicContent = dynamicContent;
    }
}

