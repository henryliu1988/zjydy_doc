package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.FileUpLoad;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.IdentityInfoNewContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewKey;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class IdentityInfoNewPresenterImp implements IdentityInfoNewContract.Presenter {

    private IdentityInfoNewContract.View mView;

    public IdentityInfoNewPresenterImp(IdentityInfoNewContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }



    @Override
    public void finish() {

    }

    @Override
    public void submitIdentifymsg(Map<Integer, String> urls,String yiImage) {
        HashMap<String,Object> params = new HashMap();
        Map<String,Object> img0 = new HashMap<>();
        img0.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
        img0.put(ViewKey.FILE_KEY_URL,urls.get(0));
        Map<String,Object> img1 = new HashMap<>();
        img1.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
        img1.put(ViewKey.FILE_KEY_URL,urls.get(1));
        List<Map<String,Object>> listIds = new ArrayList<>();
        listIds.add(img0);
        listIds.add(img1);

        List<Map<String,Object>> listYiIds = new ArrayList<>();
        Map<String,Object> imgYi = new HashMap<>();
        imgYi.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
        imgYi.put(ViewKey.FILE_KEY_URL,yiImage);
        listYiIds.add(imgYi);

        Observable<List<Map<String, Object>>> fileIdOb = FileUpLoad.uploadFiles(listIds);
        Observable<List<Map<String, Object>>> fileYiOb = FileUpLoad.uploadFiles(listYiIds);

        Observable.zip(fileIdOb, fileYiOb, new Func2<List<Map<String,Object>>, List<Map<String,Object>>, HashMap<String,Object>>() {
            @Override
            public HashMap<String,Object> call(List<Map<String, Object>> fileIds, List<Map<String, Object>> fileYis) {
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("memberid", UserData.getInstance().getToken().getId());
                params.put("idcards", Utils.getListStrsAdd(fileIds,"id"));
                params.put("yi_cards", Utils.getListStrsAdd(fileYis,"id"));
                return params;
            }
        }).flatMap(new Func1<HashMap<String, Object>, Observable<WebResponse>>() {
            @Override
            public Observable<WebResponse> call(HashMap<String, Object> params) {
                return WebCall.getInstance().call(WebKey.func_updateExpertInformation,params);
            }
        }).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"请稍后，正在上传认证信息") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                mView.onSubmitSuccess(status);
                if (status) {
                    UserData.getInstance().getToken().getmExpertInfo().setStatus_z("4");
                    ExpertData.getInstance().loadAuthentData();
                } else{
                    zhToast.showToast("上传失败");
                }
            }
        });
    }
}
