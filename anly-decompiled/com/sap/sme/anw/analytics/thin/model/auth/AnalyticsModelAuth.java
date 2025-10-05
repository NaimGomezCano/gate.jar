/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.feign.tcli.FeignPermissionService
 *  com.sap.b1.infra.share.feign.tcli.entity.Permission
 *  com.sap.sme.anw.analytics.core.base.LRUCache
 *  com.sap.sme.anw.analytics.core.config.ConfigOption
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.context.impl.BaseContextData
 *  com.sap.sme.anw.analytics.core.exception.QueryException
 *  com.sap.sme.anw.analytics.core.model.Model
 *  com.sap.sme.anw.analytics.core.model.auth.IAnalyticsModelAuth
 *  com.sap.sme.anw.analytics.thin.model.auth.AnalyticsModelAuth
 *  com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig
 *  com.sap.sme.anw.analytics.thin.springcontext.AppContext
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.model.auth;

import com.sap.b1.infra.share.feign.tcli.FeignPermissionService;
import com.sap.b1.infra.share.feign.tcli.entity.Permission;
import com.sap.sme.anw.analytics.core.base.LRUCache;
import com.sap.sme.anw.analytics.core.config.ConfigOption;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.context.impl.BaseContextData;
import com.sap.sme.anw.analytics.core.exception.QueryException;
import com.sap.sme.anw.analytics.core.model.Model;
import com.sap.sme.anw.analytics.core.model.auth.IAnalyticsModelAuth;
import com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig;
import com.sap.sme.anw.analytics.thin.springcontext.AppContext;
import com.sap.sme.anw.analytics.type.model.ModelType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class AnalyticsModelAuth
implements IAnalyticsModelAuth {
    private static final LRUCache<String, Object> permissions = new LRUCache(75, 10000L);

    public void checkAuth(IContextData contextData, Model model) {
        if (((ProfileConfig)AppContext.getContext().getBean(ProfileConfig.class)).isDevProfile()) {
            return;
        }
        if (contextData.getConfig().getBooleanConfigValue(ConfigOption.OPTION_MODEL_NO_AUTH.name())) {
            return;
        }
        ModelType modelType = model.getModelType();
        if (modelType != ModelType.DATASOURCE && modelType != ModelType.CALCULATIONVIEW) {
            return;
        }
        String modelId = model.getModelId();
        FeignPermissionService feignPermissionService = (FeignPermissionService)AppContext.getContext().getBean(FeignPermissionService.class);
        ArrayList<Permission> modelPermissions = new ArrayList<Permission>();
        Permission modelPermission = new Permission();
        modelPermission.setObjectId(modelId);
        modelPermission.setPermissionType("read_only");
        modelPermissions.add(modelPermission);
        List feignPermissions = (List)BaseContextData.retrieveContextCacheObject((IContextData)contextData, (LRUCache)permissions, (String)modelId, (Object)feignPermissionService, (String)"getFeignPermission", (Class[])new Class[]{List.class}, (Object[])new Object[]{modelPermissions});
        if (feignPermissions != null) {
            for (Permission feignPermission : feignPermissions) {
                if (!StringUtils.equals((CharSequence)modelId, (CharSequence)feignPermission.getObjectId()) || feignPermission.getGranted().booleanValue()) continue;
                throw new QueryException(contextData, "QUERY_NO_AUTH_MODEL", new Object[]{modelId});
            }
        }
    }
}

