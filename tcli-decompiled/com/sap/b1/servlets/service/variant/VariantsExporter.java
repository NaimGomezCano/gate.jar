/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infa.meta.dashboard.Dataset
 *  com.sap.b1.infa.meta.dashboard.Widget
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.protocol.response.ResponseMessageList
 *  com.sap.b1.servlets.service.variant.VariantsExporter
 *  com.sap.b1.servlets.service.variant.VariantsPkg
 *  com.sap.b1.servlets.service.variant.admin.Counter
 *  com.sap.b1.servlets.service.variant.exception.InvalidDataException
 *  com.sap.b1.servlets.service.variant.sys.SecurityOfficer
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 *  com.sap.b1.servlets.service.variant.sys.VariantValidator
 *  com.sap.b1.servlets.service.variant.sys.VariantsReader
 *  com.sap.b1.svcl.client.entityset.WebClientVariants
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.svcl.client.enums.BoYesNoEnum
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  com.sap.b1.tcli.service.ouqr.OQCNService
 *  com.sap.b1.tcli.service.ouqr.OUQRService
 *  com.sap.b1.tcli.service.owsv.OWSVService
 *  com.sap.b1.tcli.service.owsv.domain.OWSVViewType
 *  com.sap.b1.tcli.service.owsv.domain.OverviewCustomization
 *  com.sap.b1.ui.base.UiJacksonBuilder
 *  com.sap.b1.util.CompressUtil
 *  gen.bean.BmoOUQR
 *  gen.bean.BmoOWSV
 *  gen.str.VariantAdmin
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infa.meta.dashboard.Dataset;
import com.sap.b1.infa.meta.dashboard.Widget;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.protocol.response.ResponseMessageList;
import com.sap.b1.servlets.service.variant.VariantsPkg;
import com.sap.b1.servlets.service.variant.admin.Counter;
import com.sap.b1.servlets.service.variant.exception.InvalidDataException;
import com.sap.b1.servlets.service.variant.sys.SecurityOfficer;
import com.sap.b1.servlets.service.variant.sys.ValidateResult;
import com.sap.b1.servlets.service.variant.sys.VariantValidator;
import com.sap.b1.servlets.service.variant.sys.VariantsReader;
import com.sap.b1.svcl.client.entityset.WebClientVariants;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.svcl.client.enums.BoYesNoEnum;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import com.sap.b1.tcli.service.ouqr.OQCNService;
import com.sap.b1.tcli.service.ouqr.OUQRService;
import com.sap.b1.tcli.service.owsv.OWSVService;
import com.sap.b1.tcli.service.owsv.domain.OWSVViewType;
import com.sap.b1.tcli.service.owsv.domain.OverviewCustomization;
import com.sap.b1.ui.base.UiJacksonBuilder;
import com.sap.b1.util.CompressUtil;
import gen.bean.BmoOUQR;
import gen.bean.BmoOWSV;
import gen.str.VariantAdmin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VariantsExporter {
    private static Logger logger = LoggerFactory.getLogger(VariantsExporter.class);
    private static final String NEW_NAME_FLAG = " - New";
    @Autowired
    private DBTypeConfig dbTypeConfig;
    @Autowired
    private WebClientVariants svclVnts;
    @Autowired
    private VariantsReader variantsReader;
    @Autowired
    private SecurityOfficer securityOfficer;
    @Autowired(required=false)
    private List<VariantValidator> variantValidators;
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile = "prod";
    @Autowired
    private OUQRService ouqrService;
    @Autowired
    private OQCNService oqcnService;
    @Autowired
    private OWSVService linkService;
    @Autowired
    private MessageUtil messageUtil;
    private ObjectMapper mapper = UiJacksonBuilder.createObjectMapper();

    public byte[] exportAllNonSysVariantsAsBytes(Integer version, Integer userId, boolean exportLinks, boolean exportUDQs) throws Exception {
        List variants = this.variantsReader.loadExportedVariants(userId);
        return this.toSafePackageBytes(variants, version, userId, exportLinks, exportUDQs, null);
    }

    public byte[] exportSelectedVariantsAsBytes(String[] ids, Integer version, Integer userId, boolean exportLinks, boolean exportUDQs) throws Exception {
        ArrayList<String> userVariantsId = new ArrayList<String>();
        ArrayList<String> systemVariants = new ArrayList<String>();
        for (String id : ids) {
            if (!this.isValidChars(id)) {
                throw new Exception("Invalid variant id");
            }
            if (id.startsWith("SYS:")) {
                systemVariants.add(id.substring("SYS:".length()));
                continue;
            }
            userVariantsId.add(id);
        }
        List variants = Collections.emptyList();
        if (!userVariantsId.isEmpty()) {
            variants = this.variantsReader.loadExportedVariants(userId, userVariantsId.toArray(new String[0]));
        }
        return this.toSafePackageBytes(variants, version, userId, exportLinks, exportUDQs, systemVariants);
    }

    private byte[] toSafePackageBytes(List<WebClientVariant> variants, Integer version, Integer userId, boolean exportLinks, boolean exportUDQs, List<String> systemVariants) throws IOException {
        VariantsPkg variantsPkg = new VariantsPkg();
        variantsPkg.setVersion(version);
        variantsPkg.setDbType(this.dbTypeConfig.get());
        variantsPkg.setVariantList(variants);
        if (exportLinks) {
            this.appendLinks(variantsPkg, userId, systemVariants);
        }
        if (exportUDQs) {
            this.appendUDQs(variantsPkg);
        }
        byte[] bytes = this.mapper.writeValueAsBytes((Object)variantsPkg);
        byte[] content = "test".equals(this.activeProfile) ? bytes : CompressUtil.compress((byte[])bytes);
        return this.securityOfficer.safePackage(content);
    }

    private void appendUDQs(VariantsPkg variantsPkg) {
        Set udqs = this.fetchUDQsRelatedWithOverview(variantsPkg.getVariantList());
        udqs.addAll(variantsPkg.getVariantList().stream().filter(v -> MetaTable.isUDQ((String)v.ObjectName)).map(v -> v.ObjectName).collect(Collectors.toSet()));
        List dataList = udqs.stream().map(n -> {
            try {
                return this.ouqrService.findById(n);
            }
            catch (Exception e) {
                logger.warn("find UDQ by ID [" + n + "] error", (Throwable)e);
                return null;
            }
        }).filter(u -> u != null).collect(Collectors.toList());
        if (!dataList.isEmpty()) {
            variantsPkg.setUdqs(dataList);
            variantsPkg.setUdqCategoryNames(this.variantsReader.getNonSysUDQCategoryNames(dataList));
        }
    }

    private Set<String> fetchUDQsRelatedWithOverview(List<WebClientVariant> variantList) {
        HashSet<String> set = new HashSet<String>();
        variantList.stream().map(arg_0 -> this.findUDQsRelatedWithOverviewVariant(arg_0)).forEach(udqs -> set.addAll((Collection<String>)udqs));
        return set;
    }

    private Set<String> findUDQsRelatedWithOverviewVariant(WebClientVariant variant) {
        if (variant.OverviewCustomization == null || variant.OverviewCustomization.trim().isEmpty()) {
            return Collections.emptySet();
        }
        List ws = ((OverviewCustomization)new Gson().fromJson(variant.OverviewCustomization, OverviewCustomization.class)).getCustomWidgets();
        if (ws == null || ws.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<String> set = new HashSet<String>();
        for (Widget widget : ws) {
            String bo;
            if (widget.datasets == null || widget.datasets.isEmpty() || (bo = ((Dataset)widget.datasets.get((int)0)).bo) == null || !MetaTable.isUDQ((String)bo)) continue;
            set.add(bo);
        }
        return set;
    }

    private void appendLinks(VariantsPkg variantsPkg, Integer userId, List<String> systemVariants) {
        Object ids = variantsPkg.getVariantList().stream().map(v -> v.Guid).collect(Collectors.joining("','"));
        List links = this.variantsReader.loadRelatedLinks(userId, (String)(ids = "'" + (String)ids + "'"), systemVariants);
        if (links != null && !links.isEmpty()) {
            variantsPkg.setLinks(links.stream().filter(l -> !this.linkService.isSystemLink(l)).collect(Collectors.toList()));
        }
    }

    public ResponseMessageList importVariants(byte[] variantsBytes, String opts, Integer userId, Integer version) throws Exception {
        VariantsPkg pkg = this.buildVariantsPkgFromBytes(variantsBytes);
        this.doValidateVariants(pkg, version);
        return this.doImport(pkg, opts, userId);
    }

    private ResponseMessageList doImport(VariantsPkg pkg, String opts, Integer userId) throws Exception {
        Counter counter = new Counter();
        Map variantLinks = this.group(pkg.getLinks(), l -> l.getVariantId());
        Map<Object, Object> variantUDQs = Collections.emptyMap();
        Map categoryIdsInCurrentCompany = Collections.emptyMap();
        HashMap createdUDQs = new HashMap();
        if (this.notEmpty(pkg.getUdqs())) {
            variantUDQs = pkg.getUdqs().stream().collect(Collectors.toMap(u -> this.ouqrService.unifyKey(u.getIntrnalKey(), u.getQCategory()), u -> u));
            categoryIdsInCurrentCompany = this.getCategoryIdByName(pkg.getUdqCategoryNames());
        }
        try {
            if (pkg.getVariantList() != null) {
                for (WebClientVariant webClientVariant : pkg.getVariantList()) {
                    this.createObjects(webClientVariant, (List)variantLinks.remove(webClientVariant.Guid), variantUDQs, pkg.getUdqCategoryNames(), categoryIdsInCurrentCompany, opts, userId, createdUDQs, counter);
                }
            }
            if (!variantLinks.isEmpty()) {
                for (Map.Entry entry : variantLinks.entrySet()) {
                    for (BmoOWSV link : (List)entry.getValue()) {
                        if (this.linkService.isSystemLink(link)) continue;
                        this.createLink(link, opts, userId);
                        counter.addLink(link.Guid);
                    }
                }
            }
            return this.generateMessageList(counter);
        }
        catch (Exception e) {
            logger.error("import variants error", (Throwable)e);
            this.revert(counter);
            throw e;
        }
    }

    private Map<String, Integer> getCategoryIdByName(Map<String, String> udqCategoryNames) {
        String[] names = udqCategoryNames.values().toArray(new String[0]);
        return this.variantsReader.getUDQCategoryIds(names);
    }

    private void revert(Counter counter) {
        logger.warn("revert imported variants");
        this.revertLinks(counter.getLinks());
        this.revertVariants(counter.getVariants());
        this.revertUDQs(counter.getUdqs());
    }

    private void revertLinks(List<String> links) {
        for (String guid : links) {
            try {
                this.linkService.delete(guid);
            }
            catch (Exception e) {
                logger.warn("revert link [" + guid + "] error", (Throwable)e);
            }
        }
    }

    private void revertVariants(List<String> variants) {
        for (String guid : variants) {
            try {
                this.svclVnts.delete(guid);
            }
            catch (Exception e) {
                logger.warn("revert variant [" + guid + "] error", (Throwable)e);
            }
        }
    }

    private void revertUDQs(List<String> udqs) {
        for (String key : udqs) {
            try {
                this.ouqrService.delete(key);
            }
            catch (Exception e) {
                logger.warn("revert UQD [" + key + "] error", (Throwable)e);
            }
        }
    }

    private ResponseMessageList generateMessageList(Counter counter) {
        ResponseMessageList messageList = new ResponseMessageList(this.messageUtil.getMessage((MessageId)VariantAdmin.Title_Import_Success, new Object[0]));
        int c = counter.getVariants().size();
        if (c > 0) {
            messageList.info(this.messageUtil.getMessage((MessageId)VariantAdmin.Msg_Variants_Count, new Object[]{c}));
        }
        if ((c = counter.getLinks().size()) > 0) {
            messageList.info(this.messageUtil.getMessage((MessageId)VariantAdmin.Msg_Links_Count, new Object[]{c}));
        }
        if ((c = counter.getUdqs().size()) > 0) {
            messageList.info(this.messageUtil.getMessage((MessageId)VariantAdmin.Msg_UDQs_Count, new Object[]{c}));
        }
        return messageList;
    }

    private void createObjects(WebClientVariant v, List<BmoOWSV> relatedLinks, Map<String, BmoOUQR> variantUDQs, Map<String, String> udqCategoryNames, Map<String, Integer> categoryIdsInCurrentCompany, String opts, Integer userId, Map<String, String> createdUDQs, Counter counter) throws Exception {
        List relatedUDQs = this.findRelatedUDQs(variantUDQs, v);
        for (Object[] data : relatedUDQs) {
            String udqKey = (String)data[0];
            BmoOUQR relatedUDQ = (BmoOUQR)data[1];
            String newKey = createdUDQs.get(udqKey);
            if (newKey != null) continue;
            newKey = this.createUDQ(relatedUDQ, udqCategoryNames, categoryIdsInCurrentCompany, userId);
            logger.info("UDQ created: {} - {}", (Object)newKey, (Object)relatedUDQ.getQName());
            createdUDQs.put(udqKey, newKey);
            counter.addUDQ(newKey);
        }
        if (!relatedUDQs.isEmpty()) {
            this.updateNewUDQIntoVariant(v, createdUDQs);
        }
        this.createVariant(v, opts, userId);
        counter.addVariant(v.Guid);
        if (this.notEmpty(relatedLinks)) {
            for (BmoOWSV l : relatedLinks) {
                if (this.linkService.isSystemLink(l)) continue;
                this.updateNewUDQIntoLink(l, createdUDQs);
                l.setVariantId(v.Guid);
                this.createLink(l, opts, userId);
                counter.addLink(l.Guid);
            }
        }
    }

    private void updateNewUDQIntoVariant(WebClientVariant v, Map<String, String> createdUDQs) {
        if ("MDO10001".equals(v.ViewId)) {
            v.OverviewCustomization = this.updateCustomizeWidgets(v, createdUDQs);
        } else {
            if (v.OverviewCustomization != null && !v.OverviewCustomization.isEmpty()) {
                v.OverviewCustomization = this.updateCustomizeWidgets(v, createdUDQs);
            }
            v.ObjectName = createdUDQs.get(v.ObjectName);
        }
    }

    private String updateCustomizeWidgets(WebClientVariant v, Map<String, String> createdUDQs) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonObject jsonObject = (JsonObject)gson.fromJson(v.OverviewCustomization, JsonObject.class);
        JsonArray customWidgets = jsonObject.get("customWidgets").getAsJsonArray();
        for (JsonElement w : customWidgets) {
            JsonElement datasets = w.getAsJsonObject().get("datasets");
            if (datasets == null || !datasets.isJsonArray()) continue;
            for (JsonElement el : datasets.getAsJsonArray()) {
                String newUDQ;
                JsonObject dataset = el.getAsJsonObject();
                String bo = dataset.get("bo").getAsString();
                if (!MetaTable.isUDQ((String)bo) || (newUDQ = createdUDQs.get(bo)) == null) continue;
                dataset.addProperty("bo", newUDQ);
                dataset.addProperty("modelId", newUDQ);
                dataset.addProperty("sPath", dataset.get("sPath").getAsString().replace(bo, newUDQ));
                dataset.addProperty("sServiceUrl", dataset.get("sServiceUrl").getAsString().replace(bo, newUDQ));
            }
        }
        return gson.toJson((JsonElement)jsonObject);
    }

    private void updateNewUDQIntoLink(BmoOWSV l, Map<String, String> createdUDQs) {
        String newUDQ;
        String tgtObject = l.getTgtObject();
        if (MetaTable.isUDQ((String)tgtObject) && (newUDQ = createdUDQs.get(tgtObject)) != null) {
            l.setTgtObject(newUDQ);
        }
        String applyToDoc = l.getApplyToDoc().replace(" ", "");
        String newApplyToDoc = Arrays.stream(applyToDoc.split(";")).map(doc -> {
            String newUDQ = (String)createdUDQs.get(doc);
            return MetaTable.isUDQ((String)doc) && newUDQ != null ? newUDQ : doc;
        }).collect(Collectors.joining(";"));
        l.setApplyToDoc(newApplyToDoc);
    }

    private List<Object[]> findRelatedUDQs(Map<String, BmoOUQR> variantUDQs, WebClientVariant v) {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        if ("MDO10001".equals(v.ViewId)) {
            Set names = this.findUDQsRelatedWithOverviewVariant(v);
            for (String name : names) {
                BmoOUQR udqMeta = variantUDQs.get(name);
                if (udqMeta == null) continue;
                list.add(new Object[]{name, udqMeta});
            }
        } else if (MetaTable.isUDQ((String)v.ObjectName)) {
            list.add(new Object[]{v.ObjectName, variantUDQs.get(v.ObjectName)});
        }
        return list;
    }

    private String createUDQ(BmoOUQR relatedUDQ, Map<String, String> udqCategoryNames, Map<String, Integer> categoryIdsInCurrentCompany, Integer userId) throws Exception {
        String cateName = udqCategoryNames.get(relatedUDQ.getQCategory().toString());
        Integer newCategoryId = relatedUDQ.getQCategory();
        if (newCategoryId > 0) {
            newCategoryId = categoryIdsInCurrentCompany.get(cateName);
        }
        if (newCategoryId == null) {
            newCategoryId = this.createUDQCategory(userId, cateName);
            categoryIdsInCurrentCompany.put(cateName, newCategoryId);
        }
        relatedUDQ.setQCategory(newCategoryId);
        while (this.needRename(relatedUDQ)) {
            relatedUDQ.setQName(relatedUDQ.getQName() + NEW_NAME_FLAG);
        }
        return this.ouqrService.create(relatedUDQ);
    }

    private Integer createUDQCategory(Integer userId, String cateName) throws Exception {
        return this.oqcnService.create(cateName, "W", userId);
    }

    private void createLink(BmoOWSV l, String opts, Integer userId) throws Exception {
        l.setGuid(null);
        if (l.getViewType() == null) {
            l.setViewType(OWSVViewType.DETAILEDVIEWHEADER.getValue());
        }
        if ("public".equalsIgnoreCase(opts)) {
            l.setIsPublic(BoYesNoEnum.tYES.getValue());
        } else if ("private".equalsIgnoreCase(opts)) {
            l.setIsPublic(BoYesNoEnum.tNO.getValue());
        }
        l.setOwnerId(userId);
        while (this.linkService.isViewNameDuplicated(l)) {
            l.setName(l.getName() + NEW_NAME_FLAG);
        }
        this.linkService.create(l);
        logger.info("link created: {}", (Object)l.Guid);
    }

    private void createVariant(WebClientVariant v, String opts, Integer userId) {
        v.Guid = UUID.randomUUID().toString();
        v.IsSystem = BoYesNoEnum.tNO;
        if ("public".equalsIgnoreCase(opts)) {
            v.IsPublic = BoYesNoEnum.tYES;
        } else if ("private".equalsIgnoreCase(opts)) {
            v.IsPublic = BoYesNoEnum.tNO;
        }
        v.UserId = userId;
        while (this.needRename(v)) {
            v.Name = v.Name + NEW_NAME_FLAG;
        }
        this.svclVnts.create(v);
        logger.info("variant created: {}", (Object)v.Guid);
    }

    private boolean notEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private <T> Map<String, List<T>> group(List<T> dataList, Function<T, String> classier) {
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyMap();
        }
        return dataList.stream().collect(Collectors.groupingBy(classier));
    }

    private boolean needRename(WebClientVariant v) {
        return this.variantsReader.nameExists(v.Name, v.ObjectName, v.ViewType, v.ViewId);
    }

    private boolean needRename(BmoOUQR relatedUDQ) {
        return this.variantsReader.udqNameExists(relatedUDQ.getQCategory(), relatedUDQ.getQName());
    }

    private boolean needRename(BmoOWSV link) {
        return this.variantsReader.linkNameExists(link);
    }

    private void doValidateVariants(VariantsPkg pkg, Integer version) {
        if (pkg.getVersion().compareTo(version) > 0) {
            throw new InvalidDataException(this.messageUtil.getMessage((MessageId)VariantAdmin.WRONG_VERSION_PKG, new Object[0]));
        }
        if (this.variantValidators != null && this.variantValidators.size() > 0) {
            for (VariantValidator validator : this.variantValidators) {
                ValidateResult result = validator.validate(pkg);
                if (result.isValid()) continue;
                logger.error("false from validator: {}", (Object)validator.getClass().getName());
                throw new InvalidDataException(result.getErrMsg());
            }
        }
    }

    public VariantsPkg buildVariantsPkgFromBytes(byte[] bytes) throws InvalidDataException {
        try {
            byte[] content = this.securityOfficer.safeUnpackage(bytes);
            if (!"test".equals(this.activeProfile)) {
                content = CompressUtil.decompress((byte[])content);
            }
            return (VariantsPkg)this.mapper.readerFor(VariantsPkg.class).readValue(content);
        }
        catch (InvalidDataException e) {
            throw e;
        }
        catch (IOException e) {
            throw new InvalidDataException(this.messageUtil.getMessage((MessageId)VariantAdmin.INVALID_DATA_PKG, new Object[0]), (Throwable)e);
        }
    }

    private boolean isValidChars(String str) {
        return str.matches("^[a-zA-Z0-9-:]*$");
    }
}

