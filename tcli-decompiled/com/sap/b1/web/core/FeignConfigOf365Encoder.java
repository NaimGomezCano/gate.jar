/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.web.core.FeignConfigOf365Encoder
 *  feign.RequestTemplate
 *  feign.jackson.JacksonEncoder
 *  org.springframework.util.StreamUtils
 */
package com.sap.b1.web.core;

import feign.RequestTemplate;
import feign.jackson.JacksonEncoder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import org.springframework.util.StreamUtils;

public class FeignConfigOf365Encoder
extends JacksonEncoder {
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        if (object instanceof byte[]) {
            try {
                template.body(StreamUtils.copyToByteArray((InputStream)new ByteArrayInputStream((byte[])object)), null);
            }
            catch (IOException iOException) {}
        } else {
            super.encode(object, bodyType, template);
        }
    }
}

