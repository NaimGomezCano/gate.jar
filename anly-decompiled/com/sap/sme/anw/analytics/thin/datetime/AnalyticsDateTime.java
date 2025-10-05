/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.datetime.impl.CoreAnalyticsDateTime
 *  com.sap.sme.anw.analytics.sql.datatype.SQLDataType
 *  com.sap.sme.anw.analytics.sql.datatype.SQLDataTypeName
 *  com.sap.sme.anw.analytics.sql.exp.ISQLExp
 *  com.sap.sme.anw.analytics.sql.exp.SQLCastExp
 *  com.sap.sme.anw.analytics.sql.exp.SQLColumnNameExp
 *  com.sap.sme.anw.analytics.sql.exp.SQLConcatExp
 *  com.sap.sme.anw.analytics.sql.exp.SQLToNvarcharExp
 *  com.sap.sme.anw.analytics.sql.exp.SQLValueExp
 *  com.sap.sme.anw.analytics.thin.datetime.AnalyticsDateTime
 */
package com.sap.sme.anw.analytics.thin.datetime;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.datetime.impl.CoreAnalyticsDateTime;
import com.sap.sme.anw.analytics.sql.datatype.SQLDataType;
import com.sap.sme.anw.analytics.sql.datatype.SQLDataTypeName;
import com.sap.sme.anw.analytics.sql.exp.ISQLExp;
import com.sap.sme.anw.analytics.sql.exp.SQLCastExp;
import com.sap.sme.anw.analytics.sql.exp.SQLColumnNameExp;
import com.sap.sme.anw.analytics.sql.exp.SQLConcatExp;
import com.sap.sme.anw.analytics.sql.exp.SQLToNvarcharExp;
import com.sap.sme.anw.analytics.sql.exp.SQLValueExp;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;

public class AnalyticsDateTime
extends CoreAnalyticsDateTime {
    private static final DateTimeFormatter yearQuarterFormatter = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('Q').appendValue(IsoFields.QUARTER_OF_YEAR, 1).toFormatter();

    public DateTimeFormatter getYearQuarterFormatter(IContextData contextData) {
        return yearQuarterFormatter;
    }

    public ISQLExp getYearQuarterColumnExp(IContextData contextData, boolean displayName) {
        SQLConcatExp concatExp = new SQLConcatExp();
        concatExp.addExp((ISQLExp)new SQLColumnNameExp("YEAR"));
        concatExp.addExp((ISQLExp)new SQLValueExp("Q"));
        concatExp.addExp((ISQLExp)new SQLCastExp((ISQLExp)new SQLColumnNameExp("QUARTER_INT"), new SQLDataType(SQLDataTypeName.NVARCHAR)));
        return concatExp;
    }

    public ISQLExp getYearQuarterFormattedColumnExp(IContextData contextData, boolean displayName, ISQLExp columnNameExp, String para) {
        if (para != null) {
            return this.getDefaultFormattedColumnExp(columnNameExp, para);
        }
        return new SQLToNvarcharExp(columnNameExp, (ISQLExp)new SQLValueExp("YYYY\"Q\"Q"));
    }
}

