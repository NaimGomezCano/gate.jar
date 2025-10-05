/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.permission.PermissionManager
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.servlets.service.variant.admin.UDXAdapter
 *  com.sap.b1.tcli.service.ouqr.OQAGService
 *  com.sap.b1.tcli.service.ouqr.OUQRService
 *  gen.bean.BmoOQAG
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.admin;

import com.sap.b1.infra.permission.PermissionManager;
import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.tcli.service.ouqr.OQAGService;
import com.sap.b1.tcli.service.ouqr.OUQRService;
import gen.bean.BmoOQAG;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UDXAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UDXAdapter.class);
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private OQAGService svcOQAG;
    @Autowired
    private OUQRService ouqrService;

    public Integer variantObjectNameToCatetoryId(String udqKey) {
        return this.ouqrService.getCategory(udqKey);
    }

    public Boolean hasUDQCategoryPermission(Integer cateId, PermissionTypeEnum minPermission) {
        try {
            List dtOQAG = this.svcOQAG.getQueryAuthorizationGroupsByCategoryId(cateId.toString());
            for (BmoOQAG drOQAG : dtOQAG) {
                String groupCode = drOQAG.getAUTHGRPID().toString();
                PermissionTypeEnum p = this.permissionManager.getPermission(groupCode);
                if (p.ordinal() <= 0 || p.ordinal() > minPermission.ordinal()) continue;
                return Boolean.TRUE;
            }
        }
        catch (Exception e) {
            logger.warn("get permission for UDQ category error", (Throwable)e);
        }
        return Boolean.FALSE;
    }
}

