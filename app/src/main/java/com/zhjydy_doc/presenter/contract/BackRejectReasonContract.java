package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public interface BackRejectReasonContract {

    interface View extends BaseView<Presenter>
    {
        void onReject(boolean ok);
    }

    interface Presenter extends BasePresenter
    {
        void reject(String reason);
    }

}
