package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;


import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.presenter.contract.LoginContract;
import com.zhjydy_doc.util.MD5;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class LoginPresenterImp implements LoginContract.Presenter {

    private LoginContract.View mView;

    public LoginPresenterImp(LoginContract.View view) {
        this.mView = view;
        start();
        mView.setPresenter(this);

    }

    @Override
    public void start() {
        loadPrefrence();
    }

    @Override
    public void finish() {

    }

    @Override
    public void tryLogin(final String phoneNum, final String password) {
        HashMap<String, Object> map = new HashMap<>();
        String paswordMd5 = MD5.GetMD5Code(password);
        map.put("mobile", phoneNum);
        map.put("password", paswordMd5);
        map.put("type", "2");
        WebCall.getInstance().call(WebKey.func_login, map).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "正在登录") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    String data = webResponse.getData();
                    saveLogInfo(phoneNum, password);
                    saveTokenInfo(data);
                    mView.onLoginSucess();
                } else {
                    String msg = WebUtils.getWebMsg(webResponse);

                    zhToast.showToast("登录失败\n" + msg);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                zhToast.showToast(e.getMessage());
            }
        });
    }


    private void saveTokenInfo(Object tokenOb) {
        Map<String, Object> token = Utils.parseObjectToMapString(tokenOb);
        if (token != null && token.size() > 0) {
            TokenInfo info = new TokenInfo();
            info.setId(Utils.toString(token.get("id")));
            info.setMobile(Utils.toString(token.get("mobile")));
            info.setNickname(Utils.toString(token.get("nickname")));
            info.setCollectExperts(Utils.toString(token.get("collectexpert")));
            info.setCollectNews(Utils.toString(token.get("collectnews")));
            info.setIdcard(Utils.toString(token.get("idcard")));
            info.setPaypass(Utils.toString(token.get("paypass")));
            info.setStatus(Utils.toString(token.get("status")));
            info.setPassoword(Utils.toString(token.get("password")));
            Map<String,Object> headImg = Utils.parseObjectToMapString(token.get("head_img"));
            info.setPhotoId(Utils.toString(headImg.get("id")));
            info.setPhotoUrl(Utils.toString(headImg.get("path")));
            info.setSex(Utils.toString(token.get("sex")));
            AppData.getInstance().setToken(info);
            AppData.getInstance().initData();
        }
    }

    private void saveLogInfo(String phoneNum, String password) {
        SPUtils.put("login_phoneNum", phoneNum);
        SPUtils.put("login_password", password);
    }

    private void loadPrefrence() {
        String phoneNum = Utils.toString(SPUtils.get("login_phoneNum", ""));
        String passoword = Utils.toString(SPUtils.get("login_password", ""));
        if (!TextUtils.isEmpty(phoneNum) || !TextUtils.isEmpty(passoword)) {
            mView.initPreferenceInfo(phoneNum, passoword);
        }
    }

}

