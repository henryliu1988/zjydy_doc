package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.FavInfoContract;
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
public class FaveInfoPresenterImp implements FavInfoContract.Presenter {

    private FavInfoContract.View mView;

    public FaveInfoPresenterImp(FavInfoContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }
    @Override
    public void start() {
        loadMsg();
    }

    private void loadMsg() {
        HashMap<String,Object> params = new HashMap<>();
        params.put("collect", UserData.getInstance().getToken().getCollectNews());
        WebCall.getInstance().call(WebKey.func_getCollectNews,params).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updateInfoList(list);
            }
        });
    }

    @Override
    public void finish() {
    }

    @Override
    public void searchFavInfos(String condition) {
        if (TextUtils.isEmpty(condition)){
            loadMsg();
            return;
        }
        HashMap<String,Object> param = new HashMap<>();
        param.put("news",condition);
        param.put("collect",UserData.getInstance().getToken().getCollectNews());
        WebCall.getInstance().call(WebKey.func_searchCollectNews,param).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(webResponse.getData());
                mView.updateInfoList(list);
            }
        });

    }

    @Override
    public void cancelFav(final String id) {
        HashMap<String, Object> params = new HashMap<>();
        List<String> collect = new ArrayList<>();
        collect.addAll(UserData.getInstance().getToken().getCollectNewsList());
        if (collect.contains(id)) {
            collect.remove(id);
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
                    if (collect.contains(id)) {
                        collect.remove(id);
                    }
                    UserData.getInstance().getToken().setCollectNewAsList(collect);
                    loadMsg();
                } else {
                    zhToast.showToast("取消收藏失败！");
                }
            }
        });

    }
}
