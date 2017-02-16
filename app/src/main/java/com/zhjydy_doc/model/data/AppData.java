package com.zhjydy_doc.model.data;


import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.app.ZhJDocApplication;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.util.DataCleanManager;
import com.zhjydy_doc.util.ImageUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class AppData {


    private static AppData appdata;
    public AppData()
    {
    }


    public static AppData getInstance()
    {
        if (appdata == null)
        {
            appdata = new AppData();
        }
        return appdata;
    }

    public void initData() {
        DicData.getInstance().init();
    }
    private TokenInfo mToken;
    public void setToken(TokenInfo token) {
        mToken = token;
    }
    public TokenInfo getToken() {
        if (mToken == null) {
            return new TokenInfo();
        }
        return mToken;
    }





}
