/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.db.DBType
 *  com.sap.b1.servlets.service.variant.VariantsPkg
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  gen.bean.BmoOUQR
 *  gen.bean.BmoOWSV
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.db.DBType;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import gen.bean.BmoOUQR;
import gen.bean.BmoOWSV;
import java.util.List;
import java.util.Map;

public class VariantsPkg {
    @JsonProperty(value="version")
    private Integer version;
    @JsonProperty(value="dbType")
    private DBType dbType;
    @JsonProperty(value="variantList")
    private List<WebClientVariant> variantList;
    @JsonProperty
    private List<BmoOUQR> udqs;
    @JsonProperty
    private List<BmoOWSV> links;
    @JsonProperty
    private Map<String, String> udqCategoryNames;

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<WebClientVariant> getVariantList() {
        return this.variantList;
    }

    public void setVariantList(List<WebClientVariant> variantList) {
        this.variantList = variantList;
    }

    public List<BmoOUQR> getUdqs() {
        return this.udqs;
    }

    public void setUdqs(List<BmoOUQR> udqs) {
        this.udqs = udqs;
    }

    public List<BmoOWSV> getLinks() {
        return this.links;
    }

    public void setLinks(List<BmoOWSV> links) {
        this.links = links;
    }

    public Map<String, String> getUdqCategoryNames() {
        return this.udqCategoryNames;
    }

    public DBType getDbType() {
        return this.dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public void setUdqCategoryNames(Map<String, String> udqCategoryNames) {
        this.udqCategoryNames = udqCategoryNames;
    }
}

