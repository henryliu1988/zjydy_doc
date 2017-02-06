package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public interface FansListConTract   {

    interface View extends BaseView<Presenter>
    {
        void updateNewFuns(List<Map<String,Object>> list);
        void updateFuns(List<Map<String,Object>> list);
        void noDataView(boolean nodata);
    }

    interface Presenter extends BasePresenter
    {
        void readFans(String id);
    }
}
