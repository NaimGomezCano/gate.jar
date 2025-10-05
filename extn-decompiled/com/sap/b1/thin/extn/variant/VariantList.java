/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.thin.extn.variant.VariantList
 */
package com.sap.b1.thin.extn.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import java.util.ArrayList;
import java.util.List;

public class VariantList {
    @JsonProperty
    public String defaultVariant;
    @JsonProperty
    public List<WebClientVariant> list = new ArrayList();
}

