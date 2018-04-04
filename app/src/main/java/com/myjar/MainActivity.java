package com.myjar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.myjar.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(SecondActivity.class, new OnBundleListener() {
                    @Override
                    public Bundle onSetBundle(Bundle bundle) {
                        bundle.putString("qq","qqqqqq");
                        return bundle;
                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }
}
