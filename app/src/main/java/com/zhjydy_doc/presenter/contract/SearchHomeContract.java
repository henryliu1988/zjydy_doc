package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public interface SearchHomeContract {
    interface View extends BaseView<Presenter> {
        void onSearchResult(List<Map<String, Object>> experts, List<Map<String, Object>> infos);
       // void onSearchExpert(List<Map<String,Object>> experts);
       // void onSearchInfo(List<Map<String,Object>> infos);
    }

    interface Presenter extends BasePresenter {
        void searchExpertAndInfo(String info);
    }

}
