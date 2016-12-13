package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.HospitalDicItem;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.presenter.contract.UserInfoNewContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class UserInfoNewPresenterImp implements UserInfoNewContract.Presenter {

    private UserInfoNewContract.View mView;
    private String userId;
    public UserInfoNewPresenterImp(UserInfoNewContract.View view,String userid){
        this.mView = view;
        this.userId = userid;
        start();
    }
    @Override
    public void submitUserInfo(Map<String, Object> info) {

    }

    @Override
    public void start() {
        loadDistricPickData();
        loadOfficeData();
        loadBusinessData();
    }
    private void loadOfficeData() {
        ArrayList<NormalPickViewData> officeData = new ArrayList<>();
        List<NormalDicItem> items = DicData.getInstance().getOffice();
        for (NormalDicItem item:items) {
            officeData.add(new NormalPickViewData(item));
        }
        mView.updateOffice(officeData);
    }
    private void loadDistricPickData() {
        DicData.getInstance().getAllDistrictForPicker().subscribe(new BaseSubscriber<Map<String, ArrayList>>() {
            @Override
            public void onNext(Map<String, ArrayList> map) {
                mView.updateDistrict(map);
            }
        });
    }
    private void loadBusinessData() {
        ArrayList<NormalPickViewData> businessData = new ArrayList<>();
        List<NormalDicItem> items = DicData.getInstance().getBusiness();
        for (NormalDicItem item:items) {
            businessData.add(new NormalPickViewData(item));
        }
        mView.updateOffice(businessData);
    }
    @Override
    public void finish() {

    }

    @Override
    public void updateHospitalList(String addressId) {
        // List<HospitalDicItem> list = DicData.getInstance().getHospitalByAddress(addressId);
        List<HospitalDicItem> list = DicData.getInstance().getHospitals();
        ArrayList<HosipitalPickViewData> listPick = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for(HospitalDicItem item:list) {
                listPick.add(new HosipitalPickViewData(item));
            }
        }
        mView.updateHospitalByAddress(listPick);
    }
}
