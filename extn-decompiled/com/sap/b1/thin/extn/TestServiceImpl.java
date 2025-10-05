/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.feign.extn.TestService
 *  com.sap.b1.thin.extn.TestServiceImpl
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.extn;

import com.sap.b1.infra.share.feign.extn.TestService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServiceImpl
implements TestService {
    @RequestMapping(method={RequestMethod.GET}, value={"/test/{id}"})
    public List<String> test(Integer id) {
        ArrayList<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        return a;
    }
}

