/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.loader.jarmode;

import org.springframework.boot.loader.jarmode.JarModeErrorException;

public interface JarMode {
    public boolean accepts(String var1);

    public void run(String var1, String[] var2) throws JarModeErrorException;
}

