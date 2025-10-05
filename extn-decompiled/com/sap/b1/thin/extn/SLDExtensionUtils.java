/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.boot.sld.SldClient
 *  com.sap.b1.boot.sld.feign.entity.GetWebClientAddonParameter
 *  com.sap.b1.sdk.oidc.core.handler.OAuth2Handler
 *  com.sap.b1.sdk.oidc.core.handler.token.ClientToken
 *  com.sap.b1.thin.extn.CachedScpProxy
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.SLDExtensionUtils
 *  com.sap.b1.thin.extn.UIExtension
 *  com.sap.b1.thin.extn.apiversion.PVersion1$ModuleInfo
 *  feign.Response
 *  jakarta.servlet.http.HttpServletResponse
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.extn;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sap.b1.boot.Bootstrap;
import com.sap.b1.boot.sld.SldClient;
import com.sap.b1.boot.sld.feign.entity.GetWebClientAddonParameter;
import com.sap.b1.sdk.oidc.core.handler.OAuth2Handler;
import com.sap.b1.sdk.oidc.core.handler.token.ClientToken;
import com.sap.b1.thin.extn.CachedScpProxy;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.UIExtension;
import com.sap.b1.thin.extn.apiversion.PVersion1;
import feign.Response;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.CallSite;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Exception performing whole class analysis ignored.
 */
public class SLDExtensionUtils {
    private static final Logger logger = LoggerFactory.getLogger(SLDExtensionUtils.class);
    static final ConcurrentHashMap<Integer, String[]> idUrlMapping = new ConcurrentHashMap();

    public static SLDExtensionMeta[] retrieveDeployedExtensions() {
        ArrayList<SLDExtensionMeta> fullResultList;
        block18: {
            fullResultList = new ArrayList<SLDExtensionMeta>();
            Bootstrap bootstrap = Bootstrap.getInstance();
            SldClient sldClient = bootstrap.getSldClient();
            if (sldClient == null) {
                logger.error("Extn cannot connect to the SLD , please check sld configuration information!.");
                return fullResultList.toArray(new SLDExtensionMeta[0]);
            }
            ClientToken token = OAuth2Handler.getInstance().fetchClientCredential();
            HashMap<String, CallSite> headerMap = new HashMap<String, CallSite>();
            headerMap.put("Authorization", (CallSite)((Object)("Bearer " + token.getAccessToken())));
            String suId = Bootstrap.getInstance().getSldClient().getServiceUnitId();
            String commonId = sldClient.getSldService().getCommonId(suId, headerMap);
            try (Response response = sldClient.getSldService().getDeployedExtensionList(suId, commonId, headerMap);){
                String tempJson = "";
                try (Reader reader = response.body().asReader();){
                    tempJson = IOUtils.toString((Reader)reader);
                }
                catch (IOException e) {
                    logger.info("Failed to read json string from response: {}", (Throwable)e);
                }
                StringBuilder errors = new StringBuilder();
                if (response != null && response.status() < 400) {
                    SLDExtensionMeta[] extMetas;
                    JsonObject json = (JsonObject)new Gson().fromJson(tempJson, JsonObject.class);
                    for (SLDExtensionMeta meta : extMetas = SLDExtensionUtils.wrap((JsonArray)json.get("d").getAsJsonObject().get("results").getAsJsonArray(), (Boolean)true)) {
                        if (meta == null) continue;
                        fullResultList.add(meta);
                    }
                    logger.info("Retrieved {} DeployedExtensions", (Object)fullResultList.size());
                    fullResultList.toArray(new SLDExtensionMeta[0]);
                    break block18;
                }
                throw new RuntimeException(response.body().toString());
            }
        }
        return fullResultList.toArray(new SLDExtensionMeta[0]);
    }

    public static SLDExtensionMeta[] retrieveExtensionsbyUserPreference(String dbInstance, String companyDBName, String userCode) throws Exception {
        logger.debug("Call function SLDExtensionUtils::retrieveExtensions  dbInstance:{}  companyDBName:{} ", (Object)dbInstance, (Object)companyDBName);
        ArrayList<SLDExtensionMeta> result = new ArrayList<SLDExtensionMeta>();
        Bootstrap bootstrap = Bootstrap.getInstance();
        SldClient sldClient = bootstrap.getSldClient();
        if (sldClient == null) {
            logger.error("Extn cannot connect to the SLD , please check sld configuration information!.");
            return result.toArray(new SLDExtensionMeta[0]);
        }
        ClientToken token = OAuth2Handler.getInstance().fetchClientCredential();
        GetWebClientAddonParameter p = new GetWebClientAddonParameter();
        p.setDBInstance(dbInstance);
        p.setCompanyDBName(companyDBName);
        p.setUserCode(userCode);
        HashMap<String, CallSite> headerMap = new HashMap<String, CallSite>();
        headerMap.put("Authorization", (CallSite)((Object)("Bearer " + token.getAccessToken())));
        try (Response response = sldClient.getSldService().getWebClientAddonAssign(p, headerMap);){
            String tempJson = "";
            try (Reader reader = response.body().asReader();){
                tempJson = IOUtils.toString((Reader)reader);
            }
            catch (IOException e) {
                logger.info("Failed to read json string from response: {}", (Throwable)e);
            }
            StringBuilder errors = new StringBuilder();
            if (response != null && response.status() < 400) {
                SLDExtensionMeta[] extMetas;
                JsonObject json = (JsonObject)new Gson().fromJson(tempJson, JsonObject.class);
                for (SLDExtensionMeta meta : extMetas = SLDExtensionUtils.wrap((JsonArray)json.get("d").getAsJsonObject().get("results").getAsJsonArray(), (Boolean)false)) {
                    if (meta == null) continue;
                    result.add(meta);
                }
                logger.info("Retrieved {} AssignedExtensions", (Object)result.size());
                SLDExtensionMeta[] sLDExtensionMetaArray = result.toArray(new SLDExtensionMeta[0]);
                return sLDExtensionMetaArray;
            }
            throw new RuntimeException(response.body().toString());
        }
    }

