/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.service.shorlink.Link
 */
package com.sap.b1.service.shorlink;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty
    private String origin;
    @JsonProperty
    private String oriLink;
    @JsonProperty
    private String shortLink;

    public Link() {
    }

    public Link(String origin, String oriLink, String shortLink) {
        this.origin = origin;
        this.oriLink = oriLink;
        this.shortLink = shortLink;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriLink() {
        return this.oriLink;
    }

    public void setOriLink(String oriLink) {
        this.oriLink = oriLink;
    }

    public String getShortLink() {
        return this.shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }
}

