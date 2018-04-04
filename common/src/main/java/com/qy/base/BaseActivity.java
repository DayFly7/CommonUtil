package com.qy.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.qy.R;
import com.qy.common.ToastUtil;

/**
 * Created by qyang on 2018-2-26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public int page = 1;
    public void pageAdd(){
        page = page +1;
    }
    public void pageReset(){
        page = 1;
    }
    public String getCurrentPage(){
        return page +"";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }
    public abstract void initView();
    public abstract void initData();

    /**
     * 页面跳转（不携带数据）
     */
    public void start(Class<?> cls) {
        startActivity(new Intent(this,cls));
    }

    public void startF(Class<?> cls) {
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        finish();
    }

    public void start(Class<?> cls,OnBundleListener listener) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        intent.putExtras(listener.onSetBundle(bundle));
        startActivity(intent);
    }

    public void startForResult(Class<?> cls,int requestCode){
        Intent intent = new Intent(this,cls);
        startActivityForResult(intent,requestCode);
    }

    public void startForResult(Class<?> cls,int requestCode,OnBundleListener listener){
        Intent intent = new Intent(this,cls);
        Bundle bundle = new Bundle();
        intent.putExtras(listener.onSetBundle(bundle));
        startActivityForResult(intent,requestCode);
    }


    public Bundle getBundle(){
        return getIntent().getExtras();
    }

    public interface OnBundleListener{
        Bundle onSetBundle(Bundle bundle);
    }


    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput(){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean checkIsOk(BaseResult result){
        if (result != null){
            if (result.isSuccess()){
                return true;
            }
            ToastUtil.showToast(this,"数据异常");
            return false;
        }
        ToastUtil.showToast(this,"数据异常");
        return false;
    }

    public void refreshDismiss(SwipeRefreshLayout swipe){
        if(swipe.isRefreshing()){
            swipe.setRefreshing(false);
        }
    }

    public void refreshDismissOnUI(final SwipeRefreshLayout swipe){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swipe.isRefreshing()){
                    swipe.setRefreshing(false);
                }
            }
        });

    }

}
