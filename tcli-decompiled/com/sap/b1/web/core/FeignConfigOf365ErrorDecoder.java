/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.protocol.response.bo.BusinessException
 *  com.sap.b1.tcli.observer.body.of365.common.OF365RestResponse
 *  com.sap.b1.tcli.observer.body.of365.export.OF365DocResponse
 *  com.sap.b1.web.core.FeignConfigOf365ErrorDecoder
 *  feign.Response
 *  feign.codec.ErrorDecoder
 *  feign.codec.ErrorDecoder$Default
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.web.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.protocol.response.bo.BusinessException;
import com.sap.b1.tcli.observer.body.of365.common.OF365RestResponse;
import com.sap.b1.tcli.observer.body.of365.export.OF365DocResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignConfigOf365ErrorDecoder
implements ErrorDecoder {
    private Logger logger = LoggerFactory.getLogger(FeignConfigOf365ErrorDecoder.class);
    private ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
    private String of365Url;

    public FeignConfigOf365ErrorDecoder(String of365Url) {
        this.of365Url = of365Url;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Exception decode(String methodKey, Response response) {
        int httpStatus = response.status();
        String url = response.request().url();
        String path = url.substring(this.of365Url.length());
        if (httpStatus != 400 && httpStatus != 401) {
            if (httpStatus != 500) return this.defaultDecoder.decode(methodKey, response);
        }
        try (Reader reader = response.body().asReader(StandardCharsets.UTF_8);){
            ObjectMapper om = new ObjectMapper();
            if (path.startsWith("b1/export/")) {
                OF365DocResponse resp = (OF365DocResponse)om.readValue(reader, OF365DocResponse.class);
                BusinessException businessException2 = new BusinessException(resp.getError());
                return businessException2;
            }
            OF365RestResponse resp = (OF365RestResponse)om.readValue(reader, OF365RestResponse.class);
            BusinessException businessException = new BusinessException(resp.getError());
            return businessException;
        }
        catch (IOException e) {
            this.logger.warn(e.getMessage());
        }
        return this.defaultDecoder.decode(methodKey, response);
    }
}

