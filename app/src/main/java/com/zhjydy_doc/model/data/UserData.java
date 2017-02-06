package com.zhjydy_doc.model.data;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhjydy_doc.model.entity.ExpertInfo;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.util.MD5;
import com.zhjydy_doc.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/18 0018.
 */
public class UserData {
    private static UserData userData = new UserData();
    private TokenInfo mToken;


    public UserData() {
    }

    public static UserData getInstance() {
        if (userData == null) {
            userData = new UserData();
        }
        return userData;
    }

    public void tryLoginManager(final String phoneNum, final String password, Context context) {
        HashMap<String, Object> map = new HashMap<>();
        String paswordMd5 = MD5.GetMD5Code(password);
        map.put("mobile", phoneNum);
        map.put("password", paswordMd5);
        map.put("type", "2");
        WebCall.getInstance().call(WebKey.func_login, map).subscribe(new BaseSubscriber<WebResponse>(context, "登录中") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                Map<String, Object> info = new HashMap<String, Object>();
                info.put("status", status);
                if (status) {
                    String data = webResponse.getData();
                    saveLogInfo(phoneNum, password);
                    saveTokenInfo(data);
                    RefreshManager.getInstance().refreshData(RefreshKey.LOGIN_RESULT_BACK, info);
                } else {
                    String msg = WebUtils.getWebMsg(webResponse);
                    info.put("msg", msg);
                    RefreshManager.getInstance().refreshData(RefreshKey.LOGIN_RESULT_BACK, info);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Map<String, Object> info = new HashMap<String, Object>();
                info.put("status", false);
                info.put("msg", e.getMessage());
                RefreshManager.getInstance().refreshData(RefreshKey.LOGIN_RESULT_BACK, info);
            }
        });

    }

    private void saveLogInfo(String phoneNum, String password) {
        SPUtils.put("login_phoneNum", phoneNum);
        SPUtils.put("login_password", password);
        SPUtils.put("login_auto", true);
    }

    public String getLogInfoNum() {
        String phoneNum = Utils.toString(SPUtils.get("login_phoneNum",""));
        return phoneNum;
    }

    public String getLogPassword() {
        String password = Utils.toString(SPUtils.get("login_password",""));
        return password;
    }
    private void saveTokenInfo(Object tokenOb) {
        Map<String, Object> token = Utils.parseObjectToMapString(tokenOb);
        if (token != null && token.size() > 0) {
            TokenInfo info = new TokenInfo();
            info.setId(Utils.toString(token.get("id")));
            info.setMobile(Utils.toString(token.get("mobile")));
            info.setCollectNews(Utils.toString(token.get("collectnews")));
            info.setIdcard(Utils.toString(token.get("idcard")));
            info.setPaypass(Utils.toString(token.get("paypass")));
            info.setStatus(Utils.toString(token.get("status")));
            info.setPassoword(Utils.toString(token.get("password")));
            info.setLogin_time(Utils.toString(token.get("login_time")));
            Map<String, Object> headImg = Utils.parseObjectToMapString(token.get("head_img"));
            info.setPhotoId(Utils.toString(headImg.get("id")));
            info.setPhotoUrl(Utils.toString(headImg.get("path")));
            info.setSex(Utils.toString(token.get("sex")));
            String expertInfo = Utils.toString(token.get("expert"));
            ExpertInfo expert = new ExpertInfo();
            try {
                expert = JSON.parseObject(expertInfo, new TypeReference<ExpertInfo>() {
                });
            } catch (Exception e) {
            }

            info.setmExpertInfo(expert);
            setToken(info);

            AppDataManager.getInstance().initLoginSucData();
        }
    }

    public void setToken(TokenInfo token) {
        mToken = token;
    }

    public TokenInfo getToken() {
        if (mToken == null) {
            return new TokenInfo();
        }
        return mToken;
    }


    public boolean isExpertInfoSubmit() {
        if (mToken == null) {
            return false;
        }
        ExpertInfo info = mToken.getmExpertInfo();
        if (info == null || TextUtils.isEmpty(info.getRealname())) {
            return false;
        }
        return true;
    }

    public void logOut() {
        SPUtils.put("login_auto", false);
    }

    public boolean isLogin() {
        if (mToken == null || TextUtils.isEmpty(mToken.getId())) {
            return false;
        }
        return true;
    }


    public void loadUserData() {
    }


}
