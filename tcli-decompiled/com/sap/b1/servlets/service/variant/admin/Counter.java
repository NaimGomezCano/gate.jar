/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.admin.Counter
 */
package com.sap.b1.servlets.service.variant.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Counter {
    private List<String> variants;
    private List<String> links;
    private List<String> udqs;

    public boolean addVariant(String guid) {
        if (this.variants == null) {
            this.variants = new ArrayList();
        }
        return this.variants.add(guid);
    }

    public boolean addLink(String guid) {
        if (this.links == null) {
            this.links = new ArrayList();
        }
        return this.links.add(guid);
    }

    public boolean addUDQ(String key) {
        if (this.udqs == null) {
            this.udqs = new ArrayList();
        }
        return this.udqs.add(key);
    }

    public List<String> getVariants() {
        if (this.variants == null) {
            return Collections.emptyList();
        }
        return this.variants;
    }

    public List<String> getLinks() {
        if (this.links == null) {
            return Collections.emptyList();
        }
        return this.links;
    }

    public List<String> getUdqs() {
        if (this.udqs == null) {
            return Collections.emptyList();
        }
        return this.udqs;
    }
}

