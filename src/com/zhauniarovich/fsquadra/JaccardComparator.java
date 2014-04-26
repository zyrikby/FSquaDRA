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

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;

public class JaccardComparator {
    private String in1;
    private String in2;
    private String outputFilePath;
    
    public JaccardComparator(String in1, String in2, String outputFileName) {
        this.in1 = in1;
        this.in2 = in2;
        this.outputFilePath = outputFileName;
    }
    
    private File[] getApkFileList(String path) {
        //System.out.println("Getting the list of files to be analyzed...");
        File file = new File(path);
        File[] listOfFiles;
        if (file.isFile()) {
            listOfFiles = new File[]{file};
        } else {
            listOfFiles = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".apk");
            }});
        }
        
        return listOfFiles;
    }
    
    private ArrayList<ApkFileAttributes> getApkAttributesToMemory(String path) {
        System.out.println("Starting to fill the memory with data from path: " + path);
        File[] filesToConsider = getApkFileList(path);
        final int filesNum = filesToConsider.length;
        
        ArrayList<ApkFileAttributes> apkAttrList = new ArrayList<ApkFileAttributes>(filesNum);
        
        ApkFileAttributes att;
        int skipped = 0;
        int total = 0;
        for (int i = 0; i < filesNum; i++) {
            att = AttributesExtractor.getApkAttributes(filesToConsider[i]);
            total++;
            if (att == null) {
                skipped++;
                continue;
            }
            
            apkAttrList.add(att);
        }
        System.out.println("Processed: " + total + ", skipped: " + skipped + 
                " files from path [" + path + "]");
        
        return apkAttrList;
    }
    
    public void compareApks() {
        System.out.println("Starting to compare apks...");
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(outputFilePath, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        
        ArrayList<ApkFileAttributes> apkAttrs1;
        ArrayList<ApkFileAttributes> apkAttrs2;
        
        ApkFileAttributes apkAttrI, apkAttrJ;
        double jaccardDist;
        boolean sameCerts;
        HashSet<String> hashesI, hashesJ;
        int ilen, jlen;
        int filesNum1, filesNum2;
        String outLine;
        
        long timeStart, timeEnd, totalTimeMem, totalTimeCompare;
        
        if (in2 != null) {
            //getting attributes to memory
            timeStart = System.nanoTime();
            apkAttrs1 = getApkAttributesToMemory(in1);
            apkAttrs2 = getApkAttributesToMemory(in2);
            timeEnd = System.nanoTime();
            totalTimeMem = timeEnd - timeStart;
            
            filesNum1 = apkAttrs1.size();
            filesNum2 = apkAttrs2.size();
            
            timeStart = System.nanoTime();
            for (int i = 0; i < filesNum1; i++) {
                apkAttrI = apkAttrs1.get(i);
                hashesI = new HashSet<String>(apkAttrI.getFileHashMap().values());
                ilen = hashesI.size();
                for (int j = 0; j < filesNum2; j++) {
                    apkAttrJ = apkAttrs2.get(j);
                    hashesJ = new HashSet<String>(apkAttrJ.getFileHashMap().values());
                    jlen = hashesJ.size();
                    jaccardDist = PairComparator.getJaccardIndex(hashesI, hashesJ);
                    if (jaccardDist == 0) {
                        continue;
                    }
                    sameCerts = PairComparator.certsTheSame(apkAttrI, apkAttrJ);
                    outLine = createOutString(apkAttrI.getApkName(), apkAttrJ.getApkName(), ilen, jlen, jaccardDist, sameCerts);
                    writer.println(outLine);
                }
                //System.out.println(String.valueOf(i) + " th file [" + apkAttrI.getApkName() + "] compared with others!");
            }
            timeEnd = System.nanoTime();
            totalTimeCompare = timeEnd - timeStart;
            
        } else {
            timeStart = System.nanoTime();
            
            apkAttrs1 = getApkAttributesToMemory(in1);
            
            timeEnd = System.nanoTime();
            totalTimeMem = timeEnd - timeStart;
            
            filesNum1 = apkAttrs1.size();
            
            timeStart = System.nanoTime();
            for (int i = 0; i < filesNum1; i++) {
                apkAttrI = apkAttrs1.get(i);
                hashesI = new HashSet<String>(apkAttrI.getFileHashMap().values());
                ilen = hashesI.size();
                for (int j = i + 1; j < filesNum1; j++) {
                    apkAttrJ = apkAttrs1.get(j);
                    hashesJ = new HashSet<String>(apkAttrJ.getFileHashMap().values());
                    jlen = hashesJ.size();
                    jaccardDist = PairComparator.getJaccardIndex(hashesI, hashesJ);
                    if (jaccardDist == 0) {
                        continue;
                    }
                    sameCerts = PairComparator.certsTheSame(apkAttrI, apkAttrJ);
                    outLine = createOutString(apkAttrI.getApkName(), apkAttrJ.getApkName(), ilen, jlen, jaccardDist, sameCerts);
                    writer.println(outLine);
                }
                //System.out.println(String.valueOf(i) + " th file [" + apkAttrI.getApkName() + "] compared with others!");
            }
            timeEnd = System.nanoTime();
            totalTimeCompare = timeEnd - timeStart;
            
        }
        System.out.println("Getting apk attributes to memory took: " + 
                totalTimeMem / (1000*1000) + "ms");
        System.out.println("Comparing apk attributes took: " + 
                totalTimeCompare / (1000*1000) + "ms");
        
        writer.close();
    }

    private String createOutString(String apkName, String apkName2,
            int ilen, int jlen, double jaccardDist, boolean sameCerts) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(apkName).append(";");
        strBuilder.append(apkName2).append(";");
        strBuilder.append(ilen).append(";");
        strBuilder.append(jlen).append(";");
        DecimalFormat df = new DecimalFormat("0.00000000");
        strBuilder.append(df.format(jaccardDist)).append(";");
        strBuilder.append(String.valueOf(sameCerts)).append(";");
        return strBuilder.toString();
    }
    
    
}
