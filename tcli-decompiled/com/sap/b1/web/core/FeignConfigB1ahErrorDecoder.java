/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.web.core.B1ahResponse
 *  com.sap.b1.web.core.FeignConfigB1ahErrorDecoder
 *  feign.Response
 *  feign.codec.ErrorDecoder
 *  feign.codec.ErrorDecoder$Default
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.web.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.web.core.B1ahResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignConfigB1ahErrorDecoder
implements ErrorDecoder {
    private Logger logger = LoggerFactory.getLogger(FeignConfigB1ahErrorDecoder.class);
    private ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
    private String b1ahUrl;

    public FeignConfigB1ahErrorDecoder(String b1ahUrl) {
        this.b1ahUrl = b1ahUrl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Exception decode(String methodKey, Response response) {
        int httpStatus = response.status();
        String url = response.request().url();
        String path = url.substring(this.b1ahUrl.length());
        if (httpStatus != 200) return this.defaultDecoder.decode(methodKey, response);
        try (Reader reader = response.body().asReader();){
            ObjectMapper om = new ObjectMapper();
            if (!path.startsWith("es/")) return this.defaultDecoder.decode(methodKey, response);
            B1ahResponse resp = (B1ahResponse)om.readValue(reader, B1ahResponse.class);
            if (!"3".equals(resp.status)) return this.defaultDecoder.decode(methodKey, response);
            RuntimeException runtimeException = new RuntimeException(resp.getMessage());
            return runtimeException;
        }
        catch (IOException e) {
            this.logger.warn(e.getMessage());
        }
        return this.defaultDecoder.decode(methodKey, response);
    }
}

