package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface PatientCaseDetailContract {

    interface View extends BaseView<Presenter> {
        void updateInfo(Map<String, Object> info);
    }

    interface Presenter extends BasePresenter {
        void refreshData();
    }
}
