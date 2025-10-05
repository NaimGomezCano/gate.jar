/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.thin.extn.variant.VariantDisplay
 */
package com.sap.b1.thin.extn.variant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariantDisplay {
    @JsonProperty(value="Guid")
    public String guid;
    @JsonProperty(value="Name")
    public String name;
}

