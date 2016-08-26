package com.zhiw.gankapp.utils;

/**
 * ClassName: DateUtil
 * Desc:
 * Created by zhiw on 16/6/13.
 */
public class DateUtil {

    /**
     * @param date 2016-08-25T11:23:14.243Z
     * @return 2016-08-25
     */
    public static String parseDate(String date) {
        return date.substring(0, date.lastIndexOf("T"));
    }

    /**
     * @param date 2016-08-25T11:23:14.243Z
     * @return [2016, 08, 25]
     */
    public static int[] getDate(String date) {
        String[] s = parseDate(date).split("-");
        int[] result = new int[3];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.valueOf(s[i]);
        }
        return result;

    }
}
