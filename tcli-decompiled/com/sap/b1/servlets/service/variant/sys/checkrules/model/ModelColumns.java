/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelColumns
 *  lombok.Generated
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.model;

import java.util.List;
import lombok.Generated;

public class ModelColumns {
    private String modelId;
    private List columns;

    public ModelColumns(String modelId, List columns) {
        this.modelId = modelId;
        this.columns = columns;
    }

    @Generated
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Generated
    public void setColumns(List columns) {
        this.columns = columns;
    }

    @Generated
    public String getModelId() {
        return this.modelId;
    }

    @Generated
    public List getColumns() {
        return this.columns;
    }
}

