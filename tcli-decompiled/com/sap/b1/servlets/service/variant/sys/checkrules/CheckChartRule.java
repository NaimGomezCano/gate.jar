/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckChartRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelColumns
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ChartCustomizationResolveUtils
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.FilterBarLayoutResolveUtils
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.sys.checkrules;

import com.sap.b1.db.DBType;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelColumns;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ChartCustomizationResolveUtils;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.FilterBarLayoutResolveUtils;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckChartRule
implements CheckRule {
    @Autowired
    private ModelMetaService modelMetaService;

    public boolean isAccepted(CategroiesEnum categroies) {
        return categroies == CategroiesEnum.CHART;
    }

    public ValidateResult validate(WebClientVariant variant, ViewColumns viewColumns, DBType pkgDbType) {
        List chartBindFields = ChartCustomizationResolveUtils.getBindings((String)variant.ChartCustomization);
        ModelColumns modelColumns = FilterBarLayoutResolveUtils.getBindings((String)variant.FilterBarLayout);
        if (modelColumns.getModelId() == null) {
            return new ValidateResult(true);
        }
        ViewColumns columns = this.modelMetaService.getViewColumnsFromModel(variant.ObjectName, null, modelColumns.getModelId());
        if (columns instanceof ViewColumnsWithNoCheck) {
            return new ValidateResult(true);
        }
        ArrayList inValidColums = new ArrayList();
        modelColumns.getColumns().addAll(chartBindFields);
        inValidColums.addAll(modelColumns.getColumns().stream().filter(f -> !f.toString().startsWith("__")).filter(f -> columns.getByName((String)f) == null).distinct().collect(Collectors.toList()));
        if (inValidColums.size() > 0) {
            throw new ValidateException(RulesErrorEnum.VARIANT_COLUMN_NOT_EXIST, new Object[]{variant.Name, modelColumns.getModelId(), inValidColums});
        }
        return new ValidateResult(true);
    }
}

