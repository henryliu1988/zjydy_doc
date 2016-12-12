package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.RegisterContract;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class RegisterPresenterImp implements RegisterContract.Presenter {

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
    public Observable<WebResponse> getConfirmCode(String phoneNum) {
        HashMap<String,Object> params  = new HashMap<>();
        params.put("mobile",phoneNum);
        params.put("type",1);
        return WebCall.getInstance().call(WebKey.func_sendSms,params);
    }

    @Override
    public void register(HashMap<String, Object> params) {
        params.put("nickname","");
        params.put("type", "2");
        WebCall.getInstance().call(WebKey.func_register,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String msg = webResponse.getData();
                mView.registerOK(msg);
            }
        });
    }
}

