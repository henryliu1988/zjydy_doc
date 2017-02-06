package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.DistricPickViewData;
import com.zhjydy_doc.model.entity.District;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.presenter.contract.MainExpertContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainExpertPresenterImp implements MainExpertContract.MainExpertPresenter {

    private MainExpertContract.MainExpertView mView;

    public MainExpertPresenterImp(MainExpertContract.MainExpertView view) {
        mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadFilterDatas();
    }


    private void loadFilterDatas() {
       // loadDistricPickData();
        loadOfficeData();
        loadBusinessData();
        loadCityAndHospital();
    }

    private void loadOfficeData() {
        ArrayList<NormalPickViewData> officeData = new ArrayList<>();
        NormalDicItem itemDefualt = new NormalDicItem();
        itemDefualt.setId("");
        itemDefualt.setName("全部");
        NormalPickViewData defaul = new NormalPickViewData(itemDefualt);
        officeData.add(defaul);
        List<NormalDicItem> items = DicData.getInstance().getOffice();
        for (NormalDicItem item : items) {
            officeData.add(new NormalPickViewData(item));
        }
        mView.updateOffice(officeData);
    }
    private void loadCityAndHospital() {
        DicData.getInstance().getCityAndHospitalForPicker().subscribe(new BaseSubscriber<Map<String, ArrayList>>() {
            @Override
            public void onNext(Map<String, ArrayList> map) {
                mView.updateCityAndHos(map);
            }
        });
    }
    private void loadDistricPickData() {
        DicData.getInstance().getAllDistrictForPicker().subscribe(new BaseSubscriber<Map<String, ArrayList>>() {
            @Override
            public void onNext(Map<String, ArrayList> map) {
                District defaultDs = new District();
                defaultDs.setId("");
                defaultDs.setName("全部");
                DistricPickViewData viewDefuatData = new DistricPickViewData(defaultDs);

                ArrayList<DistricPickViewData> viewListDefault = new ArrayList<DistricPickViewData>();
                viewListDefault.add(viewDefuatData);

                ArrayList<ArrayList<DistricPickViewData>> view2ListDefault = new ArrayList<ArrayList<DistricPickViewData>>();
                view2ListDefault.add(viewListDefault);
                ArrayList<DistricPickViewData> proPickViewData = new ArrayList<DistricPickViewData>();
                ArrayList<ArrayList<DistricPickViewData>> cityPickViewData = new ArrayList<ArrayList<DistricPickViewData>>();
                ArrayList<ArrayList<ArrayList<DistricPickViewData>>> quPickViewData = new ArrayList<ArrayList<ArrayList<DistricPickViewData>>>();
                proPickViewData.addAll((ArrayList<DistricPickViewData>) map.get("pro"));
                cityPickViewData.addAll((ArrayList<ArrayList<DistricPickViewData>>) map.get("city"));
                quPickViewData.addAll((ArrayList<ArrayList<ArrayList<DistricPickViewData>>>) map.get("qu"));
                proPickViewData.add(0, viewDefuatData);
                cityPickViewData.add(0, viewListDefault);
                quPickViewData.add(0, view2ListDefault);
                Map<String, ArrayList> newMap = new HashMap<String, ArrayList>();
                newMap.put("pro", proPickViewData);
                newMap.put("city", cityPickViewData);
                newMap.put("qu", quPickViewData);
                mView.updateDistrict(newMap);
            }
        });
    }

    private void loadBusinessData() {
        ArrayList<NormalPickViewData> businessData = new ArrayList<>();
        NormalDicItem itemDefualt = new NormalDicItem();
        itemDefualt.setId("");
        itemDefualt.setName("全部");
        NormalPickViewData defaul = new NormalPickViewData(itemDefualt);

        businessData.add(defaul);
        List<NormalDicItem> items = DicData.getInstance().getBusiness();
        for (NormalDicItem item : items) {
            businessData.add(new NormalPickViewData(item));
        }
        mView.updateBusiness(businessData);
    }


    @Override
    public void finish() {
    }


}
