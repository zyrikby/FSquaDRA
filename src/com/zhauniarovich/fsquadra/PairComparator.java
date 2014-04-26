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

import java.util.HashSet;

public class PairComparator {
    public static double getJaccardIndex(HashSet<?> hashesI, HashSet<?> hashesJ) {
        return Jaccard.calcSimilarity(hashesI, hashesJ);
    }
    
    public static boolean certsTheSame(ApkFileAttributes fileAttr1, ApkFileAttributes fileAttr2) {
        String[] s1 = fileAttr1.getCertHashes();
        String[] s2 = fileAttr2.getCertHashes();
        
        if ((s1 == null) || (s2 == null)) {
            System.out.println("One of two or both certificates are null!");
            return false;
        }
        
        HashSet<String> set1 = new HashSet<String>();
        for (String h : s1) {
            set1.add(h);
        }
        HashSet<String> set2 = new HashSet<String>();
        for (String h : s2) {
            set2.add(h);
        }
        // Make sure s2 contains all hashes in s1.
        if (set1.equals(set2)) {
            return true;
        }
        return false;
    }
}
