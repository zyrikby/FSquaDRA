package com.zhauniarovich.fsquadra;

import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

public class ApkFileAttributes {
    private String apkName;
//    private String signature;
    private Certificate[] certificates;
//    private String[] certHashes;
    
//    private Set<Resource> resources;
    private HashMap<String, String> fileHashMap;
    
    public ApkFileAttributes() {
//        resources = new HashSet<Resource>();
        setFileHashMap(new HashMap<String, String>());
    }
    
    public String getApkName() {
        return apkName;
    }
    
    public void setApkName(String apkName) {
        this.apkName = apkName;
    }
    
//    public String getSignature() {
//        return signature;
//    }
//    
//    public void setSignature(String signature) {
//        this.signature = signature;
//    }
    
//    public Set<Resource> getResourceHashes() {
//        return resources;
//    }
//    
//    public void addAllHashes(Set<Resource> set) {
//        this.resources.addAll(set);
//    }
//    
//    public void addHash(Resource res) {
//        this.resources.add(res);
//    }

    public Certificate[] getCertificates() {
        return certificates;
    }

    public void setCertificates(Certificate[] certificates) {
        this.certificates = certificates;
    }

    public HashMap<String, String> getFileHashMap() {
        return fileHashMap;
    }
    
    public void addHash(String key, String value) {
        fileHashMap.put(key, value);
    }

    public void addAllHashes(Map<String, String> mp) {
        fileHashMap.putAll(mp);
    }
    
    public void setFileHashMap(HashMap<String, String> fileHashMap) {
        this.fileHashMap = fileHashMap;
    }

//    public String[] getCertHashes() {
//        return certHashes;
//    }
//
//    public void setCertHashes(String[] certHashes) {
//        this.certHashes = certHashes;
//    }
}
