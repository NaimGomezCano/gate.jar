/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.infa.meta.dashboard.Dashboard
 *  com.sap.b1.infa.meta.dashboard.Dataset
 *  com.sap.b1.infa.meta.dashboard.WidgetCollection
 *  com.sap.b1.service.dashboard.DashboardResponse
 *  com.sap.b1.service.dashboard.DashboardService
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckGeneralOverViewRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.str.VariantAdmin
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.sys.checkrules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.infa.meta.dashboard.Dashboard;
import com.sap.b1.infa.meta.dashboard.Dataset;
import com.sap.b1.infa.meta.dashboard.WidgetCollection;
import com.sap.b1.service.dashboard.DashboardResponse;
import com.sap.b1.service.dashboard.DashboardService;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.str.VariantAdmin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckGeneralOverViewRule
implements CheckRule {
    @Generated
    private static final Logger log = LogManager.getLogger(CheckGeneralOverViewRule.class);
    @Autowired
    private DBTypeConfig dbTypeConfig;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private ModelMetaService modelMetaService;
    @Autowired
    private MessageUtil messageUtil;
    private static final String GENERAL_OVERVIEW_ID = "MDO10001";

    public boolean isAccepted(CategroiesEnum categroies) {
        return categroies == CategroiesEnum.GENERAL_OVERVIEW;
    }

    public ValidateResult validate(WebClientVariant variant, ViewColumns viewColumns, DBType pkgDbType) {
        HashMap<String, ViewColumns> columnsMap = new HashMap<String, ViewColumns>();
        List modelTypes = OverviewCustomizationResolveUtils.getDatasets((String)variant.OverviewCustomization, (boolean)false);
        if (this.dbTypeConfig.get() == DBType.MSSQL && this.containsCalculationView(variant, pkgDbType)) {
            throw new ValidateException(RulesErrorEnum.INVALID_HANA_TO_MSSQL, new Object[0]);
        }
        try {
            for (ModelType modelType : modelTypes) {
                if (columnsMap.get(modelType.getModelId()) != null) continue;
                ViewColumns viewColumnsList = this.modelMetaService.getViewColumnsFromModel(variant.ObjectName, modelType.getModelType(), modelType.getModelId());
                columnsMap.put(modelType.getModelId(), viewColumnsList);
            }
        }
        catch (ValidateException e) {
            log.warn(e.getRulesError() + ":" + StringUtils.join((Iterable)e.getArguments(), (String)","), (Throwable)e);
        }
        List bindFields = OverviewCustomizationResolveUtils.getBindings((String)variant.OverviewCustomization, (boolean)false);
        ArrayList inValidColums = new ArrayList();
        bindFields.stream().forEach(bindField -> bindField.keySet().stream().forEach(modelId -> {
            List fields = (List)bindField.get(modelId);
            ViewColumns columns = (ViewColumns)columnsMap.get(modelId);
            if (columns instanceof ViewColumnsWithNoCheck) {
                return;
            }
            if (columns != null) {
                inValidColums.addAll(fields.stream().filter(f -> !f.toString().startsWith("__")).filter(f -> columns.getByName((String)f) == null).map(f -> f + " not in " + modelId).distinct().collect(Collectors.toList()));
            }
        }));
        if (inValidColums.size() > 0) {
            log.warn(this.messageUtil.getMessage((MessageId)VariantAdmin.CONDITION_FILTER_ERROR, new Object[]{StringUtils.join(inValidColums, (String)",")}));
        }
        return new ValidateResult(true);
    }

    private boolean containsCalculationView(WebClientVariant variant, DBType pkgDbType) {
        List allModelTypes = new ArrayList();
        try {
            DashboardResponse overviewRep = (DashboardResponse)this.dashboardService.loadOverview(GENERAL_OVERVIEW_ID);
            Dashboard generalOverviewMeta = (Dashboard)overviewRep.data;
            if (pkgDbType == DBType.MSSQL) {
                List customWidgets = generalOverviewMeta.widgets.stream().filter(o -> !this.modelMetaService.isCalculationView(((Dataset)o.datasets.get((int)0)).modelType)).collect(Collectors.toList());
                ObjectMapper mapper = new ObjectMapper();
                generalOverviewMeta.widgets = (WidgetCollection)mapper.convertValue(customWidgets, WidgetCollection.class);
            }
            allModelTypes = OverviewCustomizationResolveUtils.getAllWidgetsDatasets((String)variant.OverviewCustomization, (Dashboard)generalOverviewMeta);
        }
        catch (Exception e) {
            log.warn("Get GeneralOverview meta failed");
        }
        return allModelTypes.stream().anyMatch(m -> this.modelMetaService.isCalculationView(m.getModelType()));
    }
}

