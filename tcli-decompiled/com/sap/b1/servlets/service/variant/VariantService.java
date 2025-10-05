/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.bo.base.JpaUtil
 *  com.sap.b1.controller.base.NavigationController
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infa.meta.dashboard.Dataset
 *  com.sap.b1.infa.meta.dashboard.Widget
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.permission.PermissionManager
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.protocol.response.CategoryResponse
 *  com.sap.b1.protocol.response.ResponseMessageList
 *  com.sap.b1.protocol.response.bo.BusinessException
 *  com.sap.b1.servlets.service.anly.AnalyticsViewAdapter
 *  com.sap.b1.servlets.service.variant.FieldChange
 *  com.sap.b1.servlets.service.variant.OperationInfo
 *  com.sap.b1.servlets.service.variant.VariantBatchUpdatePayload
 *  com.sap.b1.servlets.service.variant.VariantCategory
 *  com.sap.b1.servlets.service.variant.VariantCreationPayload
 *  com.sap.b1.servlets.service.variant.VariantGroup
 *  com.sap.b1.servlets.service.variant.VariantList
 *  com.sap.b1.servlets.service.variant.VariantService
 *  com.sap.b1.servlets.service.variant.VariantWithI18N
 *  com.sap.b1.servlets.service.variant.VariantsExporter
 *  com.sap.b1.servlets.service.variant.exception.ExceptionUtils
 *  com.sap.b1.servlets.service.variant.exception.InvalidDataException
 *  com.sap.b1.servlets.service.variant.sys.VariantsReader
 *  com.sap.b1.spring.RequestMappingTx
 *  com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartSize
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn
 *  com.sap.b1.svcl.client.entityset.WebClientVariantGroups
 *  com.sap.b1.svcl.client.entityset.WebClientVariants
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.svcl.client.entitytype.WebClientVariantGroup
 *  com.sap.b1.svcl.client.enums.BoYesNoEnum
 *  com.sap.b1.svcl.client.utils.ODataList
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  com.sap.b1.tcli.resources.ResourceBundleMessageUtil
 *  com.sap.b1.tcli.service.owsv.domain.OverviewCustomization
 *  com.sap.b1.tcli.udq.UDQLoader
 *  gen.bean.BmoOUQR
 *  gen.bean.BmoOWVG
 *  gen.bean.BmoOWVT
 *  gen.dao.DaoOUQR
 *  gen.dao.DaoOWVG
 *  gen.dao.DaoOWVT
 *  gen.str.ODOCmsgs
 *  gen.str.OUQRmsgs
 *  gen.str.VariantAdmin
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  jakarta.servlet.ServletOutputStream
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.reflect.FieldUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.MessageSource
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.app.base.AppEnv;
import com.sap.b1.bo.base.JpaUtil;
import com.sap.b1.controller.base.NavigationController;
import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infa.meta.dashboard.Dataset;
import com.sap.b1.infa.meta.dashboard.Widget;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.infra.permission.PermissionManager;
import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.protocol.response.CategoryResponse;
import com.sap.b1.protocol.response.ResponseMessageList;
import com.sap.b1.protocol.response.bo.BusinessException;
import com.sap.b1.servlets.service.anly.AnalyticsViewAdapter;
import com.sap.b1.servlets.service.variant.FieldChange;
import com.sap.b1.servlets.service.variant.OperationInfo;
import com.sap.b1.servlets.service.variant.VariantBatchUpdatePayload;
import com.sap.b1.servlets.service.variant.VariantCategory;
import com.sap.b1.servlets.service.variant.VariantCreationPayload;
import com.sap.b1.servlets.service.variant.VariantGroup;
import com.sap.b1.servlets.service.variant.VariantList;
import com.sap.b1.servlets.service.variant.VariantWithI18N;
import com.sap.b1.servlets.service.variant.VariantsExporter;
import com.sap.b1.servlets.service.variant.exception.ExceptionUtils;
import com.sap.b1.servlets.service.variant.exception.InvalidDataException;
import com.sap.b1.servlets.service.variant.sys.VariantsReader;
import com.sap.b1.spring.RequestMappingTx;
import com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartSize;
import com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn;
import com.sap.b1.svcl.client.entityset.WebClientVariantGroups;
import com.sap.b1.svcl.client.entityset.WebClientVariants;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.svcl.client.entitytype.WebClientVariantGroup;
import com.sap.b1.svcl.client.enums.BoYesNoEnum;
import com.sap.b1.svcl.client.utils.ODataList;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import com.sap.b1.tcli.resources.ResourceBundleMessageUtil;
import com.sap.b1.tcli.service.owsv.domain.OverviewCustomization;
import com.sap.b1.tcli.udq.UDQLoader;
import gen.bean.BmoOUQR;
import gen.bean.BmoOWVG;
import gen.bean.BmoOWVT;
import gen.dao.DaoOUQR;
import gen.dao.DaoOWVG;
import gen.dao.DaoOWVT;
import gen.str.ODOCmsgs;
import gen.str.OUQRmsgs;
import gen.str.VariantAdmin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VariantService {
    private static final Logger logger = LoggerFactory.getLogger(VariantService.class);
    @Autowired
    private AppEnv env;
    @Autowired
    private DaoOWVT daoOWVT;
    @Autowired
    private DaoOWVG daoOWVG;
    @PersistenceContext
    protected EntityManager em;
    @Autowired
    private WebClientVariants svclVnts;
    @Autowired
    private WebClientVariantGroups svclVntGroups;
    @Autowired
    ResourceBundleMessageUtil resourceBundle;
    @Autowired
    protected NavigationController navigationController;
    @Autowired
    private VariantsReader variantsReader;
    @Autowired
    private VariantsExporter exporter;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private DBTypeConfig dbTypeConfig;
    @Autowired
    AnalyticsViewAdapter analyticsViewAdapter;
    @Autowired
    private UDQLoader loader;
    @Autowired
    private DaoOUQR daoOUQR;
    @Autowired
    protected PermissionManager permissionManager;
    private static final String OP_DEL = "D";
    private static final String OP_UPD = "U";
    private static final String OP_SDF = "S";
    private static final Integer DEFAULT_ORDER = 10000;
    private static final String DEFAULT_VIEW_ID = "-1";
    private static final String U_NAME = "UserName";
    private static final int BATCH_SIZE = 20;
    private static final String HANA_SPECIFIC_VARIANT_GUID = "fa9acfea-203c-482b-ba34-37d5db965aad";
    private static final String MSSQL_SPECIFIC_VARIANT_GUID = "6344773d-d953-4058-9986-7cf10fdebe2f";
    private static final String ODPS_BOE_VARIANT_GUID = "5750986e-9fda-43cf-8761-c039c11d4a9d";

    @RequestMappingTx(path={"/variant/batchUpdate.svc"})
    @ResponseBody
    public List<String> batchUpdate(@RequestBody VariantBatchUpdatePayload body) throws Exception {
        ArrayList<String> guids = new ArrayList<String>();
        for (OperationInfo op : body.operationList) {
            if (op.operation.equals(OP_SDF)) {
                if (body.viewId == null || body.viewId.isEmpty()) {
                    body.viewId = DEFAULT_VIEW_ID;
                }
                this.setDefault((VariantGroup)body, op.guid);
                continue;
            }
            WebClientVariant oVnt = this.svclVnts.get(op.guid);
            if (oVnt == null || oVnt.IsSystem.equals((Object)BoYesNoEnum.tYES)) continue;
            if (op.operation.equals(OP_DEL)) {
                this.svclVnts.delete(op.guid);
            } else if (op.operation.equals(OP_UPD)) {
                for (FieldChange c : op.changeList) {
                    String name = StringUtils.capitalize((String)c.colName);
                    Field field = FieldUtils.getField(oVnt.getClass(), (String)name, (boolean)true);
                    field.set(oVnt, c.value);
                }
                this.setGuidForChildren(oVnt);
                this.svclVnts.update(op.guid, oVnt);
            }
            guids.add(op.guid);
        }
        return guids;
    }

    @RequestMapping(path={"/variant/export.svc"}, method={RequestMethod.GET})
    public void exportVariants(@RequestParam(value="exportlinks", required=false) String exportLinks, @RequestParam(value="exportudqs", required=false) String exportUDQs, HttpServletResponse response) throws Exception {
        this.checkPermission();
        byte[] content = this.exporter.exportAllNonSysVariantsAsBytes(this.env.getCINF().getVersion(), this.env.getUserId(), this.toBoolean(exportLinks), this.toBoolean(exportUDQs));
        this.responseVariantsContent(content, response);
    }

    private boolean toBoolean(String exportLinks) {
        if (exportLinks == null) {
            return false;
        }
        return exportLinks.trim().equalsIgnoreCase("true");
    }

    @RequestMapping(path={"/variant/export.svc"}, method={RequestMethod.POST})
    public void exportSelectedVariants(@RequestParam(value="ids") String selectedIds, @RequestParam(value="exportlinks", required=false) String exportLinks, @RequestParam(value="exportudqs", required=false) String exportUDQs, HttpServletResponse response) throws Exception {
        this.checkPermission();
        byte[] content = this.exporter.exportSelectedVariantsAsBytes(selectedIds.split(";"), this.env.getCINF().getVersion(), this.env.getUserId(), this.toBoolean(exportLinks), this.toBoolean(exportUDQs));
        this.responseVariantsContent(content, response);
    }

    private void responseVariantsContent(byte[] content, HttpServletResponse response) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "variants_" + df.format(new Date()) + ".vx";
        response.setStatus(200);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        ServletOutputStream os = response.getOutputStream();
        os.write(content);
        os.flush();
    }

    @RequestMapping(path={"/variant/import.svc"})
    @ResponseBody
    public ResponseMessageList importVariants(@RequestParam(value="variantFile") MultipartFile file, @RequestParam(value="opts", required=false) String opts, HttpServletResponse response) throws Exception {
        this.checkPermission();
        try {
            byte[] bytes = file.getBytes();
            return this.exporter.importVariants(bytes, opts, this.env.getUserId(), this.env.getCINF().getVersion());
        }
        catch (InvalidDataException e) {
            logger.error("import variant error", (Throwable)e);
            String errMsg = e.getMessage();
            errMsg = errMsg == null ? this.messageUtil.getMessage((MessageId)VariantAdmin.IMPORT_FAILED, new Object[0]) : errMsg;
            response.setStatus(400);
            ResponseMessageList messageList = new ResponseMessageList(this.messageUtil.getMessage((MessageId)VariantAdmin.Title_Import_Error, new Object[0]));
            messageList.error(errMsg);
            return messageList;
        }
        catch (FileNotFoundException | NoSuchFileException e) {
            logger.error("import variant error", (Throwable)e);
            response.setStatus(500);
            ResponseMessageList messageList = new ResponseMessageList(this.messageUtil.getMessage((MessageId)VariantAdmin.Title_Import_Error, new Object[0]));
            String errMsg = this.messageUtil.getMessage((MessageId)VariantAdmin.IMPORT_FAILED_SYS, new Object[0]);
            messageList.error(errMsg);
            return messageList;
        }
        catch (Exception e) {
            logger.error("import variant error", (Throwable)e);
            response.setStatus(500);
            ResponseMessageList messageList = new ResponseMessageList(this.messageUtil.getMessage((MessageId)VariantAdmin.Title_Import_Error, new Object[0]));
            String errMsg = e.getMessage();
            if (errMsg != null) {
                messageList.error(errMsg);
            }
            messageList.error(ExceptionUtils.digServiceLayerErrorMessage((Throwable)e, (String)this.messageUtil.getMessage((MessageId)VariantAdmin.IMPORT_FAILED_SYS, new Object[0])));
            return messageList;
        }
    }

    @RequestMapping(path={"/variant/search.svc"})
    @ResponseBody
    public List<CategoryResponse<VariantCategory, VariantWithI18N>> search(@RequestParam(value="text", required=false) String key, @RequestParam(value="sys", required=false) String showSys) throws Exception {
        return this.variantsReader.search(this.env.getUserId(), key, "true".equalsIgnoreCase(showSys));
    }

    @RequestMapping(path={"/variant/list.svc"})
    @ResponseBody
    public VariantList list(@RequestParam(value="userId", required=false) Integer userId, @RequestParam(value="objName") String objName, @RequestParam(value="viewType") String viewType, @RequestParam(value="viewId") String viewId) throws Exception {
        if (userId == null) {
            userId = this.env.getUserId();
        }
        this.env.getCINF();
        if (viewId == null || viewId.isEmpty()) {
            viewId = DEFAULT_VIEW_ID;
        }
        if ("List".equals(viewType)) {
            this.permissionManager.validPermissionOnObject(objName, PermissionTypeEnum.PRM_READ_ONLY);
        }
        VariantList variants = this.loadVariantsFromDB(userId, objName, viewType, viewId);
        this.translate(variants, objName);
        this.handleAnalyticsVariants(variants, objName);
        this.handleVariantsForLawset(variants, objName);
        return variants;
    }

    @RequestMapping(path={"/variant/getUdqWithoutView.svc"})
    @ResponseBody
    public ResponseMessageList getUdqWithoutView() throws Exception {
        return this.variantsReader.getUdqWithoutView(this.env.getUserId());
    }

    private void handleVariantsForLawset(VariantList variants, String objName) throws Exception {
        List<String> countries;
        if (!"ANLY".equals(objName) && "ODPS".equals(objName) && !(countries = Arrays.asList("FR", "BE", "ES", "BR", "IT", "PT", "CL", "TR")).contains(this.env.getCINF().getLawsSet())) {
            variants.list.removeIf(v -> this.isODPSSpecificVariant(v.Guid));
        }
    }

    private void handleAnalyticsVariants(VariantList variants, String objName) throws Exception {
        if (!"ANLY".equals(objName)) {
            return;
        }
        if (this.dbTypeConfig.get() == DBType.HANA) {
            if (this.analyticsViewAdapter.isB1ahEnabled()) {
                variants.list.removeIf(v -> this.isMssqlSpecificVariant(v.Guid));
            } else {
                variants.list.removeIf(v -> this.isHanaSpecificVariant(v.Guid));
                variants.list.removeIf(v -> this.isMssqlSpecificVariant(v.Guid));
            }
        } else if (this.dbTypeConfig.get() == DBType.MSSQL) {
            variants.list.removeIf(v -> this.isHanaSpecificVariant(v.Guid));
        }
    }

    private boolean isHanaSpecificVariant(String guid) {
        return HANA_SPECIFIC_VARIANT_GUID.equals(guid);
    }

    private boolean isMssqlSpecificVariant(String guid) {
        return MSSQL_SPECIFIC_VARIANT_GUID.equals(guid);
    }

    private boolean isODPSSpecificVariant(String guid) {
        return ODPS_BOE_VARIANT_GUID.equals(guid);
    }

    private VariantList loadVariantsFromDB(Integer userId, String objName, String viewType, String viewId) throws Exception {
        VariantList variants = this.variantsReader.loadVariantsList(userId, objName, viewType, viewId);
        this.fillUserName(variants);
        return variants;
    }

    @Deprecated
    private VariantList loadVariantsFromSL(Integer userId, String objName, String viewType, String viewId) {
        ArrayList allSet = new ArrayList();
        List publicVariants = this.daoOWVT.getPublicVariants(objName, viewType, viewId);
        List privateVariants = this.daoOWVT.getPrivateVarints(userId, objName, viewType, viewId);
        allSet.addAll(publicVariants);
        allSet.addAll(privateVariants);
        VariantList variants = null;
        variants = allSet.size() > 0 ? this.fetchVariantsFromSvcl(userId, objName, viewType, viewId, allSet) : new VariantList();
        return variants;
    }

    private void translate(VariantList variants, String objName) {
        String url = String.format("variants/%s.variants", objName);
        MessageSource ms = this.resourceBundle.getMessageSource(url);
        Locale locale = LocaleContextHolder.getLocale();
        variants.list.forEach(v -> {
            v.Name = ms.getMessage(v.Guid, null, v.Name, locale);
        });
    }

    private boolean hasSystemVariant(VariantList variants) {
        return variants.list != null && variants.list.stream().anyMatch(v -> v.IsSystem == BoYesNoEnum.tYES);
    }

    private VariantList fetchVariantsFromSvcl(Integer userId, String objName, String viewType, String viewId, List<BmoOWVT> allSet) {
        VariantList ls = new VariantList();
        StringBuilder userIds = new StringBuilder();
        int idxInBatch = 0;
        StringBuilder filter = new StringBuilder();
        for (BmoOWVT bmoOWVT : allSet) {
            String guid = bmoOWVT.getGuid();
            if (idxInBatch == 20) {
                this.fetchByFilter(ls, filter);
                idxInBatch = 0;
                filter.setLength(0);
            }
            filter.append("Guid eq %27").append(guid).append("%27 or ");
            ++idxInBatch;
            if (bmoOWVT.getUserId() == -1) continue;
            userIds.append(bmoOWVT.getUserId()).append(",");
        }
        this.fetchByFilter(ls, filter);
        HashMap<Integer, String> userNameMap = new HashMap<Integer, String>();
        userNameMap.put(-1, "SAP");
        if (userIds.length() > 0) {
            userIds.deleteCharAt(userIds.length() - 1);
            this.getUserNames(userIds, userNameMap);
        }
        for (WebClientVariant o : ls.list) {
            o.setUserField(U_NAME, userNameMap.get(o.UserId));
        }
        List list = this.daoOWVG.getVariantGroup(userId, objName, viewType, viewId);
        if (list != null && !list.isEmpty()) {
            ls.defaultVariant = ((BmoOWVG)list.get(0)).getDftVrnt();
        }
        return ls;
    }

    private void fillUserName(VariantList ls) {
        StringBuilder userIds = new StringBuilder();
        for (WebClientVariant o : ls.list) {
            if (o.UserId == -1) continue;
            userIds.append(o.UserId).append(",");
        }
        HashMap<Integer, String> userNameMap = new HashMap<Integer, String>();
        userNameMap.put(-1, "SAP");
        if (userIds.length() > 0) {
            userIds.deleteCharAt(userIds.length() - 1);
            this.getUserNames(userIds, userNameMap);
        }
        for (WebClientVariant o : ls.list) {
            o.setUserField(U_NAME, userNameMap.get(o.UserId));
        }
    }

    private void fetchByFilter(VariantList ls, StringBuilder filter) {
        int len = filter.length();
        if (len == 0) {
            return;
        }
        filter.delete(len - 4, len);
        ODataList variants = this.svclVnts.find(filter.toString());
        if (variants != null && variants.getValue() != null) {
            ls.list.addAll(variants.getValue());
        }
    }

    private Map<Integer, String> getUserNames(StringBuilder userIds, Map<Integer, String> userNameMap) {
        String sql = String.format("select USERID, USER_CODE, U_NAME from OUSR where USERID in (%s)", userIds.toString());
        List resultList = JpaUtil.load((EntityManager)this.em, (String)sql);
        if (resultList != null && resultList.size() > 0) {
            for (Object[] u : resultList) {
                userNameMap.put((Integer)u[0], u[2] == null ? u[1].toString() : u[2].toString());
            }
        }
        return userNameMap;
    }

    @RequestMapping(path={"/variant/create.svc"}, method={RequestMethod.POST})
    @ResponseBody
    public WebClientVariant create(@RequestBody VariantCreationPayload payload) throws Exception {
        WebClientVariant o = payload.obj;
        this.checkUDQForAnlyVariant(o);
        o.Guid = UUID.randomUUID().toString();
        o.IsSystem = BoYesNoEnum.tNO;
        o.Order = DEFAULT_ORDER;
        if (o.ViewId == null || o.ViewId.isEmpty()) {
            o.ViewId = DEFAULT_VIEW_ID;
        }
        this.setGuidForChildren(o);
        WebClientVariant ret = this.svclVnts.create(o);
        if (payload.isDefault != null && payload.isDefault.equals((Object)BoYesNoEnum.tYES)) {
            VariantGroup gp = new VariantGroup();
            gp.userId = this.env.getUserId();
            gp.objName = o.ObjectName;
            gp.viewType = o.ViewType;
            gp.viewId = o.ViewId;
            this.setDefault(gp, o.Guid);
        }
        return ret;
    }

    private void setGuidForChildren(WebClientVariant o) {
        if (o.getWebClientVariantSelectedColumnCollection() != null) {
            for (WebClientVariantSelectedColumn ln : o.getWebClientVariantSelectedColumnCollection()) {
                ln.Guid = UUID.randomUUID().toString();
            }
        }
        if (o.getWebClientVariantSortByCollection() != null) {
            for (WebClientVariantSelectedColumn ln : o.getWebClientVariantSortByCollection()) {
                ln.Guid = UUID.randomUUID().toString();
            }
        }
        if (o.getWebClientVariantGroupByCollection() != null) {
            for (WebClientVariantSelectedColumn ln : o.getWebClientVariantGroupByCollection()) {
                ln.Guid = UUID.randomUUID().toString();
            }
        }
        if (o.getWebClientVariantEmbeddedChartCollection() != null) {
            for (WebClientVariantSelectedColumn ln : o.getWebClientVariantEmbeddedChartCollection()) {
                ln.Guid = UUID.randomUUID().toString();
                if (ln.WebClientVariantEmbeddedChartSizeCollection != null) {
                    for (WebClientVariantEmbeddedChartSize lnn : ln.WebClientVariantEmbeddedChartSizeCollection) {
                        lnn.Guid = UUID.randomUUID().toString();
                    }
                }
                if (ln.WebClientVariantEmbeddedChartValue1Collection != null) {
                    for (WebClientVariantEmbeddedChartSize lnn : ln.WebClientVariantEmbeddedChartValue1Collection) {
                        lnn.Guid = UUID.randomUUID().toString();
                    }
                }
                if (ln.WebClientVariantEmbeddedChartValue2Collection == null) continue;
                for (WebClientVariantEmbeddedChartSize lnn : ln.WebClientVariantEmbeddedChartValue2Collection) {
                    lnn.Guid = UUID.randomUUID().toString();
                }
            }
        }
        if (o.getWebClientVariantMChartCollection() != null) {
            for (WebClientVariantSelectedColumn ln : o.getWebClientVariantMChartCollection()) {
                ln.Guid = UUID.randomUUID().toString();
                if (ln.WebClientVariantMChartSizeCollection != null) {
                    for (WebClientVariantEmbeddedChartSize lnn : ln.WebClientVariantMChartSizeCollection) {
                        lnn.Guid = UUID.randomUUID().toString();
                    }
                }
                if (ln.WebClientVariantMChartValue1Collection != null) {
                    for (WebClientVariantEmbeddedChartSize lnn : ln.WebClientVariantMChartValue1Collection) {
                        lnn.Guid = UUID.randomUUID().toString();
                    }
                }
                if (ln.WebClientVariantMChartValue2Collection == null) continue;
                for (WebClientVariantEmbeddedChartSize lnn : ln.WebClientVariantMChartValue2Collection) {
                    lnn.Guid = UUID.randomUUID().toString();
                }
            }
        }
    }

    @RequestMappingTx(path={"/variant/update.svc"})
    @ResponseBody
    public WebClientVariant update(@RequestBody WebClientVariant o) throws Exception {
        this.checkUDQForAnlyVariant(o);
        List dtos = this.daoOWVT.getVariant(o.Guid);
        if (dtos == null || dtos.isEmpty()) {
            return null;
        }
        if (((BmoOWVT)dtos.get(0)).getIsSys().equals("Y")) {
            return null;
        }
        o.IsSystem = BoYesNoEnum.tNO;
        this.setGuidForChildren(o);
        this.svclVnts.update(o.Guid, o);
        return this.svclVnts.get(o.Guid);
    }

    private void setDefault(VariantGroup gp, String guid) throws Exception {
        Integer userId = this.env.getUserId();
        List vtGrps = this.daoOWVG.getVariantGroup(userId, gp.objName, gp.viewType, gp.viewId);
        if (vtGrps == null || vtGrps.isEmpty()) {
            WebClientVariantGroup vntGrp = new WebClientVariantGroup();
            vntGrp.UserId = userId;
            vntGrp.ObjectName = gp.objName;
            vntGrp.ViewType = gp.viewType;
            vntGrp.ViewId = gp.viewId;
            vntGrp.DefaultVariant = guid;
            vntGrp.Guid = UUID.randomUUID().toString();
            this.svclVntGroups.create(vntGrp);
        } else {
            String id = ((BmoOWVG)vtGrps.get(0)).getGuid();
            WebClientVariantGroup vntGrp = this.svclVntGroups.get(id);
            vntGrp.UserId = userId;
            vntGrp.DefaultVariant = guid;
            this.svclVntGroups.update(id, vntGrp);
        }
    }

    private void checkUDQForAnlyVariant(WebClientVariant webClientVariant) throws Exception {
        if ("ANLY".equals(webClientVariant.ObjectName) && StringUtils.isNotBlank((CharSequence)webClientVariant.OverviewCustomization)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OverviewCustomization ovCus = (OverviewCustomization)mapper.readValue(webClientVariant.OverviewCustomization, OverviewCustomization.class);
            HashSet<String> udqModelIds = new HashSet<String>();
            List widgets = ovCus.getCustomWidgets();
            for (Widget widget : widgets) {
                if (widget.datasets == null) continue;
                ArrayList list = widget.datasets;
                for (Dataset dataset : list) {
                    String modelId = dataset.modelId;
                    if (!MetaTable.isUDQ((String)modelId)) continue;
                    udqModelIds.add(modelId);
                }
            }
            if (udqModelIds.size() > 0) {
                HashSet<String> udqModelNames = new HashSet<String>();
                for (String modelId : udqModelIds) {
                    BmoOUQR drOUQR;
                    List rt = this.daoOUQR.findById(this.loader.getId(modelId), this.loader.getCategory(modelId));
                    if (rt.size() <= 0 || !"N".equals((drOUQR = (BmoOUQR)rt.get(0)).getAnaActive())) continue;
                    udqModelNames.add(drOUQR.getQName());
                }
                if (udqModelNames.size() > 0) {
                    throw new BusinessException(this.messageUtil.getMessage((MessageId)(udqModelNames.size() == 1 ? OUQRmsgs.singleDataSourceInactive : OUQRmsgs.multipleDataSourceInactive), new Object[]{String.join((CharSequence)", ", udqModelNames)}));
                }
            }
        }
    }

    private void checkPermission() throws Exception {
        this.permissionManager.validPermissionOnId(2380, PermissionTypeEnum.PRM_FULL, this.messageUtil.getMessage((MessageId)ODOCmsgs.noPermission, new Object[]{this.messageUtil.getMessage((MessageId)VariantAdmin.EXPORT_IMPORT_VIEW, new Object[0])}));
    }
}

