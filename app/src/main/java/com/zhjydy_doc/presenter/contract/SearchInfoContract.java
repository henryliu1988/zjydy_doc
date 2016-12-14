package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface SearchInfoContract {
    interface View extends BaseView<Presenter> {
        void updateInfoList(List<Map<String, Object>> list);
    }

    interface Presenter extends BasePresenter {
        void searchInfo(String condition);
    }

}
