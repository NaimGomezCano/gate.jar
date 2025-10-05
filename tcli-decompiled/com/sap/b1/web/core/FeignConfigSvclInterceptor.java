/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.web.core.FeignConfigSvclInterceptor
 *  feign.RequestInterceptor
 *  feign.RequestTemplate
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.web.core;

import com.sap.b1.infra.share.web.B1UserDetails;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignConfigSvclInterceptor
implements RequestInterceptor {
    @Value(value="${spring.profiles.active:default}")
    String activeProfile;

    public void apply(RequestTemplate template) {
        if ("test".equals(this.activeProfile)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
            StringBuilder sb = new StringBuilder();
            List cookies = (List)userDetails.getValue("cookies");
            for (String c : cookies) {
                sb.append(c.split(";")[0]).append(";");
            }
            sb.setLength(sb.length() - 1);
            if (!template.headers().containsKey("Content-Type")) {
                template.header("Content-type", new String[]{"application/json; charset=utf-8"});
            }
            template.header("Cookie", new String[]{sb.toString()});
            template.header("B1S-ReplaceCollectionsOnPatch", new String[]{"true"});
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
            if (!template.headers().containsKey("Content-Type")) {
                template.header("Content-type", new String[]{"application/json; charset=utf-8"});
            }
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
}

