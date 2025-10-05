/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.infa.meta.dashboard.Dashboard
 *  com.sap.b1.infa.meta.dashboard.Dataset
 *  com.sap.b1.infa.meta.dashboard.Widget
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.OverviewCustomizationResolveUtils
 *  com.sap.b1.tcli.service.owsv.domain.OverviewCustomization
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.infa.meta.dashboard.Dashboard;
import com.sap.b1.infa.meta.dashboard.Dataset;
import com.sap.b1.infa.meta.dashboard.Widget;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException;
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelType;
import com.sap.b1.tcli.service.owsv.domain.OverviewCustomization;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exception performing whole class analysis ignored.
 */
public class OverviewCustomizationResolveUtils {
    @Generated
    private static final Logger log = LogManager.getLogger(OverviewCustomizationResolveUtils.class);

    public static List<ModelType> getDatasets(String overviewCustomization, boolean included) {
        ArrayList<ModelType> modelTypes = new ArrayList<ModelType>();
        List customWidgets = OverviewCustomizationResolveUtils.getCustomWidgets((String)overviewCustomization, (boolean)included);
        for (Object item : customWidgets) {
            List datasets = (List)((Map)item).get("datasets");
            for (Object dataset : datasets) {
                String modelType = (String)((Map)dataset).get("modelType");
                String modelId = (String)((Map)dataset).get("modelId");
                modelTypes.add(new ModelType(modelType, modelId));
            }
        }
        return modelTypes;
    }

    public static List<ModelType> getAllWidgetsDatasets(String overviewCustomization, Dashboard metaData) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ModelType> modelTypes = new ArrayList<ModelType>();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OverviewCustomization custom = (OverviewCustomization)mapper.readValue(overviewCustomization, OverviewCustomization.class);
            List customWidgets = custom.getCustomWidgets();
            for (Widget item : customWidgets) {
                String widgetId = item.guid;
                ArrayList datasets = item.datasets;
                Dataset dataset = null;
                if (datasets != null && datasets.size() > 0) {
                    dataset = (Dataset)datasets.get(0);
                } else {
                    Widget widget = metaData.widgets.stream().filter(o -> widgetId.equals(o.guid)).findFirst().orElse(null);
                    if (widget != null) {
                        dataset = (Dataset)widget.datasets.get(0);
                    }
                }
                if (dataset == null) continue;
                String modelType = dataset.modelType;
                String modelId = dataset.modelId;
                modelTypes.add(new ModelType(modelType, modelId));
            }
        }
        catch (JsonProcessingException e) {
            log.error("OverviewCustomizationResolveUtils.getCustomWidgets Resolve json error.");
            throw new ValidateException(RulesErrorEnum.INVALID_DATA_PKG, new Object[0]);
        }
        return modelTypes;
    }

    public static List getCustomWidgets(String overviewCustomization, boolean included) {
        if (StringUtils.isBlank((CharSequence)overviewCustomization)) {
            return new ArrayList();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map custom = (Map)mapper.readValue(overviewCustomization, Map.class);
            List customWidgets = (List)custom.get("customWidgets");
            if (included) {
                return customWidgets.stream().filter(item -> ((Map)item).get("included") != null).collect(Collectors.toList());
            }
            return customWidgets.stream().filter(item -> ((Map)item).get("included") == null).collect(Collectors.toList());
        }
        catch (JsonProcessingException e) {
            log.error("OverviewCustomizationResolveUtils.getCustomWidgets Resolve json error.", (Throwable)e);
            throw new ValidateException(RulesErrorEnum.INVALID_DATA_PKG, new Object[0]);
        }
    }

    public static List<Map<String, List>> getBindings(String overviewCustomization, boolean included) {
        ArrayList<Map<String, List>> result = new ArrayList<Map<String, List>>();
        List customWidgets = OverviewCustomizationResolveUtils.getCustomWidgets((String)overviewCustomization, (boolean)included);
        for (Object item : customWidgets) {
            List bindings = (List)((Map)item).get("bindings");
            HashMap datasets = new HashMap();
            ((List)((Map)item).get("datasets")).stream().forEach(d -> datasets.put(((Map)d).get("id").toString(), ((Map)d).get("modelId").toString()));
            HashMap fields = new HashMap();
            for (Object binding : bindings) {
                String datasetId = (String)((Map)binding).get("dataset");
                List feeds = (List)((Map)binding).get("feeds");
                ArrayList allFileds = new ArrayList();
                for (Object feed : feeds) {
                    allFileds.addAll((List)((Map)feed).get("values"));
                }
                fields.put((String)datasets.get(datasetId), allFileds.stream().distinct().collect(Collectors.toList()));
            }
            result.add(fields);
        }
        return result;
    }
}

