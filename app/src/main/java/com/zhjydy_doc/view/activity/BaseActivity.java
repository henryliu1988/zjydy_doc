package com.zhjydy_doc.view.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhjydy_doc.util.ActivityUtils;


public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.removeActivity(this);
        super.onDestroy();
    }
}
