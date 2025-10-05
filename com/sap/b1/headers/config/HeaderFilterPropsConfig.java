/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.headers.config.HeaderFilterPropsConfig
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.headers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="header.filter")
public class HeaderFilterPropsConfig {
    private String path_prefix;
    private String auth_req_file_name;
    private String auth_rsp_file_name;
    private String noti_req_file_name;
    private String noti_rsp_file_name;
    private String anly_req_file_name;
    private String anly_rsp_file_name;
    private String b1ah_req_file_name;
    private String b1ah_rsp_file_name;
    private String b1s_req_file_name;
    private String b1s_rsp_file_name;
    private String crpt_req_file_name;
    private String crpt_rsp_file_name;
    private String extn_req_file_name;
    private String extn_rsp_file_name;
    private String svcl_req_file_name;
    private String svcl_rsp_file_name;
    private String tcli_req_file_name;
    private String tcli_rsp_file_name;
    private String tcli_ui_req_file_name;
    private String tcli_ui_rsp_file_name;

    public String getPath_prefix() {
        return this.path_prefix;
    }

    public void setPath_prefix(String path_prefix) {
        this.path_prefix = path_prefix;
    }

    public String getAuth_req_file_name() {
        return this.auth_req_file_name;
    }

    public void setAuth_req_file_name(String auth_req_file_name) {
        this.auth_req_file_name = auth_req_file_name;
    }

    public String getAuth_rsp_file_name() {
        return this.auth_rsp_file_name;
    }

    public void setAuth_rsp_file_name(String auth_rsp_file_name) {
        this.auth_rsp_file_name = auth_rsp_file_name;
    }

    public String getNoti_req_file_name() {
        return this.noti_req_file_name;
    }

    public void setNoti_req_file_name(String noti_req_file_name) {
        this.noti_req_file_name = noti_req_file_name;
    }

    public String getNoti_rsp_file_name() {
        return this.noti_rsp_file_name;
    }

    public void setNoti_rsp_file_name(String noti_rsp_file_name) {
        this.noti_rsp_file_name = noti_rsp_file_name;
    }

    public String getAnly_req_file_name() {
        return this.anly_req_file_name;
    }

    public void setAnly_req_file_name(String anly_req_file_name) {
        this.anly_req_file_name = anly_req_file_name;
    }

    public String getAnly_rsp_file_name() {
        return this.anly_rsp_file_name;
    }

    public void setAnly_rsp_file_name(String anly_rsp_file_name) {
        this.anly_rsp_file_name = anly_rsp_file_name;
    }

    public String getB1ah_req_file_name() {
        return this.b1ah_req_file_name;
    }

    public void setB1ah_req_file_name(String b1ah_req_file_name) {
        this.b1ah_req_file_name = b1ah_req_file_name;
    }

    public String getB1ah_rsp_file_name() {
        return this.b1ah_rsp_file_name;
    }

    public void setB1ah_rsp_file_name(String b1ah_rsp_file_name) {
        this.b1ah_rsp_file_name = b1ah_rsp_file_name;
    }

    public String getB1s_req_file_name() {
        return this.b1s_req_file_name;
    }

    public void setB1s_req_file_name(String b1s_req_file_name) {
        this.b1s_req_file_name = b1s_req_file_name;
    }

    public String getB1s_rsp_file_name() {
        return this.b1s_rsp_file_name;
    }

    public void setB1s_rsp_file_name(String b1s_rsp_file_name) {
        this.b1s_rsp_file_name = b1s_rsp_file_name;
    }

    public String getCrpt_req_file_name() {
        return this.crpt_req_file_name;
    }

    public void setCrpt_req_file_name(String crpt_req_file_name) {
        this.crpt_req_file_name = crpt_req_file_name;
    }

    public String getCrpt_rsp_file_name() {
        return this.crpt_rsp_file_name;
    }

    public void setCrpt_rsp_file_name(String crpt_rsp_file_name) {
        this.crpt_rsp_file_name = crpt_rsp_file_name;
    }

    public String getExtn_req_file_name() {
        return this.extn_req_file_name;
    }

    public void setExtn_req_file_name(String extn_req_file_name) {
        this.extn_req_file_name = extn_req_file_name;
    }

    public String getExtn_rsp_file_name() {
        return this.extn_rsp_file_name;
    }

    public void setExtn_rsp_file_name(String extn_rsp_file_name) {
        this.extn_rsp_file_name = extn_rsp_file_name;
    }

    public String getSvcl_req_file_name() {
        return this.svcl_req_file_name;
    }

    public void setSvcl_req_file_name(String svcl_req_file_name) {
        this.svcl_req_file_name = svcl_req_file_name;
    }

    public String getSvcl_rsp_file_name() {
        return this.svcl_rsp_file_name;
    }

    public void setSvcl_rsp_file_name(String svcl_rsp_file_name) {
        this.svcl_rsp_file_name = svcl_rsp_file_name;
    }

    public String getTcli_req_file_name() {
        return this.tcli_req_file_name;
    }

    public void setTcli_req_file_name(String tcli_req_file_name) {
        this.tcli_req_file_name = tcli_req_file_name;
    }

    public String getTcli_rsp_file_name() {
        return this.tcli_rsp_file_name;
    }

    public void setTcli_rsp_file_name(String tcli_rsp_file_name) {
        this.tcli_rsp_file_name = tcli_rsp_file_name;
    }

    public String getTcli_ui_req_file_name() {
        return this.tcli_ui_req_file_name;
    }

    public void setTcli_ui_req_file_name(String tcli_ui_req_file_name) {
        this.tcli_ui_req_file_name = tcli_ui_req_file_name;
    }

    public String getTcli_ui_rsp_file_name() {
        return this.tcli_ui_rsp_file_name;
    }

    public void setTcli_ui_rsp_file_name(String tcli_ui_rsp_file_name) {
        this.tcli_ui_rsp_file_name = tcli_ui_rsp_file_name;
    }
}

