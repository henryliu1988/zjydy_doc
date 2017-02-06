package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public interface AcceptOrderContract {

    interface View extends BaseView<Presenter>
    {
        void setChatMsgs(List<Map<String, Object>> msg, String expertPhoto);
        void updateExpertName(String name);
    }

    interface Presenter extends BasePresenter
    {
    }

}
