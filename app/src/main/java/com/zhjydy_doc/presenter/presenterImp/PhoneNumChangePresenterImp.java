package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.PhoneNumChangContract;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PhoneNumChangePresenterImp implements PhoneNumChangContract.Presenter {

    private PhoneNumChangContract.View mView;

    public PhoneNumChangePresenterImp(PhoneNumChangContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }



    @Override
    public void finish() {

    }


    @Override
    public Observable<WebResponse> getConfirmCode(String phone) {
        HashMap<String,Object> params  = new HashMap<>();
        params.put("mobile",phone);
        params.put("type",1);
        return WebCall.getInstance().call(WebKey.func_sendSms,params);

    }

    @Override
    public void submitChangeConfirm(final String phone, String confirmCode)
    {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        params.put("mobile",phone);
        params.put("yanzheng",confirmCode);
        WebCall.getInstance().call(WebKey.func_updateMobile,params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"请稍后，正在提交修改数据")
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                boolean status = WebUtils.getWebStatus(webResponse);
                String msg = WebUtils.getWebMsg(webResponse);
                mView.submitResult(status,msg,phone);
            }
        });
    }
}
