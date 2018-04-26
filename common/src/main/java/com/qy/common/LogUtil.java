package com.qy.common;

import android.util.Log;

/**
 * Created by qyang on 2017-11-3.
 */

public class LogUtil {
    public static boolean DEBUG = true;
    public static void setLog(boolean DEBUG) {
        LogUtil.DEBUG = DEBUG;
    }
    public static void e(String tag, String msg){
        if (DEBUG){
            Log.e(tag,msg);
        }
    }
}
