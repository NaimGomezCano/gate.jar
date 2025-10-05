/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.formsetting.FormSetting
 */
package com.sap.b1.servlets.service.formsetting;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormSetting {
    @JsonProperty(value="formId")
    public String formId;
    @JsonProperty(value="boId")
    public String objId;
}

