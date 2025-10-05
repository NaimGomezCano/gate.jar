/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.sme.anw.analytics.core.connection.DataSourceHolder
 *  com.sap.sme.anw.analytics.core.connection.IAnalyticsConnection
 *  com.sap.sme.anw.analytics.core.context.ContextDataHolder
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.context.impl.BaseContextData
 *  com.sap.sme.anw.analytics.core.exception.AnalyticsBaseException
 *  com.sap.sme.anw.analytics.core.init.service.IInitService
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.core.profiling.Profiling
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.sql.common.DbType
 *  com.sap.sme.anw.analytics.thin.config.StaticConfigs
 *  com.sap.sme.anw.analytics.thin.filter.TenantFilter
 *  com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig
 *  com.sap.sme.anw.analytics.thin.springcontext.AppContext
 *  jakarta.servlet.Filter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.FilterConfig
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.boot.jdbc.DatabaseDriver
 *  org.springframework.security.authentication.AnonymousAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package com.sap.sme.anw.analytics.thin.filter;

import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.sme.anw.analytics.core.connection.DataSourceHolder;
import com.sap.sme.anw.analytics.core.connection.IAnalyticsConnection;
import com.sap.sme.anw.analytics.core.context.ContextDataHolder;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.context.impl.BaseContextData;
import com.sap.sme.anw.analytics.core.exception.AnalyticsBaseException;
import com.sap.sme.anw.analytics.core.init.service.IInitService;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.core.profiling.Profiling;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.sql.common.DbType;
import com.sap.sme.anw.analytics.thin.config.StaticConfigs;
import com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig;
import com.sap.sme.anw.analytics.thin.springcontext.AppContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TenantFilter
implements Filter {
    private static final Locale saptrcLocale = new Locale("en", "US", "saptrc");
    private static final Locale sappsdLocale = new Locale("en", "US", "sappsd");

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String dataSourceId = StringUtils.trimToNull((String)request.getParameter("dataSource"));
        if (!StringUtils.isBlank((CharSequence)dataSourceId)) {
            DataSourceHolder.setDataSourceId((String)dataSourceId);
        }
        BaseContextData contextData = new BaseContextData(StaticConfigs.SYSTEM_CONFIG);
        Profiling profiling = contextData.getProfiling();
        profiling.startMeasureTime(this.getClass(), "TenantFilter/doFilter");
        try {
            String driverClassName;
            B1UserDetails userDetails;
            if (((ProfileConfig)AppContext.getContext().getBean(ProfileConfig.class)).isDevProfile()) {
                userDetails = dataSourceId != null ? new B1UserDetails(null, System.getProperty("DB_SCHEMA/" + dataSourceId), null) : new B1UserDetails(null, System.getProperty("DB_SCHEMA"), null);
            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                    throw new AnalyticsBaseException("Login required");
                }
                userDetails = (B1UserDetails)authentication.getPrincipal();
            }
            contextData.setRequest(request);
            contextData.setLocale(this.decideLocale(request));
            String schema = userDetails.getSchema();
            if (dataSourceId != null) {
                driverClassName = System.getProperty("DB_DRIVER/" + dataSourceId);
            } else {
                DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl((String)System.getProperty("b1_jdbc_url"));
                driverClassName = databaseDriver.getDriverClassName();
            }
            DbType dbType = DbType.fromDriverClassName((String)driverClassName);
            contextData.setDbType(dbType);
            contextData.setAnalyticsSchema(schema);
            contextData.setTenantId(schema);
            contextData.setUserId(userDetails.getUsername());
            contextData.setSessionId(userDetails.getToken());
            contextData.setConnection((IAnalyticsConnection)ServiceFactory.getService(IAnalyticsConnection.class));
            IInitService initService = (IInitService)ServiceFactory.getService(IInitService.class);
            initService.init((IContextData)contextData);
            AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.INFO, (String)schema);
            request.setAttribute("contextData", (Object)contextData);
            ContextDataHolder.setContextData((IContextData)contextData);
            chain.doFilter(request, response);
        }
        finally {
            profiling.endMeasureTime(this.getClass(), "TenantFilter/doFilter");
        }
    }

    public void destroy() {
    }

    private Locale decideLocale(ServletRequest request) {
        String sapLanguage = request.getParameter("sap-language");
        if (sapLanguage != null) {
            if ("1q".equalsIgnoreCase(sapLanguage)) {
                return saptrcLocale;
            }
            if ("2q".equalsIgnoreCase(sapLanguage)) {
                return sappsdLocale;
            }
            return Locale.forLanguageTag(sapLanguage);
        }
        Locale requestLocale = request.getLocale();
        if (requestLocale != null) {
            String languageTag = requestLocale.toLanguageTag();
            if ("en-US-x-saptrc".equalsIgnoreCase(languageTag)) {
                return saptrcLocale;
            }
            if ("en-US-x-sappsd".equalsIgnoreCase(languageTag)) {
                return sappsdLocale;
            }
        }
        return requestLocale;
    }
}

