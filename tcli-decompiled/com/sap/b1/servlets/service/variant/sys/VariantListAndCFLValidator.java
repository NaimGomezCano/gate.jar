/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.bo.base.BoFilterSqlBuilder
 *  com.sap.b1.dimview.DimViewEngine
 *  com.sap.b1.dimview.UDQViewEngine
 *  com.sap.b1.dimview.ViewColumn
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.meta.dimviewds.DimView
 *  com.sap.b1.service.view.ViewParam
 *  com.sap.b1.servlets.service.variant.VariantsPkg
 *  com.sap.b1.servlets.service.variant.sys.FilterUtilsParser
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.VariantListAndCFLValidator
 *  com.sap.b1.servlets.service.variant.sys.VariantValidator
 *  com.sap.b1.svcl.client.complextype.WebClientVariantGroupBy
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSortBy
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  com.sap.b1.tcli.service.ouqr.OUQRService
 *  gen.bean.BmoOUQR
 *  gen.str.VariantAdmin
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.stereotype.Component
 *  org.springframework.util.StringUtils
 */
package com.sap.b1.servlets.service.variant.sys;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sap.b1.app.base.AppEnv;
import com.sap.b1.bo.base.BoFilterSqlBuilder;
import com.sap.b1.dimview.DimViewEngine;
import com.sap.b1.dimview.UDQViewEngine;
import com.sap.b1.dimview.ViewColumn;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.infra.meta.dimviewds.DimView;
import com.sap.b1.service.view.ViewParam;
import com.sap.b1.servlets.service.variant.VariantsPkg;
import com.sap.b1.servlets.service.variant.sys.FilterUtilsParser;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.VariantValidator;
import com.sap.b1.svcl.client.complextype.WebClientVariantGroupBy;
import com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn;
import com.sap.b1.svcl.client.complextype.WebClientVariantSortBy;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import com.sap.b1.tcli.service.ouqr.OUQRService;
import gen.bean.BmoOUQR;
import gen.str.VariantAdmin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VariantListAndCFLValidator
implements VariantValidator {
    private static Logger log = LoggerFactory.getLogger(VariantListAndCFLValidator.class);
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private AppEnv env;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private OUQRService ouqrService;

    private boolean accept(WebClientVariant variant) {
        if (variant == null) {
            return false;
        }
        return "list".equalsIgnoreCase(variant.ViewType) || "cfl".equalsIgnoreCase(variant.ViewType);
    }

    public ValidateResult validate(VariantsPkg pkg) {
        List variantList = pkg.getVariantList();
        if (variantList == null || variantList.size() == 0) {
            return new ValidateResult(true);
        }
        HashMap map = new HashMap();
        for (WebClientVariant variant : variantList) {
            if (!this.accept(variant)) continue;
            if (map.get(variant.ObjectName) == null) {
                map.put(variant.ObjectName, new ArrayList());
            }
            ((List)map.get(variant.ObjectName)).add(variant);
        }
        List udqs = pkg.getUdqs();
        HashSet<String> existUDQ = new HashSet<String>();
        if (udqs != null) {
            for (BmoOUQR ouqr : udqs) {
                String objectName = this.ouqrService.unifyKey(ouqr.getIntrnalKey(), ouqr.getQCategory());
                existUDQ.add(objectName);
            }
        }
        BoFilterSqlBuilder filterEngine = (BoFilterSqlBuilder)this.appContext.getBean(BoFilterSqlBuilder.class);
        for (String tableName : map.keySet()) {
            List list = (List)map.get(tableName);
            if (MetaTable.isUDQ((String)tableName) && existUDQ.contains(tableName)) continue;
            ViewColumns viewColumns = null;
            try {
                viewColumns = this.getViewColumns(tableName);
            }
            catch (Exception e) {
                log.error(tableName + " does not exist!", (Throwable)e);
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.TABLE_NOT_EXIST, new Object[]{((WebClientVariant)list.get((int)0)).Name, tableName}));
            }
            for (WebClientVariant variant : list) {
                ValidateResult result = this.validateColumns(variant, viewColumns, filterEngine);
                if (result.isValid()) continue;
                return result;
            }
        }
        return new ValidateResult(true);
    }

    private ViewColumns getViewColumns(String tableName) throws Exception {
        DimView metaDimView = this.env.loadDimViewResource(ViewParam.getTableName((String)tableName));
        DimViewEngine engine = null;
        engine = MetaTable.isUDQ((String)tableName) ? (DimViewEngine)this.appContext.getBean(UDQViewEngine.class, new Object[]{metaDimView}) : (DimViewEngine)this.appContext.getBean(DimViewEngine.class, new Object[]{metaDimView});
        return engine.getViewColumns();
    }

    private ValidateResult validateColumns(WebClientVariant wcv, ViewColumns viewColumns, BoFilterSqlBuilder filterEngine) {
        String columnName = null;
        try {
            columnName = this.judgeUserFilter(wcv.ConditionFilter, viewColumns, filterEngine);
            if (columnName != null) {
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.CONDITION_FILTER_ERROR, new Object[]{wcv.Name, columnName}));
            }
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("ConditionFilter is invalid ", (Throwable)e);
            }
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.CONDITION_FILTER_ERROR, new Object[]{wcv.Name, ""}));
        }
        try {
            columnName = this.judgeUserFilter(wcv.UserFilter, viewColumns, filterEngine);
            if (columnName != null) {
                return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.USER_FILTER_ERROR, new Object[]{wcv.Name, columnName}));
            }
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("UserFilter is invalid ", (Throwable)e);
            }
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.USER_FILTER_ERROR, new Object[]{wcv.Name, ""}));
        }
        return new ValidateResult(true);
    }

    private String judgeFilterBarLayout(String filterBarLayout, ViewColumns viewColumns, BoFilterSqlBuilder filterEngine) {
        if (filterBarLayout != null && !"".equals(filterBarLayout)) {
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(filterBarLayout);
            JsonObject obj = element.getAsJsonObject();
            JsonArray jsonArray = obj.getAsJsonArray("visibleItems");
            int size = jsonArray.size();
            for (int i = 0; i < size; ++i) {
                String columnName = jsonArray.get(i).getAsString();
                ViewColumn viewColumn = filterEngine.getViewColumnByName(viewColumns, columnName);
                if (viewColumn != null) continue;
                return columnName;
            }
        }
        return null;
    }

    private String judgeGroupByColumns(List<WebClientVariantGroupBy> groupByColumns, ViewColumns viewColumns, BoFilterSqlBuilder filterEngine) {
        if (groupByColumns != null) {
            for (WebClientVariantGroupBy col : groupByColumns) {
                ViewColumn viewColumn;
                if (!StringUtils.hasLength((String)col.ColumnName) || (viewColumn = filterEngine.getViewColumnByName(viewColumns, col.ColumnName)) != null) continue;
                return col.ColumnName;
            }
        }
        return null;
    }

    private String judgeSelectColumns(List<WebClientVariantSelectedColumn> selectColumns, ViewColumns viewColumns, BoFilterSqlBuilder filterEngine) {
        if (selectColumns != null) {
            for (WebClientVariantSelectedColumn col : selectColumns) {
                ViewColumn viewColumn;
                if (!StringUtils.hasLength((String)col.ColumnName) || (viewColumn = filterEngine.getViewColumnByName(viewColumns, col.ColumnName)) != null) continue;
                return col.ColumnName;
            }
        }
        return null;
    }

    private String judgeSortByColumns(List<WebClientVariantSortBy> sortByColumns, ViewColumns viewColumns, BoFilterSqlBuilder filterEngine) {
        if (sortByColumns != null) {
            for (WebClientVariantSortBy col : sortByColumns) {
                ViewColumn viewColumn;
                if (!StringUtils.hasLength((String)col.ColumnName) || (viewColumn = filterEngine.getViewColumnByName(viewColumns, col.ColumnName)) != null) continue;
                return col.ColumnName;
            }
        }
        return null;
    }

    private String judgeUserFilter(String userFilter, ViewColumns viewColumns, BoFilterSqlBuilder filterEngine) {
        if (userFilter == null || "".equals(userFilter)) {
            return null;
        }
        List fieldNames = FilterUtilsParser.getInstance().getColumnNames(userFilter);
        for (String fieldName : fieldNames) {
            ViewColumn viewColumn = filterEngine.getViewColumnByName(viewColumns, fieldName);
            if (viewColumn != null) continue;
            return fieldName;
        }
        return null;
    }
}

