/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.infra.engines.metadata.MetaColumns
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.meta.view.cfl.ChooseFromListColumns
 *  com.sap.b1.infra.meta.view.cfl.ChooseFromListView
 *  com.sap.b1.infra.meta.view.list.ListView
 *  com.sap.b1.infra.meta.view.list.ListViewColumns
 *  com.sap.b1.infra.view.ChooseFromListService
 *  com.sap.b1.infra.view.ListViewService
 *  com.sap.b1.service.view.CoreListViewService
 *  com.sap.b1.service.view.ViewServiceResponse
 *  com.sap.b1.servlets.service.variant.sys.DefaultSysVariantGenerator
 *  com.sap.b1.servlets.service.variant.sys.DefaultSysVariantGeneratorImpl
 *  com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 *  com.sap.b1.svcl.client.enums.BoYesNoEnum
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.stereotype.Service
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.app.base.AppEnv;
import com.sap.b1.infra.engines.metadata.MetaColumns;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.infra.meta.view.cfl.ChooseFromListColumns;
import com.sap.b1.infra.meta.view.cfl.ChooseFromListView;
import com.sap.b1.infra.meta.view.list.ListView;
import com.sap.b1.infra.meta.view.list.ListViewColumns;
import com.sap.b1.infra.view.ChooseFromListService;
import com.sap.b1.infra.view.ListViewService;
import com.sap.b1.service.view.CoreListViewService;
import com.sap.b1.service.view.ViewServiceResponse;
import com.sap.b1.servlets.service.variant.sys.DefaultSysVariantGenerator;
import com.sap.b1.svcl.client.complextype.WebClientVariantSelectedColumn;
import com.sap.b1.svcl.client.entitytype.WebClientVariant;
import com.sap.b1.svcl.client.enums.BoYesNoEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DefaultSysVariantGeneratorImpl
implements DefaultSysVariantGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSysVariantGeneratorImpl.class);
    private static final Integer DEFAULT_USER_ID = -1;
    private static final int MAX_VISIABLE_COLUMS_SIZE = 8;
    private static final int MAX_VISIABLE_FILTER_SIZE = 5;
    private static final String VIEW_TYPE_LIST = "List";
    private static final String VIEW_TYPE_CFL = "CFL";
    private static final String LIST_VIEW_ID_SUFFIX = ".listView";
    private static final String CFL_VIEW_ID_SUFFIX = ".chooseFromList";
    @Autowired
    private AppEnv env;
    @Autowired
    ApplicationContext appContext;

    public WebClientVariant createSysVariant(String objName, String viewType, String viewId) throws Exception {
        if (VIEW_TYPE_LIST.equals(viewType)) {
            return this.createSysVariant4ListView(objName, viewType, viewId);
        }
        if (VIEW_TYPE_CFL.equals(viewType)) {
            return this.createSysVariant4CFLView(objName, viewType, viewId);
        }
        logger.warn("not supported view type for variant: {}", (Object)viewType);
        return null;
    }

    private WebClientVariant createSysVariant4CFLView(String objName, String viewType, String viewId) throws Exception {
        ChooseFromListService cflService = (ChooseFromListService)this.tryToFindService(ChooseFromListService.class);
        ViewServiceResponse rsp = null;
        if (cflService != null) {
            String cflViewId = this.toCFLViewId(objName);
            rsp = cflService.load(cflViewId);
        } else {
            logger.warn("can't find service to handle this bo for variant: [objName: {}, viewType: {}, viewId: {}].", new Object[]{objName, viewType, viewId});
        }
        if (rsp != null && rsp.data != null) {
            return this.createSysVariantFromCFLViewRsp(rsp.data, objName, viewType, viewId);
        }
        logger.warn("use meta table info to generate system variant, maybe can't find service to handle this bo: [objName: {}, viewType: {}, viewId: {}].", new Object[]{objName, viewType, viewId});
        return this.createSysVariantFromMetaTable(objName, viewType, viewId);
    }

    private WebClientVariant createSysVariant4ListView(String objName, String viewType, String viewId) throws Exception {
        String name = this.toBeanName(objName);
        String listViewId = this.toListViewId(objName);
        CoreListViewService listViewService = (CoreListViewService)this.tryToFindService(name, CoreListViewService.class);
        ViewServiceResponse rsp = null;
        if (listViewService != null) {
            rsp = listViewService.load(listViewId);
        } else {
            ListViewService lvService = (ListViewService)this.appContext.getBean(ListViewService.class);
            if (lvService != null) {
                rsp = lvService.load(listViewId);
            } else {
                logger.warn("can't find service to handle this bo for variant: [objName: {}, viewType: {}, viewId: {}].", new Object[]{objName, viewType, viewId});
            }
        }
        if (rsp != null && rsp.data != null) {
            return this.createSysVariantFromListViewRsp(rsp.data, objName, viewType, viewId);
        }
        logger.warn("use meta table info to generate system variant, maybe can't find service to handle this bo: [objName: {}, viewType: {}, viewId: {}].", new Object[]{objName, viewType, viewId});
        return this.createSysVariantFromMetaTable(objName, viewType, viewId);
    }

    private <T> T tryToFindService(String name, Class<T> requiredType) {
        try {
            return (T)this.appContext.getBean(name, requiredType);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private <T> T tryToFindService(Class<T> requiredType) {
        try {
            return (T)this.appContext.getBean(requiredType);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private WebClientVariant createSysVariantFromCFLViewRsp(Object data, String objName, String viewType, String viewId) {
        if (!(data instanceof ChooseFromListView)) {
            logger.warn("data in ViewServiceResponse should be instance of ChooseFromListView");
            return null;
        }
        ChooseFromListView cflView = (ChooseFromListView)data;
        List normalizedColumns = this.normalize(cflView.columns);
        return this.createSystemVariant(normalizedColumns, objName, viewType, viewId);
    }

    private WebClientVariant createSysVariantFromListViewRsp(Object data, String objName, String viewType, String viewId) {
        if (!(data instanceof ListView)) {
            logger.warn("data in ViewServiceResponse should be instance of ListView");
            return null;
        }
        ListView listView = (ListView)data;
        List normalizedColumns = this.normalize(listView.columns);
        return this.createSystemVariant(normalizedColumns, objName, viewType, viewId);
    }

    private WebClientVariant createSystemVariant(List<String> columns, String objName, String viewType, String viewId) {
        WebClientVariant dft = this.newDftSysVariant(objName, viewType, viewId);
        int visiableColumns = columns.size();
        int visiableFilters = Math.min(5, columns.size());
        int max = Math.max(visiableColumns, visiableFilters);
        ArrayList<WebClientVariantSelectedColumn> selectedColumns = new ArrayList<WebClientVariantSelectedColumn>();
        StringBuilder filterLayout = new StringBuilder("{\"visibleItems\":[");
        for (int i = 0; i < max; ++i) {
            String columnName = columns.get(i);
            if (i < visiableColumns) {
                WebClientVariantSelectedColumn sColumn = new WebClientVariantSelectedColumn();
                sColumn.Order = i + 1;
                sColumn.ColumnName = columnName;
                sColumn.Guid = UUID.randomUUID().toString();
                selectedColumns.add(sColumn);
            }
            if (i >= visiableFilters) continue;
            filterLayout.append("\"").append(columnName).append("\",");
        }
        filterLayout.deleteCharAt(filterLayout.length() - 1).append("]}");
        dft.FilterBarLayout = filterLayout.toString();
        dft.WebClientVariantSelectedColumnCollection = selectedColumns;
        return dft;
    }

    private String toListViewId(String objName) {
        return objName + LIST_VIEW_ID_SUFFIX;
    }

    private String toCFLViewId(String objName) {
        return objName + CFL_VIEW_ID_SUFFIX;
    }

    private String toBeanName(String objName) {
        return "/lv/" + objName;
    }

    private WebClientVariant createSysVariantFromMetaTable(String objName, String viewType, String viewId) throws Exception {
        MetaTable metaTable = this.env.getMetaTable(objName);
        MetaColumns metaColumns = metaTable.getColumns();
        if (metaColumns.size() == 0) {
            return null;
        }
        List normalizedColumns = this.normalize(metaColumns);
        return this.createSystemVariant(normalizedColumns, objName, viewType, viewId);
    }

    private List<String> normalize(ListViewColumns columns) {
        int max = columns.size();
        return columns.subList(0, max).stream().map(c -> c.getBind()).collect(Collectors.toList());
    }

    private List<String> normalize(ChooseFromListColumns columns) {
        int max = this.calculateMax(columns.size());
        return columns.subList(0, max).stream().map(c -> c.bind).collect(Collectors.toList());
    }

    private List<String> normalize(MetaColumns metaColumns) {
        int max = this.calculateMax(metaColumns.size());
        ArrayList<String> columns = new ArrayList<String>();
        for (int i = 0; i < max; ++i) {
            columns.add(metaColumns.getColumn(i).getColumnName());
        }
        return columns;
    }

    private int calculateMax(int size) {
        return Math.min(Math.max(8, 5), size);
    }

    private WebClientVariant newDftSysVariant(String objName, String viewType, String viewId) {
        WebClientVariant dft = new WebClientVariant();
        dft.Guid = this.buildStaticGuid(objName, viewType, viewId);
        dft.UserId = DEFAULT_USER_ID;
        dft.IsPublic = BoYesNoEnum.tNO;
        dft.IsSystem = BoYesNoEnum.tYES;
        dft.Name = "Standard";
        dft.ObjectName = objName;
        dft.ViewId = viewId;
        dft.ViewType = viewType;
        dft.Order = 1;
        dft.Version = 1;
        dft.OverviewCustomization = "";
        dft.ChartCustomization = "";
        return dft;
    }

    private String buildStaticGuid(String objName, String viewType, String viewId) {
        return "7eb0f1cc-20c4-3e27-8b79-7df40ccc0fd3";
    }
}

