/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.dimview.ViewColumn
 *  com.sap.b1.dimview.ViewColumns
 *  com.sap.b1.servlets.service.variant.sys.checkrules.model.ViewColumnsWithNoCheck
 *  lombok.Generated
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.model;

import com.sap.b1.dimview.ViewColumn;
import com.sap.b1.dimview.ViewColumns;
import lombok.Generated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewColumnsWithNoCheck
extends ViewColumns {
    @Generated
    private static final Logger log = LogManager.getLogger(ViewColumnsWithNoCheck.class);

    public boolean add(ViewColumn e) {
        log.warn("ViewColumnsWithNoCheck can not add view column");
        return false;
    }

    public ViewColumn getByName(String name) {
        log.warn("ViewColumnsWithNoCheck can not get view column");
        return null;
    }
}

