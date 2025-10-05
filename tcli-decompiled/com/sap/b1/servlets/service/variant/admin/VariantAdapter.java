/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.JpaUtil
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.protocol.response.CategoryResponse
 *  com.sap.b1.servlets.service.variant.VariantCategory
 *  com.sap.b1.servlets.service.variant.VariantWithI18N
 *  com.sap.b1.servlets.service.variant.admin.AnalyticsAdapter
 *  com.sap.b1.servlets.service.variant.admin.UDXAdapter
 *  com.sap.b1.servlets.service.variant.admin.VariantAdapter
 *  com.sap.b1.svcl.client.enums.BoYesNoEnum
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.bean.BmoOWVT
 *  gen.dao.DaoOWVT
 *  gen.str.BoViews
 *  gen.str.VariantAdmin
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.admin;

import com.sap.b1.bo.base.JpaUtil;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.protocol.response.CategoryResponse;
import com.sap.b1.servlets.service.variant.VariantCategory;
import com.sap.b1.servlets.service.variant.VariantWithI18N;
import com.sap.b1.servlets.service.variant.admin.AnalyticsAdapter;
import com.sap.b1.servlets.service.variant.admin.UDXAdapter;
import com.sap.b1.svcl.client.enums.BoYesNoEnum;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.bean.BmoOWVT;
import gen.dao.DaoOWVT;
import gen.str.BoViews;
import gen.str.VariantAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VariantAdapter {
    private static final Logger logger = LoggerFactory.getLogger(VariantAdapter.class);
    public static final String SYS_VARIANTS_PREFFIX = "SYS:";
    private static final String SEPARATOR = ";";
    private static final String ANLY_OBJ_PREFFIX = "ANLY;";
    @Autowired
    private AnalyticsAdapter analyticsAdapter;
    @Autowired
    private UDXAdapter udxAdapter;
    @Autowired
    private MessageUtil messageUtil;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DaoOWVT daoOwvt;

    public List<CategoryResponse<VariantCategory, VariantWithI18N>> adapt(List<BmoOWVT> variants, Integer userId, boolean selectAll, boolean showSys) {
        ArrayList<CategoryResponse<VariantCategory, VariantWithI18N>> dataList = new ArrayList<CategoryResponse<VariantCategory, VariantWithI18N>>();
        HashMap categoryPermissions = new HashMap();
        Map<String, List<VariantWithI18N>> groupedVariants = variants.stream().filter(v -> {
            if (MetaTable.isUDQ((String)v.getObjName())) {
                Integer cateId = this.udxAdapter.variantObjectNameToCatetoryId(v.getObjName());
                if (!categoryPermissions.containsKey(cateId)) {
                    categoryPermissions.put(cateId, this.udxAdapter.hasUDQCategoryPermission(cateId, PermissionTypeEnum.PRM_READ_ONLY));
                }
                return (Boolean)categoryPermissions.get(cateId);
            }
            return true;
        }).map(arg_0 -> this.toI18NVariant(arg_0)).collect(Collectors.groupingBy(arg_0 -> this.buildGroupKey(arg_0)));
        ArrayList udtNames = new ArrayList();
        ArrayList udqNames = new ArrayList();
        groupedVariants.keySet().forEach(name -> {
            if (MetaTable.isUDT((String)name)) {
                udtNames.add(name);
            } else if (MetaTable.isUDQ((String)name)) {
                udqNames.add(name);
            }
        });
        Map udtNameMapping = this.fetchRelatedUDTNames(udtNames);
        Map udqNameMapping = this.fetchRelatedUDQNames(udqNames);
        if (selectAll && showSys) {
            List objectsWithSysVariants = this.daoOwvt.findAllObjectsHasSysVariants();
            this.handleAnalyticsObjects(objectsWithSysVariants);
            for (BmoOWVT o2 : objectsWithSysVariants) {
                String nameKey = this.buildGroupKey(new VariantWithI18N(o2));
                if (groupedVariants.get(nameKey) != null) continue;
                groupedVariants.put(nameKey, new ArrayList());
            }
        }
        groupedVariants.forEach((name, viewVariants) -> {
            CategoryResponse r = new CategoryResponse();
            r.setCategory((Object)new VariantCategory(this.toI18NName(udtNameMapping, udqNameMapping, name)));
            r.setNodes(viewVariants);
            viewVariants.sort(Comparator.comparing(VariantWithI18N::getViewType).thenComparing(VariantWithI18N::getName));
            if (showSys && (name.startsWith("O") || name.startsWith(ANLY_OBJ_PREFFIX))) {
                VariantWithI18N objSysVariant = new VariantWithI18N();
                objSysVariant.setGuid(SYS_VARIANTS_PREFFIX + name);
                objSysVariant.setIsPublic(BoYesNoEnum.tYES.getValue());
                objSysVariant.setName(this.messageUtil.getMessage((MessageId)VariantAdmin.SYS_Variants, new Object[0]));
                viewVariants.add(0, objSysVariant);
            }
            dataList.add(r);
        });
        dataList.sort(Comparator.comparing(o -> ((VariantCategory)o.getCategory()).getName()));
        return dataList;
    }

    private void handleAnalyticsObjects(List<BmoOWVT> objectsWithSysVariants) {
        this.analyticsAdapter.excludeAnalyticsObjectsInMSSQL(objectsWithSysVariants);
    }

    private VariantWithI18N toI18NVariant(BmoOWVT v) {
        VariantWithI18N nv = new VariantWithI18N(v);
        nv.setViewTypeI18N(this.getViewTypeI18N(nv.getViewType(), nv.getViewId()));
        return nv;
    }

    private String buildGroupKey(VariantWithI18N o) {
        if (!this.analyticsAdapter.isAnalyticsObject(o.getObjName())) {
            return o.getObjName();
        }
        return String.format("%s;%s;%s", o.getObjName(), o.getViewType(), o.getViewId());
    }

    private Map<String, String> fetchRelatedUDQNames(List<String> udqNames) {
        if (udqNames.isEmpty()) {
            return Collections.emptyMap();
        }
        StringBuilder sql = new StringBuilder("select \"IntrnalKey\", \"QCategory\", \"QName\" from OUQR where ");
        ArrayList<Integer> params = new ArrayList<Integer>();
        for (String name : udqNames) {
            String[] parts = name.split("_");
            int internKey = Integer.parseInt(parts[1].replace("M", "-"));
            int category = Integer.parseInt(parts[2].replace("M", "-"));
            params.add(internKey);
            params.add(category);
            sql.append("(\"IntrnalKey\" = ? and \"QCategory\" = ?) or ");
        }
        sql.delete(sql.length() - 4, sql.length() - 1);
        List rows = JpaUtil.load((EntityManager)this.em, Object[].class, (String)sql.toString(), (Object[])params.toArray());
        HashMap<String, String> nameMapping = new HashMap<String, String>();
        rows.forEach(row -> {
            String objName = String.format("UDQ_%s_%s", row[0], row[1]).replaceAll("-", "M");
            nameMapping.put(objName, this.messageUtil.getMessage((MessageId)VariantAdmin.PREFIX_UDQ, new Object[0]) + " - " + row[2].toString());
        });
        return nameMapping;
    }

    private Map<String, String> fetchRelatedUDTNames(List<String> udtNames) {
        if (udtNames.isEmpty()) {
            return Collections.emptyMap();
        }
        StringBuilder sql = new StringBuilder("select T.\"TableName\", T.\"Descr\", O.\"Name\" from OUTB T left join OUDO O on T.\"TableName\" = O.\"TableName\" where ");
        for (String name : udtNames) {
            sql.append("T.\"TableName\" = ? or ");
        }
        sql.delete(sql.length() - 4, sql.length() - 1);
        Object[] params = udtNames.stream().map(n -> n.substring(1)).collect(Collectors.toList()).toArray();
        List names = JpaUtil.load((EntityManager)this.em, Object[].class, (String)sql.toString(), (Object[])params);
        HashMap<String, String> nameMapping = new HashMap<String, String>();
        names.forEach(rowData -> {
            boolean isUdo = rowData[2] != null;
            String name = isUdo ? rowData[2].toString() : rowData[1].toString();
            String tableName = "@" + rowData[0].toString();
            nameMapping.put(tableName, (isUdo ? this.messageUtil.getMessage((MessageId)VariantAdmin.PREFIX_UDO, new Object[0]) : this.messageUtil.getMessage((MessageId)VariantAdmin.PREFIX_UDT, new Object[0])) + " - " + name);
        });
        return nameMapping;
    }

    private String toI18NName(Map<String, String> udtNameMapping, Map<String, String> udqNameMapping, String name) {
        if (name.startsWith(ANLY_OBJ_PREFFIX)) {
            String[] parts = name.split(SEPARATOR);
            return this.getAnalyticalObjectName(parts[1], parts[2]);
        }
        if (MetaTable.isUDT((String)name)) {
            return udtNameMapping.getOrDefault(name, name);
        }
        if (MetaTable.isUDQ((String)name)) {
            return udqNameMapping.getOrDefault(name, name);
        }
        return this.getSysBONameI18N(name);
    }

    private String getViewTypeI18N(String type, String viewId) {
        if (type == null) {
            return null;
        }
        try {
            if ("MDO10001".equals(viewId)) {
                return this.messageUtil.getMessage((MessageId)VariantAdmin.Type_Dashboard_G, new Object[0]);
            }
            return this.messageUtil.getMessage((MessageId)VariantAdmin.valueOf((String)("Type_" + type)), new Object[0]);
        }
        catch (IllegalArgumentException e) {
            logger.warn("there is no I18N for variant view type {}", (Object)type);
            return type;
        }
    }

    private String getSysBONameI18N(String objName) {
        if (objName == null) {
            return null;
        }
        String i18nName = objName;
        try {
            i18nName = this.messageUtil.getMessage((MessageId)BoViews.valueOf((String)objName), new Object[0]);
        }
        catch (IllegalArgumentException e) {
            logger.warn("there is no I18N for variant object {}", (Object)objName);
        }
        return this.messageUtil.getMessage((MessageId)VariantAdmin.PREFIX_SYS, new Object[0]) + " - " + i18nName;
    }

    private String getAnalyticalObjectName(String viewType, String viewId) {
        return String.format("%s - %s", this.messageUtil.getMessage((MessageId)VariantAdmin.PREFIX_Analytics, new Object[0]), this.analyticsAdapter.getViewObjectNameI18N(viewType, viewId));
    }
}

