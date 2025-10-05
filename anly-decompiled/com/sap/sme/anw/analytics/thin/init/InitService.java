/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.base.AnalyticsCoreException
 *  com.sap.sme.anw.analytics.core.connection.DataSourceHolder
 *  com.sap.sme.anw.analytics.core.container.IAnalyticsContainer
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.init.service.IInitService
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.sql.common.DbObject
 *  com.sap.sme.anw.analytics.sql.common.DbType
 *  com.sap.sme.anw.analytics.sql.common.SQLCommon
 *  com.sap.sme.anw.analytics.sql.datatype.SQLDataTypeName
 *  com.sap.sme.anw.analytics.sql.dbservice.DbServiceFactory
 *  com.sap.sme.anw.analytics.sql.dbservice.IDbService
 *  com.sap.sme.anw.analytics.thin.init.InitService
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.init;

import com.sap.sme.anw.analytics.core.base.AnalyticsCoreException;
import com.sap.sme.anw.analytics.core.connection.DataSourceHolder;
import com.sap.sme.anw.analytics.core.container.IAnalyticsContainer;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.init.service.IInitService;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.sql.common.DbObject;
import com.sap.sme.anw.analytics.sql.common.DbType;
import com.sap.sme.anw.analytics.sql.common.SQLCommon;
import com.sap.sme.anw.analytics.sql.datatype.SQLDataTypeName;
import com.sap.sme.anw.analytics.sql.dbservice.DbServiceFactory;
import com.sap.sme.anw.analytics.sql.dbservice.IDbService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

