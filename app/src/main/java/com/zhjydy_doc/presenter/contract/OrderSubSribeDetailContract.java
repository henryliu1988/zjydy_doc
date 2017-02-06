package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public interface OrderSubSribeDetailContract   {

    interface View extends BaseView<Presenter>
    {
        void updateOrderMsg(Map<String,Object> info);
        void onAcccept(boolean status);
        void onreject(boolean status);
    }

    interface Presenter extends BasePresenter
    {
        void accept();
        void reject();
    }

}
