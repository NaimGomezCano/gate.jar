/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.infra.share.feign.tcli.FeignModelInfoService
 *  com.sap.b1.infra.share.feign.tcli.entity.ModelInfo
 *  com.sap.sme.anw.analytics.core.base.LRUCache
 *  com.sap.sme.anw.analytics.core.common.Common
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.context.impl.BaseContextData
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.core.model.Model
 *  com.sap.sme.anw.analytics.core.model.ModelCommon
 *  com.sap.sme.anw.analytics.core.model.context.IAnalyticsModelContext
 *  com.sap.sme.anw.analytics.core.model.json.impl.CoreAnalyticsModelJson
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.thin.model.json.AnalyticsModelJson
 *  com.sap.sme.anw.analytics.thin.springcontext.AppContext
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  com.sap.sme.anw.analytics.type.model.json.ModelJson
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.infra.share.feign.tcli.FeignModelInfoService;
import com.sap.b1.infra.share.feign.tcli.entity.ModelInfo;
import com.sap.sme.anw.analytics.core.base.LRUCache;
import com.sap.sme.anw.analytics.core.common.Common;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.context.impl.BaseContextData;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.core.model.Model;
import com.sap.sme.anw.analytics.core.model.ModelCommon;
import com.sap.sme.anw.analytics.core.model.context.IAnalyticsModelContext;
import com.sap.sme.anw.analytics.core.model.json.impl.CoreAnalyticsModelJson;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.thin.springcontext.AppContext;
import com.sap.sme.anw.analytics.type.model.ModelType;
import com.sap.sme.anw.analytics.type.model.json.ModelJson;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class AnalyticsModelJson
extends CoreAnalyticsModelJson {
    private static final LRUCache<String, Object> modelInfos = new LRUCache(75, 10000L);
    private static final String JSON_META_PATH = "meta/json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ModelJson getModelJson(IContextData contextData, Model model) {
        ModelType modelType = model.getModelType();
        if (modelType == null) {
            return this.getJson(contextData, model);
        }
        ModelJson modelJson = null;
        if (modelType == ModelType.DATASOURCE) {
            modelJson = this.getSemanticModelJson(contextData, model, "meta/json/datasource");
        } else if (modelType == ModelType.CALCULATIONVIEW) {
            modelJson = this.getSemanticModelJson(contextData, model, "meta/json/calcview");
        }
        if (modelJson != null) {
            String modelId = model.getModelId();
            FeignModelInfoService feignModelInfoService = (FeignModelInfoService)AppContext.getContext().getBean(FeignModelInfoService.class);
            List feignModelInfos = (List)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)modelInfos, (String)modelId, (Object)feignModelInfoService, (String)"getFeignModelInfo", (Class[])new Class[]{String.class}, (Object[])new Object[]{modelId});
            if (feignModelInfos != null) {
                for (ModelInfo feignModelInfo : feignModelInfos) {
                    if (!StringUtils.equals((CharSequence)modelId, (CharSequence)feignModelInfo.getModelId())) continue;
                    modelJson.setDescription(feignModelInfo.getDescription());
                    break;
                }
            }
        }
        return modelJson;
    }

    protected ModelJson getSemanticModelJson(IContextData contextData, Model model, String path) {
        IAnalyticsModelContext modelContext = (IAnalyticsModelContext)ServiceFactory.getService(IAnalyticsModelContext.class);
        String objectName = modelContext.getObjectName(contextData, model);
        String fileName = path + "/" + ModelCommon.getModelPath((Model)model) + objectName + ".json";
        String s = Common.readResourceFile((String)fileName);
        if (s == null) {
            return null;
        }
        try {
            return (ModelJson)objectMapper.readValue(s, ModelJson.class);
        }
        catch (Exception e) {
            AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.WARNING, (Throwable)e);
            return null;
        }
    }
}

