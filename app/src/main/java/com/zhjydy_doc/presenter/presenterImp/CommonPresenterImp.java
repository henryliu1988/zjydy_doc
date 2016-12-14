package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.presenter.contract.CommonContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class CommonPresenterImp implements CommonContract.Presenter {

    private CommonContract.View mView;

    public CommonPresenterImp(CommonContract.View view) {
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


}
