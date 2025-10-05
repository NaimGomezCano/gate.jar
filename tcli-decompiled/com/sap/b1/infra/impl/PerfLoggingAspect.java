/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.impl.PerfLoggingAspect
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.slf4j.MDC
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.infra.impl;

import java.time.ZonedDateTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerfLoggingAspect {
    Logger log = LoggerFactory.getLogger(PerfLoggingAspect.class);

    @Pointcut(value="(@annotation(org.springframework.web.bind.annotation.RequestMapping) ||  @annotation(org.springframework.web.bind.annotation.GetMapping) ||  @annotation(org.springframework.web.bind.annotation.PostMapping) ||  @annotation(com.sap.b1.spring.BoServiceMethod) ||  @annotation(com.sap.b1.spring.BoServiceMethodWrite))")
    public void reqHandling() {
    }

    @Pointcut(value="(within(com.sap.b1.tcli.service..*) && bean(*Service)) ||(within(com.sap.b1.infra.udf.model.TableLoader) || within(com.sap.b1.bo.base.BoLogicLanguage))")
    public void keyMethods() {
    }

    @Pointcut(value="within(com.sap.b1.bo..*) && bean(/bo/*)")
    public void oldPlayerService() {
    }

    @Pointcut(value="bean(*Feign) || bean(*Feign2) || execution(* gen.dao.*.*(..))")
    public void feignAndDao() {
    }

    @Pointcut(value="(reqHandling() || keyMethods()) && !within(is(EnumType)) && !within(is(FinalType))")
    public void allMethods() {
    }

    @Around(value="allMethods()")
    public Object logOnKeyMethods(ProceedingJoinPoint pjp) throws Throwable {
        return this.invokeBody(pjp);
    }

    @Around(value="feignAndDao()")
    public Object logOnFeignAndDao(ProceedingJoinPoint pjp) throws Throwable {
        return this.invokeBody(pjp);
    }

    @Around(value="execution(* com.sap.b1.svcl.client..*.*(..)) && bean(*Service)")
    public Object logOnSvclODataService(ProceedingJoinPoint pjp) throws Throwable {
        return this.invokeBody(pjp);
    }

    private Object invokeBody(ProceedingJoinPoint pjp) throws Throwable {
        if (this.log.isDebugEnabled()) {
            String method = pjp.getSignature().toShortString();
            long beforeTime = ZonedDateTime.now().toInstant().toEpochMilli();
            String callLevel = MDC.get((String)"HL");
            MDC.put((String)"HL", (String)(callLevel + "+---"));
            this.log.debug("{} -->", (Object)method);
            Object retVal = pjp.proceed();
            this.log.debug("{} <-- {}ms", (Object)method, (Object)(ZonedDateTime.now().toInstant().toEpochMilli() - beforeTime));
            MDC.put((String)"HL", (String)callLevel);
            return retVal;
        }
        return pjp.proceed();
    }
}

