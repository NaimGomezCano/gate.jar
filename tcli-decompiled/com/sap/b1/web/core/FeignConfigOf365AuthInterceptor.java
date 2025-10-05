/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.web.core.FeignConfigOf365AuthInterceptor
 *  feign.RequestInterceptor
 *  feign.RequestTemplate
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package com.sap.b1.web.core;

import com.sap.b1.infra.share.web.B1UserDetails;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class FeignConfigOf365AuthInterceptor
implements RequestInterceptor {
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        template.header("Authorization", new String[]{"Bearer " + userDetails.getToken()});
        template.header("X-b1-companyid", new String[]{userDetails.getCompanyDbId()});
    }
}

