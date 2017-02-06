package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.InfoDetailContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class InfoDetailPresenterImp implements InfoDetailContract.Presenter {

    private InfoDetailContract.View mView;

    private String infoId;

    public InfoDetailPresenterImp(InfoDetailContract.View view, String id) {
        this.mView = view;
        this.infoId = id;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        if (TextUtils.isEmpty(infoId)) {
            return;
        }
        loadInfoContent();
        loadFavStatus();
    }
    private void loadFavStatus() {
        List<String> collecs = UserData.getInstance().getToken().getCollectNewsList();
        mView.updateFavStatus(collecs.contains(infoId));
    }
    private void loadInfoContent() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", infoId);
        WebCall.getInstance().call(WebKey.func_getNewsById, params).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> map =  Utils.parseObjectToMapString(data);
                String collect = UserData.getInstance().getToken().getCollectNews();
                map.put("collect", collect);
                return map;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                mView.update(map);
            }
        });
    }

    @Override
    public void finish() {
    }

    @Override
    public void saveInfo() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("newsid", infoId);
        params.put("userid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_collectNews, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    List<String> collect = UserData.getInstance().getToken().getCollectNewsList();
                    List<String> newS = new ArrayList<String>();
                    newS.addAll(collect);
                    if (!newS.contains(infoId)) {
                        newS.add(infoId);
                    }
                    UserData.getInstance().getToken().setCollectNewAsList(newS);
                    loadFavStatus();
                } else {
                    zhToast.showToast("收藏失败");
                }
            }
        });
    }

    @Override
    public void cancelSaveInfo() {
        HashMap<String, Object> params = new HashMap<>();
        List<String> collect = new ArrayList<>();
        collect.addAll(UserData.getInstance().getToken().getCollectNewsList());
        if (collect.contains(infoId)) {
            collect.remove(infoId);
        }
        params.put("collectnews",Utils.strListToString(collect));
        params.put("userid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_cancelCollectNews, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "取消收藏") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    ArrayList<String> collect = new ArrayList<String>();
                    collect.addAll(UserData.getInstance().getToken().getCollectNewsList());
                    if (collect.contains(infoId)) {
                        collect.remove(infoId);
                    }
                    UserData.getInstance().getToken().setCollectNewAsList(collect);
                    loadFavStatus();
                } else {
                    zhToast.showToast("取消收藏失败！");
                }
            }
        });

    }

    @Override
    public void shareInfo(String url) {

    }
}
