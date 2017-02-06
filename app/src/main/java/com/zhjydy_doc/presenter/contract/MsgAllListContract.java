package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MsgAllListContract {

    interface View extends BaseView<Presenter>
    {
        void updateOrderList(Map<String,Object> order);
        void updateSystemList(Map<String,Object> order);
        void updateFans(boolean isUnread);
        void updatePatientComment(boolean isUnread);
        void updateDocComment(boolean isUnread);
    }

    interface Presenter extends BasePresenter
    {
        void readOrder(String id);
        void readComment(int type,String id);
    }
}
