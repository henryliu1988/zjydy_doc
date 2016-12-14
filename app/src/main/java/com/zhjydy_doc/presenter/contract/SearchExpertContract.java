package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface SearchExpertContract {
    interface View extends BaseView<Presenter> {
        void updateExpertList(List<Map<String, Object>> list);
    }

    interface Presenter extends BasePresenter {
        void searchExpert(String condition);
    }

}
