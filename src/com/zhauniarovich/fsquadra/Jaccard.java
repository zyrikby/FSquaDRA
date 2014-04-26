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
import java.util.List;
import java.util.Set;

public class Jaccard {

    public static double calcSimilarity(Set<? extends Object> a, Set<? extends Object> b){
        int alen = a.size();
        int blen = b.size();
        Set<Object> union = new HashSet<Object>(alen + blen);
        union.addAll(a);
        union.addAll(b);
        return calculateJaccardIndex(alen, blen, union.size());
    }

    public static double calcSimilarity(List<? extends Object> a, List<? extends Object> b){
        int alen = a.size();
        int blen = b.size();
        Set<Object> union = new HashSet<Object>(alen + blen);
        union.addAll(a);
        union.addAll(b);
        return calculateJaccardIndex(alen, blen, union.size());
    }

    private static double calculateJaccardIndex(int alen, int blen, int union) {
        double overlap = alen +  blen - union;
        if( overlap <= 0 )
            return 0.0;
        return overlap / union;
    }
}
