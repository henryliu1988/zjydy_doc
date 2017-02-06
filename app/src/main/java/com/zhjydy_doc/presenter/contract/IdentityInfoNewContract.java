package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface IdentityInfoNewContract {

    interface View extends BaseView<Presenter>
    {
        void onSubmitSuccess(boolean status);
    }

    interface Presenter extends BasePresenter
    {
        void submitIdentifymsg(Map<Integer, String> urls,String yiImage);
    }
}
