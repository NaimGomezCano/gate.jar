/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.exception.InvalidDataException
 *  com.sap.b1.servlets.service.variant.sys.SecurityOfficer
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.variant.sys;

import com.sap.b1.servlets.service.variant.exception.InvalidDataException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class SecurityOfficer {
    private static final String HASH_ALG = "SHA-256";
    private static final int MAX_CONTENT_SIZE = 20480000;
    private static final byte[] SALTS = "ad!qwe@#134cpo[]{k9(*xbgtd:;'".getBytes();

    public byte[] safePackage(byte[] content) {
        byte[] byArray;
        if (content == null || content.length == 0) {
            throw new IllegalArgumentException("empty content");
        }
        if (content.length > 20480000) {
            throw new IllegalArgumentException("max bytes size exceeded, make sure content size is less then 20M");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(128);
        try {
            byte[] contentSizeBytes = ByteBuffer.allocate(4).putInt(content.length).array();
            bos.write(contentSizeBytes);
            bos.write(content);
            bos.write(this.sign(content, 0, content.length));
            byArray = bos.toByteArray();
        }
        catch (Throwable throwable) {
            try {
                try {
                    bos.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                throw new RuntimeException("package error", e);
            }
        }
        bos.close();
        return byArray;
    }

    public byte[] safeUnpackage(byte[] packagedContent) {
        byte[] realHashed;
        if (packagedContent == null || packagedContent.length < 4) {
            throw new InvalidDataException("empty packaged content");
        }
        if (packagedContent.length > 20480068) {
            throw new InvalidDataException("max bytes size exceeded, it is not a valid package.");
        }
        byte[] sizeBytes = new byte[]{packagedContent[0], packagedContent[1], packagedContent[2], packagedContent[3]};
        int contentSize = ByteBuffer.wrap(sizeBytes).getInt();
        if (packagedContent.length < contentSize + 4) {
            throw new InvalidDataException("it is not a valid package.");
        }
        byte[] srcHashed = Arrays.copyOfRange(packagedContent, contentSize + 4, packagedContent.length);
        if (!this.compare(srcHashed, realHashed = this.sign(packagedContent, 4, contentSize))) {
            throw new InvalidDataException("it is not a valid package.");
        }
        return Arrays.copyOfRange(packagedContent, 4, contentSize + 4);
    }

    private boolean compare(byte[] srcHashed, byte[] realHashed) {
        if (srcHashed.length != realHashed.length) {
            return false;
        }
        for (int i = 0; i < srcHashed.length; ++i) {
            if (srcHashed[i] == realHashed[i]) continue;
            return false;
        }
        return true;
    }

    private byte[] sign(byte[] content, int offset, int len) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALG);
            digest.reset();
            digest.update(SALTS);
            digest.update(content, offset, len);
            byte[] hashedBytes = digest.digest();
            return hashedBytes;
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("no such algorithm: SHA-256", e);
        }
    }
}

