/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4EdmProvider
 *  com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4Servlet
 *  jakarta.servlet.http.HttpServlet
 *  org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider
 */
package com.sap.sme.anw.analytics.thin.odata.odata4;

import com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4EdmProvider;
import jakarta.servlet.http.HttpServlet;
import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;

public class ODataService4Servlet
extends HttpServlet {
    private static final long serialVersionUID = 2129773986725292256L;
    private static final CsdlEdmProvider edmProvider = new ODataService4EdmProvider();
}

