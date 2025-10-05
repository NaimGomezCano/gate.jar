/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.HttpCache
 *  com.sap.b1.thin.extn.ProxyResponse
 */
package com.sap.b1.thin.extn;

import com.sap.b1.thin.extn.ProxyResponse;
import java.io.File;

public interface HttpCache {
    public static final String CACHE_HOME = (System.getenv("B1_CACHE_HOME") == null ? System.getProperty("java.io.tmpdir") : System.getenv("B1_CACHE_HOME")) + File.separator + "b1-thinclient-cache";

    public ProxyResponse get(String var1, String var2);

    public ProxyResponse get(String var1, int var2);

    public ProxyResponse getPackageJson(int var1);

    public void put(String var1, String var2, ProxyResponse var3);

    public Boolean isCached(int var1);
}

