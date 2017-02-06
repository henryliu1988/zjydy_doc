package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public interface OrderAcceptContract {

    interface View extends BaseView<Presenter>
    {
        void onAccept(boolean ok);
        void updateHuizhen(Map<String,Object> info);
    }

    interface Presenter extends BasePresenter
    {
        void accept(Map<String,Object> info);
    }

}
