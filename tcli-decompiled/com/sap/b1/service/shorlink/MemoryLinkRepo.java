/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.service.shorlink.LinkRepo
 *  com.sap.b1.service.shorlink.MemoryLinkRepo
 *  com.sap.b1.service.shorlink.MemoryLinkRepo$InnerLink
 */
package com.sap.b1.service.shorlink;

import com.sap.b1.service.shorlink.LinkRepo;
import com.sap.b1.service.shorlink.MemoryLinkRepo;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryLinkRepo
implements LinkRepo {
    private static final int CACHE_SIZE = 2;
    private static Map<String, InnerLink> cache = new ConcurrentHashMap();

    public void store(String id, String origin, String srcLink, String userCode) {
        if (cache.size() > 2) {
            this.removeTheOldestOne();
        }
        cache.put(id, new InnerLink(System.currentTimeMillis(), srcLink));
    }

    private void removeTheOldestOne() {
        long oldest = System.currentTimeMillis();
        String id = null;
        for (Map.Entry entry : cache.entrySet()) {
            if (((InnerLink)entry.getValue()).timestamp >= oldest) continue;
            oldest = ((InnerLink)entry.getValue()).timestamp;
            id = (String)entry.getKey();
        }
        if (id != null) {
            cache.remove(id);
        }
    }

    public String load(String id) {
        InnerLink innerLink = (InnerLink)cache.get(id);
        if (innerLink == null) {
            return null;
        }
        return innerLink.link;
    }

    public void removeExpiredData() {
        long now = System.currentTimeMillis();
        Iterator it = cache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            if (now - 2592000L <= ((InnerLink)entry.getValue()).timestamp) continue;
            it.remove();
        }
    }
}

