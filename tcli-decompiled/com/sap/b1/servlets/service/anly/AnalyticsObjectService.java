/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.anly.AnalyticsObjectService
 *  com.sap.b1.tcli.object.ObjectService
 *  com.sap.b1.tcli.object.domain.ObjectDefs
 *  com.sap.b1.tcli.object.domain.Objects
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.servlets.service.anly;

import com.sap.b1.tcli.object.ObjectService;
import com.sap.b1.tcli.object.domain.ObjectDefs;
import com.sap.b1.tcli.object.domain.Objects;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsObjectService {
    @Autowired
    ObjectService objectService;

    @RequestMapping(path={"/analyticsObjects.svc"})
    public Objects getObjects() throws Exception {
        Objects objects = this.objectService.getObjects();
        Objects result = new Objects();
        for (Map.Entry entry : objects.entrySet()) {
            ObjectDefs def = (ObjectDefs)entry.getValue();
            if (!def.analyticViews && !def.analyticList) continue;
            result.put((Object)((String)entry.getKey()), (Object)((ObjectDefs)entry.getValue()));
        }
        return result;
    }
}

