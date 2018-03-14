package com.qy.common;

import android.app.Activity;
import android.util.Log;

/**
 * Created by qyang on 2017-11-3.
 */

public class LogUtil {
    private static boolean DEBUG = true;
    public static void e(String tag, String msg){
        if (DEBUG){
            Log.e(tag,msg);
        }
    }

    public static void e(Activity activity, String msg){
        if (DEBUG){
            Log.d(activity.getClass().getSimpleName()+activity.getClass().getSimpleName(),msg);
        }
    }
}
