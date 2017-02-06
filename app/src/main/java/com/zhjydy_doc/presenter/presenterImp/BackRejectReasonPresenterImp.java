package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.presenter.contract.BackRejectReasonContract;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class BackRejectReasonPresenterImp implements BackRejectReasonContract.Presenter {


    private BackRejectReasonContract.View mView;
    private String orderId;
    public BackRejectReasonPresenterImp(BackRejectReasonContract.View view, String orderId) {
        this.mView = view;
        this.orderId = orderId;
        mView.setPresenter(this);
        start();
    }
    @Override
    public void reject(String reason) {

    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }
}
