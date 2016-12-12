package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainOrderContract {

    interface MainOrderView extends BaseView<MainOrderPresenter>
    {
        void update(List<Map<String, Object>> orders);
        void onNetError();
    }

    interface MainOrderPresenter extends BasePresenter
    {
        void reloadOrders();
    }
}
