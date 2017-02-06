package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public interface MainTabsContract {
    interface View extends BaseView<Presenter>
    {
        void updateMsgCount(int count);
        void refreshOrderList();
        void refreshNewsCollectList();
    }

    interface Presenter extends BasePresenter
    {
    }

}
