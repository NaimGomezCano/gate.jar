/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.datetime.IAnalyticsDateTime
 *  com.sap.sme.anw.analytics.core.exception.QueryException
 *  com.sap.sme.anw.analytics.core.placeholder.AnalyticsPlaceholderUtils
 *  com.sap.sme.anw.analytics.core.placeholder.impl.BaseAnalyticsPlaceholder
 *  com.sap.sme.anw.analytics.core.placeholder.types.AnalyticsPlaceholderArg
 *  com.sap.sme.anw.analytics.core.placeholder.types.AnalyticsPlaceholderReplaceResult
 *  com.sap.sme.anw.analytics.core.query.fiscal.FiscalInfoUtils
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholder
 *  com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholder$1
 *  com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholderType
 *  com.sap.sme.anw.analytics.type.query.fiscal.QueryFiscalInfo
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.placeholder;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.datetime.IAnalyticsDateTime;
import com.sap.sme.anw.analytics.core.exception.QueryException;
import com.sap.sme.anw.analytics.core.placeholder.AnalyticsPlaceholderUtils;
import com.sap.sme.anw.analytics.core.placeholder.impl.BaseAnalyticsPlaceholder;
import com.sap.sme.anw.analytics.core.placeholder.types.AnalyticsPlaceholderArg;
import com.sap.sme.anw.analytics.core.placeholder.types.AnalyticsPlaceholderReplaceResult;
import com.sap.sme.anw.analytics.core.query.fiscal.FiscalInfoUtils;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholder;
import com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholderType;
import com.sap.sme.anw.analytics.type.query.fiscal.QueryFiscalInfo;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class ThinFilterPlaceholder
extends BaseAnalyticsPlaceholder {
    private static final String THIN_FILTER_PLACEHOLDER_PREFIX = "THIN_FILTER:";
    private static final String DATE_FORMULA_SIGN_POSITIVE = "+";
    private static final String DATE_FORMULA_SIGN_NEGATIVE = "-";
    private static final String DATE_FORMULA_GRANULARITY_TODAY = "TODAY";
    private static final String DATE_FORMULA_GRANULARITY_DAY = "DAY";
    private static final String DATE_FORMULA_GRANULARITY_WEEK = "WEEK";
    private static final String DATE_FORMULA_GRANULARITY_MONTH = "MONTH";
    private static final String DATE_FORMULA_GRANULARITY_QUARTER = "QUARTER";
    private static final String DATE_FORMULA_GRANULARITY_YEAR = "YEAR";
    private static final String DATE_FORMULA_GRANULARITY_POSTINGPERIOD = "POSTINGPERIOD";
    private static final String DATE_FORMULA_BEGIN = "BEGIN";
    private static final String DATE_FORMULA_END = "END";
    private static final Map<String, Integer> MONTH_VALUE_MAP;
    private static final Map<String, Integer> WEEK_VALUE_MAP;
    private static final String DATE_FORMULA_REG;
    private static final Pattern DATE_FORMULA_PATTERN;
    private static final DateTimeFormatter constantDateFormatter;

    public String getPrefix() {
        return THIN_FILTER_PLACEHOLDER_PREFIX;
    }

    public ThinFilterPlaceholderType getPlaceholderType(String str) {
        return Enum.valueOf(ThinFilterPlaceholderType.class, str);
    }

    protected AnalyticsPlaceholderReplaceResult getTodayRelatedValue(IContextData contextData, String str, AnalyticsPlaceholderArg analyticsPlaceholderArg) {
        ThinFilterPlaceholderType type = (ThinFilterPlaceholderType)analyticsPlaceholderArg.getPlaceholderType();
        List args = analyticsPlaceholderArg.getArgs();
        if (args.isEmpty()) {
            throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
        }
        String para = (String)args.get(0);
        switch (1.$SwitchMap$com$sap$sme$anw$analytics$thin$placeholder$ThinFilterPlaceholderType[type.ordinal()]) {
            case 1: {
                return this.parseDateFormula(contextData, str, para);
            }
        }
        throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
    }

    protected AnalyticsPlaceholderReplaceResult getTodayUnrelatedValue(IContextData contextData, String str, AnalyticsPlaceholderArg analyticsPlaceholderArg) {
        throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
    }

    /*
     * Unable to fully structure code
     */
    private AnalyticsPlaceholderReplaceResult parseDateFormula(IContextData contextData, String str, String para) {
        block72: {
            value = StringUtils.upperCase((String)para);
            groupMatcher = ThinFilterPlaceholder.DATE_FORMULA_PATTERN.matcher(value);
            d = this.getToday(contextData);
            found = false;
            try {
                while (groupMatcher.find()) {
                    block74: {
                        block73: {
                            g = groupMatcher.group();
                            if (StringUtils.isBlank((CharSequence)g)) continue;
                            sign = groupMatcher.group(1);
                            diff = groupMatcher.group(3);
                            granularity = groupMatcher.group(5);
                            granularityPara = groupMatcher.group(7);
                            comma = groupMatcher.group(9);
                            beginEnd = groupMatcher.group(11);
                            if (StringUtils.isEmpty((CharSequence)sign) && StringUtils.isEmpty((CharSequence)diff) && "TODAY".equals(granularity) && StringUtils.isEmpty((CharSequence)comma) && StringUtils.isEmpty((CharSequence)beginEnd)) {
                                found = true;
                                continue;
                            }
                            if (!StringUtils.isEmpty((CharSequence)sign) && !StringUtils.isEmpty((CharSequence)diff) && "DAY".equals(granularity) && StringUtils.isEmpty((CharSequence)comma) && StringUtils.isEmpty((CharSequence)beginEnd)) {
                                diffInt = Integer.parseInt(diff);
                                d = "-".equals(sign) != false ? d.minusDays(diffInt.intValue()) : d.plusDays(diffInt.intValue());
                                found = true;
                                continue;
                            }
                            if (StringUtils.isEmpty((CharSequence)granularity)) break block72;
                            fiscalInfo = null;
                            fiscalInfos = null;
                            currentFiscalInfoIndex = -1;
                            if ("POSTINGPERIOD".equals(granularity)) {
                                fiscalInfos = FiscalInfoUtils.getFiscalInfos((IContextData)contextData);
                                currentFiscalInfoIndex = FiscalInfoUtils.getCurrentFiscalInfoIndex((LocalDate)d, (List)fiscalInfos);
                                if (currentFiscalInfoIndex == -1) {
                                    return AnalyticsPlaceholderUtils.getNotReplacedResult((String)str);
                                }
                                fiscalInfo = (QueryFiscalInfo)fiscalInfos.get(currentFiscalInfoIndex);
                            }
                            if (StringUtils.isEmpty((CharSequence)sign)) break block73;
                            if (!StringUtils.isEmpty((CharSequence)diff)) ** GOTO lbl60
                            v = (Integer)ThinFilterPlaceholder.MONTH_VALUE_MAP.get(granularity);
                            if (v != null) {
                                currentMonth = d.getMonthValue();
                                month = v.intValue();
                                if ("-".equals(sign)) {
                                    if (currentMonth <= month) {
                                        d = d.minusYears(1L);
                                    }
                                } else if (currentMonth >= month) {
                                    d = d.plusYears(1L);
                                }
                                d = d.withMonth(month);
                            } else {
                                v = (Integer)ThinFilterPlaceholder.WEEK_VALUE_MAP.get(granularity);
                                if (v != null) {
                                    currentDay = d.getDayOfWeek().getValue();
                                    day = v.intValue();
                                    if ("-".equals(sign)) {
                                        if (currentDay <= day) {
                                            d = d.minusWeeks(1L);
                                        }
                                    } else if (currentDay >= day) {
                                        d = d.plusWeeks(1L);
                                    }
                                    d = d.with(WeekFields.ISO.dayOfWeek(), day);
                                } else {
                                    throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
lbl60:
                                    // 1 sources

                                    diffInt = Integer.parseInt(diff);
                                    if ("-".equals(sign)) {
                                        diffInt = -diffInt.intValue();
                                    }
                                    v = granularity;
                                    var20_22 = -1;
                                    switch (v.hashCode()) {
                                        case 67452: {
                                            if (!v.equals("DAY")) break;
                                            var20_22 = 0;
                                            break;
                                        }
                                        case 2660340: {
                                            if (!v.equals("WEEK")) break;
                                            var20_22 = 1;
                                            break;
                                        }
                                        case 73542240: {
                                            if (!v.equals("MONTH")) break;
                                            var20_22 = 2;
                                            break;
                                        }
                                        case 1369386636: {
                                            if (!v.equals("QUARTER")) break;
                                            var20_22 = 3;
                                            break;
                                        }
                                        case 2719805: {
                                            if (!v.equals("YEAR")) break;
                                            var20_22 = 4;
                                            break;
                                        }
                                        case 2104708963: {
                                            if (!v.equals("POSTINGPERIOD")) break;
                                            var20_22 = 5;
                                        }
                                    }
                                    switch (var20_22) {
                                        case 0: {
                                            d = d.plusDays(diffInt.intValue());
                                            break;
                                        }
                                        case 1: {
                                            d = d.plusWeeks(diffInt.intValue());
                                            break;
                                        }
                                        case 2: {
                                            d = d.plusMonths(diffInt.intValue());
                                            break;
                                        }
                                        case 3: {
                                            d = d.plusMonths(diffInt.longValue() * 3L);
                                            break;
                                        }
                                        case 4: {
                                            d = d.plusYears(diffInt.intValue());
                                            break;
                                        }
                                        case 5: {
                                            n = -diffInt.intValue() + 1;
                                            fiscalInfo = FiscalInfoUtils.getRecentNFiscalYearInfo((List)fiscalInfos, (int)n, (int)currentFiscalInfoIndex);
                                            if (fiscalInfo == null) {
                                                throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
                                            }
                                            d = fiscalInfo.getYearStartDate();
                                            break;
                                        }
                                        default: {
                                            throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
                                        }
                                    }
                                }
                            }
                            found = true;
                        }
                        if (StringUtils.isEmpty((CharSequence)granularityPara)) break block74;
                        granularityParaInt = Integer.parseInt(granularityPara);
                        var20_23 = granularity;
                        n = -1;
                        switch (var20_23.hashCode()) {
                            case 2660340: {
                                if (!var20_23.equals("WEEK")) break;
                                n = 0;
                                break;
                            }
                            case 73542240: {
                                if (!var20_23.equals("MONTH")) break;
                                n = 1;
                                break;
                            }
                            case 1369386636: {
                                if (!var20_23.equals("QUARTER")) break;
                                n = 2;
                                break;
                            }
                            case 2719805: {
                                if (!var20_23.equals("YEAR")) break;
                                n = 3;
                            }
                        }
                        switch (n) {
                            case 0: {
                                d = d.with(WeekFields.ISO.weekOfWeekBasedYear(), granularityParaInt.intValue());
                                break;
                            }
                            case 1: {
                                d = d.withMonth(granularityParaInt);
                                break;
                            }
                            case 2: {
                                result = 0;
                                try {
                                    result = Math.subtractExact(Math.multiplyExact(granularityParaInt, 3), 2);
                                }
                                catch (Exception e) {
                                    if (granularityParaInt > 12) {
                                        result = 12;
                                    }
                                    if (granularityParaInt >= 1) ** GOTO lbl154
                                    result = 1;
                                }
lbl154:
                                // 4 sources

                                d = d.withMonth(result);
                                break;
                            }
                            case 3: {
                                d = d.withYear(granularityParaInt);
                                break;
                            }
                            default: {
                                throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
                            }
                        }
                        found = true;
                    }
                    if (StringUtils.isEmpty((CharSequence)comma) || StringUtils.isEmpty((CharSequence)beginEnd)) continue;
                    var19_21 = granularity;
                    var20_24 = -1;
                    switch (var19_21.hashCode()) {
                        case 2660340: {
                            if (!var19_21.equals("WEEK")) break;
                            var20_24 = 0;
                            break;
                        }
                        case 73542240: {
                            if (!var19_21.equals("MONTH")) break;
                            var20_24 = 1;
                            break;
                        }
                        case 1369386636: {
                            if (!var19_21.equals("QUARTER")) break;
                            var20_24 = 2;
                            break;
                        }
                        case 2719805: {
                            if (!var19_21.equals("YEAR")) break;
                            var20_24 = 3;
                            break;
                        }
                        case 2104708963: {
                            if (!var19_21.equals("POSTINGPERIOD")) break;
                            var20_24 = 4;
                        }
                    }
                    switch (var20_24) {
                        case 0: {
                            d = d.with(WeekFields.ISO.dayOfWeek(), "END".equals(beginEnd) != false ? 7 : 1);
                            break;
                        }
                        case 1: {
                            d = d.withDayOfMonth("END".equals(beginEnd) != false ? d.lengthOfMonth() : 1);
                            break;
                        }
                        case 2: {
                            if ("END".equals(beginEnd)) {
                                date = d.withMonth(d.getMonth().firstMonthOfQuarter().getValue() + 2);
                                d = date.withDayOfMonth(date.lengthOfMonth());
                                break;
                            }
                            d = d.withMonth(d.getMonth().firstMonthOfQuarter().getValue()).withDayOfMonth(1);
                            break;
                        }
                        case 3: {
                            if ("END".equals(beginEnd)) {
                                d = d.withMonth(12).withDayOfMonth(31);
                                break;
                            }
                            d = d.withMonth(1).withDayOfMonth(1);
                            break;
                        }
                        case 4: {
                            if ("END".equals(beginEnd)) {
                                d = fiscalInfo.getYearEndDate();
                                break;
                            }
                            d = fiscalInfo.getYearStartDate();
                            break;
                        }
                        default: {
                            throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
                        }
                    }
                    found = true;
                }
            }
            catch (NumberFormatException e) {
                throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
            }
        }
        if (!found) {
            try {
                d = LocalDate.parse(value, ThinFilterPlaceholder.constantDateFormatter);
            }
            catch (DateTimeParseException e) {
                throw new QueryException(contextData, "QUERY_ILLEGAL_PLACEHOLDER", new Object[]{str});
            }
        }
        analyticsDateTime = (IAnalyticsDateTime)ServiceFactory.getService(IAnalyticsDateTime.class);
        dateFormatter = analyticsDateTime.getDateFormatter(contextData);
        s = d.format(dateFormatter);
        return new AnalyticsPlaceholderReplaceResult(true, str, s);
    }

    static {
        int value;
        String name;
        MONTH_VALUE_MAP = new LinkedHashMap();
        WEEK_VALUE_MAP = new LinkedHashMap();
        for (Month month : Month.values()) {
            name = StringUtils.upperCase((String)month.name());
            value = month.getValue();
            MONTH_VALUE_MAP.put(name, value);
            MONTH_VALUE_MAP.put(StringUtils.left((String)name, (int)3), value);
        }
        for (Enum enum_ : DayOfWeek.values()) {
            name = StringUtils.upperCase((String)enum_.name());
            value = ((DayOfWeek)enum_).getValue();
            WEEK_VALUE_MAP.put(name, value);
            WEEK_VALUE_MAP.put(StringUtils.left((String)name, (int)3), value);
        }
        DATE_FORMULA_REG = "([+|-]*)(\\s*)(\\d*)(\\s*)([TODAY|DAY|WEEK|MONTH|QUARTER|YEAR|POSTINGPERIOD|" + StringUtils.join(MONTH_VALUE_MAP.keySet(), (String)"|") + "|" + StringUtils.join(WEEK_VALUE_MAP.keySet(), (String)"|") + "]*)(\\s*)(\\d*)(\\s*)([:]*)(\\s*)([BEGIN|END]*)?";
        DATE_FORMULA_PATTERN = Pattern.compile(DATE_FORMULA_REG);
        constantDateFormatter = new DateTimeFormatterBuilder().appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL).appendLiteral('/').appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NORMAL).appendLiteral('/').appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).toFormatter();
    }
}

