package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.data.MsgData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MsgAllListContract;
import com.zhjydy_doc.util.ListMapComparator;
import com.zhjydy_doc.util.Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MsgAllListPresenterImp implements MsgAllListContract.Presenter,RefreshWithKey{

    private MsgAllListContract.View mView;

    public MsgAllListPresenterImp(MsgAllListContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.FANS_LSIT_CHANGE,this);
        start();
    }

    @Override
    public void start() {
        initOrderList();
        initSystemList();
        initCommentList();
        initFansList();
    }


    private void initFansList() {
        ExpertData.getInstance().getUnreadFans().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                mView.updateFans(maps.size() > 0);
            }
        });
    }
    private void initCommentList() {
        MsgData.getInstance().getAllCommentPatientNewList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                boolean isUnread = false;

                for (Map<String, Object> m: maps) {
                    if( Utils.toInteger(m.get("status")) == 0) {
                        isUnread = true;
                        break;
                    }
                }
                mView.updatePatientComment(isUnread);
            }
        });

        MsgData.getInstance().getAllCommentExpertNewList().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                boolean isUnread = false;

                for (Map<String, Object> m: maps) {
                    if( Utils.toInteger(m.get("status")) == 0) {
                        isUnread = true;
                        break;
                    }
                }
                mView.updateDocComment(isUnread);
            }
        });
    }

    private void initSystemList() {
        getOrderItemData().subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                if (mView != null) {
                    mView.updateSystemList(map);
                }
            }
        });
    }
    private void initOrderList() {

        getOrderItemData().subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                if (mView != null) {
                    mView.updateOrderList(map);
                }
            }
        });

    }

    private Observable<Map<String, Object>> getOrderItemData() {
        return MsgData.getInstance().getAllOrderMsgList().map(new Func1<List<Map<String, Object>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(List<Map<String, Object>> orderList) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("image", R.mipmap.msg_order_img);
                orderData.put("title", "订单");
                orderData.put("content", "暂无新消息");
                orderData.put("count", "0");
                if (orderList != null && orderList.size() > 0) {
                    Map<String, Object> order = orderList.get(0);
                    orderData.put("content", order.get("introduction"));
                    orderData.put("count", orderList.size());
                    orderData.put("time", order.get("addtime"));
                    orderData.put("orderid", order.get("id"));
                    boolean unRead = Utils.toInteger(order.get("status")) == 0;
                    orderData.put("status",unRead);
                }
                orderData.put("type", 0);
                return orderData;
            }
        });
    }

    private Observable<Map<String, Object>> getSystemItemData() {
        return MsgData.getInstance().getNewSystemMsgList().map(new Func1<List<Map<String, Object>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(List<Map<String, Object>> sysList) {
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("image", R.mipmap.msg_system_img);
                orderData.put("title", "系统消息");
                orderData.put("content", "暂无新消息");
                orderData.put("count", "0");
                if (sysList != null && sysList.size() > 0) {
                    Map<String, Object> order = sysList.get(0);
                    orderData.put("content", order.get("content"));
                    orderData.put("count", sysList.size());
                    orderData.put("time", order.get("addtime"));
                }
                orderData.put("type", 1);
                return orderData;
            }
        });
    }
    @Override
    public void readOrder(String id) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        WebCall.getInstance().call(WebKey.func_updateOrdersMsg,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                MsgData.getInstance().loadOrderMsgData();
            }
        });
    }

    @Override
    public void readComment(final int type,String id) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        WebCall.getInstance().call(WebKey.func_updateCommentStatus,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                MsgData.getInstance().loadNewCommentList(type);
            }
        });

    }

    @Override
    public void finish() {

    }

    @Override
    public void onRefreshWithKey(int key) {
        switch (key) {
            case RefreshKey.FANS_LSIT_CHANGE:
                initFansList();
                break;
        }
    }
}
