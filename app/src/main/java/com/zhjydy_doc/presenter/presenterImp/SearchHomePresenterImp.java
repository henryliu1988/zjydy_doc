package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.SearchHomeContract;
import com.zhjydy_doc.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class SearchHomePresenterImp implements SearchHomeContract.Presenter {
    private SearchHomeContract.View mView;

    public SearchHomePresenterImp(SearchHomeContract.View view) {
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
    public void searchExpertAndInfo(String info)
    {
        HashMap<String,Object> paramsExpert = new HashMap<>();
        paramsExpert.put("expert",info);
        Observable<List<Map<String,Object>>> expertOb =  WebCall.getInstance().call(WebKey.func_searchExpertsList,paramsExpert).map(new Func1<WebResponse, List<Map<String,Object>>>() {
        @Override
        public List<Map<String, Object>> call(WebResponse webResponse) {
            String data = webResponse.getData();
            List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
            return  list;
        }
    });
        HashMap<String,Object> paramsInfo = new HashMap<>();
        paramsInfo.put("news",info);
        Observable<List<Map<String,Object>>> infoOb =WebCall.getInstance().call(WebKey.func_searchNewsList,paramsInfo).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                list = Utils.parseObjectToListMapString(data);
                return  list;
            }
        });
        Observable.zip(expertOb, infoOb, new Func2<List<Map<String,Object>>, List<Map<String,Object>>, Map<String,Object>>()
        {
            @Override
            public Map<String, Object> call(List<Map<String, Object>> expert, List<Map<String, Object>> info)
            {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("expert",expert);
                map.put("info",info);
                return map;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>(mView.getContext(),"请稍后，正在查询")
        {
            @Override
            public void onNext(Map<String, Object> map)
            {
                List<Map<String, Object>> expert = ( List<Map<String, Object>> )map.get("expert");
                List<Map<String, Object>> info = ( List<Map<String, Object>> )map.get("info");
                mView.onSearchResult(expert,info);
            }
        });
    }
}
