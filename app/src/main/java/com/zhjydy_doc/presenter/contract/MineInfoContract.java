package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.model.entity.PickViewData;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MineInfoContract {

    interface View extends BaseView<Presenter>
    {
        void updateInfo(TokenInfo info);
        void updateSexPick(ArrayList<PickViewData> sexData);

    }

    interface Presenter extends BasePresenter
    {
        void updateMemberPhoto(String path);
        void updateMemberSex(int sex);
        void updateMemberName(String name);
        void refreshView();
    }
}
