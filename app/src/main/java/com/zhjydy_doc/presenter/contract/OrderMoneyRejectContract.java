package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface OrderMoneyRejectContract {
    interface View extends BaseView<Presenter> {
        void cancelResult(boolean result);
    }

    interface Presenter extends BasePresenter {
        void confirmCancel(String reason);
    }

}
