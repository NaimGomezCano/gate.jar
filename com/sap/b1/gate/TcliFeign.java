/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.TcliFeign
 *  feign.HeaderMap
 *  feign.RequestLine
 */
package com.sap.b1.gate;

import feign.HeaderMap;
import feign.RequestLine;
import java.util.Map;

public interface TcliFeign {
    @RequestLine(value="GET /service/data/user.svc")
    public String user(@HeaderMap Map<String, Object> var1);
}

