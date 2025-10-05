/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.variant.OperationList
 *  com.sap.b1.servlets.service.variant.VariantBatchUpdatePayload
 *  com.sap.b1.servlets.service.variant.VariantGroup
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.servlets.service.variant.OperationList;
import com.sap.b1.servlets.service.variant.VariantGroup;

public class VariantBatchUpdatePayload
extends VariantGroup {
    @JsonProperty
    public OperationList operationList;
}

