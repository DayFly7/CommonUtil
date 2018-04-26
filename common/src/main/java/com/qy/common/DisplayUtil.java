package com.qy.common;

import android.content.Context;

/**
 * Created by qyang on 2017-11-5.
 */

public class DisplayUtil {

    public static int px2dp(Context context,float pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale + 0.5f);
    }

    public static int dp2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale + 0.5f);
    }

    public static int getWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
