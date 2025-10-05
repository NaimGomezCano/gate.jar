/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.infra.share.feign.FeignRequestInterceptor
 *  com.sap.b1.infra.share.feign.tcli.FeignAnalyticsViewService
 *  com.sap.b1.infra.share.feign.tcli.FeignTileService
 *  com.sap.b1.infra.share.feign.tcli.FeignVariantListService
 *  com.sap.b1.infra.share.feign.tcli.entity.AnlyView
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Catalog
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Group
 *  com.sap.b1.infra.share.feign.tcli.entity.tile.Tile
 *  com.sap.b1.svcl.client.complextype.WebClientVariantGroupBy
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSortBy
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.thin.extn.AddonServlet
 *  com.sap.b1.thin.extn.variant.BOTypeEnum
 *  com.sap.b1.thin.extn.variant.SysBOEnum
 *  com.sap.b1.thin.extn.variant.VariantDisplay
 *  com.sap.b1.thin.extn.variant.VariantList
 *  com.sap.b1.thin.extn.variant.VariantServlet
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.RequestInterceptor
 *  feign.codec.Decoder
 *  feign.codec.Encoder
 *  feign.hc5.ApacheHttp5Client
 *  feign.jackson.JacksonDecoder
 *  feign.jackson.JacksonEncoder
 *  feign.slf4j.Slf4jLogger
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.extn.variant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.infra.share.feign.FeignRequestInterceptor;
import com.sap.b1.infra.share.feign.tcli.FeignAnalyticsViewService;
import com.sap.b1.infra.share.feign.tcli.FeignTileService;
import com.sap.b1.infra.share.feign.tcli.FeignVariantListService;
import com.sap.b1.infra.share.feign.tcli.entity.AnlyView;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Catalog;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Group;
import com.sap.b1.infra.share.feign.tcli.entity.tile.Tile;
import com.sap.b1.svcl.client.complextype.WebClientVariantGroupBy;
import com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn;
import com.sap.b1.svcl.client.complextype.WebClientVariantSortBy;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.thin.extn.AddonServlet;
import com.sap.b1.thin.extn.variant.BOTypeEnum;
import com.sap.b1.thin.extn.variant.SysBOEnum;
import com.sap.b1.thin.extn.variant.VariantDisplay;
import com.sap.b1.thin.extn.variant.VariantList;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hc5.ApacheHttp5Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VariantServlet {
    private static final String B1S_WEBCLIENT_URL = "";
    private Logger logger = LoggerFactory.getLogger(VariantServlet.class);
    @Value(value="${feign.client.config.default.loggerLevel:NONE}")
    Logger.Level logLevel;
    Feign.Builder builder;
    @Value(value="${tcli.url:http://localhost:9902}")
    String tcliUrl;
    private SecureRandom random = new SecureRandom();

    @Autowired
    public void initAddonFeignbuilder() throws Exception {
        this.builder = this.getFeignBuilder().encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).client((Client)new ApacheHttp5Client()).logger((feign.Logger)new Slf4jLogger(AddonServlet.class));
        this.builder.requestInterceptor((RequestInterceptor)new FeignRequestInterceptor());
    }

    @RequestMapping(path={"/api/Variants"}, method={RequestMethod.GET})
    public List<VariantDisplay> list(@RequestParam(value="objName") String objName, @RequestParam(value="objType", required=false) String objType) {
        String viewId = B1S_WEBCLIENT_URL;
        VariantList variants = null;
        Object jsonObject = null;
        ArrayList<VariantDisplay> variantDisplayList = new ArrayList<VariantDisplay>();
        String objTableName = this.getObjectTableName(objName, objType);
        String configurationviewId = this.ConfigurationDetail(objName);
        String systemviewId = this.SystemBOsDetail(objName);
        if (!configurationviewId.isEmpty()) {
            viewId = configurationviewId;
        } else if (!systemviewId.isEmpty()) {
            viewId = systemviewId;
        }
        if (objTableName.equals(B1S_WEBCLIENT_URL)) {
            this.logger.error("object name or object type is not valid");
            return variantDisplayList;
        }
        String sviewtype = this.getViewType(objTableName);
        try {
            this.initAddonFeignbuilder();
            jsonObject = this.initFeignVariantList().getFeignList(objTableName, sviewtype, viewId);
            variants = (VariantList)this.convertValue(jsonObject, VariantList.class);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return variantDisplayList;
        }
        if (objType != null && (BOTypeEnum.UDO.equals((Object)BOTypeEnum.valueOf((String)objType)) || BOTypeEnum.UDT.equals((Object)BOTypeEnum.valueOf((String)objType)) || this.stardarddefaultBOs(objName))) {
            VariantDisplay vd = this.addStandardGUID();
            variantDisplayList.add(vd);
        }
        for (WebClientVariant webVariant : variants.list) {
            VariantDisplay vd = new VariantDisplay();
            vd.name = webVariant.Name;
            vd.guid = webVariant.Guid;
            variantDisplayList.add(vd);
        }
        if (objTableName.equals(SysBOEnum.BusinessPartnerCatalogNumbers.GetTableName())) {
            variantDisplayList.clear();
        }
        return variantDisplayList;
    }

    Feign.Builder getFeignBuilder() {
        return Feign.builder();
    }

    public VariantDisplay addStandardGUID() {
        VariantDisplay vd = new VariantDisplay();
        vd.name = "Standard";
        vd.guid = "7eb0f1cc-20c4-3e27-8b79-7df40ccc0fd3";
        return vd;
    }

    @RequestMapping(path={"/api/objectsListView"}, method={RequestMethod.GET})
    public String objectsListView(@RequestParam(value="objName") String objName, @RequestParam(value="objType", required=false) String objType, @RequestParam(value="variantGUID", required=false) String variantGUID) {
        VariantList variants = null;
        Object jsonObject = null;
        WebClientVariant variant = null;
        String viewId = B1S_WEBCLIENT_URL;
        String simpleObjTableName = B1S_WEBCLIENT_URL;
        String objTableName = this.getObjectTableName(objName, objType);
        String sviewtype = this.getViewType(objTableName);
        String confviewId = this.ConfigurationDetail(objName);
        String systemviewId = this.SystemBOsDetail(objName);
        if (!confviewId.isEmpty()) {
            viewId = confviewId;
        } else if (!systemviewId.isEmpty()) {
            viewId = systemviewId;
        }
        if (objTableName.equals(B1S_WEBCLIENT_URL)) {
            this.logger.error("object name or object type is not valid");
            return B1S_WEBCLIENT_URL;
        }
        try {
            this.initAddonFeignbuilder();
            jsonObject = this.initFeignVariantList().getFeignList(objTableName, sviewtype, viewId);
            variants = (VariantList)this.convertValue(jsonObject, VariantList.class);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return B1S_WEBCLIENT_URL;
        }
        for (WebClientVariant webVariant : variants.list) {
            if (!variantGUID.equals(webVariant.Guid)) continue;
            variant = webVariant;
            break;
        }
        if (objType == null || BOTypeEnum.System.equals((Object)BOTypeEnum.valueOf((String)objType))) {
            simpleObjTableName = objTableName;
        } else if (BOTypeEnum.UDO.equals((Object)BOTypeEnum.valueOf((String)objType)) || BOTypeEnum.UDT.equals((Object)BOTypeEnum.valueOf((String)objType))) {
            simpleObjTableName = objTableName.substring(1);
        }
        if (variant == null) {
            if (objTableName.equals(SysBOEnum.UserQueries.GetTableName())) {
                return "/webx/index.html#bo" + simpleObjTableName + "-app";
            }
            if (objTableName.equals(SysBOEnum.Configuration.GetTableName()) || objTableName.equals(SysBOEnum.ChartOfAccounts.GetTableName())) {
                return "/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/Simple" + "?r=" + this.randomString();
            }
            if (!systemviewId.isEmpty()) {
                return "/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/Detail?view=" + systemviewId + "&r=" + this.randomString();
            }
            return "/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/" + sviewtype + "?s=";
        }
        if (!simpleObjTableName.isEmpty() && !confviewId.isEmpty()) {
            return "/webx/index.html#webclient-" + "Config" + "&/Objects/" + "Config" + "/Simple?subViewS=" + this.stringifySysVariant(variant) + "&subViewId=" + viewId + "&r=" + this.randomString();
        }
        if (!systemviewId.isEmpty()) {
            return "/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/List??view=" + systemviewId + "&s=" + this.stringifySysVariant(variant) + "&r=" + this.randomString();
        }
        return "/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/" + sviewtype + "?s=" + this.stringifySysVariant(variant) + "&r=" + this.randomString();
    }

    @RequestMapping(path={"/api/objectsDetailView"}, method={RequestMethod.GET})
    public ResponseEntity<String> objectsDetailView(@RequestParam(value="objName") String objName, @RequestParam(value="objType", required=false) String objType, @RequestParam(value="value") String value) {
        String objTableName = this.getObjectTableName(objName, objType);
        String confviewId = this.ConfigurationDetail(objName);
        if (objTableName.equals(B1S_WEBCLIENT_URL)) {
            this.logger.error("object name or object type is not valid");
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)B1S_WEBCLIENT_URL);
        }
        String simpleObjTableName = B1S_WEBCLIENT_URL;
        if (objType == null || BOTypeEnum.System.equals((Object)BOTypeEnum.valueOf((String)objType))) {
            simpleObjTableName = objTableName;
        } else if (BOTypeEnum.UDO.equals((Object)BOTypeEnum.valueOf((String)objType)) || BOTypeEnum.UDT.equals((Object)BOTypeEnum.valueOf((String)objType))) {
            simpleObjTableName = objTableName.substring(1);
        }
        if (objTableName.equals(SysBOEnum.Configuration.GetTableName())) {
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/Simple?subViewId=" + this.ConfigurationDetail(value)));
        }
        if (value == null || value.isEmpty()) {
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/Detail?r=" + this.randomString()));
        }
        Object encodeValue = B1S_WEBCLIENT_URL;
        try {
            if (objType == null || BOTypeEnum.System.equals((Object)BOTypeEnum.valueOf((String)objType))) {
                encodeValue = objTableName + "," + value;
            } else if (BOTypeEnum.UDO.equals((Object)BOTypeEnum.valueOf((String)objType)) || BOTypeEnum.UDT.equals((Object)BOTypeEnum.valueOf((String)objType))) {
                encodeValue = "\"" + objTableName + "\"," + value;
            }
            encodeValue = URLEncoder.encode((String)encodeValue, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~");
            encodeValue = URLEncoder.encode((String)encodeValue, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            this.logger.error(e.getMessage());
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)B1S_WEBCLIENT_URL);
        }
        if (objTableName.equals(SysBOEnum.UserQueries.GetTableName())) {
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#bo" + simpleObjTableName + "-app&/" + "detail/" + (String)encodeValue));
        }
        if (objTableName.equals(SysBOEnum.ChartOfAccounts.GetTableName())) {
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/Simple?r=" + this.randomString() + "&id=" + (String)encodeValue));
        }
        if (!simpleObjTableName.isEmpty() && !confviewId.isEmpty()) {
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-" + "Config" + "&/Objects/" + "Config" + "/Simple?subViewId=" + objTableName + ".detailView&r=" + this.randomString() + "&id=" + (String)encodeValue));
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-" + simpleObjTableName + "&/Objects/" + objTableName + "/Detail?view=" + objTableName + ".detailView&r=" + this.randomString() + "&id=" + (String)encodeValue));
    }

    @RequestMapping(path={"/api/analyticsViews"}, method={RequestMethod.GET})
    public List<AnlyView> anlylist(@RequestParam(value="analyticsType") String objType) {
        List analysviews = null;
        try {
            FeignAnalyticsViewService feignAnalyticsViewService = (FeignAnalyticsViewService)Feign.builder().encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).client((Client)new ApacheHttp5Client()).logger((feign.Logger)new Slf4jLogger(AddonServlet.class)).logLevel(Logger.Level.FULL).requestInterceptor((RequestInterceptor)new FeignRequestInterceptor()).target(FeignAnalyticsViewService.class, this.tcliUrl);
            analysviews = feignAnalyticsViewService.getAnlyViews(objType);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return null;
        }
        return analysviews;
    }

    @RequestMapping(path={"/api/analyticsViewVariants"}, method={RequestMethod.GET})
    public List<VariantDisplay> ViewVariantslist(@RequestParam(value="viewCode") String viewCode, @RequestParam(value="analyticsType") String objType) {
        ArrayList<VariantDisplay> variantDisplayList = new ArrayList<VariantDisplay>();
        VariantList variants = null;
        Object jsonObject = null;
        try {
            FeignVariantListService feignVariantListService = (FeignVariantListService)Feign.builder().encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).client((Client)new ApacheHttp5Client()).logger((feign.Logger)new Slf4jLogger(AddonServlet.class)).logLevel(Logger.Level.FULL).requestInterceptor((RequestInterceptor)new FeignRequestInterceptor()).target(FeignVariantListService.class, this.tcliUrl);
            jsonObject = feignVariantListService.getFeignList("ANLY", objType, viewCode);
            variants = (VariantList)this.convertValue(jsonObject, VariantList.class);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return variantDisplayList;
        }
        for (WebClientVariant webVariant : variants.list) {
            VariantDisplay vd = new VariantDisplay();
            vd.name = webVariant.Name;
            vd.guid = webVariant.Guid;
            variantDisplayList.add(vd);
        }
        return variantDisplayList;
    }

    @RequestMapping(path={"/api/analyticsView"}, method={RequestMethod.GET})
    public ResponseEntity<String> Viewanly(@RequestParam(value="analyticsType") String objType, @RequestParam(value="viewCode") String viewCode, @RequestParam(value="variantGUID") String variantGUID) {
        WebClientVariant variant = null;
        VariantList variants = null;
        Object jsonObject = null;
        try {
            FeignVariantListService feignVariantListService = (FeignVariantListService)Feign.builder().encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).client((Client)new ApacheHttp5Client()).logger((feign.Logger)new Slf4jLogger(AddonServlet.class)).logLevel(Logger.Level.FULL).requestInterceptor((RequestInterceptor)new FeignRequestInterceptor()).target(FeignVariantListService.class, this.tcliUrl);
            jsonObject = feignVariantListService.getFeignList("ANLY", objType, viewCode);
            variants = (VariantList)this.convertValue(jsonObject, VariantList.class);
            for (WebClientVariant webVariant : variants.list) {
                if (!variantGUID.equals(webVariant.Guid)) continue;
                variant = webVariant;
                break;
            }
            if (variant == null) {
                return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)B1S_WEBCLIENT_URL);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)B1S_WEBCLIENT_URL);
        }
        if (variantGUID.equals(variants.defaultVariant)) {
            return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-anly&" + "/Objects/ANLY/" + variant.ViewType + "?view=" + variant.ViewId + "&s=" + "&r=" + this.randomString()));
        }
        return ResponseEntity.ok().contentType(MediaType.valueOf((String)"text/plain")).body((Object)("/webx/index.html#webclient-anly&" + "/Objects/ANLY/" + variant.ViewType + "?view=" + variant.ViewId + "&s=" + this.stringifyAnlyVariant(variant) + "&r=" + this.randomString()));
    }

    public String getObjectTableName(String objectName, String objectType) {
        String objTableName;
        block5: {
            objTableName = B1S_WEBCLIENT_URL;
            try {
                if (objectType == null || BOTypeEnum.System.equals((Object)BOTypeEnum.valueOf((String)objectType))) {
                    objTableName = SysBOEnum.valueOf((String)objectName).GetTableName();
                    break block5;
                }
                if (!BOTypeEnum.UDO.equals((Object)BOTypeEnum.valueOf((String)objectType)) && !BOTypeEnum.UDT.equals((Object)BOTypeEnum.valueOf((String)objectType))) break block5;
                FeignTileService feignTileService = (FeignTileService)Feign.builder().encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).client((Client)new ApacheHttp5Client()).logger((feign.Logger)new Slf4jLogger(AddonServlet.class)).logLevel(Logger.Level.FULL).requestInterceptor((RequestInterceptor)new FeignRequestInterceptor()).target(FeignTileService.class, this.tcliUrl);
                Catalog catalog = feignTileService.getFeignCatalog();
                List groups = catalog.getData().getGroups();
                for (Group group : groups) {
                    if (!group.getGroupId().equals(objectType)) continue;
                    for (Tile tile : group.getTiles()) {
                        if (!tile.getSubtitle().equals(objectName)) continue;
                        objTableName = tile.getAction().getTarget();
                        break block5;
                    }
                    break;
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage());
                return objTableName;
            }
        }
        return objTableName;
    }

    public String getViewType(String objTableName) {
        if (objTableName.equals(SysBOEnum.AlternativeItems.GetTableName()) || objTableName.equals(SysBOEnum.JournalVoucher.GetTableName()) || objTableName.equals(SysBOEnum.ServiceContractQueues.GetTableName()) || objTableName.equals(SysBOEnum.AccountCategories.GetTableName()) || objTableName.equals(SysBOEnum.UserDefinedFields.GetTableName()) || objTableName.equals(SysBOEnum.UserDefinedValues.GetTableName()) || objTableName.equals(SysBOEnum.BusinessPartnerCatalogNumbers.GetTableName()) || objTableName.equals(SysBOEnum.SalesAndPurchasingLandedCosts.GetTableName())) {
            return "Filterable";
        }
        return "List";
    }

    public <T> T convertValue(Object bean, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.convertValue(bean, clazz);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return null;
        }
    }

    public String stringifySysVariant(WebClientVariant webVariant) {
        StringBuilder stringBuilder = new StringBuilder("~(status~())");
        stringBuilder.insert(stringBuilder.length() - 2, "version~" + webVariant.Version);
        if (webVariant.UserFilter != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~userFilter~'" + this.encode(webVariant.UserFilter));
        }
        if (webVariant.FilterBarLayout != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~filterBarLayout~'" + this.encode(webVariant.FilterBarLayout.replaceAll("\"", B1S_WEBCLIENT_URL)));
        }
        if (webVariant.ConditionFilter != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~ConditionFilter~'" + this.encode(webVariant.ConditionFilter.replaceAll("\"", B1S_WEBCLIENT_URL)));
        }
        if (webVariant.ChartCustomization != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~ChartCustomization~'" + this.encode(webVariant.ChartCustomization.replaceAll("\"", B1S_WEBCLIENT_URL)));
        }
        if (webVariant.OverviewCustomization != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~OverviewCustomization~'" + this.encode(webVariant.OverviewCustomization.replaceAll("\"", B1S_WEBCLIENT_URL)));
        }
        stringBuilder.insert(stringBuilder.length() - 2, "~selectedColumns~()");
        List webClientVariantSelectedColumnList = webVariant.getWebClientVariantSelectedColumnCollection();
        if (webClientVariantSelectedColumnList.size() > 0) {
            webClientVariantSelectedColumnList.sort(Comparator.comparing(e -> e.Order));
            for (WebClientVariantSelectedColumn selectedColumn : webClientVariantSelectedColumnList) {
                stringBuilder.insert(stringBuilder.length() - 3, "~(p~'" + this.encode(selectedColumn.ColumnName) + ")");
            }
        } else {
            stringBuilder.insert(stringBuilder.length() - 3, "~");
        }
        stringBuilder.insert(stringBuilder.length() - 2, "~groupBy~()");
        if (webVariant.getWebClientVariantGroupByCollection().size() > 0) {
            for (WebClientVariantGroupBy groupBy : webVariant.getWebClientVariantGroupByCollection()) {
                stringBuilder.insert(stringBuilder.length() - 3, "~'" + this.encode(groupBy.ColumnName));
            }
        } else {
            stringBuilder.insert(stringBuilder.length() - 3, "~");
        }
        stringBuilder.insert(stringBuilder.length() - 2, "~sortBy~()");
        if (webVariant.getWebClientVariantSortByCollection().size() > 0) {
            for (WebClientVariantSortBy sortBy : webVariant.getWebClientVariantSortByCollection()) {
                stringBuilder.insert(stringBuilder.length() - 3, "~(key~'" + this.encode(sortBy.ColumnName) + "~dir~'" + this.encode(sortBy.Direction) + ")");
            }
        } else {
            stringBuilder.insert(stringBuilder.length() - 3, "~");
        }
        stringBuilder.insert(stringBuilder.length() - 1, "~variant~(selectedKey~'" + webVariant.Guid + "~dirtyFlag~false)");
        return stringBuilder.toString();
    }

    public String stringifyAnlyVariant(WebClientVariant webVariant) {
        StringBuilder stringBuilder = new StringBuilder("~(status~())");
        stringBuilder.insert(stringBuilder.length() - 2, "version~" + webVariant.Version);
        if (webVariant.UserFilter != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~userFilter~'" + this.encode(webVariant.UserFilter));
        }
        if (webVariant.OverviewCustomization != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~overviewCustomization~'" + this.encode(webVariant.OverviewCustomization));
        }
        if (webVariant.ConditionFilter != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~conditionFilter~'" + this.encode(webVariant.ConditionFilter));
        }
        if (webVariant.SubViewType != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~subViewType~'" + this.encode(webVariant.SubViewType));
        }
        if (webVariant.FilterBarLayout != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~filterBarLayout~'" + this.encode(webVariant.FilterBarLayout));
        }
        stringBuilder.insert(stringBuilder.length() - 2, "~selectedColumns~()");
        List webClientVariantSelectedColumnList = webVariant.getWebClientVariantSelectedColumnCollection();
        if (webClientVariantSelectedColumnList.size() > 0) {
            webClientVariantSelectedColumnList.sort(Comparator.comparing(e -> e.Order));
            for (WebClientVariantSelectedColumn selectedColumn : webClientVariantSelectedColumnList) {
                stringBuilder.insert(stringBuilder.length() - 3, "~(p~'" + this.encode(selectedColumn.ColumnName) + ")");
            }
        } else {
            stringBuilder.insert(stringBuilder.length() - 3, "~");
        }
        if (webVariant.ChartCustomization != null) {
            stringBuilder.insert(stringBuilder.length() - 2, "~chartCustomization~'" + this.encode(webVariant.ChartCustomization));
        }
        stringBuilder.insert(stringBuilder.length() - 2, "~groupBy~()");
        if (webVariant.getWebClientVariantGroupByCollection().size() > 0) {
            for (WebClientVariantGroupBy groupBy : webVariant.getWebClientVariantGroupByCollection()) {
                stringBuilder.insert(stringBuilder.length() - 3, "~'" + this.encode(groupBy.ColumnName));
            }
        } else {
            stringBuilder.insert(stringBuilder.length() - 3, "~");
        }
        stringBuilder.insert(stringBuilder.length() - 2, "~sortBy~()");
        if (webVariant.getWebClientVariantSortByCollection().size() > 0) {
            for (WebClientVariantSortBy sortBy : webVariant.getWebClientVariantSortByCollection()) {
                stringBuilder.insert(stringBuilder.length() - 3, "~(key~'" + this.encode(sortBy.ColumnName) + "~dir~'" + this.encode(sortBy.Direction) + ")");
            }
        } else {
            stringBuilder.insert(stringBuilder.length() - 3, "~");
        }
        stringBuilder.insert(stringBuilder.length() - 1, "~variant~(selectedKey~'" + webVariant.Guid + "~dirtyFlag~false)");
        return stringBuilder.toString();
    }

    public String encode(String str) {
        Pattern p = Pattern.compile("[^\\w-.]");
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            StringBuilder tmpStr;
            if (m.group().equals("$")) {
                m.appendReplacement(sb, "!");
                continue;
            }
            if (m.group().charAt(0) < '\u0100') {
                tmpStr = new StringBuilder("00" + Integer.toString(m.group().charAt(0), 16));
                m.appendReplacement(sb, tmpStr.delete(0, tmpStr.length() - 2).insert(0, "*").toString());
                continue;
            }
            tmpStr = new StringBuilder("0000" + Integer.toString(m.group().charAt(0), 16));
            m.appendReplacement(sb, tmpStr.delete(0, tmpStr.length() - 4).insert(0, "**").toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public String randomString() {
        String possibleStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; ++i) {
            sb.append(possibleStr.charAt(this.random.nextInt(possibleStr.length())));
        }
        return sb.toString();
    }

    private String SystemBOsDetail(String encodeValue) {
        return switch (encodeValue) {
            case "ExchangeRates" -> "ORTT.ExchangeRate.detailView";
            case "ExchangeRatesIndexes" -> "ORTT.Indexes.detailView";
            case "SalesBlanketAgreements" -> "OOAT.sales.listView";
            case "PurchaseBlanketAgreements" -> "OOAT.purchase.listView";
            default -> B1S_WEBCLIENT_URL;
        };
    }

    private boolean stardarddefaultBOs(String encodeValue) {
        return switch (encodeValue) {
            case "UserDefinedFields", "AlternativeItems", "CreditCardPayments", "ReferenceFieldLinks", "AccountCategories", "UserDefinedValues" -> true;
            default -> false;
        };
    }

    private String ConfigurationDetail(String encodeValue) {
        switch (encodeValue) {
            case "InventorySettings": {
                return "InventorySettings.detailView";
            }
            case "ApprovalProcessSettings": {
                return "ApprovalProcessSettings.detailView";
            }
            case "ApprovalStages": {
                return "OWST.listView";
            }
            case "ApprovalTemplates": {
                return "OWTM.listView";
            }
            case "SubstituteAuthorizers": {
                return "OAAR.filterableView";
            }
            case "BusinessPartnersSettings": {
                return "BusinesspartnerSettings.detailView";
            }
            case "CompanyDetails": {
                return "OADM.CompanyDetails.detailView";
            }
            case "CompanySettings": {
                return "OADM.CrossSettings.detailView";
            }
            case "States": {
                return "OCST.filterableView";
            }
            case "Competitors": {
                return "OCMT.filterableView";
            }
            case "InformationSources": {
                return "OOSR.filterableView";
            }
            case "InterestRanges": {
                return "OOIN.filterableView";
            }
            case "LevelsOfInterest": {
                return "OOIR.filterableView";
            }
            case "OpportunityStages": {
                return "OOST.filterableView";
            }
            case "PostingPeriodsSettings": {
                return "PostingPeriodSettings.detailView";
            }
            case "PostingPeriodsDefinition": {
                return "OFPR.filterableView";
            }
            case "CommissionGroups": {
                return "OCOG.filterableView";
            }
            case "Partners": {
                return "OPRT.filterableView";
            }
            case "Reasons": {
                return "OOFR.filterableView";
            }
            case "Relationships": {
                return "OORL.filterableView";
            }
            case "PredefinedTexts": {
                return "OPDT.filterableView";
            }
            case "SalesEmployees": {
                return "OSLP.filterableView";
            }
            case "UnitOfMeasureGroups": {
                return "OUGP.listView";
            }
            case "UnitOfMeasure": {
                return "OUOM.filterableView";
            }
            case "ReferenceFieldLinks": {
                return "ORLD.listView";
            }
            case "Freights": {
                return "OEXD.filterableView";
            }
            case "CountriesRegions": {
                return "OCRY.filterableView";
            }
            case "Banks": {
                return "ODSC.filterableView";
            }
            case "CreditCards": {
                return "OCRC.filterableView";
            }
            case "PackageTypes": {
                return "OPKG.filterableView";
            }
            case "Indicators": {
                return "OIDC.filterableView";
            }
            case "ActivityTaskStatuses": {
                return "OCLA.filterableView";
            }
            case "ActivityCategories": {
                return "OCLT.filterableView";
            }
            case "ActivitySubcategories": {
                return "OCLS.filterableView";
            }
            case "MeetingLocations": {
                return "OCLO.filterableView";
            }
            case "SalesAndPurchasingSettings": {
                return "OADM.SalesAndPurchasing.detailView";
            }
            case "SalesDocumentSettings": {
                return "SalesDocumentSettings.detailView";
            }
            case "PurchasingDocumentSettings": {
                return "PurchasingDocumentSettings.detailView";
            }
            case "CustomerGroups": {
                return "OCRG.filterableView&param=%7B%22GroupType%22%3A%22C%22%7D";
            }
            case "VendorGroups": {
                return "OCRG.filterableView";
            }
            case "EmailGroups": {
                return "OEGP.filterableView";
            }
            case "BusinessPartnerPriorities": {
                return "OBPP.filterableView";
            }
            case "BusinessPartnerProperties": {
                return "OCQG.filterableView";
            }
            case "PaymentTerms": {
                return "OCTG.listView";
            }
            case "CashDiscounts": {
                return "OCDC.listView";
            }
            case "Territories": {
                return "OTER.filterableView";
            }
            case "BankChargesAllocationCodes": {
                return "OBCA.filterableView";
            }
            case "HouseBankAccounts": {
                return "DSC1.listView";
            }
            case "CreditCardPayments": {
                return "OCDT.listView";
            }
            case "CreditCardPaymentMethods": {
                return "OCRP.filterableView";
            }
            case "PaymentBlocks": {
                return "OPYB.filterableView";
            }
            case "ItemGroups": {
                return "OITB.listView";
            }
            case "ItemProperties": {
                return "OITG.filterableView";
            }
            case "Warehouses": {
                return "OWHS.listView";
            }
            case "LengthAndWidth": {
                return "OLGT.filterableView";
            }
            case "Weight": {
                return "OWGT.filterableView";
            }
            case "CustomsGroups": {
                return "OARG.filterableView";
            }
            case "Locations": {
                return "OLCT.filterableView";
            }
            case "Manufacturers": {
                return "OMRC.filterableView";
            }
            case "ShippingTypes": {
                return "OSHP.filterableView";
            }
            case "Currencies": {
                return "OCRN.filterableView";
            }
            case "Projects": {
                return "OPRJ.filterableView";
            }
            case "TransactionCodes": {
                return "OTRC.filterableView";
            }
            case "PeriodIndicators": {
                return "OPID.filterableView";
            }
            case "ServiceCallOrigins": {
                return "OSCO.filterableView";
            }
            case "ServiceCallProblemTypes": {
                return "OSCP.filterableView";
            }
            case "ServiceCallProblemSubtypes": {
                return "OPST.filterableView";
            }
            case "ServiceCallStatuses": {
                return "OSCS.filterableView";
            }
            case "ServiceCallTypes": {
                return "OSCT.filterableView";
            }
            case "SolutionStatuses": {
                return "OSST.filterableView";
            }
            case "Languages": {
                return "OLNG.filterableView";
            }
            case "Industries": {
                return "OOND.filterableView";
            }
            case "OpeningBalances": {
                return "AOOPB.filterableView";
            }
            case "JournalEntryTemplates": {
                return "OTRT.listView";
            }
            case "BlanketAgreementSettings": {
                return "BlanketAgreementSettings.detailView";
            }
            case "InventoryDocumentSettings": {
                return "InventoryDocumentSettings.detailView";
            }
            case "ServiceCallDocumentSetting": {
                return "OADMS.detailView";
            }
            case "ServiceContractTemplates": {
                return "OCTT.listView";
            }
            case "ServiceContractQueues": {
                return "OQUE.filterableView";
            }
            case "ActivitiesRecipientsLists": {
                return "ORCI.listView";
            }
            case "AttachmentSettings": {
                return "OADM.AttachmentSettings.detailView";
            }
            case "SalesAndPurchasingLandedCosts": {
                return "OALC.filterableView";
            }
            case "AccountCategories": {
                return "OACG.filterableView";
            }
        }
        return B1S_WEBCLIENT_URL;
    }

    private boolean isUI5Bo(String objTableName) {
        return objTableName.equals(SysBOEnum.ServiceCall.GetTableName()) | objTableName.equals(SysBOEnum.SolutionsKnowledgeBase.GetTableName()) | objTableName.equals(SysBOEnum.IncomingPayments.GetTableName()) | objTableName.equals(SysBOEnum.OutgoingPayments.GetTableName()) | objTableName.equals(SysBOEnum.JournalEntries.GetTableName()) | objTableName.equals(SysBOEnum.TimeSheets.GetTableName()) | objTableName.equals(SysBOEnum.UserQueries.GetTableName()) | objTableName.equals(SysBOEnum.BusinessPartners.GetTableName()) | objTableName.equals(SysBOEnum.Activities.GetTableName()) | objTableName.equals(SysBOEnum.CustomerEquipmentCards.GetTableName()) | objTableName.equals(SysBOEnum.Opportunities.GetTableName());
    }

    <T> T target(Class<T> clazz) {
        return (T)this.builder.logLevel(this.logLevel).target(clazz, this.tcliUrl);
    }

    @Bean
    FeignVariantListService initFeignVariantList() {
        return (FeignVariantListService)this.target(FeignVariantListService.class);
    }
}

