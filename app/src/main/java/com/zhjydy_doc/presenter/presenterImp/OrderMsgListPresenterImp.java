package com.zhjydy_doc.presenter.presenterImp;

import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy_doc.model.data.MsgData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.model.pageload.PageLoadDataSource;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.OrderMsgListContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderMsgListPresenterImp extends PageLoadDataSource implements OrderMsgListContract.Presenter, RefreshWithKey {

    private OrderMsgListContract.View mView;


    public OrderMsgListPresenterImp(OrderMsgListContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.ORDER_MSG_CHANGE, this);
        start();
    }

    @Override
    public void start() {

        loadOrderList();
    }

    private void loadOrderList() {
        MsgData.getInstance().getAllOrderMsgList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> orderLit) {
                if (mView != null)
                    mView.updateOrderList(orderLit);
            }
        });
    }

    @Override
    public void finish() {
    }


    @Override
    public void reLoadData() {
        MsgData.getInstance().loadOrderMsgData();
    }

    @Override
    public void readOrder(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        WebCall.getInstance().call(WebKey.func_updateOrdersMsg, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                if (WebUtils.getWebStatus(webResponse)) {
                    reLoadData();
                }
            }
        });
    }

    @Override
    public RequestHandle loadListData(ResponseSender<List<Map<String, Object>>> sender, int page) {
        return null;
    }

    @Override
    public void onRefreshWithKey(int key) {
        switch (key) {
            case RefreshKey.ORDER_MSG_CHANGE:
                start();
                break;

        }
    }
}
