/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  com.sap.b1.infra.meta.tile.Action
 *  com.sap.b1.infra.meta.tile.Tile
 *  com.sap.b1.thin.extn.CachedScpProxy
 *  com.sap.b1.thin.extn.ProxyResponse
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.apitemplate.FioriApp
 *  com.sap.b1.thin.extn.apitemplate.UserTile
 *  com.sap.b1.thin.extn.apiversion.ManifestVersion
 *  com.sap.b1.thin.extn.apiversion.PVersion1
 *  com.sap.b1.thin.extn.apiversion.PVersion1$ModuleInfo
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.extn.apiversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sap.b1.infra.meta.tile.Action;
import com.sap.b1.infra.meta.tile.Tile;
import com.sap.b1.thin.extn.CachedScpProxy;
import com.sap.b1.thin.extn.ProxyResponse;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.apitemplate.FioriApp;
import com.sap.b1.thin.extn.apitemplate.UserTile;
import com.sap.b1.thin.extn.apiversion.ManifestVersion;
import com.sap.b1.thin.extn.apiversion.PVersion1;
import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PVersion1 {
    private static final Logger logger = LoggerFactory.getLogger(PVersion1.class);
    private static final String web_client_extension_config = "WebClientExtension.json";
    public static final String static_resource_path = "/extn/ui-static/";

    public void toTileList(List<Tile> tileList, SLDExtensionMeta addon, boolean mock) {
        try {
            ArrayList<FioriApp> appList = new ArrayList<FioriApp>();
            ArrayList userTileList = new ArrayList();
            List modules = (List)new Gson().fromJson(addon.getModules(), new /* Unavailable Anonymous Inner Class!! */.getType());
            for (ModuleInfo module : modules) {
                if (module.type.toLowerCase().equals("fiori-app")) {
                    List apps = (List)new Gson().fromJson(addon.getFioriApps(), new /* Unavailable Anonymous Inner Class!! */.getType());
                    for (String app : apps) {
                        FioriApp fioriApp = new FioriApp();
                        fioriApp.setName("webclient-" + module.name + "-" + app);
                        fioriApp.setFolder(module.name + "/" + app);
                        appList.add(fioriApp);
                    }
                    continue;
                }
                if (module.type.toLowerCase().equals("url-mashup")) {
                    this.addUserTile(addon, module.name, tileList, mock);
                    continue;
                }
                if (!module.type.toLowerCase().equals("single-page-app")) continue;
                this.addSinglePageApp(addon, module.name, tileList, mock);
            }
            if (appList != null) {
                for (FioriApp app : appList) {
                    this.addFioriApp(addon, app, tileList);
                }
            }
        }
        catch (JsonSyntaxException e) {
            logger.error("Error occurred in parsing addon id= {} modules to tiles list.", (Object)addon.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void addFioriApp(SLDExtensionMeta addon, FioriApp app, List<Tile> tiles) {
        try {
            ProxyResponse fioriManifestProxy = CachedScpProxy.getInstance().proxyToCache(addon.getID().intValue(), app.folder + File.separator + "manifest.json");
            JsonObject firoiManifestJSON = (JsonObject)new GsonBuilder().create().fromJson(fioriManifestProxy.getBodyAsString(), JsonObject.class);
            ManifestVersion manifestVersion = ManifestVersion.V1;
            if (firoiManifestJSON.get("apiversion") != null) {
                try {
                    manifestVersion = ManifestVersion.valueOf((String)firoiManifestJSON.get("apiversion").getAsString());
                }
                catch (IllegalArgumentException e) {
                    logger.error("API version of manifest.json parse failed in addon {}", (Object)addon.getID());
                    logger.error(e.getMessage());
                }
            }
            String componentId = firoiManifestJSON.getAsJsonObject("sap.app").get("id").getAsString();
            ProxyResponse extConfigResponse = CachedScpProxy.getInstance().proxyToCache(addon.getID().intValue(), app.folder + File.separator + web_client_extension_config);
            JsonObject extConfigJSON = null;
            List appTiles = new ArrayList();
            if (extConfigResponse != null) {
                extConfigJSON = (JsonObject)new GsonBuilder().create().fromJson(extConfigResponse.getBodyAsString(), JsonObject.class);
                appTiles = (List)new Gson().fromJson((JsonElement)extConfigJSON.get("tiles").getAsJsonArray(), new /* Unavailable Anonymous Inner Class!! */.getType());
            }
            if (appTiles.size() > 0) {
                int index = 0;
                for (UserTile appTile : appTiles) {
                    String uuid;
                    Tile tile = new Tile();
                    tile.guid = uuid = this.revertToUUIDhexString(addon.getID().toString() + "-" + app.name + (String)(index == 0 ? "" : "-" + index));
                    tile.componentPath = addon.getID() + "/" + app.folder;
                    String string = tile.text = appTile.getText() == null ? app.name : appTile.getText();
                    if (appTile.getText() == null) {
                        tile.text = app.getName();
                    }
                    tile.subtitle = appTile.getSubtitle();
                    tile.icon = appTile.getIcon() == null ? "sap-icon://customize" : appTile.getIcon();
                    tile.action = new Action();
                    tile.action.bind = "url";
                    tile.action.target = "#" + app.name + (appTile.getUrl() == null ? "" : appTile.getUrl());
                    tile.TileId = uuid;
                    tile.componentId = componentId;
                    tile.size = appTile.getSize() != null && !appTile.getSize().isEmpty() ? appTile.getSize() : "1x1";
                    if (appTile.getImage() != null && !appTile.getImage().isEmpty()) {
                        tile.backgroundImage = appTile.getImage().trim().startsWith("http://") || appTile.getImage().trim().startsWith("https://") ? appTile.getImage() : static_resource_path + addon.getID().toString() + "/" + appTile.getFolder() + "/" + appTile.getImage();
                        tile.tileType = "IMG_LINK";
                    }
                    if (appTile.getDynamicContent() != null) {
                        tile.tileType = "Dynamic";
                        tile.dynamicContent = appTile.getDynamicContent();
                    }
                    tiles.add(tile);
                    ++index;
                }
            } else {
                logger.warn("Load default tile configuration since no extension configuration file found for module {}", (Object)app.getName());
                Tile tile = this.addDefaultTile(addon, app.getName());
                tile.action.target = "#" + app.name;
                tile.componentPath = addon.getID() + "/" + app.folder;
                tile.componentId = componentId;
                tiles.add(tile);
            }
        }
        catch (Exception e) {
            logger.error("An exception occurred when parse {}", (Object)app.getName());
            logger.error(e.getMessage());
        }
    }

    private void addUserTile(SLDExtensionMeta addon, String moduleName, List<Tile> tiles, boolean mock) {
        try {
            ProxyResponse extConfigResponse = CachedScpProxy.getInstance().proxyToCache(addon.getID().intValue(), moduleName + File.separator + web_client_extension_config);
            JsonObject extConfigJSON = null;
            List appTiles = new ArrayList();
            if (extConfigResponse != null) {
                extConfigJSON = (JsonObject)new GsonBuilder().create().fromJson(extConfigResponse.getBodyAsString(), JsonObject.class);
                appTiles = (List)new Gson().fromJson((JsonElement)extConfigJSON.get("tiles").getAsJsonArray(), new /* Unavailable Anonymous Inner Class!! */.getType());
            }
            if (appTiles.size() > 0) {
                String guid = addon.getID().toString() + moduleName + "-tile";
                int index = 1;
                for (UserTile appTile : appTiles) {
                    String uuid;
                    appTile.setName(moduleName);
                    appTile.setFolder(moduleName);
                    Tile tile = new Tile();
                    tile.guid = uuid = this.revertToUUIDhexString(guid + index);
                    tile.text = appTile.getText() == null ? moduleName + "-" + index : appTile.getText();
                    tile.subtitle = appTile.getSubtitle();
                    tile.action = new Action();
                    tile.action.bind = "url";
                    tile.componentId = addon.getID().toString() + "-" + appTile.getName();
                    tile.TileId = uuid;
                    tile.icon = appTile.getIcon() == null ? "sap-icon://customize" : appTile.getIcon();
                    tile.size = appTile.getSize() != null && !appTile.getSize().isEmpty() ? appTile.getSize() : "1x1";
                    if (appTile.getImage() != null && !appTile.getImage().isEmpty()) {
                        tile.backgroundImage = appTile.getImage().trim().startsWith("http://") || appTile.getImage().trim().startsWith("https://") ? appTile.getImage() : static_resource_path + addon.getID().toString() + "/" + appTile.getFolder() + "/" + appTile.getImage();
                        tile.tileType = "IMG_LINK";
                    }
                    if (appTile.getDynamicContent() != null) {
                        tile.tileType = "Dynamic";
                        tile.dynamicContent = appTile.getDynamicContent();
                    }
                    tile.linkMethod = appTile.getLinkmethod();
                    if (tile.linkMethod == null) {
                        tile.linkMethod = "NewWindow";
                    }
                    tile.action.target = tile.linkMethod.equals("NewWindow") || mock ? appTile.getUrl() : "#boEXTENSION-app?addonId=" + addon.getID().toString() + "&appFolder=" + URLEncoder.encode(appTile.getFolder(), "UTF-8") + "&index=" + index;
                    tiles.add(tile);
                    ++index;
                }
            } else {
                logger.warn("No tile configuration file found for module {}", (Object)moduleName);
            }
        }
        catch (JsonSyntaxException e) {
            logger.error("An exception occurred when parse {}", (Object)moduleName);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void addSinglePageApp(SLDExtensionMeta addon, String moduleName, List<Tile> tiles, boolean mock) {
        try {
            ProxyResponse extConfigResponse = CachedScpProxy.getInstance().proxyToCache(addon.getID().intValue(), moduleName + File.separator + web_client_extension_config);
            JsonObject extConfigJSON = null;
            List appTiles = new ArrayList();
            if (extConfigResponse != null) {
                extConfigJSON = (JsonObject)new GsonBuilder().create().fromJson(extConfigResponse.getBodyAsString(), JsonObject.class);
                appTiles = (List)new Gson().fromJson((JsonElement)extConfigJSON.get("tiles").getAsJsonArray(), new /* Unavailable Anonymous Inner Class!! */.getType());
            }
            if (appTiles.size() > 0) {
                int index = 1;
                for (UserTile appTile : appTiles) {
                    String uuid;
                    appTile.setName(moduleName);
                    appTile.setFolder(moduleName);
                    Tile tile = new Tile();
                    tile.guid = uuid = this.revertToUUIDhexString(addon.getID().toString() + moduleName + "-tile" + index);
                    tile.text = appTile.getText() == null ? moduleName + "-" + index : appTile.getText();
                    tile.subtitle = appTile.getSubtitle();
                    tile.icon = appTile.getIcon() == null ? "sap-icon://customize" : appTile.getIcon();
                    tile.action = new Action();
                    tile.action.bind = "url";
                    tile.size = appTile.getSize() != null && !appTile.getSize().isEmpty() ? appTile.getSize() : "1x1";
                    if (appTile.getImage() != null && !appTile.getImage().isEmpty()) {
                        tile.backgroundImage = appTile.getImage().trim().startsWith("http://") || appTile.getImage().trim().startsWith("https://") ? appTile.getImage() : static_resource_path + addon.getID().toString() + "/" + appTile.getFolder() + "/" + appTile.getImage();
                        tile.tileType = "IMG_LINK";
                    }
                    if (appTile.getDynamicContent() != null) {
                        tile.tileType = "Dynamic";
                        tile.dynamicContent = appTile.getDynamicContent();
                    }
                    tile.linkMethod = appTile.getLinkmethod();
                    if (tile.linkMethod == null) {
                        tile.linkMethod = "ExistingWindow";
                    }
                    tile.action.target = tile.linkMethod.equals("NewWindow") || mock ? static_resource_path + addon.getID().toString() + "/" + appTile.getFolder() + "/" + (appTile.getUrl() == null ? "index.html" : appTile.getUrl()) : "#boEXTENSION-app?addonId=" + addon.getID().toString() + "&appFolder=" + URLEncoder.encode(appTile.getFolder(), "UTF-8") + "&index=" + index;
                    tile.TileId = uuid;
                    tile.componentId = addon.getID().toString() + "-" + appTile.getName();
                    tiles.add(tile);
                    ++index;
                }
            } else {
                logger.warn("Load default tile configuration since no extension configuration file found for module {}", (Object)moduleName);
                Tile tile = this.addDefaultTile(addon, moduleName);
                tile.action.target = static_resource_path + addon.getID().toString() + "/" + moduleName + "/index.html";
                tile.linkMethod = "ExistingWindow";
                tiles.add(tile);
            }
        }
        catch (Exception e) {
            logger.error("An exception occurred when parse {}", (Object)moduleName);
            logger.error(e.getMessage());
        }
    }

    public void toWhiteList(List<String> whiteList, SLDExtensionMeta addon) {
        try {
            List modules = (List)new Gson().fromJson(addon.getModules(), new /* Unavailable Anonymous Inner Class!! */.getType());
            for (ModuleInfo module : modules) {
                if (!module.type.toLowerCase().equals("url-mashup")) continue;
                this.addWhiteListFromUserTile(addon, module.name, whiteList);
            }
        }
        catch (Exception e) {
            logger.error("Error occurred in parsing addon id= {} modules to tiles list.", (Object)addon.getID());
        }
    }

    private void addWhiteListFromUserTile(SLDExtensionMeta addon, String moduleName, List<String> whiteList) {
        try {
            ProxyResponse extConfigResponse = CachedScpProxy.getInstance().proxyToCache(addon.getID().intValue(), moduleName + File.separator + web_client_extension_config);
            JsonObject extConfigJSON = (JsonObject)new GsonBuilder().create().fromJson(extConfigResponse.getBodyAsString(), JsonObject.class);
            List appTiles = (List)new Gson().fromJson((JsonElement)extConfigJSON.get("tiles").getAsJsonArray(), new /* Unavailable Anonymous Inner Class!! */.getType());
            for (UserTile appTile : appTiles) {
                URL url = new URL(appTile.getUrl());
                if (url.getPort() == -1) {
                    whiteList.add(url.getProtocol() + "://" + url.getHost());
                    continue;
                }
                whiteList.add(url.getProtocol() + "://" + url.getHost() + ":" + url.getPort());
            }
        }
        catch (Exception e) {
            logger.error("An exception occurred in addWhiteListFromUserTile when parse {}", (Object)moduleName);
            logger.error(e.getMessage());
        }
    }

    public Tile addDefaultTile(SLDExtensionMeta addon, String moduleName) {
        String uuid;
        Tile tile = new Tile();
        tile.guid = uuid = this.revertToUUIDhexString(addon.getID().toString() + "-" + moduleName);
        tile.TileId = uuid;
        tile.text = moduleName;
        tile.action = new Action();
        tile.action.bind = "url";
        tile.componentId = addon.getID().toString() + "-" + moduleName;
        tile.icon = "sap-icon://customize";
        return tile;
    }

    private String revertToUUIDhexString(String id) {
        try {
            MessageDigest md5 = null;
            md5 = MessageDigest.getInstance("md5");
            byte[] bytes = md5.digest(id.getBytes("utf-8"));
            StringBuilder hexBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; ++i) {
                int a = bytes[i];
                if (a < 0) {
                    a += 256;
                }
                hexBuilder.append(Integer.toHexString(a / 16));
                hexBuilder.append(Integer.toHexString(a % 16));
            }
            String regex = "(.{8})(.{4})(.{4})(.{4})(.{12})";
            String hexString = hexBuilder.toString();
            hexString = hexString.replaceAll(regex, "$1-$2-$3-$4-$5");
            return hexString;
        }
        catch (Exception e) {
            logger.error("Failed to hash uuid :" + e.getMessage());
            return id;
        }
    }
}

