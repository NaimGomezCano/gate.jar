/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.ExceptionResponse
 *  com.sap.b1.thin.extn.WebExceptionHandler
 *  jakarta.servlet.http.HttpServletRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.HttpRequestMethodNotSupportedException
 *  org.springframework.web.bind.MethodArgumentNotValidException
 *  org.springframework.web.bind.MissingServletRequestParameterException
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.ResponseStatus
 */
package com.sap.b1.thin.extn;

import com.sap.b1.thin.extn.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {
    Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value={Exception.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse defaultErrorHandler(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionResponse(request, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }

    @ResponseBody
    @ExceptionHandler(value={MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleParameterException(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionResponse(request, HttpStatus.BAD_REQUEST.value(), "Bad Request");
    }

    @ResponseBody
    @ExceptionHandler(value={HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionResponse handleMethodNotAllowedException(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionResponse(request, HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed");
    }

    private ExceptionResponse getExceptionResponse(HttpServletRequest request, int status, String errorMsg) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status);
        exceptionResponse.setError(errorMsg);
        exceptionResponse.setPath(request.getRequestURI());
        return exceptionResponse;
    }
}

