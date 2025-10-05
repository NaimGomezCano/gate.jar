/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.JpaUtil
 *  com.sap.b1.notification.approval.ApprovalRequestInfo
 *  com.sap.b1.notification.approval.ObjectTypeEnum
 *  com.sap.b1.notification.cache.OWDDController
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.ApprovalMonitor
 *  com.sap.b1.notification.monitors.MonitorBase
 *  com.sap.b1.notification.monitors.NotificationParameter
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 *  com.sap.b1.notification.server.NotificationItem
 *  gen.bean.BmoOWNOT
 *  gen.dao.DaoOWNOT
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.bo.base.JpaUtil;
import com.sap.b1.notification.approval.ApprovalRequestInfo;
import com.sap.b1.notification.approval.ObjectTypeEnum;
import com.sap.b1.notification.cache.OWDDController;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.MonitorBase;
import com.sap.b1.notification.monitors.NotificationParameter;
import com.sap.b1.notification.monitors.NotificationTypeEnum;
import com.sap.b1.notification.server.NotificationItem;
import gen.bean.BmoOWNOT;
import gen.dao.DaoOWNOT;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.lang.invoke.CallSite;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class ApprovalMonitor
extends MonitorBase {
    private static final Logger logger = LoggerFactory.getLogger(ApprovalMonitor.class);
    @Autowired
    private OWDDController owddController;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private DaoOWNOT daoOWNOT;
    private List<BmoOWNOT> dtNOTI;
    private List<ApprovalRequestInfo> approvalRequests;

    public ApprovalMonitor(NotificationParameter notificationParameter) {
        super(notificationParameter);
    }

    public boolean refresh(Set<String> unseens) {
        boolean changed;
        String dateFilter = this.getDateFilter();
        List approvalRequests = JpaUtil.load((EntityManager)this.em, ApprovalRequestInfo.class, (String)this.getDraftAlertSql(this.getObjTypeListStr("OWDD"), this.getObjTypeListStr("ALR3")), (Object[])new Object[]{ObjectTypeEnum.SalesDraft.getValue(), ObjectTypeEnum.OPDF.getValue(), ObjectTypeEnum.InventoryCountingDraft.getValue(), this.notificationParameter.getUserId(), dateFilter, this.notificationParameter.getUserId(), dateFilter});
        List approvalRequestsForAuthorizers = JpaUtil.load((EntityManager)this.em, ApprovalRequestInfo.class, (String)this.getOALRForAuthorizerSql(), (Object[])new Object[]{this.notificationParameter.getUserId()});
        HashMap<CallSite, ApprovalRequestInfo> approvalRequestsForAuthorizersMap = new HashMap<CallSite, ApprovalRequestInfo>();
        ArrayList<CallSite> keyStrList = new ArrayList<CallSite>();
        for (int i = approvalRequestsForAuthorizers.size() - 1; i >= 0; --i) {
            Object keyStrArr;
            ApprovalRequestInfo requestsForAuthorizer = (ApprovalRequestInfo)approvalRequestsForAuthorizers.get(i);
            Object object = keyStrArr = requestsForAuthorizer.getKeyStr() == null || requestsForAuthorizer.getKeyStr().isEmpty() ? new String[]{} : requestsForAuthorizer.getKeyStr().split("\\s+");
            if (((String[])keyStrArr).length == 3) {
                requestsForAuthorizer.setWddCode(Integer.valueOf(Integer.parseInt(keyStrArr[0])));
                requestsForAuthorizer.setStepCode(Integer.valueOf(Integer.parseInt((String)keyStrArr[1])));
                requestsForAuthorizer.setUserID(Integer.valueOf(Integer.parseInt((String)keyStrArr[2])));
                approvalRequestsForAuthorizersMap.put((CallSite)((Object)((String)keyStrArr[0] + "-" + (String)keyStrArr[1] + "-" + (String)keyStrArr[2])), requestsForAuthorizer);
                keyStrList.add((CallSite)((Object)("(\"WDD1\".\"WddCode\" = '" + (String)keyStrArr[0] + "' AND \"WDD1\".\"StepCode\" = '" + (String)keyStrArr[1] + "' AND \"WDD1\".\"UserID\" = '" + (String)keyStrArr[2] + "')\n")));
                continue;
            }
            approvalRequestsForAuthorizers.remove(i);
        }
        if (keyStrList.size() != 0) {
            String owddForAuthorizerSql = this.getOWDDForAuthorizerSql(String.join((CharSequence)" OR ", keyStrList));
            List owddApprovalRequestsForAuthorizers = JpaUtil.load((EntityManager)this.em, ApprovalRequestInfo.class, (String)owddForAuthorizerSql, (Object[])new Object[0]);
            for (ApprovalRequestInfo info : owddApprovalRequestsForAuthorizers) {
                Optional.ofNullable((ApprovalRequestInfo)approvalRequestsForAuthorizersMap.get(info.getWddCode() + "-" + info.getStepCode() + "-" + info.getUserID())).ifPresent(v -> {
                    v.setIsDraft(info.getIsDraft());
                    v.setDocEntry(info.getDocEntry());
                    v.setDraftEntry(info.getDraftEntry());
                    v.setObjType(info.getObjType());
                    v.setWtmCode(info.getWtmCode());
                    v.setWtmName(info.getWtmName());
                    v.setStage(info.getStage());
                    v.setIsIns(info.getIsIns());
                    approvalRequests.add(v);
                });
            }
        }
        List newdtNOTI = this.daoOWNOT.loadByUserAndType(this.notificationParameter.getUserId(), NotificationTypeEnum.APPROVAL.getValue());
        boolean bl = changed = !this.equals(this.approvalRequests, approvalRequests) || !this.equals(this.dtNOTI, newdtNOTI) || this.notificationParameter.isShowDayChanged();
        if (changed) {
            try {
                List oldItems = new ArrayList();
                Parameter parameter = new Parameter(this.notificationParameter.getUserId());
                if (this.approvalRequests != null && this.dtNOTI != null) {
                    parameter.showDays = this.notificationParameter.getOldShowDays();
                    oldItems = this.owddController.getNotifications(this.approvalRequests, this.dtNOTI, parameter);
                }
                parameter.showDays = this.notificationParameter.getShowDays();
                List newItems = this.owddController.getNotifications(approvalRequests, newdtNOTI, parameter);
                this.calculateUnseen(unseens, oldItems, newItems);
                this.approvalRequests = approvalRequests;
                this.dtNOTI = newdtNOTI;
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                changed = false;
            }
        }
        return changed;
    }

    public List<NotificationItem> getNotifications(Parameter parameter) {
        parameter.showDays = this.notificationParameter.getShowDays();
        List<Object> notifications = new ArrayList<NotificationItem>();
        try {
            notifications = this.owddController.getNotifications(this.approvalRequests, this.dtNOTI, parameter);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return notifications;
    }

    private String getObjTypeListStr(String tableName) {
        Integer[] objTypeArrays = new Integer[]{ObjectTypeEnum.SalesOrder.getValue(), ObjectTypeEnum.SalesQuotation.getValue(), ObjectTypeEnum.Delivery.getValue(), ObjectTypeEnum.Invoice.getValue(), ObjectTypeEnum.Returns.getValue(), ObjectTypeEnum.ARCreditMemo.getValue(), ObjectTypeEnum.PurchaseRequest.getValue(), ObjectTypeEnum.PurchaseQutation.getValue(), ObjectTypeEnum.PurchaseOrder.getValue(), ObjectTypeEnum.GoodsReturn.getValue(), ObjectTypeEnum.APCreditMemos.getValue(), ObjectTypeEnum.ReturnRequest.getValue(), ObjectTypeEnum.GoodsReturnRequest.getValue(), ObjectTypeEnum.GRPO.getValue(), ObjectTypeEnum.APInvoice.getValue(), ObjectTypeEnum.OutgoingPayment.getValue(), ObjectTypeEnum.GoodsIssue.getValue(), ObjectTypeEnum.GoodsReceipt.getValue(), ObjectTypeEnum.InventoryCounting.getValue()};
        return Arrays.stream(objTypeArrays).map(objType -> this.getObjTypeString(tableName, objType)).collect(Collectors.joining(" OR "));
    }

    private String getObjTypeString(String tableName, Integer objType) {
        return "\"" + tableName + "\".\"ObjType\" = '" + objType + "'";
    }

    private String getDraftAlertSql(String OWDDObjTypeListStr, String ALR3ObjTypeListStr) {
        return "    SELECT \n        \"OAIB\".\"AlertCode\",\"OAIB\".\"RecDate\", \"OAIB\".\"RecTime\", \"OALR\".\"Subject\",         \"OALR\".\"UserSign\", \"OALR\".\"UserText\", \"OALR\".\"DataCols\",\"OUSR\".\"U_NAME\", \n        \"OWDD\".\"DocEntry\", \"OWDD\".\"IsDraft\", \"OWDD\".\"ObjType\",\"OWDD\".\"DraftEntry\",\n        \"ODRF\".\"isIns\"\n    FROM \"ALR3\"\n    INNER JOIN \"OALR\" ON \"ALR3\".\"Code\" = \"OALR\".\"Code\"\n    INNER JOIN \"OAIB\" ON \"OALR\".\"Code\"=\"OAIB\".\"AlertCode\"\n    INNER JOIN \"OWDD\" ON \"ALR3\".\"KeyStr\" = \"OWDD\".\"DraftEntry\" AND \"ALR3\".\"ObjType\" = \"OWDD\".\"DraftType\"\n    INNER JOIN \"OUSR\" ON \"OALR\".\"UserSign\" = \"OUSR\".\"USERID\"\n    LEFT JOIN \"ODRF\" ON \"ODRF\".\"DocEntry\" = \"OWDD\".\"DraftEntry\"\n    WHERE (\"ALR3\".\"ObjType\" = ? OR \"ALR3\".\"ObjType\" = ? OR \"ALR3\".\"ObjType\" = ?)\n    AND \"OAIB\".\"UserSign\"= ?\n    AND \"OWDD\".\"Status\" != 'W'\n    AND \"OAIB\".\"RecDate\" >= ?\n    AND (" + OWDDObjTypeListStr + ")\n\tUNION ALL \n\tSELECT  \n        \"OAIB\".\"AlertCode\",\"OAIB\".\"RecDate\", \"OAIB\".\"RecTime\", \"OALR\".\"Subject\",         \"OALR\".\"UserSign\", \"OALR\".\"UserText\", \"OALR\".\"DataCols\", \"OUSR\".\"U_NAME\", \n        \"OWDD\".\"DocEntry\", \"OWDD\".\"IsDraft\", \"OWDD\".\"ObjType\", \"OWDD\".\"DraftEntry\",\n        \"ODRF\".\"isIns\"\n    FROM \"ALR3\"\n    INNER JOIN \"OALR\" ON \"ALR3\".\"Code\" = \"OALR\".\"Code\"\n    INNER JOIN \"OAIB\" ON \"OALR\".\"Code\"=\"OAIB\".\"AlertCode\"\n    INNER JOIN \"OWDD\" ON \"ALR3\".\"KeyStr\" = \"OWDD\".\"DocEntry\" AND \"ALR3\".\"ObjType\" = \"OWDD\".\"ObjType\"\n    INNER JOIN \"OUSR\" ON \"OALR\".\"UserSign\" = \"OUSR\".\"USERID\"\n    LEFT JOIN \"ODRF\" ON \"ODRF\".\"DocEntry\" = \"OWDD\".\"DraftEntry\"\n    WHERE (" + ALR3ObjTypeListStr + ")\n    AND \"OAIB\".\"UserSign\"= ? \n    AND \"OWDD\".\"Status\" != 'W'\n    AND \"OAIB\".\"RecDate\" >= ?\n    AND \"OWDD\".\"ProcesStat\" = 'A'\n";
    }

    private String getOALRForAuthorizerSql() {
        return "    SELECT \n        \"OAIB\".\"RecDate\", \"OAIB\".\"RecTime\",\"OAIB\".\"AlertCode\",\n        \"ALR3\".\"KeyStr\",\n        \"OALR\".\"Subject\",\"OALR\".\"UserText\", \"OALR\".\"DataCols\",\n        \"OUSR\".\"U_NAME\" as \"Originator\" \n    FROM \"OAIB\"\n    INNER JOIN \"ALR3\" ON \"ALR3\".\"Code\" = \"OAIB\".\"AlertCode\"\n    INNER JOIN \"OALR\" ON \"OALR\".\"Code\" = \"OAIB\".\"AlertCode\"\n    INNER JOIN \"OUSR\" ON \"OALR\".\"UserSign\" = \"OUSR\".\"USERID\"\n    WHERE \"OAIB\".\"UserSign\" = ?\n";
    }

    private String getOWDDForAuthorizerSql(String filterConditions) {
        return "SELECT \n\"OWDD\".\"DocEntry\", \"OWDD\".\"IsDraft\", \"OWDD\".\"ObjType\",\"OWDD\".\"DraftEntry\",\"OWDD\".\"WtmCode\",\n\"WDD1\".\"WddCode\", \"WDD1\".\"StepCode\", \"WDD1\".\"UserID\",\n\"OWTM\".\"Name\",\n\"ODRF\".\"isIns\",\n\"OWST\".\"Name\" as \"Stage\"\n FROM WDD1 \n INNER JOIN \"OWDD\" ON \"WDD1\".\"WddCode\" = \"OWDD\".\"WddCode\"\n INNER JOIN \"OWTM\" ON \"OWTM\".\"WtmCode\" = \"OWDD\".\"WtmCode\"\n INNER JOIN \"OWST\" ON \"OWST\".\"WstCode\" = \"WDD1\".\"StepCode\"\n LEFT JOIN \"ODRF\" ON \"ODRF\".\"DocEntry\" = \"OWDD\".\"DraftEntry\"\nWHERE \n" + filterConditions;
    }

    private String getDateFilter() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.minusDays((long)this.notificationParameter.getShowDays() - 1L).format(formatter);
    }
}

