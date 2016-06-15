package com.zhiw.gankapp.utils;

/**
 * ClassName: DateUtil
 * Desc:
 * Created by zhiw on 16/6/13.
 */
public class DateUtil {

    public static String parseDate(String date){
        return date.substring(0,date.lastIndexOf("T"));
    }
}
