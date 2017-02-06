package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public interface MineStarContract  {
    interface View extends BaseView<Presenter>
    {
        void updateIdentify(int identifyScore);
        void updateScoreAll(int score);
    }
    interface Presenter extends BasePresenter

    {
    }

}
