package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface OrderCancelContract {
    interface View extends BaseView<Presenter> {

        void cancelResult(boolean result);

        void updateCancelResonList(ArrayList<NormalPickViewData> resons);
    }

    interface Presenter extends BasePresenter {
        void confirmCancel(String reason, String comment);
    }

}
