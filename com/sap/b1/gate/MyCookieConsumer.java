/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.MyCookieConsumer
 *  org.springframework.http.ResponseCookie$ResponseCookieBuilder
 */
package com.sap.b1.gate;

import java.util.function.Consumer;
import org.springframework.http.ResponseCookie;

class MyCookieConsumer
implements Consumer<ResponseCookie.ResponseCookieBuilder> {
    MyCookieConsumer() {
    }

    @Override
    public void accept(ResponseCookie.ResponseCookieBuilder t) {
        t.sameSite("None");
        t.secure(true);
    }
}

