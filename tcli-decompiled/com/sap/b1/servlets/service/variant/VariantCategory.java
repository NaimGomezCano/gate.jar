/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.variant.VariantCategory
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariantCategory {
    @JsonProperty
    private String name;

    public VariantCategory() {
    }

    public VariantCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

