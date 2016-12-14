package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface InfoDetailContract {

    interface View extends BaseView<Presenter>
    {
        void update(Map<String, Object> info);
        void updateFavStatus(boolean isCollect);
    }

    interface Presenter extends BasePresenter
    {
        void saveInfo();
        void cancelSaveInfo();
        void shareInfo(String url);
    }
}
