package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface CommonContract {

    interface View extends BaseView<Presenter>
    {
    }

    interface Presenter extends BasePresenter
    {
    }
}
