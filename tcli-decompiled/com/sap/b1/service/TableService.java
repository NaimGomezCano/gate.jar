/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.BoEnv
 *  com.sap.b1.infra.engines.ResourceDefs
 *  com.sap.b1.infra.engines.context.ResourceSystem
 *  com.sap.b1.infra.engines.metadata.MetaTable
 *  com.sap.b1.infra.meta.table.Table
 *  com.sap.b1.service.TableService
 *  com.sap.b1.service.TableServiceResponse
 *  com.sap.b1.spring.RequestMappingTx
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.service;

import com.sap.b1.bo.base.BoEnv;
import com.sap.b1.infra.engines.ResourceDefs;
import com.sap.b1.infra.engines.context.ResourceSystem;
import com.sap.b1.infra.engines.metadata.MetaTable;
import com.sap.b1.infra.meta.table.Table;
import com.sap.b1.service.TableServiceResponse;
import com.sap.b1.spring.RequestMappingTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableService {
    @Autowired
    ResourceSystem resourceSystem;
    @Autowired
    private BoEnv env;

    @RequestMappingTx(path={"/service/metadata/table.svc"})
    public TableServiceResponse getTable(@RequestParam(value="id") String tableName) throws Exception {
        MetaTable table;
        TableServiceResponse rt = new TableServiceResponse();
        rt.data = table = this.env.getMetaTable(tableName);
        return rt;
    }

    @RequestMappingTx(path={"/service/metadata/table2.svc"})
    public TableServiceResponse getTableOriginal(@RequestParam(value="id") String tableName) throws Exception {
        TableServiceResponse rt = new TableServiceResponse();
        String path = ResourceDefs.getPath((String)tableName);
        Table table = (Table)this.resourceSystem.loadResource(path, new Class[]{Table.class});
        table.initColumnsAfterLoadResource();
        table.initColumnsAll();
        rt.data = table;
        return rt;
    }

    @RequestMappingTx(path={"/service/metadata/businessobject.svc"})
    public TableServiceResponse getBusinessObjectMeta(@RequestParam(value="id") String tableName) throws Exception {
        MetaTable table;
        TableServiceResponse rt = new TableServiceResponse();
        rt.data = table = this.env.getMetaTable(tableName);
        return rt;
    }

    @RequestMappingTx(path={"/service/metadata/dimView.svc"})
    public TableServiceResponse getDimView(@RequestParam(value="id") String viewName) throws Exception {
        TableServiceResponse rt = new TableServiceResponse();
        rt.data = this.env.getDimViewMeta(viewName);
        return rt;
    }

    @RequestMappingTx(path={"/service/metadata/tableWithDescCols.svc"})
    public TableServiceResponse getTableWithDescCols(@RequestParam(value="id") String boName) throws Exception {
        MetaTable table;
        TableServiceResponse rt = new TableServiceResponse();
        rt.data = table = this.env.getMetaTableWithDescCols(boName);
        return rt;
    }
}

