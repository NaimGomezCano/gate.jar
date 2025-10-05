/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.boot.sld.SldClient
 *  com.sap.b1.boot.sld.feign.LicenseServerService
 *  com.sap.b1.boot.sld.feign.SldJsonService
 *  com.sap.b1.boot.sld.feign.entity.CompanyDatabase
 *  com.sap.b1.boot.sld.feign.entity.LicenseFileModulesInfo
 *  com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusParam
 *  com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusParamRequest
 *  com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusResult
 *  com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusResultReturn
 *  com.sap.b1.boot.sld.feign.entity.LicenseVersionResultReturn
 *  com.sap.b1.boot.sld.feign.entity.ODataResultReturn
 *  com.sap.b1.boot.sld.feign.entity.ODataReturn
 *  com.sap.b1.boot.sld.feign.entity.TrialLicenseReturn
 *  com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesParam
 *  com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesRequest
 *  com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesResult
 *  com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesResultReturn
 *  com.sap.b1.boot.sld.feign.entity.UserLicenseInfoParam
 *  com.sap.b1.boot.sld.feign.entity.UserLicenseInfoParamRequest
 *  com.sap.b1.boot.sld.feign.entity.UserLicenseInfoResult
 *  com.sap.b1.boot.sld.feign.entity.UserLicenseInfoResultReturn
 *  com.sap.b1.sdk.oidc.core.AppMetaProviderRegister
 *  com.sap.b1.sdk.oidc.core.dto.DatabaseInfo
 *  com.sap.b1.sdk.oidc.core.dto.sld.B1Service
 *  com.sap.b1.sdk.oidc.core.dto.sld.SLDResponse
 *  com.sap.b1.sdk.oidc.core.http.HttpAgentFactory
 *  com.sap.b1.sdk.oidc.core.service.SLDClient
 *  com.sap.b1.sdk.oidc.web.LoginPrincipal
 *  com.sap.b1.sdk.oidc.web.SecurityContexts
 *  com.sap.b1.sdk.oidc.web.WebUtils
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  com.sap.b1.thin.auth.DbService
 *  com.sap.b1.thin.auth.LanguageUtils
 *  com.sap.b1.thin.auth.LicenseException
 *  com.sap.b1.thin.auth.OIDCServiceLayerService
 *  com.sap.b1.thin.auth.OIDCSession
 *  com.sap.b1.thin.auth.OIDCUtils
 *  com.sap.b1.thin.auth.SupportService
 *  feign.Client
 *  feign.Feign
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.Response
 *  feign.Util
 *  feign.codec.Decoder
 *  feign.codec.Encoder
 *  feign.hc5.ApacheHttp5Client
 *  feign.jackson.JacksonDecoder
 *  feign.jackson.JacksonEncoder
 *  feign.slf4j.Slf4jLogger
 *  gen.str.LicenseError
 *  jakarta.servlet.http.HttpServletRequest
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.http.HttpStatus
 */
