package com.qy.common;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Created by Administrator on 2018-3-14.
 */

public class SpUtil {
    /**
     * 保存String
     */
    public static void setString(Context context, String s, String fileName, String tag) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(tag,s);
        editor.commit();
    }

    /**
     * 取出String
     */

    public static String getObject(Context context,String fileName,String tag) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String s = sp.getString(tag, null);
        return s;
    }

    /**
     * 保存对象
     */
    public static void setObject(Context context, Object o,String fileName,String tag) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String strJson = null;
        if (null != o) {
            Gson gson = new Gson();
            strJson = gson.toJson(o);
        }
        editor.putString(tag,strJson);
        editor.commit();
    }

    /**
     * 取出对象
     */

    public static<T> T getObject(Context context,Class<T> t,String fileName,String tag) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sp != null) {
            String strJson = sp.getString(tag, null);
            if (null == strJson) {
                return null;
            }
            Gson gson = new Gson();
            T bean = gson.fromJson(strJson, t);
            return bean;
        }
        return null;
    }


    /**
     * 保存list
     */
    public static<T> void setList(Context context, List<T> list, String fileName, String tag) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String strJson = null;
        if (null != list) {
            Gson gson = new Gson();
            strJson = gson.toJson(list);
        }
        editor.putString(tag,strJson);
        editor.commit();
    }


    /**
     * 取出list
     */

    public static<T> List<T> getList(Context context,String fileName,String tag) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sp != null) {
            String strJson = sp.getString(tag, null);
            if (null == strJson) {
                return null;
            }
            Gson gson = new Gson();
            List<T> data  = gson.fromJson(strJson, new TypeToken<List<T>>() {}.getType());
            return data;
        }
        return null;
    }

    public static void clearTag(Context context,String fileName,String tag){
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(tag);
        editor.commit();
    }

    public static void clear(Context context,String fileName){
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
