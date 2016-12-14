package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MsgAllListContract {

    interface View extends BaseView<Presenter>
    {
        void updateOrderList(List<Map<String, Object>> data);
        void updateChatList(List<Map<String, Object>> data);

    }

    interface Presenter extends BasePresenter
    {
        void readOrder(String id);
        void readComment(String id);
    }
}
