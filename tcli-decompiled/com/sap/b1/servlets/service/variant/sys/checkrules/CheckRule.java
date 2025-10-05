/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 */
package com.sap.b1.servlets.service.variant.sys.checkrules;

import com.sap.b1.db.DBType;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;

public interface CheckRule {
    public boolean isAccepted(CategroiesEnum var1);

    public ValidateResult validate(WebClientVariant var1, ViewColumns var2, DBType var3);
}

