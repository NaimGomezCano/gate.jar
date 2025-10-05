/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.ContextDataHolder
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryCountResponse
 *  com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryObject
 *  com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryReadResponse
 *  com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryService
 *  com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2Processor
 *  com.sap.sme.anw.analytics.type.request.RestRequest
 *  org.apache.olingo.odata2.api.commons.InlineCount
 *  org.apache.olingo.odata2.api.edm.EdmEntitySet
 *  org.apache.olingo.odata2.api.ep.EntityProvider
 *  org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties
 *  org.apache.olingo.odata2.api.exception.ODataException
 *  org.apache.olingo.odata2.api.processor.ODataResponse
 *  org.apache.olingo.odata2.api.processor.ODataSingleProcessor
 *  org.apache.olingo.odata2.api.uri.UriParser
 *  org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo
 *  org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo
 */
package com.sap.sme.anw.analytics.thin.odata.odata2;

import com.sap.sme.anw.analytics.core.context.ContextDataHolder;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryCountResponse;
import com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryObject;
import com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryReadResponse;
import com.sap.sme.anw.analytics.core.odata.odata2.OData2QueryService;
import com.sap.sme.anw.analytics.type.request.RestRequest;
import java.net.URI;
import java.util.List;
import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;

public class ODataService2Processor
extends ODataSingleProcessor {
    public ODataResponse countEntitySet(GetEntitySetCountUriInfo uriInfo, String contentType) throws ODataException {
        IContextData contextData = ContextDataHolder.getContextData();
        EdmEntitySet entitySet = uriInfo.getStartEntitySet();
        OData2QueryObject oData2QueryObject = new OData2QueryObject();
        oData2QueryObject.setEntitySet(entitySet);
        OData2QueryCountResponse oData2QueryCountResponse = new OData2QueryService(contextData, (RestRequest)oData2QueryObject).countEntitySet();
        Integer count = oData2QueryCountResponse.getCount();
        return EntityProvider.writeText((String)String.valueOf(count));
    }

    public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {
        IContextData contextData = ContextDataHolder.getContextData();
        EdmEntitySet entitySet = uriInfo.getStartEntitySet();
        OData2QueryObject oData2QueryObject = new OData2QueryObject();
        oData2QueryObject.setEntitySet(entitySet);
        oData2QueryObject.setUriInfo(uriInfo);
        OData2QueryReadResponse oData2QueryReadResponse = new OData2QueryService(contextData, (RestRequest)oData2QueryObject).readEntitySet();
        List list = oData2QueryReadResponse.getList();
        InlineCount inlineCountType = uriInfo.getInlineCount();
        Integer inlineCount = null;
        if (inlineCountType == InlineCount.ALLPAGES) {
            inlineCount = list.size();
        }
        return EntityProvider.writeFeed((String)contentType, (EdmEntitySet)entitySet, (List)list, (EntityProviderWriteProperties)EntityProviderWriteProperties.serviceRoot((URI)this.getContext().getPathInfo().getServiceRoot()).contentOnly(true).expandSelectTree(UriParser.createExpandSelectTree((List)uriInfo.getSelect(), (List)uriInfo.getExpand())).inlineCountType(inlineCountType).inlineCount(inlineCount).build());
    }
}

