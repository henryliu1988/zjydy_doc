package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MainOrderContract;
import com.zhjydy_doc.util.ListMapComparator;
import com.zhjydy_doc.util.Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainOrderPresenterImp  implements MainOrderContract.MainOrderPresenter,RefreshWithKey {

    private MainOrderContract.MainOrderView mView;

    public MainOrderPresenterImp(MainOrderContract.MainOrderView view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.ORDET_LIST_CHANGE,this);
        start();
    }

    @Override
    public void start() {
        loadOrders();
    }
    private void loadOrders() {
        HashMap<String,Object> params = new HashMap<>();
        params.put("expertid", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_getOrders,params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),true) {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String,Object>> list = Utils.parseObjectToListMapString(data);
                ListMapComparator comp = new ListMapComparator("showtime", 0);
                Collections.sort(list, comp);
                mView.update(list);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onNetError();
            }
        });
    }

    @Override
    public void finish() {

    }

    @Override
    public void reloadOrders() {
        loadOrders();
    }

    @Override
    public void onRefreshWithKey(int key) {
        loadOrders();
    }
}
