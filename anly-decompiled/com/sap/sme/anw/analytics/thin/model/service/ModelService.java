/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.sap.b1.infra.share.feign.tcli.FeignAnalyticsObjectService
 *  com.sap.b1.infra.share.feign.tcli.FeignModelInfoService
 *  com.sap.b1.infra.share.feign.tcli.FeignOUQRController
 *  com.sap.b1.infra.share.feign.tcli.FeignTileService
 *  com.sap.b1.infra.share.feign.tcli.entity.ModelInfo
 *  com.sap.b1.infra.share.feign.tcli.entity.QueryCategory
 *  com.sap.b1.infra.share.feign.tcli.entity.SearchResponse
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Action
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Catalog
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Group
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.LaunchPage
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Tile
 *  com.sap.b1.tcli.object.domain.ObjectDefs
 *  com.sap.b1.tcli.object.domain.Objects
 *  com.sap.b1.util.StringUtil
 *  com.sap.sme.anw.analytics.core.base.LRUCache
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.context.impl.BaseContextData
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.core.model.service.impl.ModelServiceImpl
 *  com.sap.sme.anw.analytics.thin.model.service.ModelService
 *  com.sap.sme.anw.analytics.thin.springcontext.AppContext
 *  com.sap.sme.anw.analytics.type.model.LinkableGroup
 *  com.sap.sme.anw.analytics.type.model.ModelListResponse
 *  com.sap.sme.anw.analytics.type.model.ModelObject
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  com.sap.sme.anw.analytics.type.model.ViewType
 *  gen.bean.BmoOUQR
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.sme.anw.analytics.thin.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.b1.infra.share.feign.tcli.FeignAnalyticsObjectService;
import com.sap.b1.infra.share.feign.tcli.FeignModelInfoService;
import com.sap.b1.infra.share.feign.tcli.FeignOUQRController;
import com.sap.b1.infra.share.feign.tcli.FeignTileService;
import com.sap.b1.infra.share.feign.tcli.entity.ModelInfo;
import com.sap.b1.infra.share.feign.tcli.entity.QueryCategory;
import com.sap.b1.infra.share.feign.tcli.entity.SearchResponse;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Action;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Catalog;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Group;
import com.sap.b1.infra.share.feign.tcli.entity.tile.LaunchPage;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Tile;
import com.sap.b1.tcli.object.domain.ObjectDefs;
import com.sap.b1.tcli.object.domain.Objects;
import com.sap.b1.util.StringUtil;
import com.sap.sme.anw.analytics.core.base.LRUCache;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.context.impl.BaseContextData;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.core.model.service.impl.ModelServiceImpl;
import com.sap.sme.anw.analytics.thin.springcontext.AppContext;
import com.sap.sme.anw.analytics.type.model.LinkableGroup;
import com.sap.sme.anw.analytics.type.model.ModelListResponse;
import com.sap.sme.anw.analytics.type.model.ModelObject;
import com.sap.sme.anw.analytics.type.model.ModelType;
import com.sap.sme.anw.analytics.type.model.ViewType;
import gen.bean.BmoOUQR;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelService
extends ModelServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);
    private static final LRUCache<String, Object> catalogs = new LRUCache(75, 10000L);
    private static final LRUCache<String, Object> modelInfos = new LRUCache(75, 10000L);
    private static final LRUCache<String, Object> searchResponse = new LRUCache(75, 10000L);
    private static final LRUCache<String, Object> getObjectsResponse = new LRUCache(75, 10000L);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String UDQ_GROUP_ID = "UDQ";

    public ModelListResponse getModelList(IContextData contextData, boolean auto, ViewType viewType) {
        ModelListResponse modelListResponse = new ModelListResponse();
        FeignTileService feignTileService = (FeignTileService)AppContext.getContext().getBean(FeignTileService.class);
        Catalog catalog = (Catalog)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)catalogs, null, (Object)feignTileService, (String)"getFeignCatalog", (Class[])new Class[0], (Object[])new Object[0]);
        ArrayList modelObjects = ViewType.DETAILVIEW.equals((Object)viewType) || ViewType.LISTVIEW.equals((Object)viewType) ? this.getModels(contextData, catalog, auto, viewType) : new ArrayList();
        modelListResponse.setModelObjects(modelObjects);
        return modelListResponse;
    }

    public List<LinkableGroup> getLinkableGroups(IContextData contextData) {
        FeignTileService feignTileService = (FeignTileService)AppContext.getContext().getBean(FeignTileService.class);
        Catalog catalog = (Catalog)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)catalogs, null, (Object)feignTileService, (String)"getFeignCatalog", (Class[])new Class[0], (Object[])new Object[0]);
        return this.getLinkableGroups(contextData, catalog);
    }

    private List<ModelObject> getModels(IContextData contextData, Catalog catalog, boolean auto, ViewType viewType) {
        FeignModelInfoService feignModelInfoService;
        String modelIds;
        List feignModelInfos;
        List groups;
        LaunchPage data;
        ArrayList<ModelObject> models = new ArrayList<ModelObject>();
        LinkedHashMap<String, ModelObject> map = new LinkedHashMap<String, ModelObject>();
        Objects listAsUrlTargets = null;
        Objects objects = this.getAnalyticObjects(contextData);
        if (objects == null) {
            throw new RuntimeException("Can`t get analytics objects");
        }
        if (viewType.equals((Object)ViewType.DETAILVIEW)) {
            Objects detailViewObj = new Objects();
            for (Map.Entry entry : objects.entrySet()) {
                def = (ObjectDefs)entry.getValue();
                if (!def.analyticViews) continue;
                detailViewObj.put((Object)((String)entry.getKey()), (Object)((ObjectDefs)entry.getValue()));
            }
            listAsUrlTargets = detailViewObj;
        } else if (viewType.equals((Object)ViewType.LISTVIEW)) {
            Objects listViewObj = new Objects();
            for (Map.Entry entry : objects.entrySet()) {
                def = (ObjectDefs)entry.getValue();
                if (!def.analyticList) continue;
                listViewObj.put((Object)((String)entry.getKey()), (Object)((ObjectDefs)entry.getValue()));
            }
            listAsUrlTargets = listViewObj;
        }
        if (listAsUrlTargets == null || listAsUrlTargets.isEmpty()) {
            return models;
        }
        HashSet<String> modelIdSet = new HashSet<String>();
        if (catalog != null && (data = catalog.getData()) != null && (groups = data.getGroups()) != null) {
            for (Group group : groups) {
                String groupId = group.getGroupId();
                List tiles = group.getTiles();
                if (tiles == null) continue;
                for (Tile tile : tiles) {
                    String modelId;
                    JsonNode viewNode;
                    String target;
                    String targetFullName;
                    boolean visible = tile.isVisible();
                    String string = targetFullName = tile.getAction() == null ? null : tile.getAction().getTarget();
                    if (!visible && !"#boOUQR-app".equals(targetFullName)) continue;
                    String text = tile.getText();
                    Action action = tile.getAction();
                    if (action == null || (target = action.getTarget()) == null) continue;
                    String bind = action.getBind();
                    String view = action.getView();
                    if (bind.equals("url")) {
                        if (target.startsWith("#bo") && target.endsWith("-app")) {
                            target = target.substring(3, target.lastIndexOf("-app"));
                        } else if (target.startsWith("#webclient-") && target.contains("&")) {
                            target = target.substring(11, target.indexOf("&"));
                        }
                        if (listAsUrlTargets.containsKey((Object)target)) {
                            bind = "list";
                        }
                    }
                    if (target.equals("OUQR")) {
                        this.addOUQRModels(contextData, models, true);
                        continue;
                    }
                    if ("list".equals(bind)) {
                        ObjectDefs objectDefs = (ObjectDefs)listAsUrlTargets.get((Object)target);
                        if (objectDefs == null && !target.startsWith("@") || modelIdSet.contains(target)) continue;
                        modelIdSet.add(target);
                        ObjectNode infoNode = objectMapper.createObjectNode();
                        String table = null;
                        if (objectDefs != null) {
                            table = objectDefs.table;
                            infoNode.put("view", objectDefs.view);
                        }
                        if (StringUtil.isEmpty(table)) {
                            infoNode.put("actionTarget", target);
                        } else {
                            infoNode.put("actionTarget", table);
                        }
                        infoNode.put("groupId", groupId);
                        ModelObject modelObject = this.createModel(target, ModelType.LISTVIEW, text, infoNode);
                        models.add(modelObject);
                        continue;
                    }
                    if (!"url".equals(bind) || view == null) continue;
                    try {
                        viewNode = objectMapper.readTree(view);
                    }
                    catch (IOException e) {
                        AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.ERROR, (Throwable)e);
                        continue;
                    }
                    JsonNode viewModelIdNode = viewNode.get("modelId");
                    if (viewModelIdNode == null || (modelId = viewModelIdNode.asText()) == null && modelIdSet.contains(modelId)) continue;
                    modelIdSet.add(target);
                    JsonNode viewInfoNode = viewNode.get("info");
                    ModelObject modelObject = new ModelObject();
                    modelObject.setModelId(modelId);
                    modelObject.setModelType(ModelType.CALCULATIONVIEW);
                    modelObject.setInfo((Object)viewInfoNode);
                    models.add(modelObject);
                    map.put(modelId, modelObject);
                }
            }
        }
        if (!map.isEmpty() && (feignModelInfos = (List)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)modelInfos, (String)(modelIds = StringUtils.join(map.keySet(), (String)",")), (Object)(feignModelInfoService = (FeignModelInfoService)AppContext.getContext().getBean(FeignModelInfoService.class)), (String)"getFeignModelInfo", (Class[])new Class[]{String.class}, (Object[])new Object[]{modelIds})) != null) {
            for (ModelInfo feignModelInfo : feignModelInfos) {
                String modelId = feignModelInfo.getModelId();
                ModelObject modelObject = (ModelObject)map.get(modelId);
                if (modelObject == null) continue;
                modelObject.setModelDescription(feignModelInfo.getDescription());
            }
        }
        return models;
    }

    private SearchResponse getUdqInfo(IContextData contextData) {
        FeignOUQRController feignOUQRController = (FeignOUQRController)AppContext.getContext().getBean(FeignOUQRController.class);
        SearchResponse feignSearchResponse = (SearchResponse)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)searchResponse, null, (Object)feignOUQRController, (String)"feignSearch", (Class[])new Class[]{String.class}, (Object[])new Object[]{""});
        return feignSearchResponse;
    }

    private Objects getAnalyticObjects(IContextData contextData) {
        FeignAnalyticsObjectService feignAnalyticsObjectService = (FeignAnalyticsObjectService)AppContext.getContext().getBean(FeignAnalyticsObjectService.class);
        Objects feignSearchResponse = (Objects)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)getObjectsResponse, null, (Object)feignAnalyticsObjectService, (String)"getObjects", (Class[])new Class[0], (Object[])new Object[0]);
        return feignSearchResponse;
    }

    private List<LinkableGroup> getLinkableGroups(IContextData contextData, Catalog catalog) {
        ArrayList<LinkableGroup> linkableGroups = new ArrayList<LinkableGroup>();
        if (catalog == null || catalog.getData() == null || catalog.getData().getGroups() == null) {
            return linkableGroups;
        }
        Objects objects = this.getAnalyticObjects(contextData);
        if (objects == null) {
            throw new RuntimeException("Can`t get analytics objects");
        }
        Objects listViewObj = new Objects();
        for (Map.Entry entry : objects.entrySet()) {
            ObjectDefs def = (ObjectDefs)entry.getValue();
            if (!def.analyticList) continue;
            listViewObj.put((Object)((String)entry.getKey()), (Object)((ObjectDefs)entry.getValue()));
        }
        if (listViewObj.isEmpty()) {
            return linkableGroups;
        }
        LinkableGroup administrationGroup = null;
        for (Group group : catalog.getData().getGroups()) {
            String groupId = group.getGroupId();
            String groupText = group.getText();
            String groupIcon = group.getIcon();
            List tiles = group.getTiles();
            if (tiles == null) continue;
            ArrayList<ModelObject> models = new ArrayList<ModelObject>();
            HashSet<String> modelIdSet = new HashSet<String>();
            for (Tile tile : tiles) {
                ObjectNode infoNode;
                String bind;
                String target;
                if (!tile.isVisible()) continue;
                String text = tile.getText();
                Action action = tile.getAction();
                if (action == null || (target = action.getTarget()) == null || (bind = action.getBind()).equals("addMode")) continue;
                if (bind.equals("url")) {
                    if (target.startsWith("#bo") && target.endsWith("-app")) {
                        target = target.substring(3, target.lastIndexOf("-app"));
                    } else if (target.startsWith("#webclient-") && target.contains("&")) {
                        target = target.substring(11, target.indexOf("&"));
                    }
                }
                if (target.equals("Config")) {
                    infoNode = objectMapper.createObjectNode();
                    models.add(this.createModel(target, ModelType.VIEW, text, infoNode));
                    modelIdSet.add(target);
                    continue;
                }
                if (modelIdSet.contains(target) || target.equals("OSCN")) continue;
                if (target.equals("OUQR")) {
                    this.addOUQRModels(contextData, models, false);
                    groupId = UDQ_GROUP_ID;
                    groupText = tile.getText();
                    continue;
                }
                if ((groupId.equals("UDO") || groupId.equals("UDT")) && "list".equals(bind)) {
                    infoNode = objectMapper.createObjectNode();
                    infoNode.put("analyticViews", true);
                    models.add(this.createModel(target, ModelType.LISTVIEW, text, infoNode));
                    modelIdSet.add(target);
                    continue;
                }
                if (!listViewObj.containsKey((Object)target)) continue;
                ObjectDefs objectDefs = (ObjectDefs)listViewObj.get((Object)target);
                ObjectNode infoNode2 = objectMapper.createObjectNode();
                if (objectDefs.analyticViews) {
                    infoNode2.put("analyticViews", true);
                }
                if (objectDefs.analyticViewsOnRows) {
                    infoNode2.put("analyticViewsOnRows", true);
                }
                models.add(this.createModel(target, ModelType.LISTVIEW, text, infoNode2));
                modelIdSet.add(target);
            }
            if (models.isEmpty()) continue;
            LinkableGroup linkableGroup = new LinkableGroup();
            linkableGroup.setGroupId(groupId);
            linkableGroup.setText(groupText);
            linkableGroup.setIcon(groupIcon != null ? groupIcon : "sap-icon://activity-items");
            linkableGroup.setLinkableObjects(models);
            if ("ae61a755-57b6-43ab-8514-cdc654758c87".equals(groupId)) {
                administrationGroup = linkableGroup;
                continue;
            }
            linkableGroups.add(linkableGroup);
        }
        if (administrationGroup != null) {
            linkableGroups.add(administrationGroup);
        }
        return linkableGroups;
    }

    private ModelObject createModel(String target, ModelType modelType, String text, ObjectNode infoNode) {
        ModelObject modelObject = new ModelObject();
        modelObject.setModelId(target);
        modelObject.setModelType(modelType);
        modelObject.setModelDescription(text);
        modelObject.setInfo((Object)infoNode);
        return modelObject;
    }

    private void addOUQRModels(IContextData contextData, List<ModelObject> models, boolean hasInfo) {
        SearchResponse searchResponse = this.getUdqInfo(contextData);
        if (searchResponse == null || searchResponse.size() == 0) {
            return;
        }
        for (QueryCategory category : searchResponse) {
            for (BmoOUQR ouqr : category.nodes) {
                if ("N".equals(ouqr.getAnaActive())) continue;
                String ouqrTarget = ouqr.get__guid();
                String ouqrText = ouqr.getQName() + "(" + category.category.getCatName() + ")";
                ObjectNode infoNode = objectMapper.createObjectNode();
                if (hasInfo) {
                    infoNode.put("actionTarget", ouqrTarget);
                    infoNode.put("groupId", UDQ_GROUP_ID);
                }
                models.add(this.createModel(ouqrTarget, ModelType.LISTVIEW, ouqrText, infoNode));
            }
        }
    }
}

