/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.service.shorlink.LinkRepo
 *  com.sap.b1.service.shorlink.LinkRepoImpl
 *  com.sap.b1.svcl.client.entityset.ShortLinkMappings
 *  com.sap.b1.svcl.client.entitytype.WebClientShortLink
 *  gen.bean.BmoOSLS
 *  gen.dao.DaoOSLS
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 *  org.springframework.transaction.annotation.Transactional
 */
package com.sap.b1.service.shorlink;

import com.sap.b1.service.shorlink.LinkRepo;
import com.sap.b1.svcl.client.entityset.ShortLinkMappings;
import com.sap.b1.svcl.client.entitytype.WebClientShortLink;
import gen.bean.BmoOSLS;
import gen.dao.DaoOSLS;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LinkRepoImpl
implements LinkRepo {
    @Autowired
    private DaoOSLS daoOSLS;
    @Autowired
    private ShortLinkMappings shortLinkMappings;

    @Transactional
    public void store(String id, String origin, String srcLink, String userCode) {
        Date now = new Date();
        WebClientShortLink entity = new WebClientShortLink();
        entity.Guid = id;
        entity.Origin = origin;
        entity.SrcLink = srcLink;
        entity.OwnerCode = userCode;
        entity.CreateDate = new SimpleDateFormat("yyyy-MM-dd").format(now);
        entity.CreateTime = new SimpleDateFormat("HHmmss").format(now);
        this.shortLinkMappings.create(entity);
    }

    public String load(String id) {
        List d = this.daoOSLS.get(id);
        if (d == null || d.size() == 0) {
            return null;
        }
        return ((BmoOSLS)d.get(0)).getSrcLink();
    }

    public void removeExpiredData() {
        this.shortLinkMappings.clean();
    }
}

