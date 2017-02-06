package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.AboutAdviceContract;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class AboutAdvicePresenterImp implements AboutAdviceContract.Presenter {

    private AboutAdviceContract.View mView;

    public AboutAdvicePresenterImp(AboutAdviceContract.View view) {
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
    public void confirm(String advice, String telNul) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        params.put("content", advice);
        params.put("mobile", telNul);
        WebCall.getInstance().call(WebKey.func_addidear, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                mView.submitresult(status);
            }
        });
    }
}
