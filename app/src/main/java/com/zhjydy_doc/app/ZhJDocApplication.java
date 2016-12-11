package com.zhjydy_doc.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class ZhJDocApplication extends Application {

    private static ZhJDocApplication instance;
    public static ZhJDocApplication getInstance()
    {
        return instance;
    }
    public Context getContext()
    {
        return this.getApplicationContext();
    }


}
