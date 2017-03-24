package com.android.base.util;

import java.util.Collections;
import java.util.List;

/**
 * 集合工具类
 *
 * @author mingxue.zhang@163.com
 */
public class ArraysUtils {

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List argList) {
        if (null == argList || argList.isEmpty()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(List argList) {
        if (null != argList && !argList.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] argList) {
        if (null != argList && argList.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断 列表是否为空
     *
     * @return true为null或空; false不null或空
     */
    public static boolean isEmptyList(Object[] list) {
        return list == null || list.length == 0;
    }

    /**
     * k 判断 列表是否为空
     *
     * @return true为null或空; false不null或空
     */
    public static boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }


    public static boolean isEquals(List<String> arg1, List<String> arg2) {
        if (ArraysUtils.isEmpty(arg1) || ArraysUtils.isEmpty(arg2)) {
            return false;
        }
        if (arg1.size() != arg2.size()) {
            return false;
        }
        Collections.sort(arg1);
        Collections.sort(arg2);
        for (int i = 0; i < arg1.size(); i++) {
            if (!((arg1.get(i)).equals(arg2.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public static String toLinkString(List<String> argList, String split) {
        StringBuffer strBuffer = new StringBuffer();
        if (isNotEmpty(argList)) {
            for (int i = 0; i < argList.size(); i++) {
                strBuffer.append(argList.get(i));
                if (i < argList.size() - 1) {
                    strBuffer.append(split);
                }
            }
        }
        return strBuffer.toString();
    }
}
