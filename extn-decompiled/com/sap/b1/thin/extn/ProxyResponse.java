/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.ProxyResponse
 */
package com.sap.b1.thin.extn;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ProxyResponse {
    private int statusCode;
    private String statusMessage;
    private byte[] body;
    private Map<String, List<String>> headers;

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getBodyAsString(Charset charset) {
        return new String(this.body, charset);
    }

    public String getBodyAsString() {
        return this.getBodyAsString(StandardCharsets.UTF_8);
    }
}

