/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.BoOWVT
 *  com.sap.b1.protocol.response.CategoryResponse
 *  com.sap.b1.protocol.response.ResponseMessageList
 *  com.sap.b1.servlets.service.variant.VariantCategory
 *  com.sap.b1.servlets.service.variant.VariantList
 *  com.sap.b1.servlets.service.variant.VariantWithI18N
 *  com.sap.b1.servlets.service.variant.admin.VariantAdapter
 *  com.sap.b1.servlets.service.variant.sys.VariantsReader
 *  com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChart
 *  com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartSize
 *  com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartValue1
 *  com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartValue2
 *  com.sap.b1.svcl.client.complextype.WebClientVariantGroupBy
 *  com.sap.b1.svcl.client.complextype.WebClientVariantMChart
 *  com.sap.b1.svcl.client.complextype.WebClientVariantMChartSize
 *  com.sap.b1.svcl.client.complextype.WebClientVariantMChartValue1
 *  com.sap.b1.svcl.client.complextype.WebClientVariantMChartValue2
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSortBy
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.svcl.client.enums.BoYesNoEnum
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  com.sap.b1.tcli.service.ouqr.OUQRService
 *  com.sap.b1.util.StringUtil
 *  gen.bean.BmoOUQR
 *  gen.bean.BmoOWSV
 *  gen.bean.BmoOWVG
 *  gen.bean.BmoOWVT
 *  gen.bean.BmoWVT1
 *  gen.bean.BmoWVT10
 *  gen.bean.BmoWVT11
 *  gen.bean.BmoWVT2
 *  gen.bean.BmoWVT3
 *  gen.bean.BmoWVT4
 *  gen.bean.BmoWVT5
 *  gen.bean.BmoWVT6
 *  gen.bean.BmoWVT7
 *  gen.bean.BmoWVT8
 *  gen.bean.BmoWVT9
 *  gen.str.OUQRmsgs
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.bo.BoOWVT;
import com.sap.b1.protocol.response.CategoryResponse;
import com.sap.b1.protocol.response.ResponseMessageList;
import com.sap.b1.servlets.service.variant.VariantCategory;
import com.sap.b1.servlets.service.variant.VariantList;
import com.sap.b1.servlets.service.variant.VariantWithI18N;
import com.sap.b1.servlets.service.variant.admin.VariantAdapter;
import com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChart;
import com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartSize;
import com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartValue1;
import com.sap.b1.svcl.client.complextype.WebClientVariantEmbeddedChartValue2;
import com.sap.b1.svcl.client.complextype.WebClientVariantGroupBy;
import com.sap.b1.svcl.client.complextype.WebClientVariantMChart;
import com.sap.b1.svcl.client.complextype.WebClientVariantMChartSize;
import com.sap.b1.svcl.client.complextype.WebClientVariantMChartValue1;
import com.sap.b1.svcl.client.complextype.WebClientVariantMChartValue2;
import com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn;
import com.sap.b1.svcl.client.complextype.WebClientVariantSortBy;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.svcl.client.enums.BoYesNoEnum;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import com.sap.b1.tcli.service.ouqr.OUQRService;
import com.sap.b1.util.StringUtil;
import gen.bean.BmoOUQR;
import gen.bean.BmoOWSV;
import gen.bean.BmoOWVG;
import gen.bean.BmoOWVT;
import gen.bean.BmoWVT1;
import gen.bean.BmoWVT10;
import gen.bean.BmoWVT11;
import gen.bean.BmoWVT2;
import gen.bean.BmoWVT3;
import gen.bean.BmoWVT4;
import gen.bean.BmoWVT5;
import gen.bean.BmoWVT6;
import gen.bean.BmoWVT7;
import gen.bean.BmoWVT8;
import gen.bean.BmoWVT9;
import gen.str.OUQRmsgs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class VariantsReader {
    private static final Logger logger = LoggerFactory.getLogger(VariantsReader.class);
    @Autowired
    private BoOWVT boOWVT;
    @Autowired
    private VariantAdapter variantAdapter;
    @Autowired
    private OUQRService ouqrService;
    @Autowired
    private MessageUtil messageUtil;

    public List<WebClientVariant> loadExportedVariants(Integer userId) throws Exception {
        List data = this.boOWVT.loadExportedVariantsList(userId, Collections.emptyList());
        return this.toVariantList(data);
    }

    public List<WebClientVariant> loadExportedVariants(Integer userId, String[] ids) throws Exception {
        List data = this.boOWVT.loadExportedVariantsList(userId, ids);
        return this.toVariantList(data);
    }

    public VariantList loadVariantsList(Integer userId, String objName, String viewType, String viewId) throws Exception {
        List data = this.boOWVT.loadVariantsList(userId, objName, viewType, viewId);
        BmoOWVG dftVariant = this.boOWVT.getDefaultVariant(userId, objName, viewType, viewId);
        VariantList variantList = new VariantList();
        if (dftVariant != null) {
            variantList.defaultVariant = dftVariant.getDftVrnt();
        }
        variantList.list = this.toVariantList(data);
        return variantList;
    }

    private List<WebClientVariant> toVariantList(List<BmoOWVT> data) {
        ArrayList<WebClientVariant> list = new ArrayList<WebClientVariant>();
        for (BmoOWVT d : data) {
            WebClientVariant variant = new WebClientVariant();
            variant.Guid = d.getGuid();
            variant.Order = d.getOrder();
            variant.UserId = d.getUserId();
            variant.ViewType = d.getViewType();
            variant.SubViewType = d.getSubVType();
            variant.ViewId = d.getViewId();
            variant.ObjectName = d.getObjName();
            variant.FilterBarLayout = d.getFltBarLout();
            variant.SystemFilter = d.getSysFilter();
            variant.UserFilter = d.getUserFilter();
            variant.ConditionFilter = d.getCdtFilter();
            variant.IsPublic = BoYesNoEnum.get((String)d.getIsPublic());
            variant.IsSystem = BoYesNoEnum.get((String)d.getIsSys());
            variant.Name = d.getName();
            variant.Version = d.getVersion();
            variant.OverviewCustomization = d.getOvpCus();
            variant.ChartCustomization = d.getChartCus();
            variant.WebClientVariantSelectedColumnCollection = this.toSelectedColumns(d.getWVT1());
            variant.WebClientVariantGroupByCollection = this.toGroupBy(d.getWVT2());
            variant.WebClientVariantSortByCollection = this.toSortBy(d.getWVT3());
            variant.WebClientVariantEmbeddedChartCollection = this.toEmbeddedChart(d.getWVT4());
            variant.WebClientVariantMChartCollection = this.toMChart(d.getWVT8());
            list.add(variant);
        }
        return list;
    }

    private List<WebClientVariantMChart> toMChart(List<BmoWVT8> wvt8) {
        return VariantsReader.mapList(wvt8, (T d) -> {
            WebClientVariantMChart chart = new WebClientVariantMChart();
            chart.Guid = d.getGuid();
            chart.ChartType = d.getChartType();
            chart.IsShowLegend = BoYesNoEnum.get((String)d.getShowLegend());
            chart.CategoryAxis1 = d.getCtgrAxis1();
            chart.CategoryAxis2 = d.getCtgrAxis2();
            chart.TimeAxis = d.getTimeAxis();
            chart.Color = d.getColor();
            chart.Shape = d.getShape();
            chart.BubbleWidth = d.getBblWidth();
            chart.WebClientVariantMChartValue1Collection = this.toMChartValue1(d.getWVT9());
            chart.WebClientVariantMChartValue2Collection = this.toMChartValue2(d.getWVT10());
            chart.WebClientVariantMChartSizeCollection = this.toMChartSize(d.getWVT11());
            return chart;
        });
    }

    private List<WebClientVariantMChartValue1> toMChartValue1(List<BmoWVT9> wvt9) {
        return VariantsReader.mapList(wvt9, (T d) -> {
            WebClientVariantMChartValue1 v = new WebClientVariantMChartValue1();
            v.Guid = d.getGuid();
            v.Order = d.getOrder();
            v.ColumnName = d.getColName();
            return v;
        });
    }

    private List<WebClientVariantMChartValue2> toMChartValue2(List<BmoWVT10> wvt10) {
        return VariantsReader.mapList(wvt10, (T d) -> {
            WebClientVariantMChartValue2 v = new WebClientVariantMChartValue2();
            v.Guid = d.getGuid();
            v.Order = d.getOrder();
            v.ColumnName = d.getColName();
            return v;
        });
    }

    private List<WebClientVariantMChartSize> toMChartSize(List<BmoWVT11> wvt11) {
        return VariantsReader.mapList(wvt11, (T d) -> {
            WebClientVariantMChartSize v = new WebClientVariantMChartSize();
            v.Guid = d.getGuid();
            v.Order = d.getOrder();
            v.ColumnName = d.getColName();
            return v;
        });
    }

    private List<WebClientVariantEmbeddedChart> toEmbeddedChart(List<BmoWVT4> wvt4) {
        return VariantsReader.mapList(wvt4, (T d) -> {
            WebClientVariantEmbeddedChart chart = new WebClientVariantEmbeddedChart();
            chart.Guid = d.getGuid();
            chart.ChartType = d.getChartType();
            chart.IsShowLegend = BoYesNoEnum.get((String)d.getShowLegend());
            chart.CategoryAxis1 = d.getCtgrAxis1();
            chart.CategoryAxis2 = d.getCtgrAxis2();
            chart.TimeAxis = d.getTimeAxis();
            chart.Color = d.getColor();
            chart.Shape = d.getShape();
            chart.BubbleWidth = d.getBblWidth();
            chart.WebClientVariantEmbeddedChartValue1Collection = this.toChartValue1(d.getWVT5());
            chart.WebClientVariantEmbeddedChartValue2Collection = this.toChartValue2(d.getWVT6());
            chart.WebClientVariantEmbeddedChartSizeCollection = this.toChartSize(d.getWVT7());
            return chart;
        });
    }

    private List<WebClientVariantEmbeddedChartValue1> toChartValue1(List<BmoWVT5> wvt5) {
        return VariantsReader.mapList(wvt5, (T d) -> {
            WebClientVariantEmbeddedChartValue1 v = new WebClientVariantEmbeddedChartValue1();
            v.Guid = d.getGuid();
            v.Order = d.getOrder();
            v.ColumnName = d.getColName();
            return v;
        });
    }

    private List<WebClientVariantEmbeddedChartValue2> toChartValue2(List<BmoWVT6> wvt6) {
        return VariantsReader.mapList(wvt6, (T d) -> {
            WebClientVariantEmbeddedChartValue2 v = new WebClientVariantEmbeddedChartValue2();
            v.Guid = d.getGuid();
            v.Order = d.getOrder();
            v.ColumnName = d.getColName();
            return v;
        });
    }

    private List<WebClientVariantEmbeddedChartSize> toChartSize(List<BmoWVT7> wvt7) {
        return VariantsReader.mapList(wvt7, (T d) -> {
            WebClientVariantEmbeddedChartSize v = new WebClientVariantEmbeddedChartSize();
            v.Guid = d.getGuid();
            v.Order = d.getOrder();
            v.ColumnName = d.getColName();
            return v;
        });
    }

    private List<WebClientVariantSortBy> toSortBy(List<BmoWVT3> wvt3) {
        return VariantsReader.mapList(wvt3, (T d) -> {
            WebClientVariantSortBy c = new WebClientVariantSortBy();
            c.Guid = d.getGuid();
            c.Order = d.getOrder();
            c.ColumnName = d.getColName();
            c.Direction = d.getDirection();
            return c;
        });
    }

    private List<WebClientVariantGroupBy> toGroupBy(List<BmoWVT2> wvt2) {
        return VariantsReader.mapList(wvt2, (T d) -> {
            WebClientVariantGroupBy c = new WebClientVariantGroupBy();
            c.Guid = d.getGuid();
            c.Order = d.getOrder();
            c.ColumnName = d.getColName();
            return c;
        });
    }

    private List<WebClientVariantSelectedColumn> toSelectedColumns(List<BmoWVT1> wvt1) {
        return VariantsReader.mapList(wvt1, (T d) -> {
            WebClientVariantSelectedColumn c = new WebClientVariantSelectedColumn();
            c.Guid = d.getGuid();
            c.Order = d.getOrder();
            c.ColumnName = d.getColName();
            return c;
        });
    }

    private static <T, R> List<R> mapList(List<T> list, Function<T, R> mapper) {
        ArrayList<R> newList = new ArrayList<R>();
        if (list != null && list.size() > 0) {
            for (T t : list) {
                newList.add(mapper.apply(t));
            }
        }
        return newList;
    }

    public List<CategoryResponse<VariantCategory, VariantWithI18N>> search(Integer userId, String key, boolean showSys) {
        List variants = this.boOWVT.getUserVariants(userId, key);
        return this.variantAdapter.adapt(variants, userId, StringUtil.isEmpty((String)key), showSys);
    }

    public boolean nameExists(String name, String objectName, String viewType, String viewId) {
        return this.boOWVT.countName(name, objectName, viewType, viewId) > 0;
    }

    public boolean udqNameExists(Integer qCategory, String qName) {
        return this.boOWVT.countUDQName(qCategory, qName) > 0;
    }

    public List<BmoOWSV> loadRelatedLinks(Integer userId, String ids, List<String> systemVariants) {
        return this.boOWVT.loadRelatedLinks(userId, ids, systemVariants);
    }

    public boolean linkNameExists(BmoOWSV link) {
        return this.boOWVT.countLinkName(link.getName()) > 0;
    }

    public Map<String, String> getNonSysUDQCategoryNames(List<BmoOUQR> udqs) {
        Set ids = udqs.stream().map(u -> u.getQCategory()).filter(id -> id >= 0).collect(Collectors.toSet());
        return this.boOWVT.loadUDQCategoryNames(ids);
    }

    public Map<String, Integer> getUDQCategoryIds(String[] names) {
        return this.boOWVT.loadUDQCategoryIds(names);
    }

    public ResponseMessageList getUdqWithoutView(Integer userId) throws Exception {
        List dtOUQR = this.ouqrService.findAll();
        Map categoryName = this.getUDQUserCategoryNames(dtOUQR);
        ResponseMessageList messageList = new ResponseMessageList(this.messageUtil.getMessage((MessageId)OUQRmsgs.noError, new Object[0]));
        messageList.info(this.messageUtil.getMessage((MessageId)OUQRmsgs.msgUDQNoVariant, new Object[0]));
        for (BmoOUQR drOUQR : dtOUQR) {
            Integer intrnalKey = drOUQR.getIntrnalKey();
            Integer category = drOUQR.getQCategory();
            String udqKey = this.ouqrService.unifyKey(intrnalKey, category);
            if (category <= -2 || this.udqUserVariantExists(userId, udqKey)) continue;
            messageList.info(this.messageUtil.getMessage((MessageId)OUQRmsgs.msgUDQNoVariantParam, new Object[]{categoryName.get(String.valueOf(category)), drOUQR.getQName()}));
        }
        return messageList;
    }

    public boolean udqUserVariantExists(Integer userId, String qUdqkey) {
        return this.boOWVT.countUdqUserVariant(userId, qUdqkey) > 0;
    }

    public Map<String, String> getUDQUserCategoryNames(List<BmoOUQR> udqs) {
        Set ids = udqs.stream().map(u -> u.getQCategory()).filter(id -> id > -2).collect(Collectors.toSet());
        return this.boOWVT.loadUDQCategoryNames(ids);
    }
}

