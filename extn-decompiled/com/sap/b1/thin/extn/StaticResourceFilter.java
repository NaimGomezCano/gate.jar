/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.sdk.oidc.web.WebUtils
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.SLDExtensionUtils
 *  com.sap.b1.thin.extn.StaticResourceFilter
 *  jakarta.servlet.Filter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.FilterConfig
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package com.sap.b1.thin.extn;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sap.b1.boot.Bootstrap;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.sdk.oidc.web.WebUtils;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.SLDExtensionUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class StaticResourceFilter
implements Filter {
    private static final String URL_PATTERNS = "/ui-static";
    private Cache<String, SLDExtensionMeta[]> extMap = CacheBuilder.newBuilder().expireAfterAccess(1L, TimeUnit.MINUTES).build();

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            String path = WebUtils.getPathWithoutContextPath((HttpServletRequest)httpRequest);
            boolean isMatchedUrl = path.startsWith(URL_PATTERNS);
            boolean isAssigned = false;
            if (isMatchedUrl) {
                String id = path.split("/")[2];
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String userCode = authentication.getName();
                B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
                String schema = userDetails.getSchema();
                String dbInstanceName = Bootstrap.getInstance().getSldClient().getDatabaseInstance().getName();
                SLDExtensionMeta[] sldExt = (SLDExtensionMeta[])this.extMap.getIfPresent((Object)(dbInstanceName + "##" + schema + "##" + userCode));
                for (int i = 0; i < 2; ++i) {
                    if (sldExt == null) {
                        sldExt = SLDExtensionUtils.retrieveExtensionsbyUserPreference((String)dbInstanceName, (String)schema, (String)userCode);
                        this.extMap.put((Object)(dbInstanceName + "##" + schema + "##" + userCode), (Object)sldExt);
                    }
                    for (SLDExtensionMeta addon : sldExt) {
                        if (!addon.getID().toString().equals(id)) continue;
                        isAssigned = true;
                        break;
                    }
                    if (isAssigned) break;
                    sldExt = null;
                }
            }
            if (!isAssigned && isMatchedUrl) {
                WebUtils.responseError((HttpServletResponse)httpResponse, (int)401);
                return;
            }
            chain.doFilter(request, response);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}

