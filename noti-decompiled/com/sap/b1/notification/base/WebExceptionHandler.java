/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.ExceptionResponse
 *  com.sap.b1.notification.base.WebExceptionHandler
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.str.SYSTEMmsgs
 *  jakarta.servlet.http.HttpServletRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.HttpRequestMethodNotSupportedException
 *  org.springframework.web.bind.MethodArgumentNotValidException
 *  org.springframework.web.bind.MissingServletRequestParameterException
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.ResponseStatus
 */
package com.sap.b1.notification.base;

import com.sap.b1.notification.base.ExceptionResponse;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.str.SYSTEMmsgs;
import jakarta.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.BindException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.MissingResourceException;
import java.util.jar.JarException;
import javax.naming.InsufficientResourcesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MessageUtil message;

    @ResponseBody
    @ExceptionHandler(value={Exception.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse defaultErrorHandler(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.serviceError, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={MissingServletRequestParameterException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleParameter(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.serviceError, request, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseBody
    @ExceptionHandler(value={HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionResponse handleMethodNotAllowed(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.serviceError, request, HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @ResponseBody
    @ExceptionHandler(value={FileNotFoundException.class, NoSuchFileException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse fileError(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.fileNotFound, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={SQLException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse sqlError(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.sqlError, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={BindException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse netBindError(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.netBindError, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={ConcurrentModificationException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse concurrentThreadError(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.concurrentThreadError, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={InsufficientResourcesException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse insufficientResourcesErrorHandler(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.insufficientResourcesError, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={MissingResourceException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse missingResourceError(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.missingResourceError, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseBody
    @ExceptionHandler(value={JarException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse jarError(HttpServletRequest request, Exception ex) {
        this.logger.error(ex.getMessage(), (Throwable)ex);
        return this.getExceptionMessage((MessageId)SYSTEMmsgs.jarException, request, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private ExceptionResponse getExceptionMessage(MessageId messageCode, HttpServletRequest request, int status) {
        String msg = this.message.getMessage(messageCode, new Object[0]);
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status);
        exceptionResponse.setError(msg);
        exceptionResponse.setPath(request.getRequestURI());
        return exceptionResponse;
    }
}