package com.sap.b1.thin.auth;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.boot.sld.SldClient;
import com.sap.b1.boot.sld.feign.LicenseServerService;
import com.sap.b1.boot.sld.feign.SldJsonService;
import com.sap.b1.boot.sld.feign.entity.CompanyDatabase;
import com.sap.b1.boot.sld.feign.entity.LicenseFileModulesInfo;
import com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusParam;
import com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusParamRequest;
import com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusResult;
import com.sap.b1.boot.sld.feign.entity.LicenseFileModulesStatusResultReturn;
import com.sap.b1.boot.sld.feign.entity.LicenseVersionResultReturn;
import com.sap.b1.boot.sld.feign.entity.ODataResultReturn;
import com.sap.b1.boot.sld.feign.entity.ODataReturn;
import com.sap.b1.boot.sld.feign.entity.TrialLicenseReturn;
import com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesParam;
import com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesRequest;
import com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesResult;
import com.sap.b1.boot.sld.feign.entity.UnExpiredUserLicensesResultReturn;
import com.sap.b1.boot.sld.feign.entity.UserLicenseInfoParam;
import com.sap.b1.boot.sld.feign.entity.UserLicenseInfoParamRequest;
import com.sap.b1.boot.sld.feign.entity.UserLicenseInfoResult;
import com.sap.b1.boot.sld.feign.entity.UserLicenseInfoResultReturn;
import com.sap.b1.sdk.oidc.core.AppMetaProviderRegister;
import com.sap.b1.sdk.oidc.core.dto.DatabaseInfo;
import com.sap.b1.sdk.oidc.core.dto.sld.B1Service;
import com.sap.b1.sdk.oidc.core.dto.sld.SLDResponse;
import com.sap.b1.sdk.oidc.core.http.HttpAgentFactory;
import com.sap.b1.sdk.oidc.core.service.SLDClient;
import com.sap.b1.sdk.oidc.web.LoginPrincipal;
import com.sap.b1.sdk.oidc.web.SecurityContexts;
import com.sap.b1.sdk.oidc.web.WebUtils;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import com.sap.b1.thin.auth.DbService;
import com.sap.b1.thin.auth.LanguageUtils;
import com.sap.b1.thin.auth.LicenseException;
import com.sap.b1.thin.auth.OIDCServiceLayerService;
import com.sap.b1.thin.auth.OIDCSession;
import com.sap.b1.thin.auth.SupportService;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hc5.ApacheHttp5Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import gen.str.LicenseError;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.invoke.CallSite;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OIDCUtils {
    public static final String OIDC_LOGOUT_PATH = "/auth/auth/logout";
    public static final String OIDC_AUTH_LOGOUT_PATH = "/auth/logout";
    public static final String SP_LOGIN_PATH = "/sp/login";
    public static final String REFRESH_TOKEN_PATH = "/refreshToken";
    private static final int LICENSE_SERVER_SERVICE_SUPPORT_MINIMUM_VERSION = 1000120;
    public static final String OIDC_OBJECT_SESSION = "session";
    public static final String PARAMETER_URL = "url";
    private static final String[] SUPPORTED_LICENSES = new String[]{"PROFESSIONAL", "FINANCIALS-LTD", "LOGISTICS-LTD", "CRM-LTD", "B1STARTER"};
    private static final int DEFAULT_LANGUAGE_CODE = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(OIDCUtils.class);
    public static final String LOGON_LICENSE_EXCEPTION = "logonLicenseException";
    private static final String LICENSE_NOT_FOUND_MSG = "The license installation number for the current user is not found. Please contact your administrator.";
    @Autowired
    CloseableHttpClient httpClient;
    @Autowired
    DbService dbService;
    @Autowired
    SupportService supportService;
    @Autowired
    MessageUtil messageUtil;

    private String getSldAddress() {
        return AppMetaProviderRegister.getAppMetaProvider().getAppConfig().getSld().getSldAddress();
    }

    public String getRootUrl(HttpServletRequest request) {
        String rootUrl = WebUtils.getURLWithContext((HttpServletRequest)request);
        if (rootUrl == null) {
            return null;
        }
        return this.formatUrl(rootUrl, true);
    }

    private String formatUrl(String s, boolean lowerCase) {
        if (s == null) {
            return null;
        }
        try {
            URL url = new URL(s);
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            Object str = protocol + "://" + host + (String)(port != -1 && port != url.getDefaultPort() ? ":" + String.valueOf(port) : "");
            if (lowerCase) {
                str = StringUtils.lowerCase((String)str);
            }
            return str;
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void retrieveInstallationNumber(OIDCSession oidcSession, HttpServletRequest httpRequest) {
        LOGGER.info("Begin to get installation number");
        SLDClient sldClient = HttpAgentFactory.createSLDClient();
        B1Service svl = sldClient.getService("B1ServiceLayers");
        String svclUrl = svl.getAccessUrl();
        try {
            ApacheHttp5Client apacheHttpClient = new ApacheHttp5Client((HttpClient)this.httpClient);
            OIDCServiceLayerService serviceLayerService = (OIDCServiceLayerService)Feign.builder().encoder((Encoder)new JacksonEncoder()).client((Client)apacheHttpClient).logger((feign.Logger)new Slf4jLogger(this.getClass())).logLevel(Logger.Level.FULL).target(OIDCServiceLayerService.class, svclUrl);
            LOGGER.info("Requesting to /b1s/v1/LicenseService_GetInstallationNumber");
            HashMap<String, Object> installationNumberHeaderMap = new HashMap<String, Object>();
            installationNumberHeaderMap.put("Authorization", "Bearer " + this.getAccessToken(httpRequest, oidcSession));
            installationNumberHeaderMap.put("CompanyId", oidcSession.getCompanyDatabase().getId());
            installationNumberHeaderMap.put("Language", oidcSession.getLanguage());
            try (Response response = serviceLayerService.requestInstallationNumber(installationNumberHeaderMap);){
                if (response.body() == null || response.status() != HttpStatus.OK.value()) {
                    throw new RuntimeException(LICENSE_NOT_FOUND_MSG);
                }
                Reader reader = response.body().asReader();
                String installationNumberResponse = Util.toString((Reader)reader);
                LOGGER.info("LicenseService_GetInstallationNumber Response: {}", (Object)installationNumberResponse);
                oidcSession.setInstallationNumber(installationNumberResponse);
                String cookies = this.fetchServiceLayerCookies(response);
                oidcSession.setSvclCookies(cookies);
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), (Throwable)e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String fetchServiceLayerCookies(Response response) {
        StringBuilder cookies = new StringBuilder();
        Map headers = response.headers();
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> {
                if ("set-cookie".equals(k)) {
                    v.stream().map(c -> c.split(";")[0].split("=")).forEach(c -> {
                        if (((String[])c).length == 2) {
                            cookies.append(c[0]).append("=").append(c[1]).append(";");
                        }
                    });
                }
            });
        }
        if (cookies.length() > 0) {
            return cookies.toString();
        }
        return null;
    }

    public void retrieveCompanyDatabaseInfo(OIDCSession oidcSession, String companyDatabaseName, HttpServletRequest request) throws Exception {
        List results;
        ODataResultReturn d;
        SLDClient sldAgent = HttpAgentFactory.createSLDClient();
        DatabaseInfo databaseInfo = sldAgent.getInstanceDatabaseInfo();
        oidcSession.setDbServerName(databaseInfo.getName());
        LOGGER.info("Retrieve Company Database Info");
        SldClient sldClient = new SldClient(this.getSldAddress());
        SldJsonService sldJsonService = sldClient.getSldJsonService();
        HashMap<String, CallSite> headerMap = new HashMap<String, CallSite>();
        headerMap.put("Authorization", (CallSite)((Object)("Bearer " + this.getAccessToken(request, oidcSession))));
        LOGGER.info("Requesting to /sld/sld0100.svc/CompanyDatabases?$format=json&$top=1&$filter=Name eq '{name}'");
        ODataReturn companyDatabaseReturn = sldJsonService.getCompanyDatabaseByName(companyDatabaseName, headerMap);
        CompanyDatabase companyDatabase = new CompanyDatabase();
        if (companyDatabaseReturn != null && (d = (ODataResultReturn)companyDatabaseReturn.getD()) != null && (results = d.getResults()) != null && !results.isEmpty()) {
            companyDatabase = (CompanyDatabase)results.get(0);
            oidcSession.setCompanyDatabase(companyDatabase);
        }
    }

    public String checkLicense(OIDCSession oidcSession, String sboUserCode, HttpServletRequest httpRequest) {
        boolean unExpiredUserLicenses;
        List modulesInfo;
        ArrayList licenseKeyTypes;
        boolean validTrialLicense;
        boolean trialLicense;
        String installationNumber;
        block25: {
            ApacheHttp5Client apacheHttpClient = new ApacheHttp5Client((HttpClient)this.httpClient);
            Bootstrap bootstrap = Bootstrap.getInstance();
            SldClient sldClientImpl = bootstrap.getSldClient();
            String licenseServerUrl = sldClientImpl.getLicenseServerUrl();
            LicenseServerService licenseServerService = (LicenseServerService)Feign.builder().encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).client((Client)apacheHttpClient).logger((feign.Logger)new Slf4jLogger(OIDCUtils.class)).logLevel(Logger.Level.FULL).target(LicenseServerService.class, licenseServerUrl);
            this.checkLicenseVersion(licenseServerService);
            if (this.supportService.isOPSupportUser(sboUserCode)) {
                if (!this.supportService.checkOPSupportUserActive(oidcSession)) {
                    throw new RuntimeException("To log in as a Support user, make sure that the Support user is already activated in the System Landscape Directory (SLD).");
                }
                LOGGER.info("OP RSP active support user");
                return "SUPPORT";
            }
            if (this.supportService.isODSupportUser(oidcSession)) {
                if (!this.supportService.checkODSupportUserActive(oidcSession)) {
                    throw new RuntimeException("Your current session as a partner support user has expired. Web Client for SAP Business One will close automatically.");
                }
                LOGGER.info("OD active support user");
                return "SUPPORT";
            }
            LOGGER.info("Check License");
            installationNumber = oidcSession.getInstallationNumber();
            LOGGER.info("Installation Number: {}", (Object)installationNumber);
            trialLicense = "-1".equals(installationNumber);
            LOGGER.info("Trial License: {}", (Object)trialLicense);
            validTrialLicense = false;
            licenseKeyTypes = new ArrayList();
            modulesInfo = null;
            unExpiredUserLicenses = false;
            try {
                String modules;
                SLDClient sldClient = HttpAgentFactory.createSLDClient();
                if (trialLicense) {
                    TrialLicenseReturn d;
                    String query;
                    SLDResponse trialLicenseReturn;
                    CompanyDatabase companyDatabase = oidcSession.getCompanyDatabase();
                    String companyDatabaseId = companyDatabase.getId();
                    if (companyDatabaseId != null && (trialLicenseReturn = (SLDResponse)sldClient.get(query = String.format("CompanyDatabases(%s)/IsTrialLicense", companyDatabase.getId()), new /* Unavailable Anonymous Inner Class!! */.getType())) != null && (d = (TrialLicenseReturn)trialLicenseReturn.getD()) != null) {
                        validTrialLicense = d.isTrialLicense();
                    }
                    LOGGER.info("Valid Trial License: {}", (Object)validTrialLicense);
                    break block25;
                }
                try {
                    UnExpiredUserLicensesResult unExpiredUserLicensesResult;
                    UnExpiredUserLicensesParam unExpiredUserLicensesParam = new UnExpiredUserLicensesParam();
                    unExpiredUserLicensesParam.setUser(sboUserCode);
                    unExpiredUserLicensesParam.setInstallNo(installationNumber);
                    UnExpiredUserLicensesRequest unExpiredUserLicensesRequest = new UnExpiredUserLicensesRequest();
                    unExpiredUserLicensesRequest.setUnExpiredUserLicensesParam(unExpiredUserLicensesParam);
                    LOGGER.info("Requesting to /license/GetUnExpiredUserLicenses");
                    HashMap<String, CallSite> unexpiredUserLicensesHeaderMap = new HashMap<String, CallSite>();
                    unexpiredUserLicensesHeaderMap.put("Authorization", (CallSite)((Object)("Bearer " + this.getAccessToken(httpRequest, oidcSession))));
                    UnExpiredUserLicensesResultReturn unExpiredUserLicensesResultReturn = licenseServerService.getUnExpiredUserLicenses(unExpiredUserLicensesRequest, unexpiredUserLicensesHeaderMap);
                    if (unExpiredUserLicensesResultReturn != null && (unExpiredUserLicensesResult = unExpiredUserLicensesResultReturn.getUnExpiredUserLicensesResult()) != null) {
                        modules = unExpiredUserLicensesResult.getModules();
                        this.parseLicenseModules(modules, installationNumber, licenseKeyTypes);
                    }
                    unExpiredUserLicenses = true;
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), (Throwable)e);
                }
                LOGGER.info("UnExpired User Licenses retrieved: {}", (Object)unExpiredUserLicenses);
                if (!unExpiredUserLicenses) {
                    LicenseFileModulesStatusResult licenseFileModulesStatusResult;
                    UserLicenseInfoResult userLicenseInfoResult;
                    UserLicenseInfoParam userLicenseInfoParam = new UserLicenseInfoParam();
                    userLicenseInfoParam.setUser(sboUserCode);
                    UserLicenseInfoParamRequest userLicenseInfoParamRequest = new UserLicenseInfoParamRequest();
                    userLicenseInfoParamRequest.setUserLicenseInfoParam(userLicenseInfoParam);
                    LOGGER.info("Requesting to /license/GetUserLicenseInfo");
                    HashMap<String, CallSite> licenseInfoHeaderMap = new HashMap<String, CallSite>();
                    licenseInfoHeaderMap.put("Authorization", (CallSite)((Object)("Bearer " + this.getAccessToken(httpRequest, oidcSession))));
                    UserLicenseInfoResultReturn userLicenseInfoResultReturn = licenseServerService.getUserLicenseInfo(userLicenseInfoParamRequest, licenseInfoHeaderMap);
                    if (userLicenseInfoResultReturn != null && (userLicenseInfoResult = userLicenseInfoResultReturn.getUserLicenseInfoResult()) != null) {
                        modules = userLicenseInfoResult.getModules();
                        this.parseLicenseModules(modules, installationNumber, licenseKeyTypes);
                    }
                    LicenseFileModulesStatusParam licenseFileModulesStatusParam = new LicenseFileModulesStatusParam();
                    licenseFileModulesStatusParam.setInstallNo(installationNumber);
                    LicenseFileModulesStatusParamRequest licenseFileModulesStatusParamRequest = new LicenseFileModulesStatusParamRequest();
                    licenseFileModulesStatusParamRequest.setLicenseFileModulesStatusParam(licenseFileModulesStatusParam);
                    LOGGER.info("Requesting to /license/GetLicenseFileModulesStatus");
                    HashMap<String, CallSite> licenseModelsStatusHeaderMap = new HashMap<String, CallSite>();
                    licenseModelsStatusHeaderMap.put("Authorization", (CallSite)((Object)("Bearer " + this.getAccessToken(httpRequest, oidcSession))));
                    LicenseFileModulesStatusResultReturn licenseFileModulesStatusResultReturn = licenseServerService.getLicenseFileModulesStatus(licenseFileModulesStatusParamRequest, licenseModelsStatusHeaderMap);
                    if (licenseFileModulesStatusResultReturn != null && (licenseFileModulesStatusResult = licenseFileModulesStatusResultReturn.getLicenseFileModulesStatusResult()) != null) {
                        modulesInfo = licenseFileModulesStatusResult.getModulesInfo();
                    }
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), (Throwable)e);
            }
        }
        String licenseString = "";
        LocaleContextHolder.setLocale((Locale)LanguageUtils.getLocale((OIDCSession)oidcSession));
        if (trialLicense) {
            if (!validTrialLicense) {
                LOGGER.error("Trial License expired.");
                throw new LicenseException(this.messageUtil.getMessage((MessageId)LicenseError.noLicenseAssigned, new Object[0]));
            }
            licenseString = "TRIAL";
        } else {
            LOGGER.info("Licenses: {}", (Object)StringUtils.join(licenseKeyTypes, (String)"\t"));
            HashSet assignedLicenses = new HashSet(licenseKeyTypes);
            assignedLicenses.retainAll(Arrays.asList(SUPPORTED_LICENSES));
            licenseString = StringUtils.join(assignedLicenses, (String)",");
            LOGGER.info("Valid Licenses: {}", (Object)StringUtils.join(assignedLicenses, (String)"\t"));
            if (assignedLicenses.isEmpty()) {
                LOGGER.error("Make sure you have a valid license assigned.");
                throw new LicenseException(this.messageUtil.getMessage((MessageId)LicenseError.noLicenseAssigned, new Object[0]));
            }
            if (unExpiredUserLicenses) {
                if (licenseKeyTypes.isEmpty()) {
                    LOGGER.error("No unexpired license assigned.");
                    throw new LicenseException(this.messageUtil.getMessage((MessageId)LicenseError.noLicenseAssigned, new Object[0]));
                }
            } else {
                if (licenseKeyTypes.isEmpty()) {
                    LOGGER.error("No license assigned.");
                    throw new RuntimeException(this.messageUtil.getMessage((MessageId)LicenseError.noLicenseAssigned, new Object[0]));
                }
                if (modulesInfo == null) {
                    LOGGER.error("Missing license status.");
                    throw new LicenseException(this.messageUtil.getMessage((MessageId)LicenseError.noLicenseAssigned, new Object[0]));
                }
                boolean expired = true;
                for (LicenseFileModulesInfo moduleInfo : modulesInfo) {
                    if (moduleInfo == null) continue;
                    String installNo = moduleInfo.getInstallNo();
                    String keyType = moduleInfo.getKeyType();
                    if (!StringUtils.equals((CharSequence)installNo, (CharSequence)installationNumber) || !assignedLicenses.contains(keyType) || moduleInfo.isExpired()) continue;
                    expired = false;
                    break;
                }
                if (expired) {
                    LOGGER.error("License expired.");
                    throw new LicenseException(this.messageUtil.getMessage((MessageId)LicenseError.noLicenseAssigned, new Object[0]));
                }
            }
        }
        LOGGER.info("Check License Succeeded");
        return licenseString;
    }

    private void parseLicenseModules(String modules, String installationNumber, List<String> licenseKeyTypes) throws ParserConfigurationException, SAXException, IOException {
        if (StringUtils.isBlank((CharSequence)modules)) {
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        try (InputStream is = IOUtils.toInputStream((String)modules, (Charset)StandardCharsets.UTF_8);){
            Document document = builder.parse(is);
            NodeList installNosNode = document.getElementsByTagName("InstallNo");
            if (installNosNode != null) {
                int installNosNodeCount = installNosNode.getLength();
                for (int i = 0; i < installNosNodeCount; ++i) {
                    Node installNoNode = installNosNode.item(i);
                    if (!StringUtils.equals((CharSequence)installNoNode.getTextContent(), (CharSequence)installationNumber)) continue;
                    Node moduleNode = installNoNode.getParentNode();
                    NodeList childNodes = moduleNode.getChildNodes();
                    int childNodesCount = childNodes.getLength();
                    for (int j = 0; j < childNodesCount; ++j) {
                        Node childNode = childNodes.item(j);
                        if (!"KeyType".equals(childNode.getNodeName())) continue;
                        licenseKeyTypes.add(childNode.getTextContent());
                    }
                }
            }
        }
    }

    public void setLanguage(OIDCSession oidcSession) {
        String companyDatabaseName = oidcSession.getCompanyDatabaseName();
        try {
            Integer systemLanguage;
            Integer languageCode = this.dbService.getLanguageCode(companyDatabaseName, oidcSession.getSboUserCode());
            if (languageCode == null) {
                languageCode = 3;
            }
            if ((systemLanguage = this.dbService.getSystemLanguage(companyDatabaseName, languageCode)) == null) {
                systemLanguage = 3;
            }
            String language = systemLanguage.toString();
            oidcSession.setLanguage(language);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), (Throwable)e);
        }
    }

    public void checkLocalization(OIDCSession oidcSession) {
        LOGGER.info("Check Localization");
        CompanyDatabase companyDatabase = oidcSession.getCompanyDatabase();
        String localization = companyDatabase.getLocalization();
        LOGGER.info("Company Database Localization: {}", (Object)localization);
        LOGGER.info("Check Localization Succeeded");
    }

    private void checkLicenseVersion(LicenseServerService licenseServerService) {
        LOGGER.info("Requesting to /license/GetVersion");
        LicenseVersionResultReturn licenseVersionResult = licenseServerService.getVersion();
        if (licenseVersionResult != null) {
            String licenseVersion = licenseVersionResult.getlicenseVersionResult().getVersion();
            LOGGER.info("License Version: {}", (Object)licenseVersion);
            int licenseVersionInteger = Integer.parseInt(licenseVersion.replace(".", ""));
            if (licenseVersionInteger < 1000120) {
                throw new RuntimeException(this.messageUtil.getMessage((MessageId)LicenseError.licenseServerVersionNotSupported, new Object[]{licenseVersion}));
            }
        }
    }

    public boolean isLogin(HttpServletRequest request) {
        return SecurityContexts.getLoginPrincipal((HttpServletRequest)request) != null;
    }

    public String getAccessToken(HttpServletRequest request, OIDCSession oidcSession) {
        String accessToken;
        try {
            LoginPrincipal loginPrincipal = SecurityContexts.getLoginPrincipal((HttpServletRequest)request);
            accessToken = loginPrincipal.getAccessToken().getAccessToken();
        }
        catch (Exception e) {
            accessToken = oidcSession.getAccessToken();
        }
        if (accessToken == null) {
            throw new RuntimeException("Fail to get access token from request or session");
        }
        return accessToken;
    }

    public boolean getB1UserIsLocked(String companyDB, String userName) throws RuntimeException {
        try {
            return this.dbService.getB1UserIsLocked(companyDB, userName);
        }
        catch (SQLException e) {
            throw new RuntimeException("Fail to get connection from DBService");
        }
    }
}

