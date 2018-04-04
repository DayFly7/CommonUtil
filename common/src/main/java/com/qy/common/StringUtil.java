package com.qy.common;

import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qyang on 2017/5/9.
 * String的工具类
 */

public class StringUtil {

    public static boolean isEmpty(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    /**
     * 判断不确定个数为空
     */
    public static boolean isEmpty(String ... strings){
        boolean isHasEmpty;
        for (String s : strings) {
            if (isEmpty(s)){
                isHasEmpty = true;
                return  isHasEmpty;
            }
        }
        return false;
    }

    public static String listToStr(List<String> list){
        if (null == list){
            return "";
        }
        StringBuilder SB = new StringBuilder();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            if (i != n - 1) {
                SB.append(list.get(i) + ",");
            }else {
                SB.append(list.get(i));
            }
        }
        return SB.toString();
    }

    public static String getStr(EditText editText){
        return editText.getText().toString().trim();
    }

    public static String getStr(TextView textView) {
        return textView.getText().toString().trim();
    }

}
