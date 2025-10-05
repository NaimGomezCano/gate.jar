/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.protocol.response.bo.BusinessException
 *  com.sap.b1.sdk.oidc.web.WebUtils
 *  com.sap.b1.service.ShortLinkService
 *  com.sap.b1.service.shorlink.Link
 *  com.sap.b1.service.shorlink.LinkRepo
 *  com.sap.b1.util.StringUtil
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.service;

import com.sap.b1.app.base.AppEnv;
import com.sap.b1.protocol.response.bo.BusinessException;
import com.sap.b1.sdk.oidc.web.WebUtils;
import com.sap.b1.service.shorlink.Link;
import com.sap.b1.service.shorlink.LinkRepo;
import com.sap.b1.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortLinkService {
    private static final long ONE_DAY = 86400L;
    private static final Logger logger = LoggerFactory.getLogger(ShortLinkService.class);
    @Autowired
    private LinkRepo linkRepo;
    @Autowired
    private AppEnv env;
    private volatile long lastExpiredDataCheckTime = 0L;

    @PostMapping(path={"/service/link"}, consumes={"application/json"})
    public Link create(@RequestBody Link link, HttpServletRequest request) throws UnsupportedEncodingException {
        if (link.getOriLink() == null) {
            return new Link();
        }
        this.validateInputLink(link, request);
        String id = this.createShortLink(link);
        return this.generateBridgeLink(id, link);
    }

    private void validateInputLink(Link link, HttpServletRequest request) {
        if (StringUtil.isEmpty((String)link.getOrigin()) || StringUtil.isEmpty((String)link.getOriLink())) {
            throw new BusinessException("missing parameters");
        }
        String requestOri = WebUtils.getURLWithoutContext((HttpServletRequest)request);
        if (!requestOri.equals(link.getOrigin())) {
            throw new BusinessException("invalid origin");
        }
        if (!link.getOriLink().startsWith(requestOri + "/")) {
            throw new BusinessException("invalid link url");
        }
        try {
            new URL(link.getOriLink()).toURI();
        }
        catch (MalformedURLException | URISyntaxException e) {
            logger.warn("invalid short link input", (Throwable)e);
            throw new BusinessException("invalid link url");
        }
    }

    private Link generateBridgeLink(String id, Link link) throws UnsupportedEncodingException {
        String shortLinkTarget = String.format("%s/tcli/service/link/%s?t=%s", link.getOrigin(), id, this.buildSignature());
        String bridgeLink = String.format("%s/webx/bridge.html?target=%s", link.getOrigin(), URLEncoder.encode(shortLinkTarget, StandardCharsets.UTF_8.name()));
        return new Link(link.getOrigin(), null, bridgeLink);
    }

    private String createShortLink(Link link) {
        String id = UUID.randomUUID().toString();
        this.linkRepo.store(id, link.getOrigin(), link.getOriLink(), this.env.getUserCode());
        return id;
    }

    @GetMapping(path={"/service/link/{id}"})
    public void redirect(@PathVariable String id, @RequestParam(name="t", required=false) String t, HttpServletResponse response) throws IOException {
        this.removeExpiredData();
        this.doFetchLink(id, t, response);
    }

    private void doFetchLink(String id, String t, HttpServletResponse response) throws IOException {
        if (!this.checkSignature(t)) {
            this.sendError("This link doesn't belong to current company, you may logon the wrong company.", 404, response);
            return;
        }
        String oriLink = this.linkRepo.load(id);
        if (oriLink == null) {
            this.sendError("Expired or invalid link.", 404, response);
            return;
        }
        response.setStatus(302);
        response.sendRedirect(oriLink);
    }

    private void sendError(String errorMsg, int status, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(status);
        if (errorMsg != null) {
            PrintWriter writer = response.getWriter();
            writer.println(errorMsg);
            writer.flush();
        }
    }

    private boolean checkSignature(String t) {
        if (t == null) {
            return true;
        }
        return this.buildSignature().equals(t);
    }

    private String buildSignature() {
        return UUID.nameUUIDFromBytes(this.env.getSchema().getBytes()).toString();
    }

    private void removeExpiredData() {
        long now = System.currentTimeMillis();
        if (now - this.lastExpiredDataCheckTime > 86400L) {
            try {
                this.linkRepo.removeExpiredData();
                this.lastExpiredDataCheckTime = now;
            }
            catch (Exception e) {
                logger.warn("remove expired short link data error", (Throwable)e);
            }
        }
    }
}

