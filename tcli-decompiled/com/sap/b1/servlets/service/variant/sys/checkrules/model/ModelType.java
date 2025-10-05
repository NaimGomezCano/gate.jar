/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType
 *  lombok.Generated
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.model;

import lombok.Generated;

public class ModelType {
    private String modelType;
    private String modelId;

    public ModelType(String modelType, String modelId) {
        this.modelType = modelType;
        this.modelId = modelId;
    }

    @Generated
    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    @Generated
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Generated
    public String getModelType() {
        return this.modelType;
    }

    @Generated
    public String getModelId() {
        return this.modelId;
    }
}

