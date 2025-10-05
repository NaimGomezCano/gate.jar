/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.ChartCustomizationResolveUtils
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChartCustomizationResolveUtils {
    @Generated
    private static final Logger log = LogManager.getLogger(ChartCustomizationResolveUtils.class);

    public static List<String> getBindings(String chartCustomization) {
        ArrayList<String> allFileds = new ArrayList<String>();
        if (StringUtils.isBlank((CharSequence)chartCustomization)) {
            return allFileds;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map custom = (Map)mapper.readValue(chartCustomization, Map.class);
            List feeds = (List)custom.get("feeds");
            for (Object feed : feeds) {
                allFileds.addAll((List)((Map)feed).get("values"));
            }
        }
        catch (JsonProcessingException e) {
            log.error("ChartCustomizationResolveUtils.getBindings Resolve json error.", (Throwable)e);
            throw new ValidateException(RulesErrorEnum.INVALID_DATA_PKG, new Object[0]);
        }
        return allFileds.stream().distinct().collect(Collectors.toList());
    }
}

