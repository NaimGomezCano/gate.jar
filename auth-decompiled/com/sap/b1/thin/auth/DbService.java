/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.microsoft.sqlserver.jdbc.SQLServerDriver
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.thin.auth.DbService
 *  com.sap.db.jdbc.Driver
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.thin.auth;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.db.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class DbService {
    @Autowired
    DataSource dataSource;
    @Autowired
    private DBTypeConfig dbTypeConfig;
    private Integer dbTimezoneSecOffset = null;

    public Integer getLanguageCode(String companyDB, String userName) throws SQLException {
        Integer languageCode;
        block51: {
            languageCode = null;
            try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
                ResultSet rs;
                String tablePrefix = this.getTablePrefix(conn, companyDB);
                String sql = "select " + this.doubleQuote("Language") + ", " + this.doubleQuote("DfltsGroup") + " from " + tablePrefix + "OUSR where " + this.doubleQuote("USER_CODE") + " = ? ";
                String dflts = null;
                try (PreparedStatement stmt = conn.prepareStatement(sql);){
                    stmt.setString(1, userName);
                    rs = stmt.executeQuery();
                    try {
                        if (rs.next()) {
                            languageCode = rs.getInt("Language");
                            if (rs.wasNull()) {
                                languageCode = null;
                            }
                            dflts = rs.getString("DfltsGroup");
                        }
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                }
                if (languageCode == null && dflts != null) {
                    sql = "select " + this.doubleQuote("Language") + " from " + tablePrefix + "OUDG where " + this.doubleQuote("Code") + " = ? ";
                    stmt = conn.prepareStatement(sql);
                    try {
                        stmt.setString(1, dflts);
                        rs = stmt.executeQuery();
                        try {
                            if (rs.next()) {
                                languageCode = rs.getInt("Language");
                                if (rs.wasNull()) {
                                    languageCode = null;
                                }
                            }
                        }
                        finally {
                            if (rs != null) {
                                rs.close();
                            }
                        }
                    }
                    finally {
                        if (stmt != null) {
                            stmt.close();
                        }
                    }
                }
                if (languageCode != null) break block51;
                sql = "select " + this.doubleQuote("Language") + " from " + tablePrefix + "CINF";
                stmt = conn.prepareStatement(sql);
                try {
                    rs = stmt.executeQuery();
                    try {
                        if (rs.next()) {
                            languageCode = rs.getInt("Language");
                            if (rs.wasNull()) {
                                languageCode = null;
                            }
                        }
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                }
                finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            }
        }
        return languageCode;
    }

    public Integer getSystemLanguage(String companyDB, Integer languageCode) throws SQLException {
        Integer systemLanguage = null;
        try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
            String tablePrefix = this.getTablePrefix(conn, companyDB);
            String sql = "select " + this.doubleQuote("SysLang") + " from " + tablePrefix + "OLNG where " + this.doubleQuote("Code") + " = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setInt(1, languageCode);
                try (ResultSet rs = stmt.executeQuery();){
                    if (rs.next()) {
                        systemLanguage = rs.getInt("SysLang");
                        if (rs.wasNull()) {
                            systemLanguage = null;
                        }
                    }
                }
            }
        }
        return systemLanguage;
    }

    private String getTablePrefix(Connection conn, String companyDB) throws SQLException {
        String tablePrefix;
        java.sql.Driver driver = DriverManager.getDriver(conn.getMetaData().getURL());
        String schemaName = this.doubleQuote(companyDB);
        if (driver.getClass() == SQLServerDriver.class) {
            tablePrefix = schemaName + ".dbo.";
        } else if (driver.getClass() == Driver.class) {
            tablePrefix = schemaName + ".";
        } else {
            throw new RuntimeException();
        }
        return tablePrefix;
    }

    private String doubleQuote(String s) {
        return "\"" + StringUtils.replace((String)s, (String)"\"", (String)"\"\"") + "\"";
    }

    public Map<String, Object> getSsrUploadInfo(String companyDB) throws SQLException {
        HashMap<String, Object> ssrUploadInfoMap = new HashMap<String, Object>();
        try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
            String tablePrefix = this.getTablePrefix(conn, companyDB);
            String sql = "select " + this.doubleQuote("LastSsrTim") + ", " + this.doubleQuote("LastSsrDat") + ", " + this.doubleQuote("LastSsrHsh") + " from " + tablePrefix + "CINF";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery();){
                if (rs.next()) {
                    ssrUploadInfoMap.put("LastSsrUploadDateTimestamp", rs.getTimestamp("LastSsrDat"));
                    ssrUploadInfoMap.put("LastSsrHsh", rs.getString("LastSsrHsh"));
                    ssrUploadInfoMap.put("LastSsrUploadTime", rs.getLong("LastSsrTim"));
                }
            }
        }
        return ssrUploadInfoMap;
    }

    public String getSupportUsr(String companyDB, String userCode) throws SQLException {
        try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
            String tablePrefix = this.getTablePrefix(conn, companyDB);
            String sql = "select " + this.doubleQuote("SupportUsr") + " from " + tablePrefix + "OUSR where " + this.doubleQuote("USER_CODE") + " = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, userCode);
                try (ResultSet rs = stmt.executeQuery();){
                    if (rs.next()) {
                        String string = rs.getString("SupportUsr");
                        return string;
                    }
                }
            }
        }
        return null;
    }

    public Integer getUserId(String companyDB, String userName) throws SQLException {
        Integer userId = null;
        try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
            String tablePrefix = this.getTablePrefix(conn, companyDB);
            String sql = "select " + this.doubleQuote("USERID") + " from " + tablePrefix + "OUSR where " + this.doubleQuote("USER_CODE") + " = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, userName);
                try (ResultSet rs = stmt.executeQuery();){
                    if (rs.next()) {
                        userId = rs.getInt("USERID");
                        if (rs.wasNull()) {
                            userId = null;
                        }
                    }
                }
            }
        }
        return userId;
    }

    public boolean getB1UserIsLocked(String companyDB, String userName) throws SQLException {
        String locked = null;
        try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
            String tablePrefix = this.getTablePrefix(conn, companyDB);
            String sql = "select " + this.doubleQuote("Locked") + " from " + tablePrefix + "OUSR where " + this.doubleQuote("USER_CODE") + " = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(sql);){
                stmt.setString(1, userName);
                try (ResultSet rs = stmt.executeQuery();){
                    if (rs.next()) {
                        locked = rs.getString("Locked");
                        if (rs.wasNull()) {
                            locked = null;
                        }
                    }
                }
            }
        }
        return "Y".equals(locked);
    }

    public Integer getSessionId() throws SQLException {
        Integer sessionId = null;
        try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
            DBType dbType = this.dbTypeConfig.get();
            String sql = null;
            if (DBType.HANA == dbType) {
                sql = "SELECT connection_id AS SESSIONID FROM m_connections WHERE OWN = 'TRUE'";
            } else if (DBType.MSSQL == dbType) {
                sql = "select @@SPID AS SESSIONID";
            }
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql);){
                if (rs.next()) {
                    sessionId = rs.getInt("SESSIONID");
                    if (rs.wasNull()) {
                        sessionId = null;
                    }
                }
            }
        }
        return sessionId;
    }

    public int getTimeZoneOffset(String companyDatabaseName) throws SQLException {
        int timezoneSecOffset;
        block45: {
            timezoneSecOffset = 0;
            try (Connection conn = DataSourceUtils.doGetConnection((DataSource)this.dataSource);){
                Integer companyMinOffset = null;
                String tablePrefix = this.getTablePrefix(conn, companyDatabaseName);
                String sql = String.format("SELECT * FROM %sOTIZ ORDER BY \"Id\" DESC", tablePrefix);
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql);){
                    if (rs.next() && rs.getString("ChangeDate") != null) {
                        companyMinOffset = rs.getInt("offset");
                        String activeDst = rs.getString("ActiveDst");
                        if ("Y".equals(activeDst)) {
                            companyMinOffset = companyMinOffset + 60;
                        }
                    }
                }
                if (companyMinOffset != null) {
                    timezoneSecOffset = companyMinOffset * 60;
                    break block45;
                }
                if (this.dbTimezoneSecOffset == null) {
                    DBType dbType = this.dbTypeConfig.get();
                    if (DBType.HANA == dbType) {
                        sql = "SELECT * FROM M_HOST_INFORMATION WHERE KEY = 'timezone_offset'";
                    } else if (DBType.MSSQL == dbType) {
                        sql = "SELECT SYSDATETIMEOFFSET()";
                    }
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql);){
                        if (rs.next()) {
                            if (DBType.HANA == dbType) {
                                this.dbTimezoneSecOffset = rs.getInt("VALUE");
                            } else if (DBType.MSSQL == dbType) {
                                String result = rs.getString(1);
                                Pattern p = Pattern.compile(" ([+-]\\d{2}:\\d{2})");
                                Matcher matcher = p.matcher(result);
                                if (matcher.find()) {
                                    String timezone = matcher.group(1);
                                    String[] parts = timezone.substring(1).split(":");
                                    int hour = Integer.parseInt(parts[0]);
                                    int min = Integer.parseInt(parts[1]);
                                    this.dbTimezoneSecOffset = hour * 3600 + min * 60;
                                    if (timezone.charAt(0) == '-') {
                                        this.dbTimezoneSecOffset = this.dbTimezoneSecOffset * -1;
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.dbTimezoneSecOffset != null) {
                    timezoneSecOffset = this.dbTimezoneSecOffset;
                }
            }
        }
        return timezoneSecOffset;
    }

    public void resetDbTimezoneOffset() {
        this.dbTimezoneSecOffset = null;
    }
}

