/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.variant.VariantCreationPayload
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.svcl.client.enums.BoYesNoEnum
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.svcl.client.enums.BoYesNoEnum;

public class VariantCreationPayload {
    @JsonProperty
    public WebClientVariant obj;
    @JsonProperty
    public BoYesNoEnum isDefault;
}

