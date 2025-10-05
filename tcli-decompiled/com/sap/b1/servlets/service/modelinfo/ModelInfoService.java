/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.feign.tcli.entity.ModelInfo
 *  com.sap.b1.servlets.service.modelinfo.ModelInfoService
 *  com.sap.b1.spring.RequestMappingTx
 *  com.sap.b1.tcli.resources.TranslationUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.modelinfo;

import com.sap.b1.infra.share.feign.tcli.entity.ModelInfo;
import com.sap.b1.spring.RequestMappingTx;
import com.sap.b1.tcli.resources.TranslationUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelInfoService {
    @Autowired
    private TranslationUtil translationUtil;

    @RequestMappingTx(path={"/modelInfo.svc"})
    public List<ModelInfo> getModelInfo(@RequestParam(value="modelId") String modelId) throws Exception {
        String[] modelIds = StringUtils.split((String)modelId, (String)",");
        ArrayList<ModelInfo> modelInfos = new ArrayList<ModelInfo>();
        if (modelIds != null) {
            for (String id : modelIds) {
                String key = "{i18n#" + id.replace("::", "__") + "}";
                String desc = this.translationUtil.translate(key, "dataSource/DataSource.properties");
                ModelInfo modelInfo = new ModelInfo();
                modelInfo.setModelId(id);
                modelInfo.setDescription(desc);
                modelInfos.add(modelInfo);
            }
        }
        return modelInfos;
    }
}

