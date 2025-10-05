/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckOverViewRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelColumns
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.FilterBarLayoutResolveUtils
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  lombok.Generated
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
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
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.FilterBarLayoutResolveUtils;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckOverViewRule
implements CheckRule {
    @Generated
    private static final Logger log = LogManager.getLogger(CheckOverViewRule.class);
    @Autowired
    private ModelMetaService modelMetaService;

    public boolean isAccepted(CategroiesEnum categroies) {
        return categroies == CategroiesEnum.OVERVIEW;
    }

    public ValidateResult validate(WebClientVariant variant, ViewColumns viewColumns, DBType pkgDbType) {
        HashMap<String, ViewColumns> columnsMap = new HashMap<String, ViewColumns>();
        ModelColumns modelColumns = FilterBarLayoutResolveUtils.getBindings((String)variant.FilterBarLayout);
        List modelTypes = OverviewCustomizationResolveUtils.getDatasets((String)variant.OverviewCustomization, (boolean)false);
        for (ModelType modelType : modelTypes) {
            if (columnsMap.get(modelType.getModelId()) != null) continue;
            ViewColumns viewColumnsList = this.modelMetaService.getViewColumnsFromModel(variant.ObjectName, modelType.getModelType(), modelType.getModelId());
            columnsMap.put(modelType.getModelId(), viewColumnsList);
        }
        if (columnsMap.get(modelColumns.getModelId()) == null) {
            ViewColumns viewColumnsList = this.modelMetaService.getViewColumnsFromModel(variant.ObjectName, null, modelColumns.getModelId());
            columnsMap.put(modelColumns.getModelId(), viewColumnsList);
        }
        if (columnsMap.get(modelColumns.getModelId()) == null || columnsMap.get(modelColumns.getModelId()) instanceof ViewColumnsWithNoCheck) {
            return new ValidateResult(true);
        }
        ViewColumns columns = (ViewColumns)columnsMap.values().stream().findFirst().get();
        List bindFields = OverviewCustomizationResolveUtils.getBindings((String)variant.OverviewCustomization, (boolean)false);
        ArrayList inValidColums = new ArrayList();
        bindFields.stream().forEach(bindField -> bindField.keySet().stream().forEach(modelId -> {
            List fields = (List)bindField.get(modelId);
            inValidColums.addAll(fields.stream().filter(f -> !f.toString().startsWith("__")).filter(f -> columns.getByName((String)f) == null).distinct().collect(Collectors.toList()));
        }));
        inValidColums.addAll(modelColumns.getColumns().stream().filter(f -> !f.toString().startsWith("__")).filter(f -> columns.getByName((String)f) == null).distinct().collect(Collectors.toList()));
        if (inValidColums.size() > 0) {
            throw new ValidateException(RulesErrorEnum.VARIANT_COLUMN_NOT_EXIST, new Object[]{variant.Name, modelColumns.getModelId(), inValidColums});
        }
        return new ValidateResult(true);
    }
}

