/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.service.TableServiceResponse
 */
package com.sap.b1.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.infra.engines.metadata.MetaTable;

public class TableServiceResponse {
    @JsonProperty
    public String type = "Bo.Metadata";
    @JsonProperty
    public MetaTable data;
}

