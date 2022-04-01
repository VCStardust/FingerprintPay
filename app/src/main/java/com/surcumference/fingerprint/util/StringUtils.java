package com.surcumference.fingerprint.util;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jason on 2017/9/9.
 */

public class StringUtils {

    public static Pattern sNumberPattern = Pattern.compile("(\\d+)");

    /**
     * 判断是否为最新版本方法 将版本号根据.切分为int数组 比较
     *
     * @param localVersion  本地版本号
     * @param onlineVersion 线上版本号
     * @return
     */
    public static boolean isAppNewVersion(String localVersion, String onlineVersion) {
        if (localVersion.equals(onlineVersion)) {
            return false;
        }
        String[] localArray = localVersion.split("\\.");
        String[] onlineArray = onlineVersion.split("\\.");

        int length = localArray.length < onlineArray.length ? localArray.length : onlineArray.length;

        try {
            for (int i = 0; i < length; i++) {
                if (getNumberFromString(onlineArray[i]) > getNumberFromString((localArray[i]))) {
                    return true;
                } else if (getNumberFromString(onlineArray[i]) < getNumberFromString(localArray[i])) {
                    return false;
                }
                // 相等 比较下一组值
            }
        } catch (Exception | Error e) {
            return false;
        }
        return true;
    }

    public static int getNumberFromString(String text) {
        Matcher m = sNumberPattern.matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            throw new NumberFormatException();
        }
    }

    public static String toString(@Nullable  Bundle bundle) {
        if (bundle == null) {
            return "Bundle: null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bundle:[");

        Set<String> keys = bundle.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = bundle.get(key);

            stringBuilder.append(key).append("=");
            if (value instanceof String[]) {
                stringBuilder.append(Arrays.toString((String[])value));
            } else {
                stringBuilder.append(value);
                stringBuilder.append(" type:");
                stringBuilder.append(value == null ? null : value.getClass());
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
