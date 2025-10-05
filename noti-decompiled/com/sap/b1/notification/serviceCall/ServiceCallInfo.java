/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.infra.engines.orm.UdfObject
 *  com.sap.b1.notification.serviceCall.ServiceCallInfo
 *  jakarta.persistence.Column
 */
package com.sap.b1.notification.serviceCall;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.infra.engines.orm.UdfObject;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ServiceCallInfo
extends UdfObject {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value="isForReminder")
    @Column(name="isForReminder")
    private Boolean isForReminder = false;
    @JsonProperty(value="callID")
    @Column(name="callID")
    private Integer callID;
    @JsonProperty(value="subject")
    @Column(name="subject")
    private String subject;
    @JsonProperty(value="DocNum")
    @Column(name="[DocNum]")
    private Integer DocNum;
    @JsonProperty(value="custmrName")
    @Column(name="custmrName")
    private String custmrName;
    @JsonProperty(value="status")
    @Column(name="status")
    private Integer status;
    @JsonProperty(value="priority")
    @Column(name="priority")
    private String priority;
    @JsonProperty(value="AssignDate")
    @Column(name="AssignDate")
    private Timestamp AssignDate;
    @JsonProperty(value="AssignTime")
    @Column(name="AssignTime")
    private Integer AssignTime;
    @JsonProperty(value="StartDate")
    @Column(name="StartDate")
    private Timestamp StartDate;
    @JsonProperty(value="StartTime")
    @Column(name="StartTime")
    private Integer StartTime;
    @JsonProperty(value="EndDate")
    @Column(name="EndDate")
    private Timestamp EndDate;
    @JsonProperty(value="EndTime")
    @Column(name="EndTime")
    private Integer EndTime;
    @JsonProperty(value="Duration")
    @Column(name="Duration")
    private BigDecimal Duration;
    @JsonProperty(value="Reminder")
    @Column(name="Reminder")
    private String Reminder;
    @JsonProperty(value="RemQty")
    @Column(name="RemQty")
    private BigDecimal RemQty;
    @JsonProperty(value="RemDate")
    @Column(name="RemDate")
    private Timestamp RemDate;
    @JsonProperty(value="RemTime")
    @Column(name="RemTime")
    private Integer RemTime;
    @JsonProperty(value="Technician")
    @Column(name="Technician")
    private Integer Technician;
    @JsonProperty(value="userSign")
    @Column(name="userSign")
    private Integer userSign;
    @JsonProperty(value="valid")
    @Column(name="valid")
    private Boolean valid = true;
    @JsonProperty(value="isAssign")
    @Column(name="isAssign")
    private String isAssign;

    public Boolean getIsForReminder() {
        return this.isForReminder;
    }

    public void setIsForReminder(Boolean val) {
        this.isForReminder = val;
    }

    public Integer getCallID() {
        return this.callID;
    }

    public void setCallID(Integer val) {
        this.callID = val;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String val) {
        this.subject = val;
    }

    public Integer getDocNum() {
        return this.DocNum;
    }

    public void setDocNum(Integer val) {
        this.DocNum = val;
    }

    public String getCustmrName() {
        return this.custmrName;
    }

    public void setCustmrName(String val) {
        this.custmrName = val;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer val) {
        this.status = val;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String val) {
        this.priority = val;
    }

    public Timestamp getAssignDate() {
        return this.AssignDate;
    }

    public void setAssignDate(Timestamp val) {
        this.AssignDate = val;
    }

    public Integer getAssignTime() {
        return this.AssignTime;
    }

    public void setAssignTime(Integer val) {
        this.AssignTime = val;
    }

    public Timestamp getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(Timestamp val) {
        this.StartDate = val;
    }

    public Integer getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(Integer val) {
        this.StartTime = val;
    }

    public Timestamp getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(Timestamp val) {
        this.EndDate = val;
    }

    public Integer getEndTime() {
        return this.EndTime;
    }

    public void setEndTime(Integer val) {
        this.EndTime = val;
    }

    public BigDecimal getDuration() {
        return this.Duration;
    }

    public void setDuration(BigDecimal val) {
        this.Duration = val;
    }

    public String getReminder() {
        return this.Reminder;
    }

    public void setReminder(String val) {
        this.Reminder = val;
    }

    public BigDecimal getRemQty() {
        return this.RemQty;
    }

    public void setRemQty(BigDecimal val) {
        this.RemQty = val;
    }

    public Timestamp getRemDate() {
        return this.RemDate;
    }

    public void setRemDate(Timestamp val) {
        this.RemDate = val;
    }

    public Integer getRemTime() {
        return this.RemTime;
    }

    public void setRemTime(Integer val) {
        this.RemTime = val;
    }

    public Integer getTechnician() {
        return this.Technician;
    }

    public void setTechnician(Integer val) {
        this.Technician = val;
    }

    public Integer getUserSign() {
        return this.userSign;
    }

    public void setUserSign(Integer val) {
        this.userSign = val;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean val) {
        this.valid = val;
    }

    public String getIsAssign() {
        return this.isAssign;
    }

    public void setIsAssign(String assign) {
        this.isAssign = assign;
    }
}

