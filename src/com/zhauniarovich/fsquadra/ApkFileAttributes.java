package com.zhauniarovich.fsquadra;

import java.util.HashMap;
import java.util.Map;

public class ApkFileAttributes {
    private String apkName;
    private String[] certHashes;
    
    private HashMap<String, String> fileHashMap;
    
    public ApkFileAttributes() {
        setFileHashMap(new HashMap<String, String>());
    }
    
    public String getApkName() {
        return apkName;
    }
    
    public void setApkName(String apkName) {
        this.apkName = apkName;
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

    public String[] getCertHashes() {
        return certHashes;
    }

    public void setCertHashes(String[] certHashes) {
        this.certHashes = certHashes;
    }
}
