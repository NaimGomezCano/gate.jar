/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.CustomPathResourceResolver
 *  com.sap.b1.thin.extn.StaticResourceConfig
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 *  org.springframework.web.servlet.resource.PathResourceResolver
 *  org.springframework.web.servlet.resource.ResourceResolver
 *  org.springframework.web.util.UriUtils
 */
package com.sap.b1.thin.extn;

import com.sap.b1.thin.extn.StaticResourceConfig;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.util.UriUtils;

class CustomPathResourceResolver
extends PathResourceResolver
implements ResourceResolver {
    private static final Logger logger = LoggerFactory.getLogger(StaticResourceConfig.class);

    CustomPathResourceResolver() {
    }

    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        resourcePath = UriUtils.decode((String)resourcePath, (String)"UTF-8");
        return super.getResource(resourcePath, location);
    }
}

