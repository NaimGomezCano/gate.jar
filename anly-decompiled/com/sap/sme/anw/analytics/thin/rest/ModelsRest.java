/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.model.service.IModelService
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.thin.rest.ModelsRest
 *  com.sap.sme.anw.analytics.type.model.LinkableGroup
 *  com.sap.sme.anw.analytics.type.model.ModelListResponse
 *  com.sap.sme.anw.analytics.type.model.ViewType
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.sme.anw.analytics.thin.rest;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.model.service.IModelService;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.type.model.LinkableGroup;
import com.sap.sme.anw.analytics.type.model.ModelListResponse;
import com.sap.sme.anw.analytics.type.model.ViewType;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/Models/v1"})
public class ModelsRest {
    @RequestMapping(method={RequestMethod.POST})
    public ModelListResponse handleModelsRequest(HttpServletRequest httpServletRequest, @RequestParam(required=false, defaultValue="false") boolean auto, @RequestParam(required=false, defaultValue="listView") ViewType viewType) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IModelService modelService = (IModelService)ServiceFactory.getService(IModelService.class);
        return modelService.getModelList(contextData, auto, viewType);
    }

    @RequestMapping(value={"/linkableGroups"}, method={RequestMethod.GET})
    public List<LinkableGroup> handleLinkedObjGroupsRequest(HttpServletRequest httpServletRequest) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IModelService modelService = (IModelService)ServiceFactory.getService(IModelService.class);
        return modelService.getLinkableGroups(contextData);
    }
}

