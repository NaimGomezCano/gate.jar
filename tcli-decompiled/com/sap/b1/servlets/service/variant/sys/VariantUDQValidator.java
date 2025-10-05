/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.servlets.service.variant.VariantsPkg
 *  com.sap.b1.servlets.service.variant.admin.UDXAdapter
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.VariantUDQValidator
 *  com.sap.b1.servlets.service.variant.sys.VariantValidator
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.bean.BmoOQCN
 *  gen.dao.DaoOQCN
 *  gen.str.VariantAdmin
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.core.annotation.Order
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.servlets.service.variant.VariantsPkg;
import com.sap.b1.servlets.service.variant.admin.UDXAdapter;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.VariantValidator;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.bean.BmoOQCN;
import gen.dao.DaoOQCN;
import gen.str.VariantAdmin;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=2)
public class VariantUDQValidator
implements VariantValidator {
    @Autowired
    private DaoOQCN daoOQCN;
    @Autowired
    private UDXAdapter udxAdapter;
    @Autowired
    private MessageUtil messageUtil;

    public ValidateResult validate(VariantsPkg pkg) {
        Map categoryNames = pkg.getUdqCategoryNames();
        if (categoryNames == null || categoryNames.isEmpty()) {
            return new ValidateResult(true);
        }
        for (String name : categoryNames.values()) {
            BmoOQCN category = this.daoOQCN.findByName(name);
            if (category == null || this.udxAdapter.hasUDQCategoryPermission(category.getCategoryId(), PermissionTypeEnum.PRM_READ_ONLY).booleanValue()) continue;
            return new ValidateResult(false, this.messageUtil.getMessage((MessageId)VariantAdmin.NoPerm_UDQ_CATE, new Object[]{name}));
        }
        return new ValidateResult(true);
    }
}

