package com.qy.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangqi on 17/7/31.
 */
public class DateUtil {
    public static final String FORMAT_TYPE_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TYPE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDate(String formatType){
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String getCurrentTime(String formatType){
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static String dateToString(Date date,String formatType){
        if (null == date){
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        String str = formatter.format(date);
        return str;
    }

    public static String getStrDate(String timeStamp,String formatType){
        if (timeStamp.length() == 0){
            return "";
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        long  l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    public static long getLongDate(String time,String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String getTwoPositionTime(int str){
        String result = String.valueOf(str);
        if (str<10){
            result = "0"+str;
        }
        return result;
    }
}
