/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.exception.ExceptionUtils
 *  com.sap.b1.svcl.client.ServiceLayerException
 */
package com.sap.b1.servlets.service.variant.exception;

import com.sap.b1.svcl.client.ServiceLayerException;

/*
 * Exception performing whole class analysis ignored.
 */
public class ExceptionUtils {
    public static String digServiceLayerErrorMessage(Throwable t) {
        while (t != null && !(t instanceof ServiceLayerException)) {
            t = t.getCause();
        }
        if (t != null) {
            return t.getMessage();
        }
        return null;
    }

    public static String digServiceLayerErrorMessage(Throwable t, String defaultMsg) {
        String msg = ExceptionUtils.digServiceLayerErrorMessage((Throwable)t);
        if (msg == null) {
            msg = defaultMsg;
        }
        return msg;
    }
}

