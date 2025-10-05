/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.servlets.service.variant.VariantsPkg
 *  com.sap.b1.servlets.service.variant.admin.AnalyticsAdapter
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.VariantChartValidator
 *  com.sap.b1.servlets.service.variant.sys.VariantChartValidatorImpl
 *  com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.str.VariantAdmin
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.stereotype.Service
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.servlets.service.variant.VariantsPkg;
import com.sap.b1.servlets.service.variant.admin.AnalyticsAdapter;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.VariantChartValidator;
import com.sap.b1.servlets.service.variant.sys.checkrules.CheckRule;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException;
import com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.str.VariantAdmin;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class VariantChartValidatorImpl
implements VariantChartValidator {
    private static final Logger logger = LoggerFactory.getLogger(VariantChartValidatorImpl.class);
    private List<CheckRule> checkRules = new ArrayList();
    @Autowired
    private DBTypeConfig dbTypeConfig;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private ModelMetaService modelMetaService;
    @Autowired
    private AnalyticsAdapter analyticsAdapter;

    public ValidateResult validate(WebClientVariant variant, ViewColumns viewColumns, DBType pkgDbType) {
        CategroiesEnum categroies = CategroiesEnum.getVariantCategroies((WebClientVariant)variant);
        try {
            for (CheckRule checkRule : this.checkRules) {
                if (!checkRule.isAccepted(categroies)) continue;
                checkRule.validate(variant, viewColumns, pkgDbType);
            }
        }
        catch (ValidateException e) {
            logger.error("import variants check failed:", (Throwable)e);
            return this.formatValidateResult(e, variant);
        }
        catch (Exception e) {
            logger.error("import variants check failed:", (Throwable)e);
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.INVALID_DATA_PKG, new Object[0]));
        }
        return new ValidateResult(true);
    }

    @Autowired
    private void init() {
        this.applicationContext.getBeansOfType(CheckRule.class).forEach((s, checkRule) -> this.checkRules.add(checkRule));
    }

    private ValidateResult formatValidateResult(ValidateException exception, WebClientVariant variant) {
        if (RulesErrorEnum.TYPE_MODEL_NOT_EXIST == exception.getRulesError()) {
            String modelName = exception.getArguments().get(0).toString();
            if (MetaTable.isUDT((String)modelName)) {
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.TYPE_MODEL_NOT_EXIST, new Object[]{variant.Name, exception.getArguments(), ""}));
            }
            if (modelName.contains(":")) {
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.TYPE_MODEL_VIEW_NOT_EXIST, new Object[]{variant.Name, "", exception.getArguments()}));
            }
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.TYPE_MODEL_NOT_EXIST, new Object[]{variant.Name, exception.getArguments(), ""}));
        }
        if (RulesErrorEnum.VARIANT_COLUMN_NOT_EXIST == exception.getRulesError()) {
            String modelName = exception.getArguments().get(1).toString();
            StringBuffer groupId = new StringBuffer("");
            String viewDesc = this.modelMetaService.getModelDescription(variant, groupId);
            if (MetaTable.isUDT((String)modelName)) {
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.VARIANT_COLUMN_NOT_EXIST, new Object[]{variant.Name, ((ArrayList)exception.getArguments().get(2)).stream().collect(Collectors.toList()), groupId, viewDesc}));
            }
            if (modelName.contains(":")) {
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.VARIANT_COLUMN_VIEW_NOT_EXIST, new Object[]{variant.Name, ((ArrayList)exception.getArguments().get(2)).stream().collect(Collectors.toList()), "", viewDesc}));
            }
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.VARIANT_COLUMN_NOT_EXIST, new Object[]{variant.Name, ((ArrayList)exception.getArguments().get(2)).stream().collect(Collectors.toList()), viewDesc, ""}));
        }
        if (RulesErrorEnum.INVALID_HANA_TO_MSSQL == exception.getRulesError()) {
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.Invalid_HANA_To_MSSQL, new Object[0]));
        }
        return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.INVALID_DATA_PKG, new Object[0]));
    }

    public ValidateResult validate(VariantsPkg pkg) {
        List variantList = pkg.getVariantList();
        if (variantList == null || variantList.size() == 0) {
            return new ValidateResult(true);
        }
        if (this.isFromHANAtoMSSQL(pkg.getDbType()) && this.hasAnalyticsObjects(pkg)) {
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.Invalid_HANA_To_MSSQL, new Object[0]));
        }
        for (WebClientVariant variant : variantList) {
            ValidateResult result = this.validate(variant, null, pkg.getDbType());
            if (result.isValid()) continue;
            return result;
        }
        return new ValidateResult(true);
    }

    private boolean hasAnalyticsObjects(VariantsPkg pkg) {
        List variants = pkg.getVariantList();
        if (variants != null) {
            return variants.stream().anyMatch(v -> this.analyticsAdapter.isHanaSpecificObject(v.ObjectName, v.ViewId));
        }
        return false;
    }

    private boolean isFromHANAtoMSSQL(DBType pkgDbType) {
        return pkgDbType == DBType.HANA && this.dbTypeConfig.get() == DBType.MSSQL;
    }
}

