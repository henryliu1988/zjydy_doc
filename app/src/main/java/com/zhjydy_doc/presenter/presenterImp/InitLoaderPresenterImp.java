package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.preference.SPUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithData;
import com.zhjydy_doc.presenter.contract.InitLoaderContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/17 0017.
 */
public class InitLoaderPresenterImp implements InitLoaderContract.Presenter {

    private InitLoaderContract.View mView;

    public InitLoaderPresenterImp(InitLoaderContract.View view) {
        this.mView = view;
        start();
    }

    @Override
    public void start() {
        tryLogInBackGroud();
    }

    private void tryLogInBackGroud() {

        String phoneNum = Utils.toString(SPUtils.get("login_phoneNum", ""));
        String passoword = Utils.toString(SPUtils.get("login_password", ""));
        boolean autoLogin = Utils.toBoolean(SPUtils.get("login_auto", false));
        if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(passoword) && autoLogin) {
            UserData.getInstance().tryLoginManager(phoneNum, passoword, null);
        } else {
            if (mView != null) {
                mView.gotoMainTabs();
            }
        }
    }

    @Override
    public void finish() {

    }

    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.LOGIN_RESULT_BACK) {
            Map<String, Object> dataMap = Utils.parseObjectToMapString(data);
            boolean status = Utils.toBoolean(dataMap.get("status"));
            String msg = Utils.toString(dataMap.get("msg"));
            if (mView != null) {
                if (status) {
                    boolean infoConf = UserData.getInstance().isExpertInfoSubmit();
                    if (infoConf) {
                        mView.gotoMainTabs();
                    } else {
                        zhToast.showToast("您的信息还没有录入，请先录入信息");
                        mView.gotoInfoSubmit();
                    }

                }else {
                    mView.gotoMainTabs();
                }
            }
        }
    }
}
