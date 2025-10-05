/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.env.DataAccessEnv
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.base.PollingEnvImpl
 *  gen.bean.BmoOUSR
 *  gen.dao.DaoOUSR
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.base;

import com.sap.b1.env.DataAccessEnv;
import com.sap.b1.notification.base.NotiEnv;
import gen.bean.BmoOUSR;
import gen.dao.DaoOUSR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="PollingEnv")
public class PollingEnvImpl
extends DataAccessEnv
implements NotiEnv {
    private String schema;
    private String userCode;
    @Autowired
    DaoOUSR daoOUSR;
    private Boolean isMultiScheduling;
    private String userFullName;

    public Integer getUserId() throws Exception {
        String userCode = this.getUserCode();
        return ((BmoOUSR)this.daoOUSR.getUser(userCode).get(0)).getUSERID();
    }

    public String getUserFullName() {
        return ((BmoOUSR)this.daoOUSR.getUser(this.userCode).get(0)).getU_NAME();
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Boolean getIsMultiScheduling() throws Exception {
        return this.isMultiScheduling;
    }

    public void setIsMultiScheduling(Boolean isMultiScheduling) {
        this.isMultiScheduling = isMultiScheduling;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public String getSchema() {
        return this.schema;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}

