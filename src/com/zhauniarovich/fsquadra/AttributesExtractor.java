/*
 * Copyright (C) 2013-2014 FSquaDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * Author(s): Yury Zhauniarovich
 */

package com.zhauniarovich.fsquadra;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class AttributesExtractor {
    
    /** File name in an APK for the Android manifest. */
    private static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";

    
    
    public static ApkFileAttributes getApkAttributes (File apkFile) {
        Map<String, String> fileHashMap = getFileHashMap(apkFile);
        if (fileHashMap == null) {
            //We are not manage to read file
            return null;
        }
        String[] certHashes = collectCertificateHashes(apkFile);
        if (certHashes == null) {
            //It seems that the package is not valid
            return null;
        }
        
        ApkFileAttributes attrs = new ApkFileAttributes();
        attrs.setApkName(apkFile.getName());
        attrs.addAllHashes(fileHashMap);
        attrs.setCertHashes(certHashes);
        return attrs;
    }
    
    
    public static ApkFileAttributes getApkAttributes (String apkFilePath) {
        File f = new File(apkFilePath);
        if (!f.exists()) {
            return null;
        }
        return getApkAttributes(f);
    }
    
    
    
    private static Map<String, String> getFileHashMap(File apkFile) {
        System.out.println("Getting map of files and hashes from file: " + apkFile.getName());
        try {
            ZipFile zf = new ZipFile(apkFile);

            ZipEntry ze = zf.getEntry("META-INF/MANIFEST.MF");
            if (ze == null) {
                ze = zf.getEntry("META-INF/manifest.mf");
                if (ze == null) {
                    System.out.println("Cannot read manifest file! Returning null!");
                    return null;
                }
            }

            InputStream is = zf.getInputStream(ze);
            Manifest manifest = new Manifest(is);
            HashMap <String, Attributes> map  = (HashMap<String, Attributes>) manifest.getEntries();
            Iterator <Entry <String, Attributes>> it = map.entrySet().iterator();
            Entry<String, Attributes> pair;
            Map<String, String> fileHashMap = new HashMap<String, String>();
            while (it.hasNext()) {
                pair = it.next();
                fileHashMap.put(pair.getKey().toString(), pair.getValue().getValue("SHA1-Digest"));
            }

            is.close();
            zf.close();
            return fileHashMap;
        }
        catch (ZipException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    // this part is from AOSP
    private static final Object mSync = new Object();
    private static WeakReference<byte[]> mReadBuffer;
    
      
    private static String[] collectCertificateHashes(File apkFile) {
        System.out.println("Getting certificates from file: " + apkFile.getName());
        WeakReference<byte[]> readBufferRef;
        byte[] readBuffer = null;
        synchronized (mSync) {
            readBufferRef = mReadBuffer;
            if (readBufferRef != null) {
                mReadBuffer = null;
                readBuffer = readBufferRef.get();
            }
            if (readBuffer == null) {
                readBuffer = new byte[8192];
                readBufferRef = new WeakReference<byte[]>(readBuffer);
            }
        }
        
        Certificate[] certs = null;
        try {
            JarFile jarFile = new JarFile(apkFile);
            
            // So as we can assume that the application will not be installed without strict
            // verification process during the installation of file we check only one entry
            // AndroidManifest.xml
            // to retrieve its signatures, not validating all of the
            // files.
            JarEntry jarEntry = jarFile.getJarEntry(ANDROID_MANIFEST_FILENAME);
            certs = loadCertificates(jarFile, jarEntry, readBuffer);
            if (certs == null) {
                System.out.println("File " + jarFile.getName()
                        + " has no certificates at entry "
                        + jarEntry.getName() + "; ignoring!");
                jarFile.close();
                return null;
            }
            jarFile.close();
            
        } catch (IOException e) {
            System.out.println("Exception reading " + apkFile);
            e.printStackTrace();
            return null;
        } catch (RuntimeException e) {
            System.out.println("Exception reading " + apkFile);
            e.printStackTrace();
            return null;
        }
        
        synchronized (mSync) {
            mReadBuffer = readBufferRef;
        }
        
//        we can later get the hashes of certificates to reduce memory consumption
//        or to improve speed of calculation
        String[] certHashes = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        
        if (certs != null && certs.length > 0) {
            final int N = certs.length;
            certHashes = new String[certs.length];
            for (int i=0; i<N; i++) {
                try {
                    certHashes[i] = new String(digest.digest(certs[i].getEncoded()));
                } catch (CertificateEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        
        return certHashes;
    }
    
    private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je,
            byte[] readBuffer) {
        try {
            // We must read the stream for the JarEntry to retrieve
            // its certificates.
            InputStream is = new BufferedInputStream(jarFile.getInputStream(je));
            while (is.read(readBuffer, 0, readBuffer.length) != -1) {
                // not using
            }
            is.close();
            return je != null ? je.getCertificates() : null;
        } catch (IOException e) {
            System.out.println("Error while extracting certificates from file! Returning null!");
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println("Error while extracting certificates from file! Returning null!");
            e.printStackTrace();
        }
        return null;
    }
   
}
