/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.flp.tile.TileAdapter
 *  com.sap.b1.flp.tile.TileService
 *  com.sap.b1.infra.meta.tile.Action
 *  com.sap.b1.infra.meta.tile.Group
 *  com.sap.b1.infra.meta.tile.LaunchPage
 *  com.sap.b1.infra.meta.tile.Tile
 *  com.sap.b1.infra.share.feign.tcli.entity.AnlyView
 *  com.sap.b1.servlets.service.anly.AnalyticsViewAdapter
 *  com.sap.b1.servlets.service.anly.AnalyticsViewService
 *  com.sap.b1.util.StringUtil
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.anly;

import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.flp.tile.TileAdapter;
import com.sap.b1.flp.tile.TileService;
import com.sap.b1.infra.meta.tile.Action;
import com.sap.b1.infra.meta.tile.Group;
import com.sap.b1.infra.meta.tile.LaunchPage;
import com.sap.b1.infra.meta.tile.Tile;
import com.sap.b1.infra.share.feign.tcli.entity.AnlyView;
import com.sap.b1.servlets.service.anly.AnalyticsViewAdapter;
import com.sap.b1.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsViewService {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsViewService.class);
    @Autowired
    TileAdapter adapter;
    @Autowired
    TileService tileService;
    @Autowired
    DBTypeConfig dbTypeConfig;
    @Autowired
    AnalyticsViewAdapter analyticsViewAdapter;
    public static final String GENERAL_OVERVIEW_GUID = "39175606-1ff4-4369-bac9-0d3bf1a0270b";

    @RequestMapping(path={"/analyticsViews.svc"})
    public List<AnlyView> getAnlyViewsByType(@RequestParam String analyticsType) throws Exception {
        List tiles;
        Group anlyGroup = null;
        ArrayList<AnlyView> viewList = new ArrayList<AnlyView>();
        LaunchPage data = this.adapter.loadAnlyTiles();
        this.tileService.applyPermissionAndLicense(data);
        if (data.groups != null && data.groups.size() > 0) {
            anlyGroup = (Group)data.groups.get(0);
        }
        if (anlyGroup == null) {
            return viewList;
        }
        if (DBType.HANA == this.dbTypeConfig.get() && this.analyticsViewAdapter.isB1ahEnabled() && anlyGroup != null && (tiles = anlyGroup.tiles) != null) {
            for (Tile tile : tiles) {
                String viewId;
                String bind;
                String target;
                Action action;
                boolean visible = tile.visible;
                if (!visible || (action = tile.action) == null || (target = action.target) == null || !"url".equals(bind = action.bind) || !target.contains(analyticsType) || !target.contains("view=") || StringUtil.isEmpty((String)(viewId = target.substring(target.indexOf("view=") + 5)))) continue;
                AnlyView anlyView = new AnlyView();
                anlyView.setViewId(viewId);
                anlyView.setDescription(tile.text);
                viewList.add(anlyView);
            }
        }
        if (DBType.MSSQL == this.dbTypeConfig.get() || DBType.HANA == this.dbTypeConfig.get() && !this.analyticsViewAdapter.isB1ahEnabled()) {
            if ("ChartContainer".equals(analyticsType)) {
                return viewList;
            }
            if ("Dashboard".equals(analyticsType)) {
                Tile tile = anlyGroup.tiles.stream().filter(v -> GENERAL_OVERVIEW_GUID.equals(v.guid)).findFirst().get();
                String target = tile.action.target;
                String viewId = target.substring(target.indexOf("view=") + 5);
                AnlyView anlyView = new AnlyView();
                anlyView.setViewId(viewId);
                anlyView.setDescription(tile.text);
                viewList.add(anlyView);
            }
        }
        return viewList;
    }
}

