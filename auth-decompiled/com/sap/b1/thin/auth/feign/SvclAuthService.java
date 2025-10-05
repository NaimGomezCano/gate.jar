/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.feign.SvclAuthService
 *  com.sap.b1.thin.auth.feign.SvclAuthService$ServiceLayerAuthServiceLogin
 *  feign.HeaderMap
 *  feign.Headers
 *  feign.RequestLine
 *  feign.Response
 */
package com.sap.b1.thin.auth.feign;

import com.sap.b1.thin.auth.feign.SvclAuthService;
import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;
import feign.Response;
import java.util.Map;

public interface SvclAuthService {
    @Headers(value={"Content-type: application/json; charset=utf-8"})
    @RequestLine(value="POST /b1s/v1/Login")
    public Response login(ServiceLayerAuthServiceLogin var1, @HeaderMap Map<String, Object> var2);

    @RequestLine(value="POST /b1s/v1/Logout")
    public void logout(@HeaderMap Map<String, Object> var1);
}

