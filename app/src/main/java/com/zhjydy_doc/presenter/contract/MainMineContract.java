package com.zhjydy_doc.presenter.contract;

import android.content.Context;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainMineContract {

    interface MainMineView extends BaseView<MainMinePresenter>
    {
        void updateIdentiFyStatus(int status, String msg);
        void updateTokenInfo();
    }

    interface MainMinePresenter extends BasePresenter
    {
        void loadIdentifyInfo();
        Map<String,Object> getIdentifyInfo(Context context);
    }
}
