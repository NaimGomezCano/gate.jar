/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.placeholder.IAnalyticsPlaceholder
 *  com.sap.sme.anw.analytics.core.placeholderfactory.CoreAnalyticsPlaceholderFactory
 *  com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholder
 *  com.sap.sme.anw.analytics.thin.placeholderfactory.AnalyticsPlaceholderFactory
 */
package com.sap.sme.anw.analytics.thin.placeholderfactory;

import com.sap.sme.anw.analytics.core.placeholder.IAnalyticsPlaceholder;
import com.sap.sme.anw.analytics.core.placeholderfactory.CoreAnalyticsPlaceholderFactory;
import com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholder;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsPlaceholderFactory
extends CoreAnalyticsPlaceholderFactory {
    private static final IAnalyticsPlaceholder thinFilterPlaceholder = new ThinFilterPlaceholder();

    public List<IAnalyticsPlaceholder> getPlaceholders() {
        ArrayList<IAnalyticsPlaceholder> placeholders = new ArrayList<IAnalyticsPlaceholder>();
        placeholders.addAll(super.getPlaceholders());
        placeholders.add(thinFilterPlaceholder);
        return placeholders;
    }
}

