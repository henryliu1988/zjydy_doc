package com.zhjydy_doc.model.data;

import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.util.Utils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public class OrderData {

    public static OrderData instance = new OrderData();

    public static OrderData getInstance() {
        return instance;
    }


    public Observable<Map<String,Object>> getOrderDetaiById(String orderId) {
        HashMap<String,Object> p = new HashMap<>();
        p.put("id",orderId);
        return  WebCall.getInstance().call(WebKey.func_getOrdersById,p).map(new Func1<WebResponse, Map<String,Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                Map<String,Object> order = new HashMap<String, Object>();
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    order = Utils.parseObjectToMapString(webResponse.getData());
                }
                return order;
            }
        });
    }


    public static final int OPERATE_SUBRIBE_DETAIL = 1;
    public static final int OPERATE_TOPAY_DETAIL = 2;
    public static final int OPERATE_PAYOK_DETAIL = 3;
    public static final int OPERATE_BACKPAY_DETAIL = 4;
    public static final int OPERATE_ORDER_FINISH = 5;
    public static final int OPERATE_ORDER_CLOSE = 6;
    public static final int OPERATE_ORDER_BACKING = 7;
    public static final int OPERATE_ORDER_HUIZHEN = 8;
    public static final int OPERATE_ORDER_ZHILIAO = 9;

    public int getOrderOperateByItem(Map<String,Object> item) {
        int status = Utils.toInteger(Utils.toString(item.get("status")));

        switch (status) {
            case 1:
                return OPERATE_SUBRIBE_DETAIL;
            case 2:
                return OPERATE_TOPAY_DETAIL;
            case 3:
                return OPERATE_PAYOK_DETAIL;
            case 4:
                return OPERATE_BACKPAY_DETAIL;
            case 5:
            case 13:
                return OPERATE_ORDER_FINISH;
            case 6:
            case 7:
            case 9:
            case 10:
                return OPERATE_ORDER_CLOSE;
            case 8:
                return OPERATE_ORDER_BACKING;
            case 11:
                return OPERATE_ORDER_HUIZHEN;
            case 12:
                return OPERATE_ORDER_ZHILIAO;

        }
        return -1;
    }
}
