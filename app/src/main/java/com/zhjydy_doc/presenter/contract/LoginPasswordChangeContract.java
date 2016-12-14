package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface LoginPasswordChangeContract {

    interface View extends BaseView<Presenter>
    {
        void updatePassWordOk();
    }

    interface Presenter extends BasePresenter
    {
        void confirmUpdate(String oldPw, String newPw);
    }
}
