/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.web.core.FeignConfigB1ahInterceptor
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

public class FeignConfigB1ahInterceptor
implements RequestInterceptor {
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
        String jwtToken = "Bearer " + userDetails.getToken();
        template.header("Authorization", new String[]{jwtToken});
        template.header("Content-Type", new String[]{"application/json"});
    }
}

