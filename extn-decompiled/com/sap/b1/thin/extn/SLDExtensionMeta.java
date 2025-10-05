/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 */
package com.sap.b1.thin.extn;

public class SLDExtensionMeta {
    private Integer ID;
    private String Name;
    private String Version;
    private String Vendor;
    private String Modules;
    private String FioriApps;
    private String Component;
    private String LastUpdated;

    public Integer getID() {
        return this.ID;
    }

    public void setID(Integer iD) {
        this.ID = iD;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getVersion() {
        return this.Version;
    }

    public void setVersion(String version) {
        this.Version = version;
    }

    public String getFioriApps() {
        return this.FioriApps;
    }

    public void setFioriApps(String fioriApps) {
        this.FioriApps = fioriApps;
    }

    public String getModules() {
        return this.Modules;
    }

    public void setModules(String modules) {
        this.Modules = modules;
    }

    public String getComponent() {
        return this.Component;
    }

    public void setComponent(String component) {
        this.Component = component;
    }

    public String getVendor() {
        return this.Vendor;
    }

    public void setVendor(String vendor) {
        this.Vendor = vendor;
    }

    public String getLastUpdated() {
        return this.LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.LastUpdated = lastUpdated;
    }
}

