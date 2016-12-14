package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface FavInfoContract {

    interface View extends BaseView<Presenter>
    {
        void updateInfoList(List<Map<String, Object>> infos);
    }

    interface Presenter extends BasePresenter
    {
        void searchFavInfos(String condition);
        void cancelFav(String id);
    }
}
