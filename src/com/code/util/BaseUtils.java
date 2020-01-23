package com.code.util;


import java.util.HashMap;
import java.util.Map;
import net.sf.cglib.beans.BeanCopier;


public final class BaseUtils {
    private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    public static String firstLetterUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }

    public static String markToHump(String str, String mark, Integer start) {
        String[] strs = str.split(mark);
        String returnStr = "";
        if (start == null) {
            start = 0;
        }
        for (int i = 0; i < strs.length; ++i) {
            returnStr = returnStr + (start == null ? firstLetterUpperCase(strs[i]) : (start == i ? strs[i] : firstLetterUpperCase(strs[i])));
        }
        return returnStr;
    }
}

