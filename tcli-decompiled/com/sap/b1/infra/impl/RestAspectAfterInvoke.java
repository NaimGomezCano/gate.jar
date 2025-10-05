/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.BoEnv
 *  com.sap.b1.bo.base.BoFinder
 *  com.sap.b1.infra.impl.RestAspectAfterInvoke
 *  com.sap.b1.protocol.response.ResponseBase
 *  com.sap.b1.protocol.response.ResponseData
 *  com.sap.b1.protocol.response.ResponseUICommand
 *  com.sap.b1.protocol.uicommand.UiCommandLeave
 *  com.sap.b1.protocol.uicommand.UiCommandNavigate
 *  org.apache.commons.lang3.StringUtils
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.infra.impl;

import com.sap.b1.bo.base.BoEnv;
import com.sap.b1.bo.base.BoFinder;
import com.sap.b1.protocol.response.ResponseBase;
import com.sap.b1.protocol.response.ResponseData;
import com.sap.b1.protocol.response.ResponseUICommand;
import com.sap.b1.protocol.uicommand.UiCommandLeave;
import com.sap.b1.protocol.uicommand.UiCommandNavigate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class RestAspectAfterInvoke {
    @Autowired
    BoFinder boFinder;
    @Autowired
    BoEnv bizEnv;

    public RestAspectAfterInvoke(ProceedingJoinPoint pjp, Object[] args, BoEnv bizEnv, ApplicationContext appContext, boolean ui) {
    }

    Object execute(Object response) throws Exception {
        if (response instanceof ResponseUICommand) {
            response = this.afterInvoke4UiCommand((ResponseBase)response);
        }
        return response;
    }

    protected ResponseBase afterInvoke4UiCommand(ResponseBase response) throws Exception {
        ResponseUICommand uiCommand = (ResponseUICommand)response;
        if (uiCommand.command instanceof UiCommandNavigate) {
            return this.afterInvoke4UiNaviation(response, uiCommand);
        }
        if (uiCommand.command instanceof UiCommandLeave) {
            return this.afterInvoke4UiLeave(response, uiCommand);
        }
        return response;
    }

    private ResponseBase afterInvoke4UiNaviation(ResponseBase response, ResponseUICommand uiCommand) throws Exception {
        UiCommandNavigate uiNavigate = (UiCommandNavigate)uiCommand.command;
        if (uiNavigate.data != null && uiNavigate.data.boData != null) {
            ResponseData respData = new ResponseData(uiNavigate.data.boData, uiNavigate.targetBo);
            respData = (ResponseData)this.execute((Object)respData);
            uiNavigate.data.boData = respData.getDataList();
            return response;
        }
        return response;
    }

    private ResponseBase afterInvoke4UiLeave(ResponseBase response, ResponseUICommand uiCommand) throws Exception {
        UiCommandLeave uiLeave = (UiCommandLeave)uiCommand.command;
        if (uiLeave.data != null && !StringUtils.isEmpty((CharSequence)uiLeave.targetBoTableName)) {
            ResponseData respData = new ResponseData((List)uiLeave.data, uiLeave.targetBoTableName);
            respData = (ResponseData)this.execute((Object)respData);
            uiLeave.data = respData.data;
            return response;
        }
        return response;
    }
}