public class InitService
implements IInitService {
    private static final String TABLE_LIST_TIME_DATA = "TimeData";
    private static final Map<String, Boolean> map = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void init(IContextData contextData) {
        String key = StringUtils.join((Object[])new String[]{DataSourceHolder.getDataSourceId(), contextData.getAnalyticsSchema()}, (String)"\t");
        if (Boolean.TRUE.equals(map.get(key))) {
            return;
        }
        Class<?> clazz = this.getClass();
        synchronized (clazz) {
            if (Boolean.TRUE.equals(map.get(key))) {
                return;
            }
            IAnalyticsContainer analyticsContainer = (IAnalyticsContainer)ServiceFactory.getService(IAnalyticsContainer.class);
            try {
                this.dataSourceInitialize(contextData, analyticsContainer);
            }
            catch (SQLException e) {
                throw new AnalyticsCoreException(e.getMessage(), (Throwable)e);
            }
            map.put(key, Boolean.TRUE);
        }
    }

    private void dataSourceInitialize(IContextData contextData, IAnalyticsContainer analyticsContainer) throws SQLException {
        String databaseName = analyticsContainer.getContainerDatabaseName(contextData);
        String schemaName = analyticsContainer.getContainerSchemaName(contextData);
        IDbService dbService = DbServiceFactory.getDbService((DbType)contextData.getDbType());
        try (Connection conn = contextData.getConnection().generateConnection();){
            DbObject timeDbObject = new DbObject(databaseName, schemaName, "M_TIME_DIMENSION");
            dbService.ensureTimeDataExist(conn, timeDbObject, 1995, 2044);
            dbService.ensureSystemTimeDataExist(conn, 1995, 2044);
            DbObject listTimeDataDbObject = new DbObject(databaseName, schemaName, TABLE_LIST_TIME_DATA);
            this.ensureListTimeData(contextData, conn, listTimeDataDbObject, 1995, 2044);
            DbObject analyticsMetadataDbObject = new DbObject(databaseName, schemaName, "ANALYTICS_METADATA");
            this.ensureAnalyticsMetadataData(contextData, conn, analyticsMetadataDbObject);
            DbObject nullObjDbObject = new DbObject(databaseName, schemaName, "NULL_OBJ");
            this.ensureNullObjData(contextData, conn, nullObjDbObject);
        }
    }

    private void ensureListTimeData(IContextData contextData, Connection conn, DbObject dbObject, int yearFrom, int yearTo) throws SQLException {
        IDbService dbService = DbServiceFactory.getDbService((DbType)contextData.getDbType());
        String fullTableName = dbService.getFullObjectName(dbObject);
        String sql = "select count(*) - " + dbService.getDaysBetweenFunctionStr() + " from " + fullTableName + " where " + SQLCommon.doubleQuote((String)"DateYear") + " between ? and ? ";
        LocalDate startDate = LocalDate.of(yearFrom, 1, 1);
        LocalDate endDate = LocalDate.of(yearTo, 12, 31);
        String startDay = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String endDay = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String startYear = StringUtils.leftPad((String)String.valueOf(yearFrom), (int)4, (char)'0');
        String endYear = StringUtils.leftPad((String)String.valueOf(yearTo), (int)4, (char)'0');
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[]{startDay, endDay, startYear, endYear});
        boolean exist = false;
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setDate(1, Date.valueOf(startDay));
            stmt.setDate(2, Date.valueOf(endDay));
            stmt.setString(3, startYear);
            stmt.setString(4, endYear);
            try (ResultSet rs = stmt.executeQuery();){
                rs.next();
                if (rs.getInt(1) == 1) {
                    exist = true;
                }
            }
        }
        catch (SQLException e) {
            AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.DEBUG, (Throwable)e);
        }
        if (exist) {
            return;
        }
        String dateTimestampColumnType = SQLCommon.getDbSqlDataTypeName((DbType)contextData.getDbType(), (SQLDataTypeName)SQLDataTypeName.TIMESTAMP).name();
        dbService.ensureTableExist(conn, dbObject, "(DATETIMESTAMP " + dateTimestampColumnType + " NOT NULL, " + SQLCommon.doubleQuote((String)"DateYear") + " VARCHAR(4), " + SQLCommon.doubleQuote((String)"DateQuarter") + " VARCHAR(2), " + SQLCommon.doubleQuote((String)"DateMonth") + " VARCHAR(2), " + SQLCommon.doubleQuote((String)"DateWeek") + " VARCHAR(2), " + SQLCommon.doubleQuote((String)"DateYearAndQuarter") + " VARCHAR(6), " + SQLCommon.doubleQuote((String)"DateYearAndMonth") + " VARCHAR(6), " + SQLCommon.doubleQuote((String)"DateYearAndWeek") + " VARCHAR(6), PRIMARY KEY (DATETIMESTAMP))");
        sql = "delete from " + fullTableName + " where " + SQLCommon.doubleQuote((String)"DateYear") + " between ? and ? ";
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[]{startYear, endYear});
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, startYear);
            stmt.setString(2, endYear);
            stmt.execute();
        }
        LocalDate date = startDate;
        sql = "insert into " + fullTableName + " (DATETIMESTAMP, " + SQLCommon.doubleQuote((String)"DateYear") + ", " + SQLCommon.doubleQuote((String)"DateQuarter") + ", " + SQLCommon.doubleQuote((String)"DateMonth") + ", " + SQLCommon.doubleQuote((String)"DateWeek") + ", " + SQLCommon.doubleQuote((String)"DateYearAndQuarter") + ", " + SQLCommon.doubleQuote((String)"DateYearAndMonth") + ", " + SQLCommon.doubleQuote((String)"DateYearAndWeek") + ") values (?,?,?,?,?,?,?,?)";
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[0]);
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            while (date.isBefore(endDate) || date.isEqual(endDate)) {
                Date sqlDate = Date.valueOf(date);
                int year = date.getYear();
                int quarter = date.get(IsoFields.QUARTER_OF_YEAR);
                int month = date.getMonth().getValue();
                String yearStr = StringUtils.leftPad((String)String.valueOf(year), (int)4, (char)'0');
                String quarterStr = "Q" + String.valueOf(quarter);
                String monthStr = StringUtils.leftPad((String)String.valueOf(month), (int)2, (char)'0');
                int week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                String weekStr = StringUtils.leftPad((String)String.valueOf(week), (int)2, (char)'0');
                int weekYear = date.get(IsoFields.WEEK_BASED_YEAR);
                String weekYearStr = String.valueOf(weekYear);
                stmt.setDate(1, sqlDate);
                stmt.setString(2, yearStr);
                stmt.setString(3, quarterStr);
                stmt.setString(4, monthStr);
                stmt.setString(5, weekStr);
                stmt.setString(6, yearStr + quarterStr);
                stmt.setString(7, yearStr + monthStr);
                stmt.setString(8, weekYearStr + weekStr);
                stmt.addBatch();
                date = date.plusDays(1L);
            }
            stmt.executeBatch();
        }
    }

    private void ensureAnalyticsMetadataData(IContextData contextData, Connection conn, DbObject dbObject) throws SQLException {
        PreparedStatement stmt;
        IDbService dbService = DbServiceFactory.getDbService((DbType)contextData.getDbType());
        String fullTableName = dbService.getFullObjectName(dbObject);
        String sql = "select count(*) from " + fullTableName + " where ID between ? and ? ";
        int startId = 0;
        int endId = 72;
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[]{String.valueOf(startId), String.valueOf(endId)});
        boolean exist = false;
        try {
            stmt = conn.prepareStatement(sql);
            try {
                stmt.setInt(1, startId);
                stmt.setInt(2, endId);
                try (ResultSet rs = stmt.executeQuery();){
                    rs.next();
                    if (rs.getInt(1) == endId - startId + 1) {
                        exist = true;
                    }
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.DEBUG, (Throwable)e);
        }
        if (exist) {
            return;
        }
        dbService.ensureTableExist(conn, dbObject, "(ID INTEGER, TYPE INTEGER, TYPEDESC NVARCHAR(30), RELVALUE NVARCHAR(20), SHRTNAME NVARCHAR(30), DSPLNAME NVARCHAR(255), INTRMEMO NVARCHAR(255), RELTABLE NVARCHAR(30), GROUPTYPE NVARCHAR(30), PRIMARY KEY (ID))");
        sql = "delete from " + fullTableName + " where ID between ? and ? ";
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[]{String.valueOf(startId), String.valueOf(endId)});
        stmt = conn.prepareStatement(sql);
        try {
            stmt.setInt(1, startId);
            stmt.setInt(2, endId);
            stmt.execute();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        Object[][] data = new Object[][]{{0, 0, "Transaction Type", "-5", "IRUBalancingTransaction", "IRU Balancing Transaction", "Internal Reconciliation Upgrade Balancing Transaction after upgrade from below version 2007", "OJDT", null}, {1, 0, "Transaction Type", "-4", "ReconciliationAdjustment", "Reconciliation Adjustment", null, "OJDT", null}, {2, 0, "Transaction Type", "-3", "ClosingBalance", "Closing Balance", "Closing Balance", "OJDT", null}, {3, 0, "Transaction Type", "-2", "OpeningBalance", "Opening Balance", "Opening Balance", "OJDT", null}, {4, 0, "Transaction Type", "-1", "DocumentTypeNotAvailable", "N/A", "All Transactions", null, null}, {5, 0, "Transaction Type", "13", "ARInvoice", "A/R Invoice", "A/R Invoice", "OINV", "Invoice"}, {6, 0, "Transaction Type", "14", "ARCreditMemo", "A/R Credit Memo", "A/R Credit Memo", "ORIN", "Invoice"}, {7, 0, "Transaction Type", "15", "Delivery", "Delivery", "Delivery", "ODLN", "Delivery"}, {8, 0, "Transaction Type", "16", "Return", "Return", "Returns", "ORDN", "Delivery"}, {9, 0, "Transaction Type", "17", "SalesOrder", "Sales Order", "Orders", "ORDR", "Order"}, {10, 0, "Transaction Type", "18", "APInvoice", "A/P Invoice", "A/P Invoice", "OPCH", "Invoice"}, {11, 0, "Transaction Type", "19", "APCreditMemo", "A/P Credit Memo", "A/P Credit Memo", "ORPC", "Invoice"}, {12, 0, "Transaction Type", "20", "GoodsReceiptPO", "Goods Receipt PO", "Goods Receipt PO", "OPDN", "Delivery"}, {13, 0, "Transaction Type", "21", "GoodsReturn", "Goods Return", "Goods Return", "ORPD", "Delivery"}, {14, 0, "Transaction Type", "22", "PurchaseOrder", "Purchase Order", "Purchase Orders", "OPOR", "Order"}, {15, 0, "Transaction Type", "23", "SalesQuotation", "Sales Quotation", "Quotations", "OQUT", "Quotation"}, {16, 0, "Transaction Type", "24", "IncomingPayments", "Incoming Payments", "Incoming Payment", "ORCT", null}, {17, 0, "Transaction Type", "25", "Deposit", "Deposit", "Deposit", "ODPS", null}, {18, 0, "Transaction Type", "30", "JournalEntry", "Journal Entry", "Journal Entry", "OJDT", null}, {19, 0, "Transaction Type", "46", "OutgoingPayments", "Outgoing Payments", "Vendor Payment", "OVPM", null}, {20, 0, "Transaction Type", "57", "ChecksforPayment", "ChecksforPayment", "Checks for Payment", "OCHO", null}, {21, 0, "Transaction Type", "58", "InventoryList", "Inventory List", "Inventory List", "OINM", null}, {22, 0, "Transaction Type", "59", "GoodsReceipt", "Goods Receipt", "Goods Receipt", "OIGN", null}, {23, 0, "Transaction Type", "60", "GoodsIssue", "Goods Issue", "Goods Issue", "OIGE", null}, {24, 0, "Transaction Type", "67", "InventoryTransfer", "Inventory Transfer", "Inventory Transfers", "OWTR", null}, {25, 0, "Transaction Type", "68", "WorkOrder", "Work Order", "Work Instructions", "OWKO", null}, {26, 0, "Transaction Type", "69", "LandedCosts", "Landed Costs", "Landed Costs", "OIPF", null}, {27, 0, "Transaction Type", "76", "PostdatedDeposit", "Postdated Deposit", "Postdated Deposit", "ODPT", null}, {28, 0, "Transaction Type", "132", "CorrectionInvoice", "Correction Invoice", "Correction Invoice", "OCIN", null}, {29, 0, "Transaction Type", "162", "InventoryRevaluation", "Inventory Revaluation", "Inventory Valuation", "OMRV", null}, {30, 0, "Transaction Type", "163", "APCorrectionInvoice", "A/P Correction Invoice", "A/P Correction Invoice", "OCPI", "Invoice"}, {31, 0, "Transaction Type", "164", "APCorrectionInvoiceReversal", "A/P Correction Invoice Reversal", "A/P Correction Invoice Reversal", "OCPV", "Invoice"}, {32, 0, "Transaction Type", "165", "ARCorrectionInvoice", "A/R Correction Invoice", "A/R Correction Invoice", "OCSI", "Invoice"}, {33, 0, "Transaction Type", "166", "ARCorrectionInvoiceReversal", "A/R Correction Invoice Reversal", "A/R Correction Invoice Reversal", "OCSV", "Invoice"}, {34, 0, "Transaction Type", "182", "BoETransaction", "BoE Transaction", "BoE Transaction", null, null}, {35, 0, "Transaction Type", "202", "ProductionOrder", "Production Order", "Production Order", "OWOR", null}, {36, 0, "Transaction Type", "203", "ARDownPaymentInvoice", "A/R Down Payment Invoice", "A/R Down Payment", "ODPI", null}, {37, 0, "Transaction Type", "204", "APDownPaymentInvoice", "A/P Down Payment Invoice", "A/P Down Payment", "ODPO", null}, {38, 0, "Transaction Type", "280", "ARTaxInvoice", "A/R Tax Invoice", "A/R Tax Invoice", null, null}, {39, 0, "Transaction Type", "281", "APTaxInvoice", "A/R Tax Invoice", "A/P Tax Invoice", null, null}, {40, 0, "Transaction Type", "321", "InternalReconciliation", "Internal Reconciliation", "Internal Reconciliation", "OITR", null}, {41, 0, "Transaction Type", "10000046", "DataArchive", "Data Archive", "Data Archive", null, null}, {42, 0, "Transaction Type", "10000071", "InventoryPosting", "Inventory Posting", "Inventory Posting", null, null}, {43, 0, "Transaction Type", "10000079", "TDSAdjustment", "TDS Adjustment", "TDS Adjustment", null, null}, {44, 0, "Transaction Type", "140000008", "TaxPayment", "Tax Payment", "Tax Payment", null, null}, {45, 0, "Transaction Type", "140000009", "OutgoingExciseInvoice", "Outgoing Excise Invoice", "Outgoing Excise Invoice", "OOEI", null}, {46, 0, "Transaction Type", "140000010", "IncomingExciseInvoice", "Incoming Excise Invoice", "Incoming Excise Invoice", "OIEI", null}, {47, 0, "Transaction Type", "310000001", "InventoryOpeningBalance", "Inventory Opening Balance", "Inventory Opening Balance", null, null}, {48, 0, "Transaction Type", "1470000049", "FixedAssetCapitalization", "Fixed Asset Capitalization", "Fixed Asset Capitalization", null, null}, {49, 0, "Transaction Type", "1470000060", "FixedAssetCapitaliz.CreditMemo", "Fixed Asset Capitalization Credit Memo", "Fixed Asset Capitalization Credit Memo", null, null}, {50, 0, "Transaction Type", "1470000075", "FixedAssetManualDepreciation", "Fixed Asset Manual Depreciation", "Fixed Asset Manual Depreciation", null, null}, {51, 0, "Transaction Type", "1470000085", "FixedAssetRevaluation", "Fixed Asset Revaluation", "Fixed Asset Revaluation", null, null}, {52, 0, "Transaction Type", "1470000090", "FixedAssetTransfer", "Fixed Asset Transfer", "Fixed Asset Transfer", null, null}, {53, 0, "Transaction Type", "1470000094", "FixedAssetRetirement", "Fixed Asset Retirement", "Fixed Asset Retirement", null, null}, {54, 1, "Reconciliation Type", "0", "Manual", "Manual", null, null, null}, {55, 1, "Reconciliation Type", "1", "Automatic", "Automatic", null, null, null}, {56, 1, "Reconciliation Type", "2", "Semi-Automatic", "Semi-Automatic", null, null, null}, {57, 1, "Reconciliation Type", "3", "Payment", "Payment", null, null, null}, {58, 1, "Reconciliation Type", "4", "CreditMemo", "Credit Memo", null, null, null}, {59, 1, "Reconciliation Type", "5", "Reversal", "Reversal", null, null, null}, {60, 1, "Reconciliation Type", "6", "ZeroValue", "Zero Value", null, null, null}, {61, 1, "Reconciliation Type", "7", "Cancellation", "Cancellation", null, null, null}, {62, 1, "Reconciliation Type", "8", "BoE", "BoE", null, null, null}, {63, 1, "Reconciliation Type", "9", "Deposit", "Deposit", null, null, null}, {64, 1, "Reconciliation Type", "10", "BankStatementProcessing", "Bank Statement Processing", null, null, null}, {65, 1, "Reconciliation Type", "11", "PeriodClosing", "Period Closing", null, null, null}, {66, 1, "Reconciliation Type", "12", "CorrectionInvoice", "Correction Invoice", null, null, null}, {67, 1, "Reconciliation Type", "13", "Inventory/ExpenseAllocation", "Inventory/Expense Allocation", null, null, null}, {68, 1, "Reconciliation Type", "14", "WIP", "WIP", null, null, null}, {69, 1, "Reconciliation Type", "15", "DeferredTaxInterimAccount", "Deferred Tax Interim Account", null, null, null}, {70, 1, "Reconciliation Type", "16", "DownPaymentAllocation", "Down Payment Allocation", null, null, null}, {71, 1, "Reconciliation Type", "17", "Auto.ConversionDifference", "Auto. Conversion Difference", null, null, null}, {72, 0, "Transaction Type", "1470000071", "DepreciationRun", "Depreciation Run", null, null, null}};
        sql = "insert into " + fullTableName + " (ID, TYPE, TYPEDESC, RELVALUE, SHRTNAME, DSPLNAME, INTRMEMO, RELTABLE, GROUPTYPE) values (?,?,?,?,?,?,?,?,?)";
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[0]);
        try (PreparedStatement stmt2 = conn.prepareStatement(sql);){
            for (int i = 0; i < data.length; ++i) {
                Object[] line = data[i];
                stmt2.setInt(1, (Integer)line[0]);
                stmt2.setInt(2, (Integer)line[1]);
                stmt2.setString(3, (String)line[2]);
                stmt2.setString(4, (String)line[3]);
                stmt2.setString(5, (String)line[4]);
                stmt2.setString(6, (String)line[5]);
                stmt2.setString(7, (String)line[6]);
                stmt2.setString(8, (String)line[7]);
                stmt2.setString(9, (String)line[8]);
                stmt2.addBatch();
            }
            stmt2.executeBatch();
        }
    }

    private void ensureNullObjData(IContextData contextData, Connection conn, DbObject dbObject) throws SQLException {
        IDbService dbService = DbServiceFactory.getDbService((DbType)contextData.getDbType());
        String fullTableName = dbService.getFullObjectName(dbObject);
        String sql = "select count(*) from " + fullTableName;
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[0]);
        boolean exist = false;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();){
            rs.next();
            if (rs.getInt(1) == 1) {
                exist = true;
            }
        }
        catch (SQLException e) {
            AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.DEBUG, (Throwable)e);
        }
        if (exist) {
            return;
        }
        String timestampColumnType = SQLCommon.getDbSqlDataTypeName((DbType)contextData.getDbType(), (SQLDataTypeName)SQLDataTypeName.TIMESTAMP).name();
        LocalDate minDate = LocalDate.of(1753, 1, 1);
        dbService.ensureTableExist(conn, dbObject, "(NULL_ID INTEGER, NULL_STR NVARCHAR(10), NULL_CHAR_Y NVARCHAR(1), NULL_CHAR_N NVARCHAR(1), NULL_DATE DATE, NULL_DECIMAL DECIMAL(21, 6), NULL_INTEGER INTEGER, NULL_TIMESTAMP " + timestampColumnType + ")");
        sql = "delete from " + fullTableName;
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[0]);
        try (PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.execute();
        }
        sql = "insert into " + fullTableName + " (NULL_ID, NULL_STR, NULL_CHAR_Y, NULL_CHAR_N, NULL_DATE, NULL_DECIMAL, NULL_INTEGER, NULL_TIMESTAMP) values (?,?,?,?,?,?,?,?)";
        AnalyticsLog.logSQL(this.getClass(), (String)sql, (String[])new String[0]);
        stmt = conn.prepareStatement(sql);
        try {
            stmt.setInt(1, -9999);
            stmt.setString(2, "-NULL-");
            stmt.setString(3, "Y");
            stmt.setString(4, "N");
            stmt.setDate(5, Date.valueOf(minDate));
            stmt.setBigDecimal(6, BigDecimal.ZERO);
            stmt.setInt(7, 0);
            stmt.setDate(8, Date.valueOf(minDate));
            stmt.execute();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}

