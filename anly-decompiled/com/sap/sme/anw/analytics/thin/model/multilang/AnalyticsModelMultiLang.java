/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.common.ResourceCommon
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.model.Model
 *  com.sap.sme.anw.analytics.core.model.multilang.impl.CoreAnalyticsModelMultiLang
 *  com.sap.sme.anw.analytics.thin.model.multilang.AnalyticsModelMultiLang
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.model.multilang;

import com.sap.sme.anw.analytics.core.common.ResourceCommon;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.model.Model;
import com.sap.sme.anw.analytics.core.model.multilang.impl.CoreAnalyticsModelMultiLang;
import com.sap.sme.anw.analytics.type.model.ModelType;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public class AnalyticsModelMultiLang
extends CoreAnalyticsModelMultiLang {
    private static final String GLOBALIZATION_META_PATH = "meta/globalization";
    private static final String GLOBALIZATION_MODEL_FILE_PREFIX = "MODEL";
    private static final String GLOBALIZATION_STRING_FILE_PREFIX = "STRING";

    public String getModelName(IContextData contextData, Model model) {
        String prefix = this.getModelLanguageKeyPrefix(model);
        String languageKey = prefix + "description";
        return this.getText(contextData, GLOBALIZATION_MODEL_FILE_PREFIX, languageKey);
    }

    public String getModelAnalysisObjectDescription(IContextData contextData, Model model, String fieldName) {
        String prefix = this.getModelLanguageKeyPrefix(model);
        String languageKey = prefix + "column/" + fieldName;
        return this.getText(contextData, GLOBALIZATION_MODEL_FILE_PREFIX, languageKey);
    }

    public String getModelAnalysisObjectEnumDescription(IContextData contextData, Model model, String languageKey) {
        return this.getText(contextData, GLOBALIZATION_STRING_FILE_PREFIX, languageKey);
    }

    public String getModelVariableObjectDescription(IContextData contextData, Model model, String variableName) {
        String prefix = this.getModelLanguageKeyPrefix(model);
        String languageKey = prefix + "variable/" + variableName;
        return this.getText(contextData, GLOBALIZATION_MODEL_FILE_PREFIX, languageKey);
    }

    public String getModelVariableValueDescription(IContextData contextData, Model model, String variableName, String variableValueName) {
        String prefix = this.getModelLanguageKeyPrefix(model);
        String languageKey = prefix + "variablevalue/" + variableName + "/" + variableValueName;
        return this.getText(contextData, GLOBALIZATION_MODEL_FILE_PREFIX, languageKey);
    }

    private String getModelLanguageKeyPrefix(Model model) {
        String modelId = model.getModelId();
        return StringUtils.replace((String)modelId, (String)"::", (String)"//") + "." + ModelType.CALCULATIONVIEW.name().toLowerCase() + "/";
    }

    private String getText(IContextData contextData, String prefix, String languageKey) {
        return ResourceCommon.getText((String)GLOBALIZATION_META_PATH, (String)prefix, (Locale)contextData.getLocale(), (String)languageKey, (Object[])new Object[0]);
    }
}

