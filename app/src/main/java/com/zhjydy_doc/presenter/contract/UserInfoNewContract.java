package com.zhjydy_doc.presenter.contract;

import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public interface UserInfoNewContract  {
    interface View extends BaseView<Presenter>
    {
        void updateDistrict(Map<String,ArrayList> distrctData);
        void updateOffice(ArrayList<NormalPickViewData> officeData);
        void updateBusiness(ArrayList<NormalPickViewData> business);
        void updateHospitalByAddress(ArrayList<HosipitalPickViewData> hosData);
        void onSubmitSuc();
        void gotoMainTabs();
    }

    interface Presenter extends BasePresenter
    {
        void submitUserInfo(Map<String,Object> info);
        void updateHospitalList(String addressId);

    }
}

