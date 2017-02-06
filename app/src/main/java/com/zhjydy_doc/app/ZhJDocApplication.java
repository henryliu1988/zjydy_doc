package com.zhjydy_doc.app;

import android.app.Application;
import android.content.Context;

import com.zhjydy_doc.model.data.AppDataManager;
import com.zhjydy_doc.util.ImageUtils;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class ZhJDocApplication extends Application {

    private static ZhJDocApplication instance;

    public static ZhJDocApplication getInstance() {
        return instance;
    }

    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void init() {
        instance = this;
        ImageUtils.getInstance().initImageLoader();
        AppDataManager.getInstance().initData();
    }

}
