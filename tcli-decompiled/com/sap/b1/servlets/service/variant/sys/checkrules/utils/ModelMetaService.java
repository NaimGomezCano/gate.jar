/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.dimview.DimViewEngine
 *  com.sap.b1.dimview.ViewColumn
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.flp.tile.TileService
 *  com.sap.b1.flp.tile.TileServiceResponse
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.meta.dimviewds.DimView
 *  com.sap.b1.infra.meta.tile.Action
 *  com.sap.b1.infra.meta.tile.Group
 *  com.sap.b1.infra.meta.tile.LaunchPage
 *  com.sap.b1.infra.meta.tile.Tile
 *  com.sap.b1.service.view.ViewParam
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ModelMetaService
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.tcli.observer.feign.AnlyFeign
 *  com.sap.b1.util.StringUtil
 *  com.sap.sme.anw.analytics.type.model.AnalysisObject
 *  com.sap.sme.anw.analytics.type.model.ModelListResponse
 *  com.sap.sme.anw.analytics.type.model.ModelMetaObject
 *  com.sap.sme.anw.analytics.type.model.ModelMetaResponse
 *  com.sap.sme.anw.analytics.type.model.ModelObject
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  com.sap.sme.anw.analytics.type.model.ViewType
 *  lombok.Generated
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.stereotype.Service
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.utils;

import com.sap.b1.app.base.AppEnv;
import com.sap.b1.dimview.DimViewEngine;
import com.sap.b1.dimview.ViewColumn;
import com.sap.b1.dimview.ViewColumns;
import com.sap.b1.flp.tile.TileService;
import com.sap.b1.flp.tile.TileServiceResponse;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.infra.meta.dimviewds.DimView;
import com.sap.b1.infra.meta.tile.Action;
import com.sap.b1.infra.meta.tile.Group;
import com.sap.b1.infra.meta.tile.LaunchPage;
import com.sap.b1.infra.meta.tile.Tile;
import com.sap.b1.service.view.ViewParam;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.tcli.observer.feign.AnlyFeign;
import com.sap.b1.util.StringUtil;
import com.sap.sme.anw.analytics.type.model.AnalysisObject;
import com.sap.sme.anw.analytics.type.model.ModelListResponse;
import com.sap.sme.anw.analytics.type.model.ModelMetaObject;
import com.sap.sme.anw.analytics.type.model.ModelMetaResponse;
import com.sap.sme.anw.analytics.type.model.ModelObject;
import com.sap.sme.anw.analytics.type.model.ModelType;
import com.sap.sme.anw.analytics.type.model.ViewType;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ModelMetaService {
    @Generated
    private static final Logger log = LogManager.getLogger(ModelMetaService.class);
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private AppEnv env;
    @Autowired
    AnlyFeign anlyFeign;
    @Autowired
    TileService tileService;
    private static final String analyticsGuid = "8e6d8259-e328-48bd-86cb-aab722b62e6c";

    public boolean isCalculationView(String modelType) {
        return ModelType.CALCULATIONVIEW.toString().equals(modelType);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ViewColumns getViewColumnsFromModel(String objectName, String modelType, String modelId) {
        ViewColumns viewColumns = new ViewColumns();
        if (this.isUDX(objectName)) {
            return this.getViewColumns(objectName);
        }
        if (this.isCalculationView(modelType) || this.isANLY(objectName)) {
            ModelMetaObject request = new ModelMetaObject();
            request.setModelId(modelId);
            request.setModelType(ModelType.CALCULATIONVIEW);
            try {
                ModelMetaResponse modelMeta = this.anlyFeign.getModelMeta(request);
                if (modelMeta == null) {
                    throw new ValidateException(RulesErrorEnum.TYPE_MODEL_NOT_EXIST, new Object[]{modelId});
                }
                List anlyObjs = modelMeta.getAnalysisObjects();
                if (anlyObjs == null) return viewColumns;
                if (anlyObjs.size() <= 0) return viewColumns;
                return this.getViewColumns(anlyObjs);
            }
            catch (Exception e) {
                log.error("check model error:", (Throwable)e);
                throw new ValidateException(RulesErrorEnum.TYPE_MODEL_NOT_EXIST, new Object[]{modelId});
            }
        }
        if ("ODATA".equals(modelType)) return this.getViewColumns(modelId);
        if (ModelType.LISTVIEW.toString().equals(modelType)) {
            return this.getViewColumns(modelId);
        }
        log.error("Unhandled model: ModelId [{}], ModelType [{}]", (Object)modelId, (Object)modelType);
        return viewColumns;
    }

    private boolean isUDX(String objectName) {
        return MetaTable.isUDT((String)objectName) || MetaTable.isUDQ((String)objectName);
    }

    private boolean isANLY(String objectName) {
        return "ANLY".equals(objectName);
    }

    private ViewColumns getViewColumns(List<AnalysisObject> anlyObjs) {
        ViewColumns viewColumns = new ViewColumns();
        for (AnalysisObject anlyObj : anlyObjs) {
            ViewColumn vc = new ViewColumn(null, anlyObj.getFieldName(), anlyObj.getId(), null);
            viewColumns.add(vc);
        }
        return viewColumns;
    }

    private ViewColumns getViewColumns(String tableName) {
        try {
            DimViewEngine engine = null;
            if (MetaTable.isUDQ((String)tableName)) {
                return new ViewColumnsWithNoCheck();
            }
            DimView metaDimView = this.env.loadDimViewResource(ViewParam.getTableName((String)tableName));
            engine = (DimViewEngine)this.appContext.getBean(DimViewEngine.class, new Object[]{metaDimView});
            return engine.getViewColumns();
        }
        catch (Exception e) {
            log.error("load meta table from:{},exception", (Object)tableName, (Object)e);
            throw new ValidateException(RulesErrorEnum.TYPE_MODEL_NOT_EXIST, new Object[]{tableName});
        }
    }

    public String getModelDescription(WebClientVariant variant, StringBuffer groupId) {
        String desc = "";
        if (this.isANLY(variant.ObjectName)) {
            desc = this.searchDescByViewId(variant.ViewId);
        } else if ("List".equals(variant.ViewType)) {
            ModelListResponse response = this.anlyFeign.getModelList(ViewType.LISTVIEW);
            List modelObjects = response.getModelObjects();
            for (ModelObject modelObject : modelObjects) {
                if (!variant.ObjectName.equals(modelObject.getModelId())) continue;
                Map node = (Map)modelObject.getInfo();
                groupId.append((String)node.get("groupId"));
                return modelObject.getModelDescription();
            }
        }
        if (StringUtil.isEmpty((String)desc)) {
            log.error("failed to find view for variantId {}, variantType {}, ObjectName {}", (Object)variant.ViewId, (Object)variant.ViewType, (Object)variant.ObjectName);
        }
        return desc;
    }

    private String searchDescByViewId(String viewId) {
        try {
            List groups;
            TileServiceResponse response = this.tileService.loadCatalogs();
            LaunchPage data = response.data;
            if (data != null && (groups = data.groups) != null) {
                for (Group group : groups) {
                    List tiles;
                    if (!analyticsGuid.equals(group.guid) || (tiles = group.tiles) == null) continue;
                    for (Tile tile : tiles) {
                        String bind;
                        String target;
                        Action action;
                        boolean visible = tile.visible;
                        if (!visible || (action = tile.action) == null || (target = action.target) == null || !"url".equals(bind = action.bind) || !target.contains("view=") || !viewId.equals(target.substring(target.indexOf("view=") + 5))) continue;
                        return tile.text;
                    }
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), (Throwable)e);
        }
        return "";
    }
}

