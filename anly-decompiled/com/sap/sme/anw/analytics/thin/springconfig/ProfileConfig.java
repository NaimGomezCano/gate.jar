/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig
 */
package com.sap.sme.anw.analytics.thin.springconfig;

public class ProfileConfig {
    private static final String DEV_PROFILE = "dev";
    private String activeProfile;

    public String getActiveProfile() {
        return this.activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    public boolean isDevProfile() {
        return DEV_PROFILE.equals(this.activeProfile);
    }
}

