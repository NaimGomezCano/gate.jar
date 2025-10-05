/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.HashedHttpCache
 *  com.sap.b1.thin.extn.HttpCache
 *  com.sap.b1.thin.extn.ProxyResponse
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.extn;

import com.sap.b1.thin.extn.HttpCache;
import com.sap.b1.thin.extn.ProxyResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashedHttpCache
implements HttpCache {
    private static final Logger logger = LoggerFactory.getLogger(HashedHttpCache.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProxyResponse get(String path, String version) {
        block31: {
            if (path == null) {
                logger.warn("Path should not be null");
                return null;
            }
            ObjectInputStream headerInputStream = null;
            try {
                String key = this.buildKey(path, version);
                if (key == null) {
                    ProxyResponse proxyResponse = null;
                    return proxyResponse;
                }
                String bodyFilePath = CACHE_HOME + File.separator + key;
                String headerFilePath = bodyFilePath + ".headers";
                File bodyFile = new File(bodyFilePath);
                if (!bodyFile.exists()) break block31;
                ProxyResponse response = new ProxyResponse();
                FileInputStream bodyInputStream = new FileInputStream(bodyFilePath);
                try {
                    byte[] buffer = IOUtils.toByteArray((InputStream)bodyInputStream);
                    ((InputStream)bodyInputStream).close();
                    File headerFile = new File(headerFilePath);
                    if (headerFile.exists()) {
                        headerInputStream = new ObjectInputStream(new FileInputStream(headerFile));
                        Map headers = (Map)headerInputStream.readObject();
                        response.setHeaders(headers);
                    }
                    response.setBody(buffer);
                    response.setStatusCode(200);
                    response.setStatusMessage("Load from local cache.");
                }
                finally {
                    try {
                        ((InputStream)bodyInputStream).close();
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
                ProxyResponse proxyResponse = response;
                return proxyResponse;
            }
            catch (IOException e) {
                logger.error("Failed to get addon resource: [{}]", (Object)e.getMessage());
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
            finally {
                try {
                    if (headerInputStream != null) {
                        headerInputStream.close();
                    }
                }
                catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProxyResponse get(String path, int extensionId) {
        File f = new File(CACHE_HOME + File.separator + String.valueOf(extensionId));
        InputStream bodyInputStream = null;
        try {
            File[] paths = f.listFiles();
            if (paths.length > 0) {
                Object p = f.getPath();
                File bodyFile = new File((String)(p = (String)p + File.separator + path));
                if (bodyFile.exists()) {
                    ProxyResponse response = new ProxyResponse();
                    bodyInputStream = new FileInputStream((String)p);
                    byte[] buffer = IOUtils.toByteArray((InputStream)bodyInputStream);
                    bodyInputStream.close();
                    response.setBody(buffer);
                    response.setStatusCode(200);
                    response.setStatusMessage("Load from local cache.");
                    ProxyResponse proxyResponse = response;
                    return proxyResponse;
                }
                System.err.println(String.format("Proxy to file: {%s} not exist", bodyFile.getPath()));
            }
        }
        catch (IOException e) {
            logger.error("Proxy to file for {%s} failed", (Object)path);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (bodyInputStream != null) {
                    bodyInputStream.close();
                }
            }
            catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    public void put(String path, String version, ProxyResponse proxyResp) {
    }

    String buildKey(String path, String version) {
        try {
            return DigestUtils.sha256Hex((String)(version + File.separator + path));
        }
        catch (Exception e) {
            logger.warn("build cache key failed");
            return null;
        }
    }

    public Boolean isCached(int extensionId) {
        File file = new File(CACHE_HOME + File.separator + String.valueOf(extensionId));
        return file.exists() && file.list().length > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProxyResponse getPackageJson(int extensionId) {
        InputStream bodyInputStream = null;
        try {
            File bodyFile = new File(CACHE_HOME + File.separator + String.valueOf(extensionId) + File.separator + "package.json");
            if (bodyFile.exists()) {
                ProxyResponse response = new ProxyResponse();
                bodyInputStream = new FileInputStream(bodyFile);
                byte[] buffer = IOUtils.toByteArray((InputStream)bodyInputStream);
                response.setBody(buffer);
                response.setStatusCode(200);
                response.setStatusMessage("Load from local cache.");
                ProxyResponse proxyResponse = response;
                return proxyResponse;
            }
            System.out.println("The package.json for  not exit");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (bodyInputStream != null) {
                    bodyInputStream.close();
                }
            }
            catch (IOException iOException) {}
        }
        return null;
    }
}

