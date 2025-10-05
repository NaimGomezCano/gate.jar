/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.thin.config.ViewTypeEnumConverter
 *  com.sap.sme.anw.analytics.type.model.ViewType
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.convert.converter.Converter
 */
package com.sap.sme.anw.analytics.thin.config;

import com.sap.sme.anw.analytics.type.model.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

class ViewTypeEnumConverter
implements Converter<String, ViewType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewTypeEnumConverter.class);

    ViewTypeEnumConverter() {
    }

    public ViewType convert(String source) {
        try {
            return ViewType.valueOf((String)source.toUpperCase());
        }
        catch (Exception e) {
            LOGGER.error("Convert parameter {} error", (Object)source);
            return null;
        }
    }
}

