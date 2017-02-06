package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.IdentityInfoContract;
import com.zhjydy_doc.util.Utils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class IdentityInfoPresenterImp implements IdentityInfoContract.Presenter,RefreshWithKey {

    private IdentityInfoContract.View mView;

    public IdentityInfoPresenterImp(IdentityInfoContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.IDENTIFY_MSG_UPDATE,this);
        start();
    }
    public void loadIdentifyInfo() {

        ExpertData.getInstance().getAuthenDatas().subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                List<Map<String,Object>> idList = Utils.parseObjectToListMapString(map.get("idcards"));
                List<Map<String,Object>> YiList = Utils.parseObjectToListMapString(map.get("yi_cards"));
                int status = Utils.toInteger(UserData.getInstance().getToken().getmExpertInfo().getStatus_z());
                List<String> idStrs = Utils.getListFromMapList(idList,"path");
                List<String> yiStrs = Utils.getListFromMapList(YiList,"path");
                mView.updateIdentifyInfo(status,idStrs,yiStrs);
            }
        });
        /*
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_patient, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
               if (status) {
                  Map<String,Object> identifyMsg = Utils.parseObjectToMapString(webResponse.getData());
                   int msgInt = Utils.toInteger(identifyMsg.get("msg"));
                   List<String> path = Utils.parseObjectToListString(identifyMsg.get("path"));
                   if (mView != null) {
                       mView.updateIdentifyInfo(msgInt,path);
                   }
               }
            }
        });
        */
    }
    @Override
    public void start() {
        loadIdentifyInfo();
    }



    @Override
    public void finish() {

    }


    @Override
    public void uploadImageFiles(List<String> urls) {

    }

    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.IDENTIFY_MSG_UPDATE) {
            loadIdentifyInfo();
        }
    }
}
