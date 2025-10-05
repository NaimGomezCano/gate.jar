/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.thin.config.ViewTypeConfig
 *  com.sap.sme.anw.analytics.thin.config.ViewTypeEnumConverter
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.format.support.FormattingConversionService
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
 */
package com.sap.sme.anw.analytics.thin.config;

import com.sap.sme.anw.analytics.thin.config.ViewTypeEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ViewTypeConfig
extends WebMvcConfigurationSupport {
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();
        f.addConverter((Converter)new ViewTypeEnumConverter());
        return f;
    }
}

