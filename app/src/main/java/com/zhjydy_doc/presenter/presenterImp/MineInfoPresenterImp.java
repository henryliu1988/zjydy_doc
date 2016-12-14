package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.AppData;
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
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_NICK_NAME,this);
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_PHOTO,this);
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_SEX,this);

        start();
    }

    @Override
    public void start() {
        loadPersonInfo();
        loadSexPickData();
    }
    private void loadSexPickData() {
        ArrayList<PickViewData> sexData = new ArrayList<>();
        sexData.add(new PickViewData("1","男"));
        sexData.add(new PickViewData("2","女"));
        mView.updateSexPick(sexData);
    }


    private void loadPersonInfo() {
        if (mView !=null){
            mView.updateInfo( AppData.getInstance().getToken());
        }
    }

    @Override
    public void finish() {

    }


    private String photoUrl;
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
                params.put("nickname",AppData.getInstance().getToken().getNickname());
                params.put("sex",AppData.getInstance().getToken().getSex());
                String imageIds = Utils.getListStrsAdd(files,"id");
                params.put("head_img",imageIds);
                params.put("id",AppData.getInstance().getToken().getId());
                if (files.size() >0) {
                    photoUrl = Utils.toString(files.get(0).get("url"));
                }
                return WebCall.getInstance().call(WebKey.func_updateMember,params);
            }
        }).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"正在上传头像") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    AppData.getInstance().getToken().setPhotoUrl(photoUrl);
                } else {
                    zhToast.showToast("上传失败");
                    photoUrl = null;
                }
            }
        });
    }

    @Override
    public void updateMemberSex(final int sex) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("nickname",AppData.getInstance().getToken().getNickname());
        params.put("sex",sex);
        params.put("head_img",AppData.getInstance().getToken().getPhotoId());
        params.put("id",AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateMember,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    AppData.getInstance().getToken().setSex(sex + "");
                }
                zhToast.showToast(WebUtils.getWebMsg(webResponse));
            }
        });
    }

    @Override
    public void updateMemberName(String name) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("nickname",name);
        params.put("sex",AppData.getInstance().getToken().getSex());
        params.put("head_img",AppData.getInstance().getToken().getPhotoId());
        params.put("id",AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateMember,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                zhToast.showToast(WebUtils.getWebMsg(webResponse));
            }
        });
    }

    @Override
    public void refreshView() {
     loadPersonInfo();
    }

    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.TOKEN_MSG_NICK_NAME || key == RefreshKey.TOKEN_MSG_SEX ||key == RefreshKey.TOKEN_MSG_PHOTO) {
            loadPersonInfo();
        }
    }
}
