/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.variant.VariantList
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 */
package com.sap.b1.servlets.service.variant;

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

