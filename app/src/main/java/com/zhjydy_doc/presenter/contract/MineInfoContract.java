package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.entity.PickViewData;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MineInfoContract {

    interface View extends BaseView<Presenter>
    {
        void updateInfo(TokenInfo info);
        void updateSexPick(ArrayList<PickViewData> sexData);
        void updateDistrict(Map<String,ArrayList> distrctData);
        void updateOffice(ArrayList<NormalPickViewData> officeData);
        void updateBusiness(ArrayList<NormalPickViewData> business);
        void updateHospitalByAddress(ArrayList<HosipitalPickViewData> hosData);

    }

    interface Presenter extends BasePresenter
    {
        void updateHospitalList(String addressId);
        void updateMemberPhoto(String path);

        void updateExpertInfo(String key,String value);
        void refreshView();
    }
}
