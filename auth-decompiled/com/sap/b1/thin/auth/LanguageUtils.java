/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.engines.metadata.Languages
 *  com.sap.b1.thin.auth.LanguageUtils
 *  com.sap.b1.thin.auth.OIDCSession
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.sap.b1.thin.auth;

import com.sap.b1.infra.engines.metadata.Languages;
import com.sap.b1.thin.auth.OIDCSession;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*
 * Exception performing whole class analysis ignored.
 */
public class LanguageUtils {
    public static Locale getLocale(OIDCSession oidcSession) {
        String sapLanguage = LanguageUtils.getSapLanguage();
        if (sapLanguage != null) {
            return Languages.fromSapLanguage((String)sapLanguage);
        }
        Integer languageCode = 0;
        try {
            languageCode = Integer.parseInt(oidcSession.getLanguage());
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return Languages.getLocale((int)languageCode);
    }

    private static String getSapLanguage() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
            return request.getParameter("sap-language");
        }
        return null;
    }
}

