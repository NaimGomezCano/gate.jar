/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckCardRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ChartCustomizationResolveUtils
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  org.apache.commons.lang3.StringUtils
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
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ChartCustomizationResolveUtils;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.sme.anw.analytics.type.model.ModelType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckCardRule
implements CheckRule {
    @Autowired
    private ModelMetaService modelMetaService;

    public boolean isAccepted(CategroiesEnum categroies) {
        return categroies == CategroiesEnum.CARD_VIEW || categroies == CategroiesEnum.CHART_VIEW;
    }

    public ValidateResult validate(WebClientVariant variant, ViewColumns viewColumns, DBType pkgDbType) {
        if (StringUtils.isBlank((CharSequence)variant.ObjectName)) {
            return new ValidateResult(true);
        }
        ViewColumns columns = this.modelMetaService.getViewColumnsFromModel(variant.ObjectName, ModelType.LISTVIEW.toString(), variant.ObjectName);
        if (columns instanceof ViewColumnsWithNoCheck) {
            return new ValidateResult(true);
        }
        List chartBindFields = ChartCustomizationResolveUtils.getBindings((String)variant.ChartCustomization);
        List cardBindFields = OverviewCustomizationResolveUtils.getBindings((String)variant.OverviewCustomization, (boolean)false);
        List allFields = this.merge(chartBindFields, cardBindFields);
        ArrayList inValidColums = new ArrayList();
        inValidColums.addAll(allFields.stream().filter(f -> !f.toString().startsWith("__")).filter(f -> columns.getByName((String)f) == null).distinct().collect(Collectors.toList()));
        if (inValidColums.size() > 0) {
            throw new ValidateException(RulesErrorEnum.VARIANT_COLUMN_NOT_EXIST, new Object[]{variant.Name, variant.ObjectName, inValidColums});
        }
        return new ValidateResult(true);
    }

    private List merge(List<String> chartBindFields, List<Map<String, List>> cardBindFields) {
        ArrayList<String> allFields = new ArrayList<String>();
        allFields.addAll(chartBindFields);
        for (Map<String, List> item : cardBindFields) {
            item.keySet().stream().forEach(f -> allFields.addAll((Collection)item.get(f)));
        }
        return allFields.stream().distinct().collect(Collectors.toList());
    }
}

