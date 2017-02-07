package com.zhjydy_doc.app;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
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
    {
        PlatformConfig.setWeixin("wx9c6ad53df1ed2164", "5787f8d33c292f8a00d325da8aa31f67");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
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
