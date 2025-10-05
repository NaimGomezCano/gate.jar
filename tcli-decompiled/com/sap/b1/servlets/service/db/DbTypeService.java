/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.servlets.service.db.DbTypeService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.db;

import com.sap.b1.db.DBTypeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTypeService {
    @Autowired
    private DBTypeConfig dbTypeConfig;

    @RequestMapping(path={"/dbtype/get.svc"})
    public String get() {
        return this.dbTypeConfig.get().toString();
    }
}

