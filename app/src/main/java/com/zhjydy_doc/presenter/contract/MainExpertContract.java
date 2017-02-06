package com.zhjydy_doc.presenter.contract;


import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.presenter.BasePresenter;
import com.zhjydy_doc.presenter.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainExpertContract {

    interface MainExpertView extends BaseView<MainExpertPresenter> {
        void updateDistrict(Map<String, ArrayList> distrctData);

        void updateOffice(ArrayList<NormalPickViewData> officeData);

        void updateBusiness(ArrayList<NormalPickViewData> officeData);

        void updateCityAndHos(Map<String, ArrayList> data);
        void reloadData();
    }

    interface MainExpertPresenter extends BasePresenter {
    }

    interface TabView extends BaseView<TabPresenter> {

        Map<String, Object> getFilterConditions();
        void onGuanzhuChange();

    }

    interface TabPresenter extends BasePresenter {
        void reloadExperts();
        void guanExpert(Map<String,Object> item);
        void cancelGuanExpert(Map<String,Object> item);
    }

}
