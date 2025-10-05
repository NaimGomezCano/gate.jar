/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.service.shorlink.LinkRepo
 */
package com.sap.b1.service.shorlink;

public interface LinkRepo {
    public static final long TTL = 2592000L;

    public void store(String var1, String var2, String var3, String var4);

    public String load(String var1);

    public void removeExpiredData();
}

