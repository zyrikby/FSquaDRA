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