    private static SLDExtensionMeta[] wrap(JsonArray array, Boolean expandFrom) {
        Iterator it = array.iterator();
        ArrayList<SLDExtensionMeta> metas = new ArrayList<SLDExtensionMeta>();
        while (it.hasNext()) {
            try {
                JsonObject obj = ((JsonElement)it.next()).getAsJsonObject();
                SLDExtensionMeta meta = new SLDExtensionMeta();
                meta.setID(Integer.valueOf(expandFrom == true ? obj.get("Extension").getAsJsonObject().get("ID").getAsInt() : obj.get("ID").getAsInt()));
                meta.setName(expandFrom == true ? obj.get("Extension").getAsJsonObject().get("Name").getAsString() : obj.get("Name").getAsString());
                meta.setModules(expandFrom == true ? obj.get("Extension").getAsJsonObject().get("Modules").getAsString() : obj.get("Modules").getAsString());
                meta.setVendor(expandFrom == true ? obj.get("Extension").getAsJsonObject().get("Vendor").getAsString() : obj.get("Vendor").getAsString());
                meta.setVersion(expandFrom == true ? obj.get("Extension").getAsJsonObject().get("Version").getAsString() : obj.get("Version").getAsString());
                JsonElement lastUpdatedElement = expandFrom == true ? obj.get("Extension").getAsJsonObject().get("LastUpdated") : obj.get("LastUpdated");
                String lastUpdated = lastUpdatedElement == null ? "" : lastUpdatedElement.getAsString();
                meta.setLastUpdated(SLDExtensionUtils.formatUpdateTime((String)lastUpdated));
                metas.add(meta);
            }
            catch (Exception e) {
                logger.error("Failed to parse extension meta:{}", (Throwable)e);
            }
        }
        return metas.toArray(new SLDExtensionMeta[array.size()]);
    }

    public static UIExtension[] cacheUIExtensions(SLDExtensionMeta[] extMetas) throws IOException {
        ArrayList<UIExtension> extList = new ArrayList<UIExtension>();
        UIExtension[] exts = null;
        ArrayList<SLDExtensionMeta> uiExt = new ArrayList<SLDExtensionMeta>();
        if (extMetas != null) {
            for (SLDExtensionMeta meta : extMetas) {
                boolean hasUIext = false;
                List modules = (List)new Gson().fromJson(meta.getModules(), new /* Unavailable Anonymous Inner Class!! */.getType());
                for (PVersion1.ModuleInfo module : modules) {
                    if (!module.getType().toLowerCase().equals("ui-extension")) continue;
                    hasUIext = true;
                    UIExtension ext = new UIExtension();
                    ext.id = meta.getID();
                    ext.version = meta.getVersion();
                    ext.name = meta.getVendor() + "." + meta.getName() + "." + module.getName();
                    ext.path = "/extn/ui-static/" + meta.getID() + "/" + module.getName();
                    ext.lastUpdated = meta.getLastUpdated();
                    extList.add(ext);
                }
                if (!hasUIext) continue;
                uiExt.add(meta);
            }
            exts = extList.toArray(new UIExtension[extList.size()]);
            SLDExtensionMeta[] uiextMetas = uiExt.toArray(new SLDExtensionMeta[uiExt.size()]);
            CachedScpProxy.getInstance().manageAddonCache(null, uiextMetas, false);
        }
        return exts;
    }

    public static void writeResponse(String body, Integer statusCode, HttpServletResponse response) throws IOException {
        if (!statusCode.equals(200)) {
            // empty if block
        }
        response.setStatus(statusCode.intValue());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(body);
        response.getWriter().close();
    }

    public static String[] getUrlAndVersionByNamespace(Integer id) {
        if (idUrlMapping.containsKey(id)) {
            return (String[])idUrlMapping.get(id);
        }
        return null;
    }

    private static String formatUpdateTime(String lastUpdated) {
        Pattern p = Pattern.compile("Date\\(([0-9]+)\\)");
        Matcher m = p.matcher(lastUpdated);
        if (m.find()) {
            System.out.println(m.group(1));
            long milliseconds = Long.valueOf(m.group(1));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return sdf.format(calendar.getTime());
        }
        return "";
    }
}

