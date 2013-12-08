package com.zhauniarovich.fsquadra;

import java.util.HashSet;

public class PairComparator {
    public static double getJaccardDistance(HashSet<?> hashesI, HashSet<?> hashesJ) {
        return Jaccard.calcDistance(hashesI, hashesJ);
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
