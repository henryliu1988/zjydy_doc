package com.zhjydy_doc.presenter.presenterImp;

import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.pageload.PageLoadDataSource;
import com.zhjydy_doc.presenter.contract.SearchExpertContract;
import com.zhjydy_doc.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class SearchExpertPresenterImp extends PageLoadDataSource implements SearchExpertContract.Presenter {

    private SearchExpertContract.View mView;
    public SearchExpertPresenterImp(SearchExpertContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }
    @Override
    public void start() {
        
    }

    @Override
    public void finish() {

    }

    @Override
    public void searchExpert(String condition) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("expert",condition);
        WebCall.getInstance().call(WebKey.func_searchExpertsList,params).map(new Func1<WebResponse, List<Map<String,Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
                return  list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>(mView.getContext(),"请稍后，正在查询") {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updateExpertList(list);
            }
        });
    }

    @Override
    public RequestHandle loadListData(ResponseSender<List<Map<String, Object>>> sender, int page)
    {
        return null;
    }
}
