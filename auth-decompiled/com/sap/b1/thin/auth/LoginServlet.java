/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.JwtUtils
 *  com.sap.b1.thin.auth.AuthLoginRequest
 *  com.sap.b1.thin.auth.DbService
 *  com.sap.b1.thin.auth.LoginServlet
 *  com.sap.b1.thin.auth.feign.SvclAuthService
 *  com.sap.b1.thin.auth.feign.SvclAuthService$ServiceLayerAuthServiceLogin
 *  feign.Response
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Profile
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.auth;

import com.sap.b1.infra.share.web.JwtUtils;
import com.sap.b1.thin.auth.AuthLoginRequest;
import com.sap.b1.thin.auth.DbService;
import com.sap.b1.thin.auth.feign.SvclAuthService;
import feign.Response;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.security.PrivateKey;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile(value={"test"})
@RestController
public class LoginServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    @Autowired
    PrivateKey privateKey;
    @Autowired
    SvclAuthService login;
    @Autowired
    DbService dbService;

    @RequestMapping(path={"/login.svc"})
    protected String service(@RequestBody AuthLoginRequest request, HttpServletResponse response) throws IOException, SQLException {
        SvclAuthService.ServiceLayerAuthServiceLogin svcl = new SvclAuthService.ServiceLayerAuthServiceLogin();
        svcl.companyDB = request.getSchema();
        svcl.userName = request.getUsername();
        svcl.password = request.getPassword();
        Integer languageCode = this.dbService.getLanguageCode(svcl.companyDB, svcl.userName);
        if (languageCode != null && languageCode < 100000) {
            svcl.language = languageCode.toString();
        }
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Language", svcl.language);
        try (Response session = this.login.login(svcl, headerMap);){
            String data;
            int status = session.status();
            response.setStatus(status);
            if (HttpStatus.valueOf((int)status).is2xxSuccessful()) {
                Collection cookies = (Collection)session.headers().get("Set-Cookie");
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("schema", svcl.companyDB);
                map.put("cookies", cookies);
                map.put("licenses", "TRIAL");
                this.verifyNode(cookies);
                String token = JwtUtils.buildJwt((PrivateKey)this.privateKey, (TimeUnit)TimeUnit.HOURS, (long)24L, (String)svcl.userName, map);
                response.addHeader("Y-Authorization", token);
                String string = "Success";
                return string;
            }
            String string = data = IOUtils.toString((Reader)session.body().asReader());
            return string;
        }
    }

    private void verifyNode(Collection<String> cookies) {
        boolean found = false;
        for (String c : cookies) {
            if (!c.startsWith("ROUTEID")) continue;
            found = true;
        }
        if (!found) {
            logger.error("ROUTEID not exists");
        }
    }
}

