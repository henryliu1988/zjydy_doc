package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithData;
import com.zhjydy_doc.presenter.contract.LoginContract;
import com.zhjydy_doc.util.Utils;

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
        UserData.getInstance().tryLoginManager(phoneNum, password, mView.getContext());
    }





    private void loadPrefrence() {
        String phoneNum = Utils.toString(SPUtils.get("login_phoneNum", ""));
        String passoword = Utils.toString(SPUtils.get("login_password", ""));
        if (!TextUtils.isEmpty(phoneNum) || !TextUtils.isEmpty(passoword)) {
            mView.initPreferenceInfo(phoneNum, passoword);
        }
    }
    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.LOGIN_RESULT_BACK) {
            Map<String, Object> dataMap = Utils.parseObjectToMapString(data);
            boolean status = Utils.toBoolean(dataMap.get("status"));
            String msg = Utils.toString(dataMap.get("msg"));
            if (status) {
                mView.onLoginSucess();
            } else {
                mView.onLoginFail();
            }
        }
    }
}

