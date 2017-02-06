package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithData;
import com.zhjydy_doc.presenter.contract.RegisterContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class RegisterPresenterImp implements RegisterContract.Presenter,RefreshWithData {

    private RegisterContract.View mView;

    public RegisterPresenterImp(RegisterContract.View view) {
        this.mView = view;
        start();
        mView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }


    @Override
    public Observable<WebResponse> getConfirmCode(final String phoneNum){
        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", phoneNum);
        params.put("type",2);
        return WebCall.getInstance().call(WebKey.func_checkMobile,params).flatMap(new Func1<WebResponse, Observable<WebResponse>>() {
            @Override
            public Observable<WebResponse> call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    WebResponse response = new WebResponse(1,"该手机号码已经注册,请直接登录","");
                    return Observable.just(response);
                } else {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("mobile", phoneNum);
                    params.put("type", 2);
                    return WebCall.getInstance().call(WebKey.func_sendSms, params);
                }
            }
        });
    }

    @Override
    public void register(final HashMap<String, Object> params) {
        params.put("nickname","");
        params.put("type", "2");
        WebCall.getInstance().call(WebKey.func_register,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status){
                    zhToast.showToast("注册成功");
                    String phoneNum = Utils.toString(params.get("mobile"));
                    String passWordMd5 = Utils.toString(params.get("password"));
                    RefreshManager.getInstance().addNewListener(RefreshKey.LOGIN_RESULT_BACK,RegisterPresenterImp.this);
                    UserData.getInstance().tryLoginManager(phoneNum,passWordMd5,mView.getContext());
                    mView.registerOK(Utils.toString(data));
                } else {
                    zhToast.showToast(data);
                }

            }
        });
    }

    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.LOGIN_RESULT_BACK) {
            Map<String, Object> dataMap = Utils.parseObjectToMapString(data);
            boolean status = Utils.toBoolean(dataMap.get("status"));
            String msg = Utils.toString(dataMap.get("msg"));
            if (status) {
                mView.registerOK("");
            } else {
                zhToast.showToast("注册失败");
            }
        }
    }
}

