/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.permission.PermissionManager
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.infra.share.feign.tcli.entity.Permission
 *  com.sap.b1.servlets.service.permission.PermissionService
 *  com.sap.b1.spring.RequestMappingTx
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.permission;

import com.sap.b1.infra.permission.PermissionManager;
import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.infra.share.feign.tcli.entity.Permission;
import com.sap.b1.spring.RequestMappingTx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    @Autowired
    PermissionManager permissionManager;

    @Deprecated
    @RequestMappingTx(path={"/permission.svc"})
    public List<Permission> getPermission(@RequestParam(name="objectId", required=false) String objectId, @RequestParam(name="permissionId", required=false) String permissionId) throws Exception {
        String[] objectIds = StringUtils.split((String)objectId, (String)",");
        ArrayList<Permission> permissions = new ArrayList<Permission>();
        if (objectIds != null) {
            for (String id : objectIds) {
                PermissionTypeEnum type = this.permissionManager.getPermission(id);
                Permission permission = new Permission();
                permission.setObjectId(id);
                permission.setPermissionType(type.getPermissionTypeName());
                permissions.add(permission);
            }
        }
        permissions.addAll(this.getPermissionByPermissionId(permissionId));
        return permissions;
    }

    @RequestMapping(method={RequestMethod.POST}, path={"/permission.svc/v2"})
    public List<Permission> checkPermissions(@RequestBody List<Permission> permissions) throws Exception {
        for (Permission permission : permissions) {
            permission.setGranted(Boolean.valueOf(true));
            PermissionTypeEnum permissionType = PermissionTypeEnum.getEnumFromTypeName((String)permission.getPermissionType());
            if (permissionType == null) continue;
            if (permission.getObjectId() != null) {
                permission.setGranted(Boolean.valueOf(this.permissionManager.isGrantPermissionOnObject(permission.getObjectId(), permissionType)));
                continue;
            }
            if (permission.getPermissionId() == null) continue;
            permission.setGranted(Boolean.valueOf(this.permissionManager.isGrantPermissionOnId(permission.getPermissionId().intValue(), permissionType, permission.getPermissionId().toString())));
        }
        return permissions;
    }

    private List<Permission> getPermissionByPermissionId(String permissionId) throws Exception {
        String[] permissionIds = StringUtils.split((String)permissionId, (String)",");
        ArrayList<Permission> permissions = new ArrayList<Permission>();
        if (permissionIds == null) {
            return permissions;
        }
        Set permissionIdSet = Arrays.stream(permissionIds).map(c -> Integer.valueOf(c.trim())).collect(Collectors.toSet());
        if (permissionIdSet != null) {
            for (Integer id : permissionIdSet) {
                PermissionTypeEnum type = this.permissionManager.getPermission(id.intValue());
                Permission permission = new Permission();
                permission.setPermissionId(id);
                permission.setPermissionType(type.getPermissionTypeName());
                permissions.add(permission);
                logger.info("PermissionId is {}, PermissionType is {}", (Object)id, (Object)type.getPermissionTypeName());
            }
        }
        return permissions;
    }
}

