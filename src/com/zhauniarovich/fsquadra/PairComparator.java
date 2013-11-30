package com.zhauniarovich.fsquadra;

import java.security.cert.Certificate;
import java.util.HashSet;

public class PairComparator {
    public static double getJaccardDistance(HashSet<?> hashesI, HashSet<?> hashesJ) {
        return Jaccard.calcDistance(hashesI, hashesJ);
    }
    
    public static boolean certsTheSame(ApkFileAttributes fileAttr1, ApkFileAttributes fileAttr2) {
        Certificate[] s1 = fileAttr1.getCertificates();
        Certificate[] s2 = fileAttr2.getCertificates();
        
        if ((s1 == null) || (s2 == null)) {
            System.out.println("One of two or both certificates are null!");
            return false;
        }
        
        HashSet<Certificate> set1 = new HashSet<Certificate>();
        for (Certificate sig : s1) {
            set1.add(sig);
        }
        HashSet<Certificate> set2 = new HashSet<Certificate>();
        for (Certificate sig : s2) {
            set2.add(sig);
        }
        // Make sure s2 contains all signatures in s1.
        if (set1.equals(set2)) {
            return true;
        }
        return false;
    }
}
