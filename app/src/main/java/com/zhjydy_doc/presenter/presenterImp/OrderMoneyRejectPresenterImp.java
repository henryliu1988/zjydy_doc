package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.OrderMoneyRejectContract;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderMoneyRejectPresenterImp implements OrderMoneyRejectContract.Presenter {

    private OrderMoneyRejectContract.View mView;

    private String mOrderId;

    public OrderMoneyRejectPresenterImp(OrderMoneyRejectContract.View view, String info) {
        this.mView = view;
        this.mOrderId = info;
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
    public void confirmCancel(final String reason) {

        HashMap<String,Object> params = new HashMap<>();
        params.put("id",mOrderId);
        params.put("rebackpay_reason ",reason);
        WebCall.getInstance().call(WebKey.func_cancelRebackMoney,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                mView.cancelResult(status);
            }
        });
    }
}
