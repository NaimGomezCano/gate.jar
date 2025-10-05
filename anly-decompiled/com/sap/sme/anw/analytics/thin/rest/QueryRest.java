/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.query.service.IQueryService
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.thin.rest.QueryRest
 *  com.sap.sme.anw.analytics.type.query.QueryLOVObject
 *  com.sap.sme.anw.analytics.type.query.QueryLOVResponse
 *  com.sap.sme.anw.analytics.type.query.QueryObject
 *  com.sap.sme.anw.analytics.type.query.blending.BlendingQueryObject
 *  com.sap.sme.anw.analytics.type.query.blending.BlendingQueryResultResponse
 *  com.sap.sme.anw.analytics.type.query.multiquery.MultiQueryObject
 *  com.sap.sme.anw.analytics.type.query.multiquery.MultiQueryResultResponse
 *  com.sap.sme.anw.analytics.type.query.result.QueryResultResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.sme.anw.analytics.thin.rest;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.query.service.IQueryService;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.type.query.QueryLOVObject;
import com.sap.sme.anw.analytics.type.query.QueryLOVResponse;
import com.sap.sme.anw.analytics.type.query.QueryObject;
import com.sap.sme.anw.analytics.type.query.blending.BlendingQueryObject;
import com.sap.sme.anw.analytics.type.query.blending.BlendingQueryResultResponse;
import com.sap.sme.anw.analytics.type.query.multiquery.MultiQueryObject;
import com.sap.sme.anw.analytics.type.query.multiquery.MultiQueryResultResponse;
import com.sap.sme.anw.analytics.type.query.result.QueryResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/Query/v1"})
public class QueryRest {
    @RequestMapping(value={"/LOV"}, method={RequestMethod.POST})
    public QueryLOVResponse handleLOVRequest(HttpServletRequest httpServletRequest, @RequestBody QueryLOVObject lovObject) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IQueryService queryService = (IQueryService)ServiceFactory.getService(IQueryService.class);
        return queryService.getLOV(contextData, lovObject);
    }

    @RequestMapping(value={"/QueryResult"}, method={RequestMethod.POST})
    public QueryResultResponse handleQueryResultRequest(HttpServletRequest httpServletRequest, @RequestBody QueryObject queryObject) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IQueryService queryService = (IQueryService)ServiceFactory.getService(IQueryService.class);
        return queryService.getQueryResult(contextData, queryObject);
    }

    @RequestMapping(value={"/BlendingQueryResult"}, method={RequestMethod.POST})
    public BlendingQueryResultResponse handleBlendingQueryResultRequest(HttpServletRequest httpServletRequest, @RequestBody BlendingQueryObject blendingQueryObject) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IQueryService queryService = (IQueryService)ServiceFactory.getService(IQueryService.class);
        return queryService.getBlendingQueryResult(contextData, blendingQueryObject);
    }

    @RequestMapping(value={"/MultiQueryResult"}, method={RequestMethod.POST})
    public MultiQueryResultResponse handleMultiQueryResultRequest(HttpServletRequest httpServletRequest, @RequestBody MultiQueryObject multiQueryObject) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IQueryService queryService = (IQueryService)ServiceFactory.getService(IQueryService.class);
        return queryService.getMultiQueryResult(contextData, multiQueryObject);
    }
}

