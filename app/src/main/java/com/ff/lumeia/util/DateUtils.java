package com.ff.lumeia.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class DateUtils {
    private DateUtils() {
        throw new UnsupportedOperationException("can not be instanced!");
    }

    public static String convertDateToZhString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }

    public static String convertDateToEnString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "/" + month + "/" + day + "/";
    }

}
