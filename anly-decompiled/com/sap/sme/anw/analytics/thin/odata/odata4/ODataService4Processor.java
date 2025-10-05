/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.ContextDataHolder
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryCountResponse
 *  com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryObject
 *  com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryReadResponse
 *  com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryService
 *  com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4Processor
 *  com.sap.sme.anw.analytics.type.request.RestRequest
 *  org.apache.olingo.commons.api.data.AbstractEntityCollection
 *  org.apache.olingo.commons.api.data.ContextURL
 *  org.apache.olingo.commons.api.data.ContextURL$Suffix
 *  org.apache.olingo.commons.api.data.EntityCollection
 *  org.apache.olingo.commons.api.edm.EdmEntitySet
 *  org.apache.olingo.commons.api.edm.EdmStructuredType
 *  org.apache.olingo.commons.api.format.ContentType
 *  org.apache.olingo.commons.api.http.HttpStatusCode
 *  org.apache.olingo.server.api.OData
 *  org.apache.olingo.server.api.ODataApplicationException
 *  org.apache.olingo.server.api.ODataLibraryException
 *  org.apache.olingo.server.api.ODataRequest
 *  org.apache.olingo.server.api.ODataResponse
 *  org.apache.olingo.server.api.ServiceMetadata
 *  org.apache.olingo.server.api.processor.CountEntityCollectionProcessor
 *  org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions
 *  org.apache.olingo.server.api.serializer.SerializerException
 *  org.apache.olingo.server.api.serializer.SerializerResult
 *  org.apache.olingo.server.api.uri.UriInfo
 *  org.apache.olingo.server.api.uri.UriResource
 *  org.apache.olingo.server.api.uri.UriResourceEntitySet
 *  org.apache.olingo.server.api.uri.queryoption.CountOption
 *  org.apache.olingo.server.api.uri.queryoption.ExpandOption
 *  org.apache.olingo.server.api.uri.queryoption.SelectOption
 */
package com.sap.sme.anw.analytics.thin.odata.odata4;

import com.sap.sme.anw.analytics.core.context.ContextDataHolder;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryCountResponse;
import com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryObject;
import com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryReadResponse;
import com.sap.sme.anw.analytics.core.odata.odata4.OData4QueryService;
import com.sap.sme.anw.analytics.type.request.RestRequest;
import java.net.URI;
import org.apache.olingo.commons.api.data.AbstractEntityCollection;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmStructuredType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataLibraryException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.CountEntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.queryoption.CountOption;
import org.apache.olingo.server.api.uri.queryoption.ExpandOption;
import org.apache.olingo.server.api.uri.queryoption.SelectOption;

public class ODataService4Processor
implements CountEntityCollectionProcessor {
    private OData odata;
    private ServiceMetadata serviceMetadata;

    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    public void countEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo) throws ODataApplicationException, ODataLibraryException {
        IContextData contextData = ContextDataHolder.getContextData();
        EdmEntitySet edmEntitySet = this.getEdmEntitySet(uriInfo, contextData);
        OData4QueryObject oData4QueryObject = new OData4QueryObject();
        oData4QueryObject.setEdmEntitySet(edmEntitySet);
        oData4QueryObject.setUriInfo(uriInfo);
        OData4QueryCountResponse oData4QueryCountResponse = new OData4QueryService(contextData, (RestRequest)oData4QueryObject).countEntityCollection();
        Integer count = oData4QueryCountResponse.getCount();
        response.setContent(this.odata.createFixedFormatSerializer().count(count));
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader("Content-Type", ContentType.TEXT_PLAIN.toContentTypeString());
    }

    public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
        IContextData contextData = ContextDataHolder.getContextData();
        EdmEntitySet edmEntitySet = this.getEdmEntitySet(uriInfo, contextData);
        OData4QueryObject oData4QueryObject = new OData4QueryObject();
        oData4QueryObject.setEdmEntitySet(edmEntitySet);
        oData4QueryObject.setUriInfo(uriInfo);
        OData4QueryReadResponse oData4QueryReadResponse = new OData4QueryService(contextData, (RestRequest)oData4QueryObject).readEntityCollection();
        EntityCollection entityCollection = oData4QueryReadResponse.getEntityCollection();
        CountOption countOption = uriInfo.getCountOption();
        if (countOption != null && countOption.getValue()) {
            entityCollection.setCount(Integer.valueOf(entityCollection.getEntities().size()));
        }
        String rootUri = request.getRawBaseUri() + "/";
        String id = rootUri + edmEntitySet.getName();
        SerializerResult serializerResult = this.odata.createSerializer(responseFormat).entityCollection(this.serviceMetadata, edmEntitySet.getEntityType(), (AbstractEntityCollection)entityCollection, EntityCollectionSerializerOptions.with().id(id).select(uriInfo.getSelectOption()).contextURL(this.isODataMetadataNone(responseFormat) ? null : this.getContextUrl(URI.create(rootUri), edmEntitySet, false, uriInfo.getExpandOption(), uriInfo.getSelectOption(), null)).count(countOption).build());
        response.setContent(serializerResult.getContent());
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader("Content-Type", responseFormat.toContentTypeString());
    }

    private EdmEntitySet getEdmEntitySet(UriInfo uriInfo, IContextData contextData) {
        UriResource uriResource = (UriResource)uriInfo.asUriInfoResource().getUriResourceParts().get(0);
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet)uriResource;
        return uriResourceEntitySet.getEntitySet();
    }

    private boolean isODataMetadataNone(ContentType contentType) {
        return contentType.isCompatible(ContentType.APPLICATION_JSON) && "none".equalsIgnoreCase(contentType.getParameter("odata.metadata"));
    }

    private ContextURL getContextUrl(URI serviceRoot, EdmEntitySet entitySet, boolean isSingleEntity, ExpandOption expand, SelectOption select, String navOrPropertyPath) throws SerializerException {
        return ContextURL.with().entitySet(entitySet).serviceRoot(serviceRoot).selectList(this.odata.createUriHelper().buildContextURLSelectList((EdmStructuredType)entitySet.getEntityType(), expand, select)).suffix((ContextURL.Suffix)(isSingleEntity ? ContextURL.Suffix.ENTITY : null)).navOrPropertyPath(navOrPropertyPath).build();
    }
}

