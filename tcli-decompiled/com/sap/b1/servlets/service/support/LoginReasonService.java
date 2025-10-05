/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.support.LoginReason
 *  com.sap.b1.servlets.service.support.LoginReasonService
 *  com.sap.b1.tcli.resources.ResourceBundleMessageUtil
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.MessageSource
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.support;

import com.sap.b1.servlets.service.support.LoginReason;
import com.sap.b1.tcli.resources.ResourceBundleMessageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginReasonService {
    private final List<String> LOGIN_REASON_IDS = Arrays.asList("c_LogReason_v_F", "c_LogReason_v_J", "c_LogReason_v_K", "c_LogReason_v_H");
    @Autowired
    ResourceBundleMessageUtil resourceBundle;

    @RequestMapping(path={"/loginreason/get.svc"})
    @ResponseBody
    public List<LoginReason> getLoginReason() {
        ArrayList<LoginReason> reasons = new ArrayList<LoginReason>();
        String objName = "OSUL";
        this.loadLoginReasonIds(reasons);
        this.translate(reasons, objName);
        return reasons;
    }

    private void loadLoginReasonIds(List<LoginReason> reasons) {
        List reasonIds = this.LOGIN_REASON_IDS;
        for (String id : reasonIds) {
            LoginReason loginReason = new LoginReason();
            loginReason.setId(id);
            reasons.add(loginReason);
        }
    }

    private void translate(List<LoginReason> reasons, String objName) {
        String url = String.format("table/%s.table", objName);
        MessageSource ms = this.resourceBundle.getMessageSource(url);
        Locale locale = LocaleContextHolder.getLocale();
        reasons.forEach(v -> v.setDesc(ms.getMessage(v.getId(), null, v.getDesc(), locale)));
    }
}

