/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.web.core.PerfLogFilter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpFilter
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.RandomStringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.slf4j.MDC
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.web.core;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class PerfLogFilter
extends HttpFilter {
    static final Logger log = LoggerFactory.getLogger(PerfLogFilter.class);
    static final String REQID = "ReqId";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            try {
                long beforeTime = ZonedDateTime.now().toInstant().toEpochMilli();
                String url = request.getRequestURI();
                MDC.put((String)REQID, (String)RandomStringUtils.randomAlphanumeric((int)8));
                MDC.put((String)"HL", (String)"");
                log.debug("REQ:[{}] -->", (Object)url);
                chain.doFilter((ServletRequest)request, (ServletResponse)response);
                log.debug("REQ:[{}] <-- {}ms", (Object)url, (Object)(ZonedDateTime.now().toInstant().toEpochMilli() - beforeTime));
            }
            finally {
                MDC.remove((String)REQID);
                MDC.remove((String)"HL");
            }
        } else {
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
        }
    }
}

