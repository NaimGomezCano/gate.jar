/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.sys.FilterUtilsParser
 *  org.graalvm.polyglot.Context
 *  org.graalvm.polyglot.Source
 *  org.graalvm.polyglot.Value
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.servlets.service.variant.sys;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterUtilsParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtilsParser.class);
    private static final FilterUtilsParser instance = new FilterUtilsParser();
    private Value filterStringToModel;
    private boolean enableDebug = false;

    public static FilterUtilsParser getInstance() {
        return instance;
    }

    private FilterUtilsParser() {
        Context jsContext = this.getContext();
        try {
            URL url = this.getClass().getClassLoader().getResource("VariantSapUI.js");
            jsContext.eval(Source.newBuilder((String)"js", (URL)url).build());
            url = this.getClass().getClassLoader().getResource("ExpressionParser2.js");
            jsContext.eval(Source.newBuilder((String)"js", (URL)url).build());
            url = this.getClass().getClassLoader().getResource("FilterUtil.js");
            Value fileUtils = jsContext.eval(Source.newBuilder((String)"js", (URL)url).build());
            this.filterStringToModel = fileUtils.getMember("filterStringToModel");
        }
        catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error occurs in initializing FilterUtil: [{}]", (Object)e.getMessage());
        }
    }

    public synchronized List<String> getColumnNames(String filter) {
        if (filter == null || "".equals(filter)) {
            return new ArrayList<String>();
        }
        Value mockFilter = this.filterStringToModel.execute(new Object[]{filter});
        Value getFieldNames = mockFilter.getMember("getFieldNames");
        Value result = getFieldNames.execute(new Object[0]);
        List columnNames = this.getColumnNames(result);
        return columnNames.stream().distinct().collect(Collectors.toList());
    }

    private List<String> getColumnNames(Value v) {
        ArrayList<String> columnNames;
        block4: {
            block3: {
                if (v == null) {
                    return new ArrayList<String>();
                }
                columnNames = new ArrayList<String>();
                if (!v.isIterator()) break block3;
                while (v.hasIteratorNextElement()) {
                    Value iteratorNextElement = v.getIteratorNextElement();
                    columnNames.add(iteratorNextElement.asString());
                }
                break block4;
            }
            if (!v.hasArrayElements()) break block4;
            long arraySize = v.getArraySize();
            for (long i = 0L; i < arraySize; ++i) {
                columnNames.add(v.getArrayElement(i).asString());
            }
        }
        return columnNames;
    }

    private Context getContext() {
        if (this.enableDebug) {
            String port = "5000";
            String path = UUID.randomUUID().toString();
            Context jsContext = Context.newBuilder((String[])new String[]{"js"}).option("inspect", port).option("inspect.Path", path).build();
            String hostAdress = "localhost";
            String url = String.format("chrome-devtools://devtools/bundled/js_app.html?ws=%s:%s/%s", hostAdress, port, path);
            return jsContext;
        }
        return Context.newBuilder((String[])new String[]{"js"}).build();
    }
}

