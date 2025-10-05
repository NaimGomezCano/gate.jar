/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.notification.base.NotiEnv
 */
package com.sap.b1.notification.base;

import com.sap.b1.infra.engines.metadata.MetaTable;

public interface NotiEnv {
    public Integer getUserId() throws Exception;

    public Boolean getIsMultiScheduling() throws Exception;

    public void setIsMultiScheduling(Boolean var1);

    public String getUserCode();

    public void setUserCode(String var1);

    public String getUserFullName();

    public void setUserFullName(String var1);

    public String getSchema();

    public void setSchema(String var1);

    public MetaTable getMetaTable(Class<?> var1) throws Exception;
}

