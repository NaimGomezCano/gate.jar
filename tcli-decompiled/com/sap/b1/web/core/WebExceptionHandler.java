/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.exc.InputCoercionException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.exc.InvalidFormatException
 *  com.sap.b1.bo.base.BoEnv
 *  com.sap.b1.bo.exception.BoUserAuthException
 *  com.sap.b1.protocol.response.ResponseBase
 *  com.sap.b1.protocol.response.ResponseUICommand
 *  com.sap.b1.protocol.response.ResponseUICommandWithError
 *  com.sap.b1.protocol.response.bo.BankAccountValidationException
 *  com.sap.b1.protocol.response.bo.BusinessException
 *  com.sap.b1.protocol.response.bo.DetailedResponseBoException
 *  com.sap.b1.protocol.response.bo.PermissionException
 *  com.sap.b1.protocol.uicommand.UiCommandBase
 *  com.sap.b1.protocol.uicommand.UiCommandMessageBox
 *  com.sap.b1.protocol.uicommand.UiCommandMessageBox$MsgType
 *  com.sap.b1.protocol.uicommand.UiCommandOption
 *  com.sap.b1.svcl.client.ServiceLayerException
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  com.sap.b1.web.core.AuthException
 *  com.sap.b1.web.core.WebExceptionHandler
 *  gen.str.SYSTEMmsgs
 *  gen.str.UiCommandMessageBoxTiles
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.exception.ExceptionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.ResponseStatus
 */
