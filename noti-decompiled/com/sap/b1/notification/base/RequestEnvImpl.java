/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.base.RequestEnvImpl
 *  org.springframework.context.annotation.Primary
 *  org.springframework.stereotype.Component
 *  org.springframework.web.context.annotation.RequestScope
 */
package com.sap.b1.notification.base;

import com.sap.b1.app.base.AppEnv;
import com.sap.b1.notification.base.NotiEnv;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component(value="RequestEnv")
@RequestScope
@Primary
public class RequestEnvImpl
extends AppEnv
implements NotiEnv {
    public Boolean getIsMultiScheduling() throws Exception {
        return this.isMultiScheduling();
    }

    public void setIsMultiScheduling(Boolean isMultiScheduling) {
        throw new RuntimeException();
    }

    public void setUserCode(String userCode) {
        throw new RuntimeException();
    }

    public String getUserFullName() {
        return this.getUserFullName();
    }

    public void setUserFullName(String userFullName) {
        throw new RuntimeException();
    }

    public void setSchema(String schema) {
        throw new RuntimeException();
    }
}

