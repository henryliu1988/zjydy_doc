package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter>
    {
        void onLoginSucess();
        void onLoginFail();
        void initPreferenceInfo(String phone, String password);
    }

    interface Presenter extends BasePresenter
    {
        void  tryLogin(String phoneNum, String password);
    }
}
