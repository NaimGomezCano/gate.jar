/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum
 *  com.sap.b1.servlets.service.variant.sys.checkrules.exception.ValidateException
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.exception;

import com.sap.b1.servlets.service.variant.sys.checkrules.enums.RulesErrorEnum;
import java.util.Arrays;
import java.util.List;

public class ValidateException
extends RuntimeException {
    private RulesErrorEnum rulesError;
    private List arguments;

    public ValidateException(RulesErrorEnum error, Object ... arguments) {
        super(error.name());
        this.rulesError = error;
        this.arguments = Arrays.asList(arguments);
    }

    public List getArguments() {
        return this.arguments;
    }

    public RulesErrorEnum getRulesError() {
        return this.rulesError;
    }
}

