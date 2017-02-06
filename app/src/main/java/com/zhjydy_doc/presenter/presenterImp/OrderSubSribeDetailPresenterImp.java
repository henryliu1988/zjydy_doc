package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.OrderData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.presenter.contract.OrderSubSribeDetailContract;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public class OrderSubSribeDetailPresenterImp implements OrderSubSribeDetailContract.Presenter {


    private OrderSubSribeDetailContract.View mView;
    private String orderId;
    public OrderSubSribeDetailPresenterImp(OrderSubSribeDetailContract.View view,String orderId){
        this.mView = view;
        this.orderId = orderId;
        mView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadOrderDetail();
    }

    private void loadOrderDetail() {
        OrderData.getInstance().getOrderDetaiById(orderId).subscribe(new BaseSubscriber<Map<String, Object>>(mView.getContext(),true) {
            @Override
            public void onNext(Map<String, Object> info) {
                if (mView != null) {
                    mView.updateOrderMsg(info);
                }
            }
        });
    }
    @Override
    public void accept() {

    }

    @Override
    public void reject() {

    }


    @Override
    public void finish() {

    }
}
