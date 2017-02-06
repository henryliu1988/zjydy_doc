package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.OrderData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.presenter.contract.OrderAcceptContract;
import com.zhjydy_doc.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class OrderAcceptPresenterImp implements OrderAcceptContract.Presenter {

    private String mOrderId;
    private OrderAcceptContract.View view;
    public OrderAcceptPresenterImp(OrderAcceptContract.View view,String orderId) {
        this.mOrderId = orderId;
        this.view = view;
        this.view.setPresenter(this);
        start();
    }
    @Override
    public void accept(Map<String, Object> info) {
        HashMap<String,Object> param = new HashMap<>();
        param.put("id",mOrderId);
        param.put("hui_comment",info);
        WebCall.getInstance().call(WebKey.func_getOrderTrue,param).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (view != null) {
                    view.onAccept(status);
                }
                if (status) {
                    RefreshManager.getInstance().refreshData(RefreshKey.ORDET_LIST_CHANGE);
                }
            }
        });
    }

    @Override
    public void start() {
        loadOrderDetail();
    }


    private void loadOrderDetail() {
        OrderData.getInstance().getOrderDetaiById(mOrderId).subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                Map<String, Object> huizhenData = Utils.parseObjectToMapString(map.get("hui_comment"));
                if (huizhenData != null && huizhenData.size() > 0) {
                    view.updateHuizhen(huizhenData);
                }
            }
        });
    }
    @Override
    public void finish() {

    }
}
