package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.entity.ExpertInfo;
import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.HospitalDicItem;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.entity.PickViewData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.FileUpLoad;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MineInfoContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewKey;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MineInfoPresenterImp implements MineInfoContract.Presenter,RefreshWithKey {

    private MineInfoContract.View mView;

    public MineInfoPresenterImp(MineInfoContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.EXPERT_INFO_CHANGE,this);
        start();
    }

    @Override
    public void start() {
        loadPersonInfo();
        loadSexPickData();
        loadDistricPickData();
        loadOfficeData();
        loadHospitalData();
        loadBusinessData();
    }
    private void loadSexPickData() {
        ArrayList<PickViewData> sexData = new ArrayList<>();
        sexData.add(new PickViewData("1","男"));
        sexData.add(new PickViewData("2","女"));
        mView.updateSexPick(sexData);
    }
    private void loadOfficeData() {
        ArrayList<NormalPickViewData> officeData = new ArrayList<>();
        List<NormalDicItem> items = DicData.getInstance().getOffice();
        for (NormalDicItem item:items) {
            officeData.add(new NormalPickViewData(item));
        }
        mView.updateOffice(officeData);
    }
    private void loadHospitalData() {
        List<HospitalDicItem> list = DicData.getInstance().getHospitals();
        ArrayList<HosipitalPickViewData> listPick = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for(HospitalDicItem item:list) {
                listPick.add(new HosipitalPickViewData(item));
            }
        }
        mView.updateHospitalByAddress(listPick);
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
        mView.updateBusiness(businessData);
    }


    private void loadPersonInfo() {
        if (mView !=null){
            mView.updateInfo( UserData.getInstance().getToken());
        }
    }

    @Override
    public void finish() {

    }


    private String photoUrl;

    @Override
    public void updateHospitalList(String addressId)
    {
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

    @Override
    public void updateMemberPhoto(String path) {
        List<Map<String,Object>> files = new ArrayList<>();
        Map<String,Object> file = new HashMap<>();
        file.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
        file.put(ViewKey.FILE_KEY_URL,path);
        files.add(file);
        FileUpLoad.uploadFiles(files).flatMap(new Func1<List<Map<String,Object>>, Observable<WebResponse>>() {
            @Override
            public Observable<WebResponse> call(List<Map<String,Object>> files) {
                HashMap<String,Object> params = new HashMap<>();
               // params.put("nickname",UserData.getInstance().getToken().getNickname());
                params.put("memberid",UserData.getInstance().getToken().getId());
                String imageIds = Utils.getListStrsAdd(files,"id");
                params.put("img",imageIds);
                if (files.size() >0) {
                    photoUrl = Utils.toString(files.get(0).get("url"));
                }
                return WebCall.getInstance().call(WebKey.func_updateExpertInformation,params);
            }
        }).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"正在上传头像") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    UserData.getInstance().getToken().setPhotoUrl(photoUrl);
                } else {
                    zhToast.showToast("上传失败");
                    photoUrl = null;
                }
            }
        });
    }




    @Override
    public void updateExpertInfo(final String key, final String value)
    {
        String nowValue = UserData.getInstance().getToken().getmExpertInfo().getValueByKey(key);
        if (nowValue.equals(value)) {
            return;
        }
        HashMap<String,Object> params = new HashMap<>();
        // params.put("nickname",UserData.getInstance().getToken().getNickname());
        params.put(key,value);
        params.put("memberid",UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateExpertInformation,params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    ExpertInfo info=  UserData.getInstance().getToken().getmExpertInfo();
                    info.setValueByKey(key,value);
                    UserData.getInstance().getToken().setmExpertInfo(info);
                } else{
                    zhToast.showToast("提交信息失败");
                }
                //
            }
        });

    }

    @Override
    public void refreshView() {
     loadPersonInfo();
    }

    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.EXPERT_INFO_CHANGE) {
            loadPersonInfo();
        }
    }
}
