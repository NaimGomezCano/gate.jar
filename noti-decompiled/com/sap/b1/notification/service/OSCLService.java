/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.service.OSCLService
 *  com.sap.b1.tcli.core.BaseService
 *  com.sap.b1.tcli.observer.body.OSCLBody
 *  com.sap.b1.tcli.observer.dagpool.OSCLDagPool
 *  com.sap.b1.tcli.observer.feign.OSCLFeign
 *  com.sap.b1.tcli.observer.mapper.OSCLMapper
 *  gen.bean.BmoOSCL
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.service;

import com.sap.b1.tcli.core.BaseService;
import com.sap.b1.tcli.observer.body.OSCLBody;
import com.sap.b1.tcli.observer.dagpool.OSCLDagPool;
import com.sap.b1.tcli.observer.feign.OSCLFeign;
import com.sap.b1.tcli.observer.mapper.OSCLMapper;
import gen.bean.BmoOSCL;
import org.springframework.stereotype.Component;

@Component
public class OSCLService
extends BaseService<BmoOSCL, Integer, OSCLFeign, OSCLBody, OSCLDagPool, OSCLMapper> {
    public BmoOSCL findById(Integer id) throws Exception {
        return (BmoOSCL)super.findById((Object)id);
    }
}

