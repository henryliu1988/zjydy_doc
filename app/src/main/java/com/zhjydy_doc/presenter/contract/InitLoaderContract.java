package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/12/17 0017.
 */
public interface InitLoaderContract {
    interface View extends BaseView<Presenter> {
        void gotoMainTabs();
        void gotoInfoSubmit();
        void gotoLogIn();
    }

    interface Presenter extends BasePresenter {
    }
}
