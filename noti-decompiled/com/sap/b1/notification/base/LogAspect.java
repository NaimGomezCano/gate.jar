/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.LogAspect
 *  jakarta.servlet.http.HttpServletRequest
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Component
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.sap.b1.notification.base;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Around(value="execution(* com.sap.b1.notification.server.*.*(..))")
    public Object profileServlet(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object object = joinPoint.proceed();
            return object;
        }
        finally {
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                logger.debug("Request Profiling: " + request.getRequestURL().toString() + " | Duration: " + (System.currentTimeMillis() - start) + " milliseconds");
            }
        }
    }

    @Around(value="execution(* com.sap.b1.notification.cache.CacheObject.*(..))")
    public Object cacheObjectMethodes(ProceedingJoinPoint joinPoint) throws Throwable {
        return this.logMethodTime(joinPoint);
    }

    @Around(value="execution(* com.sap.b1.notification.monitors.ActivityMonitor.*(..))")
    public Object notificationCacheMethodes(ProceedingJoinPoint joinPoint) throws Throwable {
        return this.logMethodTime(joinPoint);
    }

    @Around(value="execution(* com.sap.b1.notification.monitors.ServiceCallMonitor.*(..))")
    public Object notificationServiceCallCacheMethodes(ProceedingJoinPoint joinPoint) throws Throwable {
        return this.logMethodTime(joinPoint);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object logMethodTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object object = joinPoint.proceed();
            return object;
        }
        finally {
            logger.debug("Method Profiling: " + joinPoint.getSignature().getDeclaringTypeName() + "::" + joinPoint.getSignature().getName() + " | Duration: " + (System.currentTimeMillis() - start) + " milliseconds");
        }
    }
}

