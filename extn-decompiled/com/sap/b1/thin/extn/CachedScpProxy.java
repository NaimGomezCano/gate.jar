/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.boot.sld.SldClient
 *  com.sap.b1.sdk.oidc.core.handler.OAuth2Handler
 *  com.sap.b1.sdk.oidc.core.handler.token.ClientToken
 *  com.sap.b1.thin.extn.CachedScpProxy
 *  com.sap.b1.thin.extn.HashedHttpCache
 *  com.sap.b1.thin.extn.HttpCache
 *  com.sap.b1.thin.extn.ProxyResponse
 *  com.sap.b1.thin.extn.SLDExtensionMeta
 *  com.sap.b1.thin.extn.apiversion.PVersion1$ModuleInfo
 *  feign.Response
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.cloudfoundry.multiapps.common.ContentException
 *  org.cloudfoundry.multiapps.common.ParsingException
 *  org.cloudfoundry.multiapps.common.SLException
 *  org.cloudfoundry.multiapps.mta.handlers.v3.DescriptorParser
 *  org.cloudfoundry.multiapps.mta.model.DeploymentDescriptor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.yaml.snakeyaml.Yaml
 */
package com.sap.b1.thin.extn;

import com.google.gson.Gson;
import com.sap.b1.boot.Bootstrap;
import com.sap.b1.boot.sld.SldClient;
import com.sap.b1.sdk.oidc.core.handler.OAuth2Handler;
import com.sap.b1.sdk.oidc.core.handler.token.ClientToken;
import com.sap.b1.thin.extn.HashedHttpCache;
import com.sap.b1.thin.extn.HttpCache;
import com.sap.b1.thin.extn.ProxyResponse;
import com.sap.b1.thin.extn.SLDExtensionMeta;
import com.sap.b1.thin.extn.apiversion.PVersion1;
import feign.Response;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.CallSite;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.cloudfoundry.multiapps.common.ContentException;
import org.cloudfoundry.multiapps.common.ParsingException;
import org.cloudfoundry.multiapps.common.SLException;
import org.cloudfoundry.multiapps.mta.handlers.v3.DescriptorParser;
import org.cloudfoundry.multiapps.mta.model.DeploymentDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/*
 * Exception performing whole class analysis ignored.
 */
public class CachedScpProxy {
    private static final Logger logger = LoggerFactory.getLogger(CachedScpProxy.class);
    private static final long MAX_MANIFEST_SIZE = 0x100000L;
    private static final long MAX_MTA_DESCRIPTOR_SIZE = 0x100000L;
    private static final long MAX_RESOURCE_FILE_SIZE = 0x40000000L;
    private static final CachedScpProxy INSTANCE = new CachedScpProxy();
    private static final String MTA_PATH = "META-INF/mtad.yaml";
    private final HttpCache cache = new HashedHttpCache();
    static final String CACHE_HOME = (System.getenv("B1_CACHE_HOME") == null ? System.getProperty("java.io.tmpdir") : System.getenv("B1_CACHE_HOME")) + File.separator + "b1-thinclient-cache";

    private CachedScpProxy() {
    }

    public static CachedScpProxy getInstance() {
        return INSTANCE;
    }

