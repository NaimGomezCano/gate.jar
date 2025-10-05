/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.OIDCServiceLayerService
 *  feign.HeaderMap
 *  feign.Headers
 *  feign.RequestLine
 *  feign.Response
 */
package com.sap.b1.thin.auth;

import com.sap.b1.thin.auth.AccessLog.AccessLog;
import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;
import feign.Response;
import java.util.Map;

public interface OIDCServiceLayerService {
    @RequestLine(value="POST /b1s/v1/LicenseService_GetInstallationNumber")
    public Response requestInstallationNumber(@HeaderMap Map<String, Object> var1);

    @Headers(value={"Content-Type: application/json"})
    @RequestLine(value="POST /b1s/v1/CompanyService_LogLoginAction")
    public Response requestLogLoginAction(AccessLog var1, @HeaderMap Map<String, Object> var2);

    @Headers(value={"Content-Type: application/json"})
    @RequestLine(value="POST /b1s/v1/CompanyService_LogLogoffAction")
    public Response requestLogLogoffAction(AccessLog var1, @HeaderMap Map<String, Object> var2);
}

