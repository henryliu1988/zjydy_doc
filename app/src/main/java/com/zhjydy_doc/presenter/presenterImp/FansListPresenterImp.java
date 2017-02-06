package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.FansListConTract;
import com.zhjydy_doc.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class FansListPresenterImp implements FansListConTract.Presenter,RefreshWithKey {


    private FansListConTract.View mView;
    public FansListPresenterImp(FansListConTract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.FANS_LSIT_CHANGE,this);
        start();
    }
    @Override
    public void start() {
        ExpertData.getInstance().getFunsAndGuanzhu().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
           @Override
           public void onNext(List<Map<String, Object>> maps) {
               List<Map<String, Object>> newFans = new ArrayList<Map<String, Object>>();
               List<Map<String, Object>> fans = new ArrayList<Map<String, Object>>();

               for (Map<String,Object> m: maps) {
                   int isUnread = Utils.toInteger(m.get("read_status"));
                   if (isUnread == 1) {
                       newFans.add(m);
                   } else {
                       fans.add(m);
                   }
               }
               mView.updateNewFuns(newFans);
               mView.updateFuns(fans);
               mView.noDataView(maps.size() < 1);

           }
       });
    }

    @Override
    public void finish() {

    }

    @Override
    public void readFans(String id) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("memberid", UserData.getInstance().getToken().getId());
        params.put("bei_memberid",id);
        WebCall.getInstance().call(WebKey.func_readMyfans,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                ExpertData.getInstance().loadFans();
            }
        });
    }

    @Override
    public void onRefreshWithKey(int key) {
        start();
    }
}
