/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.infra.permission.PermissionManager
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.servlets.service.variant.VariantsPkg
 *  com.sap.b1.servlets.service.variant.exception.InvalidDataException
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.VariantCommonValidator
 *  com.sap.b1.servlets.service.variant.sys.VariantValidator
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.bean.BmoOWSV
 *  gen.str.VariantAdmin
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.core.annotation.Order
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.app.base.AppEnv;
import com.sap.b1.infra.permission.PermissionManager;
import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.servlets.service.variant.VariantsPkg;
import com.sap.b1.servlets.service.variant.exception.InvalidDataException;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.VariantValidator;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.bean.BmoOWSV;
import gen.str.VariantAdmin;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=0)
public class VariantCommonValidator
implements VariantValidator {
    private static final Logger logger = LoggerFactory.getLogger(VariantCommonValidator.class);
    @Autowired
    private AppEnv env;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private PermissionManager permissionManager;

    public ValidateResult validate(VariantsPkg pkg) {
        try {
            this.checkVersion(pkg.getVersion());
            return new ValidateResult(true);
        }
        catch (Exception e) {
            logger.error("error happened in common variant validator", (Throwable)e);
            return new ValidateResult(false, e.getMessage());
        }
    }

    private void checkLinkCreationPermission(List<BmoOWSV> links) throws Exception {
        if (links != null && !links.isEmpty()) {
            PermissionTypeEnum permission = this.permissionManager.getPermission(2339);
            if (permission == PermissionTypeEnum.PRM_FULL || "Y".equals(this.env.getOADM().getWebCliRO()) && permission == PermissionTypeEnum.PRM_READ_ONLY) {
                return;
            }
            throw new InvalidDataException(this.messageUtil.getMessage((MessageId)VariantAdmin.NO_LINK_PERMISSION, new Object[0]));
        }
    }

    private void checkVersion(Integer pkgVersion) throws Exception {
        Integer version = this.env.getCINF().getVersion();
        if (pkgVersion.compareTo(version) > 0) {
            throw new InvalidDataException(this.messageUtil.getMessage((MessageId)VariantAdmin.WRONG_VERSION_PKG, new Object[0]));
        }
    }
}

