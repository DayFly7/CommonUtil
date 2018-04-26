package com.qy.common;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by qyang on 2017/5/17.
 */

public class ToastUtil {
    private static Toast mToast = null;
    public static void showToast(Context context,String text) {

        if(mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
