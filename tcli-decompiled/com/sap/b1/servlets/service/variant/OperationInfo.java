/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.variant.ChangeList
 *  com.sap.b1.servlets.service.variant.OperationInfo
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.servlets.service.variant.ChangeList;

public class OperationInfo {
    @JsonProperty
    public String guid;
    @JsonProperty
    public String operation;
    @JsonProperty
    public ChangeList changeList;
}

