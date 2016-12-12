package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void registerOK(String msg);
    }

    interface Presenter extends BasePresenter {
        Observable<WebResponse> getConfirmCode(String phone);
        void register(HashMap<String, Object> params);
    }
}
