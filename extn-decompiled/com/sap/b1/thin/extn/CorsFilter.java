/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.CorsFilter
 *  com.sap.b1.thin.extn.Whitelist
 *  jakarta.servlet.Filter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.FilterConfig
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.extn;

import com.sap.b1.thin.extn.Whitelist;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorsFilter
implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String originHeader = ((HttpServletRequest)servletRequest).getHeader("Origin");
        if (((HttpServletRequest)servletRequest).getRequestURI().indexOf("/extn/api") == 0 && originHeader != null) {
            try {
                List whiteStringList = new ArrayList();
                Whitelist whitelist = new Whitelist();
                whiteStringList = whitelist.getDomains();
                HttpServletResponse response = (HttpServletResponse)servletResponse;
                HashSet allowedOrigins = new HashSet(whiteStringList);
                if (allowedOrigins.contains(originHeader)) {
                    response.setHeader("Access-Control-Allow-Origin", originHeader);
                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                }
            }
            catch (Exception e) {
                logger.error("Fail to get domains from addon service.");
                logger.error(e.getMessage());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

