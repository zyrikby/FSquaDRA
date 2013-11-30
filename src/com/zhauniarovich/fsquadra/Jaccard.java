package com.zhauniarovich.fsquadra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Jaccard {

    public static double calcDistance(Set<? extends Object> a, Set<? extends Object> b){
        int alen = a.size();
        int blen = b.size();
        Set<Object> union = new HashSet<Object>(alen + blen);
        union.addAll(a);
        union.addAll(b);
        return calculateJaccardDist(alen, blen, union.size());
    }

    public static double calcDistance(List<? extends Object> a, List<? extends Object> b){
        int alen = a.size();
        int blen = b.size();
        Set<Object> union = new HashSet<Object>(alen + blen);
        union.addAll(a);
        union.addAll(b);
        return calculateJaccardDist(alen, blen, union.size());
    }

    private static double calculateJaccardDist(int alen, int blen, int union) {
        double overlap = alen +  blen - union;
        if( overlap <= 0 )
            return 0.0;
        return overlap / union;
    }
}
