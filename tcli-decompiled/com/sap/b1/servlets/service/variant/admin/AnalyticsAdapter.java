/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infra.engines.ResourceDefs
 *  com.sap.b1.servlets.service.variant.admin.AnalyticsAdapter
 *  com.sap.b1.tcli.resources.TranslationUtil
 *  gen.bean.BmoOWVT
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.admin;

import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infra.engines.ResourceDefs;
import com.sap.b1.tcli.resources.TranslationUtil;
import gen.bean.BmoOWVT;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsAdapter
implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsAdapter.class);
    public static final String ANLY_OBJ_NAME = "ANLY";
    public static final String GENERAL_OVERVIEW_ID = "MDO10001";
    @Autowired
    TranslationUtil translation;
    @Autowired
    private DBTypeConfig dbTypeConfig;
    private Map<String, String> idTitleMapping;

    public void excludeAnalyticsObjectsInMSSQL(List<BmoOWVT> objectsWithSysVariants) {
        if (this.dbTypeConfig.get() == DBType.HANA) {
            return;
        }
        objectsWithSysVariants.removeIf(v -> this.isHanaSpecificObject(v.getObjName(), v.getViewId()));
    }

    public boolean isHanaSpecificObject(String objectName, String viewId) {
        return ANLY_OBJ_NAME.equals(objectName) && !GENERAL_OVERVIEW_ID.equals(viewId);
    }

    public boolean isAnalyticsObject(String objectName) {
        return ANLY_OBJ_NAME.equals(objectName);
    }

    public String getViewObjectNameI18N(String viewType, String viewId) {
        String type;
        if ("ChartContainer".equalsIgnoreCase(viewType)) {
            type = "chart";
        } else if ("Dashboard".equalsIgnoreCase(viewType)) {
            type = "dashboard";
        } else {
            logger.warn("not a supported analytics view type: " + viewType);
            return this.defaultName(viewType, viewId);
        }
        String titleId = (String)this.idTitleMapping.get(viewId + "." + type);
        if (titleId == null) {
            String name = this.defaultName(viewType, viewId);
            logger.warn("can't find this analytics view: " + name);
            return name;
        }
        String translatedContent = this.translation.translate(titleId, this.getPath(viewId, type));
        return translatedContent;
    }

    private String defaultName(String viewType, String viewId) {
        return String.format("%s - %s", viewType, viewId);
    }

    private String getPath(String viewId, String type) {
        String path = ResourceDefs.getPath((String)viewId, (String)type);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    public void afterPropertiesSet() throws Exception {
        this.idTitleMapping = this.loadViewIdTitleIdMapping();
    }

    private Map<String, String> loadViewIdTitleIdMapping() {
        HashMap<String, String> idTitleMapping = new HashMap<String, String>();
        idTitleMapping.put("ITW80002.chart", "{i18n#4710770d-005f-4d1b-abc5-5d9b8de2b52c/title}");
        idTitleMapping.put("IVL80002.chart", "{i18n#a93257a2-cf67-4ad5-838b-041c90b0ec0b/title}");
        idTitleMapping.put("JDT80002.chart", "{i18n#f096c1b6-1c21-4086-b052-a7c68c3e4f4d/title}");
        idTitleMapping.put("POR80002.chart", "{i18n#812d871d-8498-48d8-80d2-8c5518ae038e/title}");
        idTitleMapping.put("POR80003.chart", "{i18n#bd87b901-1d13-41ec-9ebd-5d6c87cb7a63/title}");
        idTitleMapping.put("POR80004.chart", "{i18n#6650c8a7-f010-43f9-8933-dd4c296950fa/title}");
        idTitleMapping.put("QUT10001.chart", "{i18n#5964692b-bf70-4ea1-89a0-61ace66e5c53/title}");
        idTitleMapping.put("QUT10002.chart", "{i18n#6bddf26c-49e8-40f4-bca0-7df0b77cb16e/title}");
        idTitleMapping.put("RDR80002.chart", "{i18n#71356de7-5594-4096-b6a9-99f295257d14/title}");
        idTitleMapping.put("RDR80003.chart", "{i18n#108e6bab-e508-4f67-8eff-043af05f5a78/title}");
        idTitleMapping.put("RDR80004.chart", "{i18n#7c073c76-cf65-4283-92ac-96ce85c1b737/title}");
        idTitleMapping.put("ITW90002.dashboard", "{i18n#76881004-9e99-4275-af05-c85dfac7475f/title}");
        idTitleMapping.put("IVL90002.dashboard", "{i18n#411ec169-c970-4f85-995e-f1f0f5abde01/title}");
        idTitleMapping.put("JDT90002.dashboard", "{i18n#9ce4c4eb-ee52-47f8-957d-32627f7820c7/title}");
        idTitleMapping.put("MDO10001.dashboard", "{i18n#4ce5052d-7d22-46b9-904f-3cbb58f86bac/title}");
        idTitleMapping.put("POR90002.dashboard", "{i18n#c5919dcc-3567-4da3-9c56-9f46a2ca8107/title}");
        idTitleMapping.put("POR90003.dashboard", "{i18n#8637f5eb-60cd-4473-98e0-3c5f35867e0a/title}");
        idTitleMapping.put("POR90004.dashboard", "{i18n#9417ee22-b909-4e03-bbc6-0d6c64c1110f/title}");
        idTitleMapping.put("QUT20001.dashboard", "{i18n#f7969f4f-0b7a-47e3-b7be-ed43c220456a/title}");
        idTitleMapping.put("QUT20002.dashboard", "{i18n#9d524f07-04c5-44c7-926c-d2bdd11c5e21/title}");
        idTitleMapping.put("RDR90002.dashboard", "{i18n#8109b145-3819-4ce4-8111-1c22164bdaf6/title}");
        idTitleMapping.put("RDR90003.dashboard", "{i18n#f773ecd0-257f-4882-9803-e7f43e133805/title}");
        idTitleMapping.put("RDR90004.dashboard", "{i18n#2596e88c-aeb8-4529-b914-1a6d45852970/title}");
        return idTitleMapping;
    }
}

