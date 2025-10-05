/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.infra.engines.orm.UdfObject
 *  com.sap.b1.tcli.domain.Views$Observer
 *  com.sap.b1.tcli.observer.core.Body
 *  com.sap.b1.tcli.observer.dagpool.BaseDagPool
 *  com.sap.b1.web.core.FeignConfigObserverEncoder
 *  feign.RequestTemplate
 *  feign.codec.EncodeException
 *  feign.codec.Encoder
 */
package com.sap.b1.web.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.infra.engines.orm.UdfObject;
import com.sap.b1.tcli.domain.Views;
import com.sap.b1.tcli.observer.core.Body;
import com.sap.b1.tcli.observer.dagpool.BaseDagPool;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.lang.reflect.Type;

public class FeignConfigObserverEncoder
implements Encoder {
    private final ObjectMapper mapper;

    public FeignConfigObserverEncoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void encode(Object object, Type bodyType, RequestTemplate template) {
        try {
            String eTag;
            Body body;
            BaseDagPool dagPool;
            UdfObject oBmoObject;
            if (object instanceof Body && (oBmoObject = (dagPool = (body = (Body)object).getDag()).getMainDag()) != null && (eTag = oBmoObject.get__eTag()) != null) {
                template.header("If-Match", new String[]{eTag});
            }
            JavaType javaType = this.mapper.getTypeFactory().constructType(bodyType);
            template.body(this.mapper.writerWithView(Views.Observer.class).writeValueAsString(object));
        }
        catch (JsonProcessingException e) {
            throw new EncodeException(e.getMessage(), (Throwable)e);
        }
    }
}

