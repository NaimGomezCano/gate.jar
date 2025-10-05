/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.bo.base.BoEnv
 *  com.sap.b1.bo.base.render.bean.BeanValue
 *  com.sap.b1.controller.base.BaseController
 *  com.sap.b1.dbd.BeanConvertFromMapWithDefaultValues
 *  com.sap.b1.dbd.algo.BeanMergeIfNotNull
 *  com.sap.b1.env.DataAccessEnv
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.impl.RestAspectBeforeInvoke
 *  com.sap.b1.protocol.request.RequestBase
 *  com.sap.b1.protocol.request.RequestData
 *  com.sap.b1.protocol.request.UiContextViewStack
 *  com.sap.b1.ui.base.UiJacksonBuilder
 *  jakarta.servlet.http.HttpServletRequest
 *  org.apache.commons.io.IOUtils
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.reflect.MethodSignature
 *  org.springframework.beans.BeanUtils
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.sap.b1.infra.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.bo.base.BoEnv;
import com.sap.b1.bo.base.render.bean.BeanValue;
import com.sap.b1.controller.base.BaseController;
import com.sap.b1.dbd.BeanConvertFromMapWithDefaultValues;
import com.sap.b1.dbd.algo.BeanMergeIfNotNull;
import com.sap.b1.env.DataAccessEnv;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.protocol.request.RequestBase;
import com.sap.b1.protocol.request.RequestData;
import com.sap.b1.protocol.request.UiContextViewStack;
import com.sap.b1.ui.base.UiJacksonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;

public class RestAspectBeforeInvoke {
    private Parameter[] methodParams;
    private Object[] args;
    boolean isDirectCall;
    String object;
    String action;
    RequestBase requestData;
    RequestBase requestDataWithBody;
    private HttpServletRequest httpRequest;
    private BoEnv env;

    private Map<String, Object> searchServiceRemoteDollar(Map<String, Object> param) {
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("$")) {
                newMap.put(key.substring(1), entry.getValue());
                continue;
            }
            newMap.put(key, entry.getValue());
        }
        return newMap;
    }

    RestAspectBeforeInvoke(HttpServletRequest httpRequest, ProceedingJoinPoint pjp, Object[] args, BoEnv env) throws Exception {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        this.methodParams = method.getParameters();
        this.httpRequest = httpRequest;
        this.args = args;
        this.env = env;
        String body = IOUtils.toString((InputStream)httpRequest.getInputStream(), (String)"utf8");
        if (!body.isEmpty()) {
            ObjectMapper om = UiJacksonBuilder.createObjectMapper();
            this.requestDataWithBody = (RequestBase)om.readValue(body, RequestData.class);
        }
        this.parseUrl(httpRequest.getRequestURI());
        this.checkRequestBody();
        Map param = this.mergeODataDollar();
        this.requestData = this.readRequestData();
        this.requestData.requestUrl = httpRequest.getRequestURI() + "?" + httpRequest.getQueryString();
        if (this.isDirectCall) {
            BeanUtils.copyProperties((Object)param, (Object)this.requestData);
            this.requestData.setObject(this.object);
            this.requestData.setAction(this.action);
        }
        this.mergeBodyParams();
        this.requestData.init(env);
        if (this.requestData instanceof RequestData) {
            this.convertBody((RequestData)this.requestData);
        }
        Object a = pjp.getTarget();
        BaseController serviceHandler = (BaseController)a;
        serviceHandler.beforeInvoke(this.requestData);
    }

    private void parseUrl(String uri) {
        String[] parts = uri.split("/");
        boolean bl = this.isDirectCall = parts[2].equals("co") || parts[2].equals("bo");
        if (!this.isDirectCall) {
            return;
        }
        this.object = URLDecoder.decode(parts[parts[2].equals("co") ? 4 : 3], Charset.defaultCharset());
        this.action = parts[parts[2].equals("co") ? 5 : 4];
    }

    boolean isUi() {
        return this.requestData.isUi();
    }

    private void convertBody(RequestData request) throws Exception {
        List mapList = request.dataTable;
        MetaTable metaTable = this.env.getMetaTable(request.getRootTableName());
        request.dataTable = BeanConvertFromMapWithDefaultValues.parseUdfObjectListFromUI((List)mapList, (MetaTable)metaTable, (DataAccessEnv)this.env);
        if (request.uiContext != null && request.uiContext.viewStack != null) {
            for (UiContextViewStack ui : request.uiContext.viewStack) {
                BeanValue.nodeToValue((Map)ui.data);
                String tableName = this.env.getTableNameByBoName(ui.bo);
                MetaTable uiStackMetaTable = this.env.getMetaTable(tableName);
                if ("List".equals(ui.viewType) && ui.data.get("dataList") instanceof List) {
                    List dataList = (List)ui.data.get("dataList");
                    ArrayList<Object> beanList = new ArrayList<Object>();
                    for (Map d : dataList) {
                        beanList.add(BeanConvertFromMapWithDefaultValues.fromJSMap((Map)d, (MetaTable)uiStackMetaTable, (DataAccessEnv)this.env));
                    }
                    ui.setBizObj(beanList);
                    continue;
                }
                Object bean = BeanConvertFromMapWithDefaultValues.fromJSMap((Map)ui.data, (MetaTable)uiStackMetaTable, (DataAccessEnv)this.env);
                ui.setBizObj(bean);
            }
        }
    }

    private Map<String, Object> mergeODataDollar() {
        HashMap<String, Object> param = new HashMap();
        if (this.httpRequest.getParameterMap() != null) {
            for (Map.Entry mapEntry : this.httpRequest.getParameterMap().entrySet()) {
                if (mapEntry.getValue() == null) continue;
                param.put((String)mapEntry.getKey(), ((String[])mapEntry.getValue())[0]);
            }
        }
        param = this.searchServiceRemoteDollar(param);
        return param;
    }

    private void checkRequestBody() {
        for (int i = 0; i < this.args.length; ++i) {
            RequestBody anno = this.methodParams[i].getAnnotation(RequestBody.class);
            if (anno == null) continue;
            throw new RuntimeException("@ReqeustBody should not be used");
        }
    }

    private RequestBase readRequestData() {
        for (int i = 0; i < this.args.length; ++i) {
            Object arg = this.args[i];
            if (!(arg instanceof RequestBase)) continue;
            return (RequestBase)arg;
        }
        return null;
    }

    private void mergeBodyParams() {
        if (this.requestData != null && this.requestDataWithBody != null) {
            BeanMergeIfNotNull.copyProperties((Object)this.requestDataWithBody, (Object)this.requestData);
        }
    }
}

