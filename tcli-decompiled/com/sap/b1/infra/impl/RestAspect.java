/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.BoEnv
 *  com.sap.b1.infra.impl.RestAspect
 *  com.sap.b1.infra.impl.RestAspectAfterInvoke
 *  com.sap.b1.infra.impl.RestAspectBeforeInvoke
 *  jakarta.servlet.http.HttpServletRequest
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.annotation.Order
 *  org.springframework.stereotype.Component
 *  org.springframework.util.StopWatch
 */
package com.sap.b1.infra.impl;

import com.sap.b1.bo.base.BoEnv;
import com.sap.b1.infra.impl.RestAspectAfterInvoke;
import com.sap.b1.infra.impl.RestAspectBeforeInvoke;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Order(value=100002)
@Component
public class RestAspect {
    Logger log = LoggerFactory.getLogger(RestAspect.class);
    @Autowired
    private HttpServletRequest httpRequest;
    @Autowired
    BoEnv bizEnv;
    @Autowired
    ApplicationContext appContext;

    @Pointcut(value="@annotation(com.sap.b1.spring.BoServiceMethod) || @annotation(com.sap.b1.spring.BoServiceMethodWrite)")
    private void pointcut() {
    }

    @Around(value="com.sap.b1.infra.impl.RestAspect.pointcut()")
    public Object onInvoke(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch crunchifyWatch = new StopWatch("AOP");
        crunchifyWatch.start("before");
        Object[] args = pjp.getArgs();
        RestAspectBeforeInvoke before = new RestAspectBeforeInvoke(this.httpRequest, pjp, args, this.bizEnv);
        crunchifyWatch.stop();
        crunchifyWatch.start("process");
        Object retVal = pjp.proceed(args);
        crunchifyWatch.stop();
        crunchifyWatch.start("after");
        RestAspectAfterInvoke after = (RestAspectAfterInvoke)this.appContext.getBean(RestAspectAfterInvoke.class, new Object[]{pjp, args, this.bizEnv, this.appContext, before.isUi()});
        retVal = after.execute(retVal);
        crunchifyWatch.stop();
        this.log.debug(crunchifyWatch.prettyPrint());
        return retVal;
    }
}

