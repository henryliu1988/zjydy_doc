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

    interface MainExpertView extends BaseView<MainExpertPresenter>
    {
        void updateDistrict(Map<String, ArrayList> distrctData);
        void updateOffice(ArrayList<NormalPickViewData> officeData);
        void updateBusiness(ArrayList<NormalPickViewData> officeData);
        Map<String,Object> getFilterConditions();
        void updateFavExpertCount(int count);

    }

    interface MainExpertPresenter extends BasePresenter
    {
        void reloadExperts();
    }
}
