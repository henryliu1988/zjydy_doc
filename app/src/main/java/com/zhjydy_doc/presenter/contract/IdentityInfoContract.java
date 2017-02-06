package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface IdentityInfoContract {

    interface View extends BaseView<Presenter>
    {
        void updateIdentifyInfo(int status, List<String> idPath,List<String> yiPath);
    }

    interface Presenter extends BasePresenter
    {
        void uploadImageFiles(List<String> urls);
    }
}
