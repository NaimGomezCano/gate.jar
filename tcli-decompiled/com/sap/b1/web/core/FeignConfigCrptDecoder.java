/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.web.core.FeignConfigCrptDecoder
 *  feign.Response
 *  feign.codec.Decoder
 *  feign.codec.Decoder$Default
 *  org.springframework.http.MediaType
 */
package com.sap.b1.web.core;

import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Collection;
import org.springframework.http.MediaType;

public class FeignConfigCrptDecoder
implements Decoder {
    private Decoder defaultDecoder = new Decoder.Default();

    public Object decode(Response response, Type type) throws IOException {
        Collection contentTypes = (Collection)response.headers().get("Content-Type");
        if (contentTypes == null || !contentTypes.contains(MediaType.APPLICATION_PDF.toString())) {
            String error = null;
            Collection errorMsgs = (Collection)response.headers().get("ERROR-MSG");
            if (errorMsgs != null && errorMsgs.size() > 0) {
                error = (String)errorMsgs.iterator().next();
                error = URLDecoder.decode(error, "UTF-8").replace("@#$", "\n");
            }
            throw new RuntimeException(error);
        }
        return this.defaultDecoder.decode(response, type);
    }
}

