package com.qy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qy.common.ToastUtil;

/**
 * Created by qyang on 2018-3-15.
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = onCreate(inflater, container, savedInstanceState);
        }

        return mRootView;
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        isFirst = false;
        initData();
    }

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    protected abstract View onCreate(LayoutInflater inflater,
                                     @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState);

    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */


    /**
     * 这里获取数据，刷新界面
     */
    protected abstract void initData();

    public void start(Class<?> cls) {
        startActivity(new Intent(getActivity(),cls));
    }

    public boolean checkIsOk(BaseResult result){
        if (result != null){
            if (result.isSuccess()){
                return true;
            }
            ToastUtil.showToast(getActivity(),"数据异常");
            return false;
        }
        ToastUtil.showToast(getActivity(),"数据异常");
        return false;
    }


    public void refreshDismiss(SwipeRefreshLayout swipe){
        if(swipe.isRefreshing()){
            swipe.setRefreshing(false);
        }
    }

    public void refreshDismissOnUI(final SwipeRefreshLayout swipe){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swipe.isRefreshing()){
                    swipe.setRefreshing(false);
                }
            }
        });

    }


    public void startForResult(Class<?> cls,int requestCode){
        Intent intent = new Intent(getActivity(),cls);
        getActivity().startActivityForResult(intent,requestCode);
    }

    public void startForResult(Class<?> cls,int requestCode,OnBundleListener listener){
        Intent intent = new Intent(getActivity(),cls);
        Bundle bundle = new Bundle();
        intent.putExtras(listener.onSetBundle(bundle));
        getActivity().startActivityForResult(intent,requestCode);
    }

    public void startForResultFragment(Class<?> cls,int requestCode){
        Intent intent = new Intent(getActivity(),cls);
        startActivityForResult(intent,requestCode);
    }

    public void startForResultFragment(Class<?> cls,int requestCode,OnBundleListener listener){
        Intent intent = new Intent(getActivity(),cls);
        Bundle bundle = new Bundle();
        intent.putExtras(listener.onSetBundle(bundle));
        startActivityForResult(intent,requestCode);
    }


    public Bundle getBundle(){
        return getActivity().getIntent().getExtras();
    }

    public interface OnBundleListener{
        Bundle onSetBundle(Bundle bundle);
    }
}
