/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.infra.engines.orm.UdfObject
 *  com.sap.b1.notification.approval.ApprovalRequestInfo
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 */
package com.sap.b1.notification.approval;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.infra.engines.orm.UdfObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class ApprovalRequestInfo
extends UdfObject {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value="DataCols")
    @Column(name="DataCols")
    private Integer DataCols;
    @JsonProperty(value="ObjType")
    @Column(name="ObjType")
    private String ObjType;
    @JsonProperty(value="AlertCode")
    @Column(name="AlertCode")
    private Integer AlertCode;
    @JsonProperty(value="UserSign")
    @Column(name="UserSign")
    private Integer UserSign;
    @JsonProperty(value="Authorizer")
    @Column(name="U_NAME")
    private String Authorizer;
    @JsonProperty(value="Remarks")
    @Column(name="UserText")
    private String Remarks;
    @JsonProperty(value="DocEntry")
    @Column(name="DocEntry")
    private Integer DocEntry;
    @JsonProperty(value="IsDraft")
    @Column(name="IsDraft")
    private String IsDraft;
    @JsonProperty(value="DraftEntry")
    @Column(name="DraftEntry")
    private Integer DraftEntry;
    @JsonProperty(value="Subject")
    @Column(name="Subject")
    private String Subject;
    @JsonProperty(value="RecDate")
    @Column(name="RecDate")
    private Timestamp RecDate;
    @JsonProperty(value="RecTime")
    @Column(name="RecTime")
    private Integer RecTime;
    @JsonProperty(value="WddCode")
    @Column(name="WddCode")
    private Integer WddCode;
    @JsonProperty(value="WtmCode")
    @Column(name="WtmCode")
    private Integer WtmCode;
    @JsonProperty(value="StepCode")
    @Column(name="StepCode")
    private Integer StepCode;
    @JsonProperty(value="UserID")
    @Column(name="UserID")
    private Integer UserID;
    @JsonProperty(value="KeyStr")
    @Column(name="KeyStr")
    private String KeyStr;
    @JsonProperty(value="Originator")
    @Column(name="Originator")
    private String Originator;
    @JsonProperty(value="WtmName")
    @Column(name="Name")
    private String WtmName;
    @JsonProperty(value="Stage")
    @Column(name="Stage")
    private String Stage;
    @JsonProperty(value="isIns")
    @Column(name="isIns")
    private String isIns;

    public Integer getDataCols() {
        return this.DataCols;
    }

    public void setDataCols(Integer dataCols) {
        this.DataCols = dataCols;
    }

    public String getObjType() {
        return this.ObjType;
    }

    public void setObjType(String objType) {
        this.ObjType = objType;
    }

    public Integer getAlertCode() {
        return this.AlertCode;
    }

    public void setAlertCode(Integer alertCode) {
        this.AlertCode = alertCode;
    }

    public String getAuthorizer() {
        return this.Authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.Authorizer = authorizer;
    }

    public Integer getUserSign() {
        return this.UserSign;
    }

    public void setUserSign(Integer userSign) {
        this.UserSign = userSign;
    }

    public String getRemarks() {
        return this.Remarks;
    }

    public void setRemarks(String remarks) {
        this.Remarks = remarks;
    }

    public Integer getDocEntry() {
        return this.DocEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.DocEntry = docEntry;
    }

    public String getIsDraft() {
        return this.IsDraft;
    }

    public void setIsDraft(String isDraft) {
        this.IsDraft = isDraft;
    }

    public Integer getDraftEntry() {
        return this.DraftEntry;
    }

    public void setDraftEntry(Integer draftEntry) {
        this.DraftEntry = draftEntry;
    }

    public String getSubject() {
        return this.Subject;
    }

    public void setSubject(String subject) {
        this.Subject = subject;
    }

    public Timestamp getRecDate() {
        return this.RecDate;
    }

    public void setRecDate(Timestamp recDate) {
        this.RecDate = recDate;
    }

    public Integer getRecTime() {
        return this.RecTime;
    }

    public void setRecTime(Integer recTime) {
        this.RecTime = recTime;
    }

    public Integer getWddCode() {
        return this.WddCode;
    }

    public void setWddCode(Integer wddCode) {
        this.WddCode = wddCode;
    }

    public Integer getWtmCode() {
        return this.WtmCode;
    }

    public void setWtmCode(Integer wtmCode) {
        this.WtmCode = wtmCode;
    }

    public Integer getStepCode() {
        return this.StepCode;
    }

    public void setStepCode(Integer stepCode) {
        this.StepCode = stepCode;
    }

    public Integer getUserID() {
        return this.UserID;
    }

    public void setUserID(Integer userID) {
        this.UserID = userID;
    }

    public String getOriginator() {
        return this.Originator;
    }

    public void setOriginator(String originator) {
        this.Originator = originator;
    }

    public String getKeyStr() {
        return this.KeyStr;
    }

    public void setKeyStr(String keyStr) {
        this.KeyStr = keyStr;
    }

    public String getWtmName() {
        return this.WtmName;
    }

    public void setWtmName(String wtmName) {
        this.WtmName = wtmName;
    }

    public String getStage() {
        return this.Stage;
    }

    public void setStage(String stage) {
        this.Stage = stage;
    }

    public String getIsIns() {
        return this.isIns;
    }

    public void setIsIns(String isIns) {
        this.isIns = isIns;
    }
}

