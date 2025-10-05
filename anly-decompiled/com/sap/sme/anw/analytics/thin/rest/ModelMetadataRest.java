/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.model.service.IModelService
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.thin.rest.ModelMetadataRest
 *  com.sap.sme.anw.analytics.type.model.ModelMetaObject
 *  com.sap.sme.anw.analytics.type.model.ModelMetaResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.sme.anw.analytics.thin.rest;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.model.service.IModelService;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.type.model.ModelMetaObject;
import com.sap.sme.anw.analytics.type.model.ModelMetaResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/ModelMetadata/v1"})
public class ModelMetadataRest {
    @RequestMapping(value={"", "/{id}", "/"}, method={RequestMethod.POST})
    public ModelMetaResponse handleModelMetaRequest(HttpServletRequest httpServletRequest, @PathVariable(value="id", required=false) String id, @RequestBody ModelMetaObject modelMetaObject) {
        IContextData contextData = (IContextData)httpServletRequest.getAttribute("contextData");
        IModelService modelService = (IModelService)ServiceFactory.getService(IModelService.class);
        if (modelMetaObject.getModelId() == null) {
            modelMetaObject.setModelId(id);
        }
        return modelService.getModelMeta(contextData, modelMetaObject);
    }
}