package com.sap.b1.web.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sap.b1.bo.base.BoEnv;
import com.sap.b1.bo.exception.BoUserAuthException;
import com.sap.b1.protocol.response.ResponseBase;
import com.sap.b1.protocol.response.ResponseUICommand;
import com.sap.b1.protocol.response.ResponseUICommandWithError;
import com.sap.b1.protocol.response.bo.BankAccountValidationException;
import com.sap.b1.protocol.response.bo.BusinessException;
import com.sap.b1.protocol.response.bo.DetailedResponseBoException;
import com.sap.b1.protocol.response.bo.PermissionException;
import com.sap.b1.protocol.uicommand.UiCommandBase;
import com.sap.b1.protocol.uicommand.UiCommandMessageBox;
import com.sap.b1.protocol.uicommand.UiCommandOption;
import com.sap.b1.svcl.client.ServiceLayerException;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import com.sap.b1.web.core.AuthException;
import gen.str.SYSTEMmsgs;
import gen.str.UiCommandMessageBoxTiles;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.net.BindException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.MissingResourceException;
import java.util.jar.JarException;
import javax.naming.InsufficientResourcesException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebExceptionHandler {
    Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);
    @Autowired
    BoEnv env;
    @Autowired
    MessageUtil message;

    @ResponseBody
    @ExceptionHandler(value={Exception.class})
    public String defaultErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        resp.setStatus(500);
        this.logger.error("exception", (Throwable)expSrc);
        Throwable exception = ExceptionUtils.getRootCause((Throwable)expSrc);
        if (exception == null) {
            exception = expSrc;
        }
        if (exception instanceof AuthException) {
            resp.setStatus(401);
            return "{\"type\":\"exception.loginRequired\"}";
        }
        if (!(exception instanceof ServiceLayerException)) {
            if (exception instanceof PermissionException) {
                exceptionMessage = this.getExceptionMessage((BusinessException)exception);
                exception = new PermissionException(exceptionMessage);
            } else if (exception instanceof BusinessException) {
                exceptionMessage = this.getExceptionMessage((BusinessException)exception);
                exception = new BusinessException(exceptionMessage);
            } else if (exception instanceof OutOfMemoryError) {
                exceptionMessage = this.message.getMessage((MessageId)SYSTEMmsgs.outOfMemoryError, new Object[0]);
                exception = new RuntimeException(exceptionMessage);
            } else if (exception instanceof StackOverflowError) {
                exceptionMessage = this.message.getMessage((MessageId)SYSTEMmsgs.stackOverflowError, new Object[0]);
                exception = new RuntimeException(exceptionMessage);
            } else if (exception instanceof InputCoercionException) {
                exceptionMessage = this.message.getMessage((MessageId)SYSTEMmsgs.outOfRangeNum, new Object[0]);
                exception = new RuntimeException(exceptionMessage);
            } else if (exception instanceof InvalidFormatException) {
                exceptionMessage = this.message.getMessage((MessageId)SYSTEMmsgs.invalidFormatError, new Object[]{((InvalidFormatException)exception).getValue()});
                exception = new RuntimeException(exceptionMessage);
            } else {
                String msg = this.message.getMessage((MessageId)SYSTEMmsgs.serviceError, new Object[0]);
                exception = new BusinessException(msg);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        DetailedResponseBoException response = new DetailedResponseBoException(exception);
        String rt = mapper.writeValueAsString((Object)response);
        return rt;
    }

    @ResponseBody
    @ExceptionHandler(value={FileNotFoundException.class, NoSuchFileException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String fileErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.fileNotFound);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={SQLException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String sqlErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.sqlError);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={BindException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String netBindErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.netBindError);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={ConcurrentModificationException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String concurrentThreadErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.concurrentThreadError);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={InsufficientResourcesException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String insufficientResourcesErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.insufficientResourcesError);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={MissingResourceException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String missingResourceErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.missingResourceError);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={JarException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String jarErrorHandler(HttpServletResponse resp, Exception expSrc) throws JsonProcessingException {
        String exceptionStr = this.getExceptionMessage((MessageId)SYSTEMmsgs.jarException);
        this.logger.error("exception", (Throwable)expSrc);
        return exceptionStr;
    }

    @ResponseBody
    @ExceptionHandler(value={BusinessException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBase bussineExceptionHandler(BusinessException exception) {
        String exceptionDes = this.getExceptionMessage(exception);
        if (exception.getErrorCode() != null) {
            return this.getUICommandWithErrorCode(exceptionDes, exception.getErrorCode());
        }
        return this.getUiCommand(exceptionDes);
    }

    @ResponseBody
    @ExceptionHandler(value={BoUserAuthException.class})
    @ResponseStatus(value=HttpStatus.UNAUTHORIZED)
    public ResponseBase boUserAuthExceptionHandler(BoUserAuthException exception) {
        String exceptionDes = this.getExceptionMessage((BusinessException)exception);
        return this.getUiCommand(exceptionDes);
    }

    @ResponseBody
    @ExceptionHandler(value={PermissionException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBase permissionExceptionHandler(PermissionException exception) {
        String exceptionDes = this.getExceptionMessage((BusinessException)exception);
        if (exceptionDes == null) {
            exceptionDes = this.message.getMessage((MessageId)SYSTEMmsgs.nonePermission, new Object[0]);
        }
        return this.getUICommandWithErrorCode(exceptionDes, exception.getErrorCode());
    }

    @ResponseBody
    @ExceptionHandler(value={BankAccountValidationException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public String bankAccountValidationExceptionHandler(BankAccountValidationException exception) throws JsonProcessingException {
        exception = new BankAccountValidationException(this.getExceptionMessage((BusinessException)exception));
        ObjectMapper mapper = new ObjectMapper();
        DetailedResponseBoException response = new DetailedResponseBoException((Throwable)exception);
        response.message = exception.getMessage();
        String rt = mapper.writeValueAsString((Object)response);
        this.logger.error("Bank account validation failed: IBAN Validation Exception --> {}", (Object)rt);
        return rt;
    }

    private String getExceptionMessage(MessageId strCode) throws JsonProcessingException {
        String msg = this.message.getMessage(strCode, new Object[0]);
        RuntimeException exception = new RuntimeException(msg);
        ObjectMapper mapper = new ObjectMapper();
        DetailedResponseBoException response = new DetailedResponseBoException((Throwable)exception);
        return mapper.writeValueAsString((Object)response);
    }

    private String getExceptionMessage(BusinessException exception) {
        String exceptionDes = null;
        exceptionDes = exception.getMessageId() != null ? this.message.getMessage(exception.getMessageId(), new Object[0]) : exception.getMessage();
        return exceptionDes;
    }

    private ResponseBase getUiCommand(String exceptionDes) {
        UiCommandOption optionClose = new UiCommandOption("851118bf-9f32-4f38-bf4a-8cd91fe3234c", this.message);
        optionClose.defaultButton = true;
        optionClose.setActionToNone();
        UiCommandMessageBox out = new UiCommandMessageBox(UiCommandMessageBox.MsgType.Error, this.message.getMessage((MessageId)UiCommandMessageBoxTiles.Error, new Object[0]), exceptionDes, new UiCommandOption[]{optionClose});
        return new ResponseUICommand((UiCommandBase)out);
    }

    private ResponseBase getUICommandWithErrorCode(String exceptionDes, String errorCode) {
        UiCommandOption optionClose = new UiCommandOption("851118bf-9f32-4f38-bf4a-8cd91fe3234c", this.message);
        optionClose.defaultButton = true;
        optionClose.setActionToNone();
        UiCommandMessageBox out = new UiCommandMessageBox(UiCommandMessageBox.MsgType.Error, this.message.getMessage((MessageId)UiCommandMessageBoxTiles.Error, new Object[0]), exceptionDes, new UiCommandOption[]{optionClose});
        return new ResponseUICommandWithError((UiCommandBase)out, errorCode);
    }
}

