package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.SearchInfoContract;
import com.zhjydy_doc.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class SearchInfoPresenterImp implements SearchInfoContract.Presenter {

    private SearchInfoContract.View mView;

    public SearchInfoPresenterImp(SearchInfoContract.View view) {
        mView = view;
        mView.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void searchInfo(String condition) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("news",condition);
        WebCall.getInstance().call(WebKey.func_searchNewsList,params).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                list = Utils.parseObjectToListMapString(data);
                return  list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>(mView.getContext(),"请稍后，正在查询") {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updateInfoList(list);
            }
        });

    }
}
