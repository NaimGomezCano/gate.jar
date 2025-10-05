/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.StaticResourceLoader
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.gate;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.resources")
public class StaticResourceLoader {
    private List<String> staticLocations;
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    public List<String> getStaticLocations() {
        return this.staticLocations;
    }

    public void setStaticLocations(List<String> staticLocations) {
        this.staticLocations = staticLocations;
    }

    public Resource loadResource(String path) {
        if (this.staticLocations == null || this.staticLocations.size() == 0) {
            return null;
        }
        for (int i = 0; i < this.staticLocations.size(); ++i) {
            Resource resource;
            String location = (String)this.staticLocations.get(i);
            if (location.endsWith("/")) {
                location = location.substring(0, location.length() - 1);
            }
            if (!(resource = this.resourceLoader.getResource(location + path)).exists()) continue;
            return resource;
        }
        return null;
    }
}

