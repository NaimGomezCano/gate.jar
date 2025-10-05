/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelColumns
 *  com.sap.b1.servlets.service.variant.sys.checkrules.utils.FilterBarLayoutResolveUtils
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
import com.sap.b1.servlets.service.variant.sys.checkrules.model.ModelColumns;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilterBarLayoutResolveUtils {
    @Generated
    private static final Logger log = LogManager.getLogger(FilterBarLayoutResolveUtils.class);

    public static ModelColumns getBindings(String filterBarLayout) {
        String modelId = null;
        List<Object> columns = new ArrayList();
        if (StringUtils.isBlank((CharSequence)filterBarLayout)) {
            return new ModelColumns(modelId, columns);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map custom = (Map)mapper.readValue(filterBarLayout, Map.class);
            List visibleItems = (List)custom.get("visibleItems");
            if (visibleItems.size() > 0) {
                modelId = visibleItems.get(0).toString().substring(0, visibleItems.get(0).toString().indexOf(47));
                columns = visibleItems.stream().map(i -> i.toString().substring(i.toString().indexOf(47) + 1)).collect(Collectors.toList());
            }
            return new ModelColumns(modelId, columns);
        }
        catch (JsonProcessingException e) {
            log.error("FilterBarLayoutResolveUtils.getBindings Resolve json error.", (Throwable)e);
            throw new ValidateException(RulesErrorEnum.INVALID_DATA_PKG, new Object[0]);
        }
    }
}

