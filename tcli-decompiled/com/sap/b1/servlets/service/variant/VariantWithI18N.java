/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.variant.VariantWithI18N
 *  gen.bean.BmoOWVT
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import gen.bean.BmoOWVT;

public class VariantWithI18N {
    @JsonProperty
    private String Guid;
    @JsonProperty
    private String ViewType;
    @JsonProperty
    private String SubVType;
    @JsonProperty
    private String ViewId;
    @JsonProperty
    private String ObjName;
    @JsonProperty
    private String Name;
    @JsonProperty
    private String ViewTypeI18N;
    @JsonProperty
    private String IsPublic;

    public VariantWithI18N() {
    }

    public VariantWithI18N(BmoOWVT v) {
        this.Guid = v.getGuid();
        this.ViewType = v.getViewType();
        this.SubVType = v.getSubVType();
        this.ViewId = v.getViewId();
        this.ObjName = v.getObjName();
        this.Name = v.getName();
        this.IsPublic = v.getIsPublic();
    }

    public String getGuid() {
        return this.Guid;
    }

    public void setGuid(String guid) {
        this.Guid = guid;
    }

    public String getViewType() {
        return this.ViewType;
    }

    public void setViewType(String viewType) {
        this.ViewType = viewType;
    }

    public String getSubVType() {
        return this.SubVType;
    }

    public void setSubVType(String subVType) {
        this.SubVType = subVType;
    }

    public String getViewId() {
        return this.ViewId;
    }

    public void setViewId(String viewId) {
        this.ViewId = viewId;
    }

    public String getObjName() {
        return this.ObjName;
    }

    public void setObjName(String objName) {
        this.ObjName = objName;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getViewTypeI18N() {
        return this.ViewTypeI18N;
    }

    public void setViewTypeI18N(String viewTypeI18N) {
        this.ViewTypeI18N = viewTypeI18N;
    }

    public String getIsPublic() {
        return this.IsPublic;
    }

    public void setIsPublic(String isPublic) {
        this.IsPublic = isPublic;
    }
}

