/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.VariantChartValidator
 *  com.sap.b1.servlets.service.variant.sys.VariantValidator
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.db.DBType;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.VariantValidator;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;

public interface VariantChartValidator
extends VariantValidator {
    public ValidateResult validate(WebClientVariant var1, ViewColumns var2, DBType var3);
}

