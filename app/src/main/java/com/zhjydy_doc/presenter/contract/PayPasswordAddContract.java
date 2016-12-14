package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface PayPasswordAddContract {

    interface View extends BaseView<Presenter>
    {
        void confirmResult(boolean result, String msg);
    }

    interface Presenter extends BasePresenter
    {
        void confirm(String newPw);
    }
}
