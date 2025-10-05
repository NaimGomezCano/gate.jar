/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.feign.extn.TestService
 *  com.sap.b1.web.core.TestService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.web.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestService {
    @Autowired
    com.sap.b1.infra.share.feign.extn.TestService service;

    @RequestMapping(value={"/test.svc"})
    List<String> test() {
        return this.service.test(Integer.valueOf(100));
    }
}

