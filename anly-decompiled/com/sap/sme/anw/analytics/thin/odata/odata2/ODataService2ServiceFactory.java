/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2EdmProvider
 *  com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2Processor
 *  com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2ServiceFactory
 *  org.apache.olingo.odata2.api.ODataService
 *  org.apache.olingo.odata2.api.ODataServiceFactory
 *  org.apache.olingo.odata2.api.edm.provider.EdmProvider
 *  org.apache.olingo.odata2.api.exception.ODataException
 *  org.apache.olingo.odata2.api.processor.ODataContext
 *  org.apache.olingo.odata2.api.processor.ODataSingleProcessor
 */
package com.sap.sme.anw.analytics.thin.odata.odata2;

import com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2EdmProvider;
import com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2Processor;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;

public class ODataService2ServiceFactory
extends ODataServiceFactory {
    public static final EdmProvider edmProvider = new ODataService2EdmProvider();
    public static final ODataSingleProcessor processor = new ODataService2Processor();

    public ODataService createService(ODataContext context) throws ODataException {
        return this.createODataSingleProcessorService(edmProvider, processor);
    }
}

