package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.HospitalDicItem;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.FileUpLoad;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithData;
import com.zhjydy_doc.presenter.contract.UserInfoNewContract;
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
 * Created by Administrator on 2016/12/13 0013.
 */
public class UserInfoNewPresenterImp implements UserInfoNewContract.Presenter, RefreshWithData {

    private UserInfoNewContract.View mView;
    private String userId;
    public UserInfoNewPresenterImp(UserInfoNewContract.View view,String userid){
        this.mView = view;
        this.userId = userid;
        mView.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.LOGIN_RESULT_BACK, this);
        start();
    }
    @Override
    public void submitUserInfo(final Map<String, Object> info) {

        String path = Utils.toString(info.get("photo"));
        info.remove("photo");
        Observable<WebResponse> ob;
        if (!TextUtils.isEmpty(path)){
            List<Map<String,Object>> files = new ArrayList<>();
            Map<String,Object> file = new HashMap<>();
            file.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
            file.put(ViewKey.FILE_KEY_URL,path);
            files.add(file);
            ob=  FileUpLoad.uploadFiles(files).flatMap(new Func1<List<Map<String, Object>>, Observable<WebResponse>>() {
                @Override
                public Observable<WebResponse> call(List<Map<String, Object>> files) {
                    String imageIds = Utils.getListStrsAdd(files,"id");
                    HashMap<String,Object> params  = new HashMap<String, Object>();
                    params.putAll(info);
                    params.put("img",imageIds);
                    return WebCall.getInstance().call(WebKey.func_updateExpertInformation,params);
                }
            });
        } else {
            HashMap<String,Object> params  = new HashMap<String, Object>();
            params.putAll(info);
            ob = WebCall.getInstance().call(WebKey.func_updateExpertInformation,params);
        }
        ob.subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getReturnData();
                Map<String,Object> map = Utils.parseObjectToMapString(data);
                boolean status = Utils.toBoolean(map.get("status"));
                if (status){
                    //zhToast.showToast("提交信息成功");
                    UserData.getInstance().tryLoginManager(UserData.getInstance().getLogInfoNum(),UserData.getInstance().getLogPassword(),mView.getContext());
                   // mView.onSubmitSuc();
                } else{
                    zhToast.showToast("提交信息失败");
                }
            }
        });
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
        mView.updateBusiness(businessData);
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

    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.LOGIN_RESULT_BACK) {
            Map<String, Object> dataMap = Utils.parseObjectToMapString(data);
            boolean status = Utils.toBoolean(dataMap.get("status"));
            String msg = Utils.toString(dataMap.get("msg"));
            if (mView != null) {
                if (status) {
                    boolean infoConf = UserData.getInstance().isExpertInfoSubmit();
                    if (infoConf) {
                        mView.gotoMainTabs();
                    }
                }else {
                    mView.gotoMainTabs();
                }

            }
        }
    }
}
