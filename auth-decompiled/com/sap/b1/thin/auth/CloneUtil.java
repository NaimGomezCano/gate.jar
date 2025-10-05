/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord
 *  com.sap.b1.thin.auth.AccessLog.UserAccessLog
 *  com.sap.b1.thin.auth.CloneUtil
 *  org.apache.commons.io.serialization.ValidatingObjectInputStream
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.auth;

import com.sap.b1.thin.auth.AccessLog.AccessLog;
import com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord;
import com.sap.b1.thin.auth.AccessLog.UserAccessLog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloneUtil.class);

    public static <T extends Serializable> T clone(T obj) {
        Serializable cloneObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ValidatingObjectInputStream objectInStream = new ValidatingObjectInputStream((InputStream)bais);
            objectInStream.accept(new Class[]{UserAccessLog.class, SupportUserLoginRecord.class, AccessLog.class, String.class, Integer.class, Number.class});
            cloneObj = (Serializable)objectInStream.readObject();
            objectInStream.close();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), (Throwable)e);
        }
        return (T)cloneObj;
    }
}

