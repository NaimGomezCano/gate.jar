/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.ExceptionResponse
 *  com.sap.b1.gate.WebExceptionHandler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.ResponseStatus
 *  org.springframework.web.server.ServerWebExchange
 */
package com.sap.b1.gate;

import com.sap.b1.gate.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
public class WebExceptionHandler {
    Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value={Exception.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse defaultErrorHandler(ServerWebExchange exchange, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionResponse(exchange.getRequest(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }

    private ExceptionResponse getExceptionResponse(ServerHttpRequest request, int status, String errorMsg) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status);
        exceptionResponse.setError(errorMsg);
        exceptionResponse.setPath(request.getURI().getPath());
        return exceptionResponse;
    }
}

