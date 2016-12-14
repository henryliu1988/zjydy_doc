package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.presenter.contract.PasswordChangContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PasswordChangPresenterImp implements PasswordChangContract.Presenter {

    private PasswordChangContract.View mView;

    public PasswordChangPresenterImp(PasswordChangContract.View view) {
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
