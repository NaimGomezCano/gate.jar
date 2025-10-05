/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.tcli.observer.body.of365.common.OF365RestResponse
 *  com.sap.b1.web.core.AuthException
 *  com.sap.b1.web.core.FeignConfigOf365AuthErrorDecoder
 *  feign.Response
 *  feign.codec.ErrorDecoder
 *  feign.codec.ErrorDecoder$Default
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.web.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.tcli.observer.body.of365.common.OF365RestResponse;
import com.sap.b1.web.core.AuthException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignConfigOf365AuthErrorDecoder
implements ErrorDecoder {
    private Logger logger = LoggerFactory.getLogger(FeignConfigOf365AuthErrorDecoder.class);
    private ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Exception decode(String methodKey, Response response) {
        int httpStatus = response.status();
        if (httpStatus == 401) {
            try (Reader reader2 = response.body().asReader(StandardCharsets.UTF_8);){
                String errorString2 = IOUtils.toString((Reader)reader2);
                AuthException authException = new AuthException(errorString2);
                return authException;
            }
            catch (IOException e) {
                this.logger.warn(e.getMessage());
                return this.defaultDecoder.decode(methodKey, response);
            }
        }
        if (httpStatus != 500) return this.defaultDecoder.decode(methodKey, response);
        try (Reader reader = response.body().asReader(StandardCharsets.UTF_8);){
            String errorString = IOUtils.toString((Reader)reader);
            ObjectMapper mapper = new ObjectMapper();
            OF365RestResponse resp = (OF365RestResponse)mapper.readValue(errorString, OF365RestResponse.class);
            if (resp.getError() != null) {
                RuntimeException runtimeException2 = new RuntimeException(resp.getError());
                return runtimeException2;
            }
            RuntimeException runtimeException = new RuntimeException(errorString);
            return runtimeException;
        }
        catch (Exception e) {
            this.logger.warn(e.getMessage());
        }
        return this.defaultDecoder.decode(methodKey, response);
    }
}