    public ProxyResponse proxyPackageJson(int extensionId) throws KeyManagementException, MalformedURLException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        return this.cache.getPackageJson(extensionId);
    }

    public ProxyResponse proxyToCache(int extensionId, String path) {
        return this.cache.get(path, extensionId);
    }

    public Boolean isCached(int extensionId) {
        return this.cache.isCached(extensionId);
    }

    public void refreshCacheIfNeed(String value) {
        File[] flist;
        ArrayList<String> cacheFolders = new ArrayList<String>();
        File cacheHome = new File(CACHE_HOME + File.separator);
        if (!cacheHome.exists()) {
            CachedScpProxy.createDirectoriesWithPerm((Path)cacheHome.toPath());
        }
        for (File f : flist = cacheHome.listFiles()) {
            if (!f.isDirectory()) continue;
            cacheFolders.add(f.getName());
        }
        HashMap<Integer, String> hmap = new HashMap<Integer, String>();
        String[] versions = value.split(";");
        for (String v : versions) {
            String[] ve = v.split(":");
            int id = Integer.parseInt(ve[0]);
            String version = ve[1];
            hmap.put(id, version);
            for (int i = 0; i < cacheFolders.size(); ++i) {
                if (!((String)cacheFolders.get(i)).equals(id)) continue;
                cacheFolders.remove(i);
                break;
            }
            this.cacheInLocal(id, version, null);
        }
        for (String id : cacheFolders) {
            File idFolder = new File(CACHE_HOME + File.separator + id);
            try {
                FileUtils.deleteDirectory((File)idFolder);
            }
            catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void saveEntryStream(byte[] content, String entryName, String modulefolder, boolean isZip) throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(content));
        try {
            ZipEntry e;
            while ((e = zis.getNextEntry()) != null) {
                if (!e.getName().equals(entryName)) continue;
                Object projectfolderStr = "";
                if (isZip) {
                    projectfolderStr = modulefolder + File.separator + e.getName().substring(0, e.getName().length() - 4) + File.separator;
                    CachedScpProxy.CheckZipEntryPath((String)projectfolderStr, (String)modulefolder);
                    File file2 = new File((String)projectfolderStr);
                    if (!file2.exists() || !file2.isDirectory()) {
                        CachedScpProxy.createDirectoriesWithPerm((Path)file2.toPath());
                    }
                } else {
                    projectfolderStr = modulefolder + File.separator + e.getName();
                    CachedScpProxy.CheckZipEntryPath((String)projectfolderStr, (String)modulefolder);
                }
                File zipTmp = new File(modulefolder + File.separator + e.getName());
                if (!zipTmp.toPath().normalize().startsWith(modulefolder)) {
                    throw new ContentException("Bad zip entry path");
                }
                if (e.isDirectory()) {
                    CachedScpProxy.createDirectoriesWithPerm((Path)zipTmp.toPath());
                    return;
                }
                CachedScpProxy.createFileWithPerm((Path)zipTmp.toPath());
                FileOutputStream outputStream = new FileOutputStream(zipTmp);
                try {
                    int bytesRead = -1;
                    byte[] buff = new byte[4096];
                    while ((bytesRead = zis.read(buff)) != -1) {
                        outputStream.write(buff, 0, bytesRead);
                    }
                    outputStream.flush();
                    outputStream.getFD().sync();
                }
                finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        }
                        catch (IOException ex) {
                            logger.error(ex.getMessage());
                        }
                    }
                }
                if (!isZip) return;
                FileInputStream fsStream = new FileInputStream(zipTmp.getAbsolutePath());
                ZipInputStream zpStream = new ZipInputStream(fsStream);
                try {
                    File projectfolder = new File((String)projectfolderStr);
                    CachedScpProxy.unzipFile((ZipInputStream)zpStream, (File)projectfolder);
                    return;
                }
                finally {
                    if (zpStream != null) {
                        try {
                            zpStream.close();
                        }
                        catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    if (fsStream != null) {
                        try {
                            fsStream.close();
                        }
                        catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    zipTmp.delete();
                    return;
                }
            }
        }
        finally {
            if (zis != null) {
                try {
                    zis.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public synchronized void manageAddonCache(SLDExtensionMeta[] deployedExtension, SLDExtensionMeta[] assignedExtension, boolean ifClear) throws IOException {
        File cacheHome = new File(CACHE_HOME + File.separator);
        if (!cacheHome.exists()) {
            CachedScpProxy.createDirectoriesWithPerm((Path)cacheHome.toPath());
        }
        if (ifClear) {
            File[] list;
            File[] fileArray = list = cacheHome.listFiles();
            int n = fileArray.length;
            for (int i = 0; i < n; ++i) {
                File f = fileArray[i];
                boolean flag = false;
                for (SLDExtensionMeta ext : deployedExtension) {
                    if (!ext.getID().toString().equals(f.getName())) continue;
                    flag = true;
                    break;
                }
                if (flag) continue;
                FileUtils.deleteDirectory((File)f);
            }
        }
        for (SLDExtensionMeta ext : assignedExtension) {
            this.cacheInLocal(ext.getID().intValue(), ext.getVersion(), ext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void cacheInLocal(int extensionId, String version, SLDExtensionMeta extensionMeta) {
        block68: {
            if (this.cache.isCached(extensionId).booleanValue()) {
                String localVersion = this.readVersionFromDescriptor(extensionId);
                if (version.equals(localVersion)) {
                    String appsString = this.readFioriAppsFromLocal(extensionId, extensionMeta.getModules());
                    extensionMeta.setFioriApps(appsString);
                    return;
                }
                File idFolder = new File(CACHE_HOME + File.separator + String.valueOf(extensionId));
                try {
                    FileUtils.deleteDirectory((File)idFolder);
                    logger.info("The old version in the cache removed.  Addon id is:  {}.", (Object)extensionId);
                }
                catch (IOException e) {
                    logger.error("Error in function cacheInLocal when delete Cached folder, Error is : {}", (Throwable)e);
                }
            }
            try {
                Bootstrap bootstrap = Bootstrap.getInstance();
                SldClient sldClient = bootstrap.getSldClient();
                ClientToken token = OAuth2Handler.getInstance().fetchClientCredential();
                HashMap<String, CallSite> headerMap = new HashMap<String, CallSite>();
                headerMap.put("Authorization", (CallSite)((Object)("Bearer " + token.getAccessToken())));
                try (Response response = sldClient.getSldService().downloadExtension(String.valueOf(extensionId), headerMap);){
                    if (response.status() != 200) break block68;
                    try (InputStream inStream = response.body().asInputStream();){
                        String saveMtarFileStr = CACHE_HOME + File.separator + String.valueOf(extensionId) + ".mtar";
                        File mtarFile = new File(saveMtarFileStr);
                        if (mtarFile.exists()) {
                            mtarFile.delete();
                        }
                        logger.info("Create mtar file in local cache folder: {}.  Addon id is:  {}.", (Object)saveMtarFileStr, (Object)extensionId);
                        CachedScpProxy.createFileWithPerm((Path)mtarFile.toPath());
                        FileOutputStream outputStream = new FileOutputStream(saveMtarFileStr);
                        try {
                            int bytesRead = -1;
                            byte[] buff = new byte[4096];
                            while ((bytesRead = inStream.read(buff)) != -1) {
                                outputStream.write(buff, 0, bytesRead);
                            }
                            outputStream.flush();
                            outputStream.getFD().sync();
                        }
                        finally {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                }
                                catch (IOException e) {
                                    logger.error(e.getMessage());
                                }
                            }
                        }
                        String folder = CACHE_HOME + File.separator + String.valueOf(extensionId);
                        File file = new File(folder);
                        if (!file.exists() || !file.isDirectory()) {
                            CachedScpProxy.createDirectoriesWithPerm((Path)file.toPath());
                        }
                        logger.info("Begin exact zip file in local cache folder: {}.  Addon id is:  {}.", (Object)folder, (Object)extensionId);
                        FileInputStream mtarInStream = new FileInputStream(mtarFile.getAbsolutePath());
                        InputStream manifestStream = CachedScpProxy.getInputStream((InputStream)mtarInStream, (String)"META-INF/MANIFEST.MF", (long)0x100000L);
                        try {
                            Manifest manifest = new Manifest(manifestStream);
                            Map<String, Attributes> entries = manifest.getEntries();
                            HashMap<String, String> modulesPath = new HashMap<String, String>();
                            for (Map.Entry<String, Attributes> entry : entries.entrySet()) {
                                if (entry.getKey().toString().equals("META-INF/mtad.yaml")) continue;
                                Attributes subNode = entry.getValue();
                                for (Map.Entry<Object, Object> entry2 : subNode.entrySet()) {
                                    if (entry2.getKey() == null || !entry2.getKey().toString().equals("MTA-Module")) continue;
                                    modulesPath.put(entry2.getValue().toString(), entry.getKey().toString());
                                }
                            }
                            mtarInStream = new FileInputStream(mtarFile.getAbsolutePath());
                            InputStream YAMLStream = CachedScpProxy.getInputStream((InputStream)mtarInStream, (String)"META-INF/mtad.yaml", (long)0x100000L);
                            CachedScpProxy.saveToFile((InputStream)YAMLStream, (String)folder, (String)(File.separator + "mtad.yaml"));
                            ArrayList<String> fioriApps = new ArrayList<String>();
                            List modulelist = (List)new Gson().fromJson(extensionMeta.getModules(), new /* Unavailable Anonymous Inner Class!! */.getType());
                            for (Map.Entry<Object, Object> entry : modulesPath.entrySet()) {
                                byte[] moduleContent;
                                String moduleType = "";
                                mtarInStream = new FileInputStream(mtarFile.getAbsolutePath());
                                String moduleFolderString = folder + File.separator + entry.getKey().toString();
                                File moduleDic = new File(moduleFolderString);
                                if (!moduleDic.exists() || !moduleDic.isDirectory()) {
                                    CachedScpProxy.createDirectoriesWithPerm((Path)moduleDic.toPath());
                                }
                                for (PVersion1.ModuleInfo module : modulelist) {
                                    if (!module.getName().equals(entry.getKey())) continue;
                                    moduleType = module.getType();
                                    break;
                                }
                                InputStream moduleStream = CachedScpProxy.getInputStream((InputStream)mtarInStream, (String)entry.getValue().toString(), (long)0x40000000L);
                                try {
                                    moduleContent = IOUtils.toByteArray((InputStream)moduleStream);
                                }
                                finally {
                                    if (moduleStream != null) {
                                        try {
                                            moduleStream.close();
                                        }
                                        catch (IOException e1) {
                                            logger.error(e1.getMessage());
                                        }
                                    }
                                }
                                boolean ifSelfDesignedYaml = false;
                                ZipInputStream moduleZipStream = new ZipInputStream(new ByteArrayInputStream(moduleContent));
                                try {
                                    ZipEntry e;
                                    while ((e = moduleZipStream.getNextEntry()) != null) {
                                        if (moduleType.equals("fiori-app")) {
                                            if (e.getName().toLowerCase().endsWith(".zip") && !ifSelfDesignedYaml) {
                                                fioriApps.add(e.getName().substring(0, e.getName().length() - 4));
                                                this.saveEntryStream(moduleContent, e.getName(), moduleFolderString, true);
                                                continue;
                                            }
                                            if (!ifSelfDesignedYaml) {
                                                File singleModuleDic = new File(moduleFolderString = moduleFolderString + File.separator + entry.getKey().toString());
                                                if (!singleModuleDic.exists() || !singleModuleDic.isDirectory()) {
                                                    CachedScpProxy.createDirectoriesWithPerm((Path)singleModuleDic.toPath());
                                                }
                                                fioriApps.add(entry.getKey().toString());
                                            }
                                            ifSelfDesignedYaml = true;
                                            File fileSingleAppFolder = new File(moduleFolderString + File.separator + e.getName());
                                            if (!fileSingleAppFolder.toPath().normalize().startsWith(moduleFolderString)) {
                                                throw new ContentException("Bad zip entry path");
                                            }
                                            if (e.isDirectory()) {
                                                CachedScpProxy.createDirectoriesWithPerm((Path)fileSingleAppFolder.toPath());
                                                continue;
                                            }
                                            this.saveEntryStream(moduleContent, e.getName(), moduleFolderString, false);
                                            continue;
                                        }
                                        this.saveEntryStream(moduleContent, e.getName(), moduleFolderString, false);
                                    }
                                }
                                finally {
                                    if (moduleZipStream == null) continue;
                                    try {
                                        moduleZipStream.close();
                                    }
                                    catch (IOException iOException) {}
                                }
                            }
                            extensionMeta.setFioriApps(new Gson().toJson(fioriApps));
                        }
                        finally {
                            if (manifestStream != null) {
                                try {
                                    manifestStream.close();
                                }
                                catch (IOException e1) {
                                    logger.error(e1.getMessage());
                                }
                            }
                        }
                        mtarFile.delete();
                    }
                    catch (Exception e) {
                        logger.error("Error in function cacheInLocal when downloadExtension from SLD , Error is : {}", (Throwable)e);
                    }
                }
                catch (Exception e) {
                    logger.error("Error in function cacheInLocal when downloadExtension from SLD , Error is : {}", (Throwable)e);
                }
            }
            catch (Exception e) {
                logger.error("Error in function cacheInLocal when downloadExtension from SLD , Error is : {}", (Throwable)e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static File saveToFile(InputStream in, String outPath, String fileName) {
        FilterOutputStream out = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            int nn;
            File dir = new File(outPath);
            if (!dir.exists() && dir.isDirectory()) {
                CachedScpProxy.createDirectoriesWithPerm((Path)dir.toPath());
            }
            file = new File(outPath + File.separator + fileName);
            CachedScpProxy.createFileWithPerm((Path)file.toPath());
            fos = new FileOutputStream(file);
            out = new BufferedOutputStream(fos);
            byte[] b = new byte[1024];
            while ((nn = in.read(b)) > 0) {
                ((BufferedOutputStream)out).write(b, 0, nn);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage());
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e1) {
                    logger.error(e1.getMessage());
                }
            }
        }
        return file;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DeploymentDescriptor loadDeploymentDescriptor(String location, DescriptorParser parser, Class<?> clazz) throws ParsingException, IOException {
        FileInputStream deploymentDescriptorYaml = new FileInputStream(location);
        try {
            Map deploymentDescriptor = (Map)new Yaml().load((InputStream)deploymentDescriptorYaml);
            DeploymentDescriptor deploymentDescriptor2 = parser.parseDeploymentDescriptor(deploymentDescriptor);
            return deploymentDescriptor2;
        }
        finally {
            if (deploymentDescriptorYaml != null) {
                try {
                    deploymentDescriptorYaml.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private String readVersionFromDescriptor(int extensionId) {
        try {
            File[] flist;
            File idFolder = new File(CACHE_HOME + File.separator + String.valueOf(extensionId));
            for (File f : flist = idFolder.listFiles()) {
                if (!f.getName().startsWith("mtad.yaml")) continue;
                DeploymentDescriptor mergedDescriptor = CachedScpProxy.loadDeploymentDescriptor((String)f.getAbsolutePath(), (DescriptorParser)new DescriptorParser(), this.getClass());
                return mergedDescriptor.getVersion();
            }
        }
        catch (FileNotFoundException e) {
            logger.error("Error in function readVersionFromPackageJson id is : {} , Error is : {}", (Object)extensionId, (Object)e);
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    private String readFioriAppsFromLocal(int extensionId, String modules) {
        ArrayList<String> fioriApps = new ArrayList<String>();
        try {
            List modulelist = (List)new Gson().fromJson(modules, new /* Unavailable Anonymous Inner Class!! */.getType());
            for (PVersion1.ModuleInfo module : modulelist) {
                File[] flist;
                if (!module.getType().equals("fiori-app")) continue;
                File projectFolder = new File(CACHE_HOME + File.separator + String.valueOf(extensionId) + File.separator + module.getName());
                for (File f : flist = projectFolder.listFiles()) {
                    if (!f.isDirectory()) continue;
                    fioriApps.add(f.getName());
                }
            }
        }
        catch (Exception e) {
            logger.error("Error in function readVersionFromPackageJson id is : {} , Error is : {}", (Object)extensionId, (Object)e);
        }
        return new Gson().toJson(fioriApps);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void unzipFile(ZipInputStream zis, File folderFile) throws IOException {
        ZipEntry entry;
        byte[] buffer = new byte[10240];
        FileOutputStream fos = null;
        while ((entry = zis.getNextEntry()) != null) {
            File file;
            if (entry.isDirectory()) {
                file = new File(folderFile, entry.getName());
                if (!file.toPath().normalize().startsWith(folderFile.toPath())) {
                    throw new ContentException("Bad zip entry path");
                }
                CachedScpProxy.createDirectoriesWithPerm((Path)file.toPath());
                continue;
            }
            file = null;
            try {
                int len;
                File targetFolder;
                file = new File(folderFile, entry.getName());
                if (!file.toPath().normalize().startsWith(folderFile.toPath())) {
                    throw new ContentException("Bad zip entry path");
                }
                if (!file.exists() && !(targetFolder = file.getParentFile()).exists()) {
                    CachedScpProxy.createDirectoriesWithPerm((Path)targetFolder.toPath());
                }
                CachedScpProxy.createFileWithPerm((Path)file.toPath());
                fos = new FileOutputStream(file);
                while ((len = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
                fos.getFD().sync();
            }
            finally {
                if (fos == null) continue;
                try {
                    fos.close();
                }
                catch (IOException e) {
                    logger.error("Error while closing file: " + file.getPath() + "::", (Throwable)e);
                }
                fos = null;
            }
        }
    }

    public static InputStream getInputStream(InputStream is, String entryName, long maxEntrySize) {
        try {
            ZipEntry e;
            ZipInputStream zis = new ZipInputStream(is);
            do {
                if ((e = zis.getNextEntry()) != null) continue;
                throw new ContentException("Cannot find archive entry \"{0}\"", new Object[]{entryName});
            } while (!e.getName().equals(entryName));
            CachedScpProxy.validateZipEntrySize((ZipEntry)e, (long)maxEntrySize);
            return zis;
        }
        catch (IOException var6) {
            throw new SLException((Throwable)var6, "Error while retrieving archive entry \"{0}\"", new Object[]{entryName});
        }
    }

    private static void validateZipEntrySize(ZipEntry zipEntry, long maxEntrySize) {
        if (zipEntry.getSize() > maxEntrySize) {
            throw new ContentException("The size \"{0}\" of mta file \"{1}\" exceeds the configured max size limit \"{2}\"", new Object[]{zipEntry.getSize(), zipEntry.getName(), maxEntrySize});
        }
    }

    private static void setExistFilePermission(File folderFile) {
        try {
            Path folderPath = folderFile.toPath();
            if (folderPath.getFileSystem().supportedFileAttributeViews().contains("posix")) {
                File[] flist;
                EnumSet<PosixFilePermission> posixDirPermissions = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE);
                Files.setPosixFilePermissions(folderPath, posixDirPermissions);
                for (File f : flist = folderFile.listFiles()) {
                    if (f.isDirectory()) {
                        CachedScpProxy.setExistFilePermission((File)f);
                        continue;
                    }
                    EnumSet<PosixFilePermission> posixFilePermissions = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
                    Path fPath = f.toPath();
                    Files.setPosixFilePermissions(fPath, posixFilePermissions);
                }
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void createDirectoriesWithPerm(Path tempDirPath) {
        try {
            if (tempDirPath.getFileSystem().supportedFileAttributeViews().contains("posix")) {
                EnumSet<PosixFilePermission> posixFilePermissions = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE);
                if (!Files.exists(tempDirPath, new LinkOption[0])) {
                    Files.createDirectories(tempDirPath, PosixFilePermissions.asFileAttribute(posixFilePermissions));
                } else {
                    Files.setPosixFilePermissions(tempDirPath, posixFilePermissions);
                }
            } else if (!Files.exists(tempDirPath, new LinkOption[0])) {
                Files.createDirectories(tempDirPath, new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void createFileWithPerm(Path tempFilePath) {
        try {
            if (tempFilePath.getFileSystem().supportedFileAttributeViews().contains("posix")) {
                EnumSet<PosixFilePermission> posixFilePermissions = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
                if (!Files.exists(tempFilePath, new LinkOption[0])) {
                    Files.createFile(tempFilePath, PosixFilePermissions.asFileAttribute(posixFilePermissions));
                } else {
                    Files.setPosixFilePermissions(tempFilePath, posixFilePermissions);
                }
            } else if (!Files.exists(tempFilePath, new LinkOption[0])) {
                Files.createFile(tempFilePath, new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void CheckZipEntryPath(String projectFolder, String destDirectory) {
        File zipTmp = new File(projectFolder);
        if (!zipTmp.toPath().normalize().startsWith(destDirectory)) {
            throw new ContentException("Bad zip entry path");
        }
    }

    static {
        File cacheHome = new File((String)CACHE_HOME + File.separator);
        if (cacheHome.exists()) {
            CachedScpProxy.setExistFilePermission((File)cacheHome);
        }
    }
}

