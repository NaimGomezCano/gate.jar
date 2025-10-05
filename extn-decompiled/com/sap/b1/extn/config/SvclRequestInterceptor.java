/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.extn.config.SvclRequestInterceptor
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  feign.RequestInterceptor
 *  feign.RequestTemplate
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package com.sap.b1.extn.config;

import com.sap.b1.infra.share.web.B1UserDetails;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SvclRequestInterceptor
implements RequestInterceptor {
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        template.header("Content-type", new String[]{"application/json; charset=utf-8"});
        template.header("Authorization", new String[]{"Bearer " + userDetails.getToken()});
        template.header("CompanyId", new String[]{userDetails.getCompanyDbId()});
        template.header("Language", new String[]{userDetails.getLanguageCode()});
        template.header("B1S-ReplaceCollectionsOnPatch", new String[]{"true"});
        String svclCookie = (String)userDetails.getValue("x-wc-svcl-state");
        if (svclCookie != null) {
            template.header("Cookie", new String[]{svclCookie});
        }
    }
}

