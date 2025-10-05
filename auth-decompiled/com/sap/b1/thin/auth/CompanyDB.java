/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.thin.auth.CompanyDB
 */
package com.sap.b1.thin.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CompanyDB {
    public static final String[] fieldList = new String[]{"ID", "NAME", "COMPANYNAME", "LOCALIZATION", "VERSION"};
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String companyName;
    @JsonProperty
    private final String localization;
    @JsonProperty
    private final String version;

    private CompanyDB(String name, String companyName, String localization, String version) {
        this.name = name;
        this.companyName = companyName;
        this.localization = localization;
        this.version = version;
    }

    public static List<CompanyDB> fromRecordSet(ResultSet rs) throws SQLException {
        ArrayList<CompanyDB> dbs = new ArrayList<CompanyDB>();
        while (rs.next()) {
            dbs.add(new CompanyDB(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return dbs;
    }
}

