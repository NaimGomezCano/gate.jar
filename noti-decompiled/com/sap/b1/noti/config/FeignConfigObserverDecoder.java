/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.infra.engines.orm.UdfObject
 *  com.sap.b1.noti.config.FeignConfigObserverDecoder
 *  com.sap.b1.tcli.observer.core.Body
 *  com.sap.b1.tcli.observer.dagpool.BaseDagPool
 *  feign.Response
 *  feign.jackson.JacksonDecoder
 */
package com.sap.b1.noti.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.infra.engines.orm.UdfObject;
import com.sap.b1.tcli.observer.core.Body;
import com.sap.b1.tcli.observer.dagpool.BaseDagPool;
import feign.Response;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class FeignConfigObserverDecoder
extends JacksonDecoder {
    public FeignConfigObserverDecoder(ObjectMapper mapper) {
        super(mapper);
    }

    public Object decode(Response response, Type type) throws IOException {
        Object rt = super.decode(response, type);
        Collection eTabHeaders = (Collection)response.headers().get("ETag");
        if (eTabHeaders != null) {
            Body body;
            BaseDagPool dagPool;
            UdfObject oBmoObject;
            Object etag = (String)eTabHeaders.iterator().next();
            if (((String)etag).endsWith("-gzip\"")) {
                etag = ((String)etag).substring(0, ((String)etag).length() - "-gzip\"".length()) + "\"";
            }
            if ((oBmoObject = (dagPool = (body = (Body)rt).getDag()).getMainDag()) != null) {
                oBmoObject.set__eTag((String)etag);
            }
        }
        return rt;
    }
}

