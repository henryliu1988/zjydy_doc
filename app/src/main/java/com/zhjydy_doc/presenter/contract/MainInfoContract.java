package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainInfoContract {

    interface MainInfoView extends BaseView<MainInfoPresenter>
    {
        void updateFavInfoCount(int count);

    }

    interface MainInfoPresenter extends BasePresenter
    {
    }
}
