/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.notification.server.Action
 */
package com.sap.b1.notification.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Action {
    public static final String Text_Accept = "Accept";
    public static final String Text_Reject = "Reject";
    public static final String Nature_Positive = "POSITIVE";
    public static final String Nature_Negative = "NEGATIVE";
    public static final String Id_Accept = "Accept";
    public static final String Id_Reject = "Reject";
    public static final String Approval_Status_Pending = "W";
    public static final String Approval_Status_Approved = "Y";
    public static final String Approval_Status_Rejected = "N";
    public static final String Approval_Decision_Pending = "ardPending";
    public static final String Approval_Decision_Approved = "ardApproved";
    public static final String Approval_Decision_Rejected = "ardNotApproved";
    @JsonProperty
    public String ActionText;
    @JsonProperty
    public String Nature;
    @JsonProperty
    public String ActionId;
}

