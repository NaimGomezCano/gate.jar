/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.bo.TableNameEnum
 *  com.sap.b1.bo.base.BoMappingHelper
 *  com.sap.b1.bo.base.JpaUtil
 *  com.sap.b1.infra.meta.table.Table
 *  com.sap.b1.protocol.response.bo.BusinessException
 *  com.sap.b1.servlets.service.formsetting.FormSetting
 *  com.sap.b1.servlets.service.formsetting.FormSettingSearchItem
 *  com.sap.b1.servlets.service.formsetting.FormSettingService
 *  com.sap.b1.spring.RequestMappingTx
 *  com.sap.b1.svcl.client.complextype.WebClientFormSettingItem
 *  com.sap.b1.svcl.client.entityset.WebClientFormSettings
 *  com.sap.b1.svcl.client.entitytype.WebClientFormSetting
 *  com.sap.b1.util.StringUtil
 *  gen.bean.BmoOWFST
 *  gen.bean.BmoWFST1
 *  gen.dao.DaoODRF
 *  gen.dao.DaoOUDO
 *  gen.dao.DaoOWFST
 *  gen.dao.DaoWFST1
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.formsetting;

import com.sap.b1.app.base.AppEnv;
import com.sap.b1.bo.TableNameEnum;
import com.sap.b1.bo.base.BoMappingHelper;
import com.sap.b1.bo.base.JpaUtil;
import com.sap.b1.infra.meta.table.Table;
import com.sap.b1.protocol.response.bo.BusinessException;
import com.sap.b1.servlets.service.formsetting.FormSetting;
import com.sap.b1.servlets.service.formsetting.FormSettingSearchItem;
import com.sap.b1.spring.RequestMappingTx;
import com.sap.b1.svcl.client.complextype.WebClientFormSettingItem;
import com.sap.b1.svcl.client.entityset.WebClientFormSettings;
import com.sap.b1.svcl.client.entitytype.WebClientFormSetting;
import com.sap.b1.util.StringUtil;
import gen.bean.BmoOWFST;
import gen.bean.BmoWFST1;
import gen.dao.DaoODRF;
import gen.dao.DaoOUDO;
import gen.dao.DaoOWFST;
import gen.dao.DaoWFST1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormSettingService {
    @Autowired
    private AppEnv env;
    @Autowired
    private WebClientFormSettings feignClient;
    @Autowired
    private DaoOWFST daoOWFST;
    @Autowired
    private DaoWFST1 daoWFST1;
    @Autowired
    private DaoODRF daoODRF;
    @Autowired
    private DaoOUDO daoOUDO;
    @PersistenceContext
    EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(FormSettingService.class);

    @PostMapping(path={"/formsetting/getFormSettings.svc"})
    @ResponseBody
    public Map<String, List<WebClientFormSetting>> getFormSettings(@RequestBody List<FormSetting> formSettingInfo) throws Exception {
        HashMap<String, List<WebClientFormSetting>> resultMap = new HashMap<String, List<WebClientFormSetting>>();
        for (FormSetting item : formSettingInfo) {
            if (StringUtil.isEmpty((String)item.formId)) continue;
            resultMap.put(item.formId, new ArrayList());
        }
        List dbFormSettings = this.querySettingsFromDB(formSettingInfo);
        for (BmoOWFST setting : dbFormSettings) {
            WebClientFormSetting oneSetting = new WebClientFormSetting();
            oneSetting.Guid = setting.getGuid();
            oneSetting.UserId = setting.getUserId();
            for (BmoWFST1 o1 : setting.getWFST1()) {
                WebClientFormSettingItem item = new WebClientFormSettingItem();
                item.Guid = o1.getGuid();
                item.ItemId = o1.getItemId();
                item.Order = o1.getOrder();
                item.Visible = o1.getVisible();
                item.Editable = o1.getEditable();
                item.VisibleInGrid = o1.getVisInGrid();
                item.EditableInGrid = o1.getEditInGrid();
                oneSetting.getWebClientFormSettingItems().add(item);
            }
            ((List)resultMap.get(setting.FormId)).add(oneSetting);
        }
        return resultMap;
    }

    @NotNull
    private List<BmoOWFST> querySettingsFromDB(List<FormSetting> formSettingInfo) throws Exception {
        List formQuery = this.createFormQueryCriteria(formSettingInfo);
        List originFormSettings = this.fetchFormSettings(formQuery);
        List subFormQuery = this.createSubFormQueryCriteria(formQuery, originFormSettings);
        List subFormSettings = this.fetchSubFormSettings(subFormQuery);
        List formSettings = this.combineFormAndSubFormSettngs(originFormSettings, subFormSettings);
        return formSettings;
    }

    @RequestMappingTx(path={"/formsetting/get.svc"})
    @ResponseBody
    public List<WebClientFormSetting> get(@RequestParam(value="formId") String formId, @RequestParam(value="boId") String objId) throws Exception {
        if (StringUtil.isEmpty((String)formId)) {
            throw new RuntimeException("No valid FormId");
        }
        Integer userId = this.getCurrentUserId();
        String objType = this.parseObjTypeId(objId);
        List settings = this.daoOWFST.loadByUserIdAndFormId(userId, formId, objType);
        for (BmoOWFST setting : settings) {
            List subTable = this.daoWFST1.loadByUserIdandFormIdandObjType(userId, formId, objType);
            setting.setWFST1(subTable);
        }
        ArrayList<WebClientFormSetting> list = new ArrayList<WebClientFormSetting>();
        for (BmoOWFST setting : settings) {
            WebClientFormSetting o = new WebClientFormSetting();
            o.Guid = setting.getGuid();
            o.UserId = setting.getUserId();
            ArrayList<WebClientFormSettingItem> items = new ArrayList<WebClientFormSettingItem>();
            for (BmoWFST1 o1 : setting.getWFST1()) {
                WebClientFormSettingItem e1 = new WebClientFormSettingItem();
                e1.Guid = o1.getGuid();
                e1.ItemId = o1.getItemId();
                e1.Order = o1.getOrder();
                e1.Visible = o1.getVisible();
                e1.Editable = o1.getEditable();
                e1.VisibleInGrid = o1.getVisInGrid();
                e1.EditableInGrid = o1.getEditInGrid();
                items.add(e1);
            }
            o.WebClientFormSettingItems = items;
            list.add(o);
        }
        return list;
    }

    @RequestMappingTx(path={"/formsetting/update.svc"})
    @ResponseBody
    public WebClientFormSetting update(@RequestParam(value="formId") String formId, @RequestParam(value="boId") String objId, @RequestBody WebClientFormSetting setting) throws Exception {
        if (StringUtil.isEmpty((String)formId)) {
            throw new RuntimeException("No valid FormId");
        }
        String objType = this.parseObjTypeId(objId);
        if (StringUtil.isEmpty((String)setting.Guid)) {
            this.detectDuplication(formId, objType);
            return this.createSetting(setting, objType);
        }
        return this.updateSetting(setting, objType);
    }

    @RequestMappingTx(path={"/formsetting/delete.svc"})
    @ResponseBody
    public WebClientFormSetting delete(@RequestBody WebClientFormSetting setting) throws Exception {
        if (!StringUtil.isEmpty((String)setting.Guid)) {
            try {
                this.feignClient.delete(setting.Guid);
            }
            catch (Exception e) {
                logger.error("error while deleting the form setting", (Throwable)e);
                throw new BusinessException("Failed to delete form setting");
            }
        }
        return setting;
    }

    @Nullable
    private String parseObjTypeId(String objId) {
        List l;
        String defaultTypeId = "GENERAL";
        if (StringUtil.isEmpty((String)objId)) {
            return defaultTypeId;
        }
        String[] parts = objId.split(",");
        String tableName = BoMappingHelper.checkTable((String)parts[0]);
        if (Objects.equals(tableName, "DOC1")) {
            return defaultTypeId;
        }
        if (tableName.startsWith("@")) {
            List dtTemp = this.daoOUDO.loadParentName(tableName.substring(1));
            if (dtTemp != null && dtTemp.size() >= 1) {
                return Table.udtAddPrefix((String)((Object[])dtTemp.get(0))[0].toString());
            }
            return defaultTypeId;
        }
        String objEntry = parts[1];
        String objType = this.getObjType(tableName);
        if (objType == null) {
            logger.error("");
            logger.error("+----------------------------------------------------------------------------------------");
            logger.error("ObjectType is not configured for " + tableName + ", please check TableNameEnum.java.");
            logger.error("Otherwise, form settings won't work.");
            logger.error("+----------------------------------------------------------------------------------------");
            logger.error("");
            return defaultTypeId;
        }
        if (this.isDraftDocument(objType) && (l = this.daoODRF.getDocType(Integer.valueOf(objEntry))) != null && l.size() > 0) {
            objType = ((Object[])l.get(0))[0].toString();
        }
        return objType;
    }

    private void detectDuplication(String formId, String objType) throws Exception {
        List settings_in_db = this.daoOWFST.loadByUserIdAndFormId(this.getCurrentUserId(), formId, objType);
        if (settings_in_db.size() > 0) {
            throw new RuntimeException("Form Setting already existed, please check the guid");
        }
    }

    private Integer getCurrentUserId() throws Exception {
        if (this.env.getUserId() == null) {
            throw new RuntimeException("User Not login");
        }
        return this.env.getUserId();
    }

    private String getObjType(String tableName) {
        Integer objId = TableNameEnum.getObjectId((String)tableName);
        return objId == null ? null : objId.toString();
    }

    private WebClientFormSetting createSetting(WebClientFormSetting setting, String objType) throws Exception {
        setting.UserId = this.getCurrentUserId();
        setting.Guid = UUID.randomUUID().toString();
        setting.DocObjectCode = objType;
        for (WebClientFormSettingItem i : setting.getWebClientFormSettingItems()) {
            i.Guid = i.ItemId;
        }
        return this.feignClient.create(setting);
    }

    private WebClientFormSetting updateSetting(WebClientFormSetting setting, String objType) throws Exception {
        setting.UserId = this.getCurrentUserId();
        setting.DocObjectCode = objType;
        for (WebClientFormSettingItem i : setting.getWebClientFormSettingItems()) {
            i.Guid = i.ItemId;
        }
        this.feignClient.update(setting.Guid, setting);
        return setting;
    }

    private List<FormSettingSearchItem> createFormQueryCriteria(List<FormSetting> formSettings) throws Exception {
        ArrayList<FormSettingSearchItem> list = new ArrayList<FormSettingSearchItem>();
        HashMap<Integer, String> indexParamMap = new HashMap<Integer, String>();
        for (int i = 0; i < formSettings.size(); ++i) {
            FormSetting formSettingItem = formSettings.get(i);
            if (StringUtil.isEmpty((String)formSettingItem.formId)) {
                throw new RuntimeException("No valid FormId");
            }
            Integer userId = this.getCurrentUserId();
            String objType = this.parseObjTypeId(formSettingItem.objId);
            if (this.isDraftDocument(objType)) {
                String objEntry = formSettingItem.objId.split(",")[1];
                indexParamMap.put(i, objEntry);
            }
            FormSettingSearchItem formSettingSearchItem = new FormSettingSearchItem();
            formSettingSearchItem.UserId = userId;
            formSettingSearchItem.FormId = formSettingItem.formId;
            formSettingSearchItem.ObjType = objType;
            list.add(formSettingSearchItem);
        }
        Map indexObjectTypeMap = this.getIndexObjTypeMap(indexParamMap);
        Set keys = indexObjectTypeMap.keySet();
        for (Integer key : keys) {
            ((FormSettingSearchItem)list.get((int)key.intValue())).ObjType = (String)indexObjectTypeMap.get(key);
        }
        return list;
    }

    private boolean isDraftDocument(String objType) {
        return Objects.equals(objType, String.valueOf(112));
    }

    private Map<Integer, String> getIndexObjTypeMap(Map<Integer, String> indexParamMap) {
        List objTypeObjs = new ArrayList();
        Object[] params = indexParamMap.values().toArray(new String[0]);
        HashMap<Integer, String> indexObjectTypeMap = new HashMap<Integer, String>();
        int queryNumber = indexParamMap.size();
        if (queryNumber > 0) {
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("select [ObjType], [DocEntry] from ODRF where");
            for (int i = 0; i < queryNumber; ++i) {
                if (i > 0) {
                    queryStr.append(" or");
                }
                queryStr.append(" [DocEntry] = ?");
            }
            objTypeObjs = JpaUtil.load((EntityManager)this.em, Object[].class, (String)queryStr.toString(), (Object[])params);
        }
        if (objTypeObjs != null && objTypeObjs.size() > 0) {
            HashMap<String, String> docEntryObjTypeMap = new HashMap<String, String>();
            for (Object[] objectArray : objTypeObjs) {
                if (docEntryObjTypeMap.containsKey(objectArray[1])) continue;
                docEntryObjTypeMap.put(objectArray[1].toString(), objectArray[0].toString());
            }
            for (Map.Entry entry : indexParamMap.entrySet()) {
                String value = (String)entry.getValue();
                String result = (String)docEntryObjTypeMap.get(value);
                if (result == null) continue;
                indexObjectTypeMap.put((Integer)entry.getKey(), result);
            }
        }
        return indexObjectTypeMap;
    }

    private List<BmoOWFST> fetchFormSettings(List<FormSettingSearchItem> searchCriteria) {
        List<Object> formSettings = new ArrayList<BmoOWFST>();
        int queryNumber = searchCriteria.size();
        if (queryNumber > 0) {
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("select * from OWFST where");
            ArrayList<Object> params = new ArrayList<Object>();
            for (int i = 0; i < queryNumber; ++i) {
                if (i > 0) {
                    queryStr.append(" or");
                }
                queryStr.append(" ([UserId] = ? and [FormId] = ? and [ObjType] = ?)");
                FormSettingSearchItem searchCriteriaItem = searchCriteria.get(i);
                params.add(searchCriteriaItem.UserId);
                params.add(searchCriteriaItem.FormId);
                params.add(searchCriteriaItem.ObjType);
            }
            formSettings = JpaUtil.load((EntityManager)this.em, BmoOWFST.class, (String)queryStr.toString(), (Object[])params.toArray());
        }
        return formSettings;
    }

    private List<FormSettingSearchItem> createSubFormQueryCriteria(List<FormSettingSearchItem> searchCriteria, List<BmoOWFST> formSettings) {
        ArrayList<FormSettingSearchItem> list = new ArrayList<FormSettingSearchItem>();
        for (BmoOWFST setting : formSettings) {
            FormSettingSearchItem criteria = searchCriteria.stream().filter(item -> item.FormId.equals(setting.FormId)).findFirst().get();
            list.add(criteria);
        }
        return list;
    }

    private List<BmoWFST1> fetchSubFormSettings(List<FormSettingSearchItem> searchCriteria) {
        List<Object> subFormSettings = new ArrayList<BmoWFST1>();
        int queryNumber = searchCriteria.size();
        if (queryNumber > 0) {
            StringBuilder queryStr = new StringBuilder();
            queryStr.append("select T1.* from OWFST T0 inner join WFST1 T1 on T0.[Guid] = T1.[ParentId] where");
            ArrayList<Object> params = new ArrayList<Object>();
            for (int i = 0; i < searchCriteria.size(); ++i) {
                if (i > 0) {
                    queryStr.append(" or");
                }
                queryStr.append(" (T0.[UserId] = ? and T0.[FormId] = ? and T0.[ObjType] = ?)");
                FormSettingSearchItem searchCriteriaItem = searchCriteria.get(i);
                params.add(searchCriteriaItem.UserId);
                params.add(searchCriteriaItem.FormId);
                params.add(searchCriteriaItem.ObjType);
            }
            queryStr.append(" order by T1.[Order]");
            subFormSettings = JpaUtil.load((EntityManager)this.em, BmoWFST1.class, (String)queryStr.toString(), (Object[])params.toArray());
        }
        return subFormSettings;
    }

    private List<BmoOWFST> combineFormAndSubFormSettngs(List<BmoOWFST> originFormSettings, List<BmoWFST1> subFormSettings) {
        for (BmoOWFST setting : originFormSettings) {
            List subSettings = subFormSettings.stream().filter(item -> item.ParentId.equals(setting.Guid)).collect(Collectors.toList());
            setting.setWFST1(subSettings);
        }
        return originFormSettings;
    }
}

