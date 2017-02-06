package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy_doc.model.data.OrderData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.net.WebUtils;
import com.zhjydy_doc.presenter.contract.OrderDetailContract;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderDetailPresenterImp implements OrderDetailContract.Presenter {

    private OrderDetailContract.View mView;

    private String orderId;

    private Map<String,Object> orderInfo = new HashMap<>();
    public OrderDetailPresenterImp(OrderDetailContract.View view, String id) {
        this.mView = view;
        this.orderId = id;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
       if (TextUtils.isEmpty(orderId)) {
           return;
       }
        loadOrderContent(orderId);
    }

    private void loadOrderContent(String id) {

        OrderData.getInstance().getOrderDetaiById(orderId).subscribe(new BaseSubscriber<Map<String, Object>>(mView.getContext(),true) {
            @Override
            public void onNext(Map<String, Object> info) {
                if (mView != null) {
                    mView.update(info);
                }
            }
        });
    }
    @Override
    public void finish() {
    }


    @Override
    public void confirmHuizhen() {
        HashMap<String,Object> p = new HashMap<>();
        p.put("id",orderId);
        WebCall.getInstance().call(WebKey.func_intoHui,p).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status){
                    zhToast.showToast("确认成功");
                } else{
                    zhToast.showToast("确认失败");
                }
                loadOrderContent(orderId);

            }
        });
    }

    @Override
    public void confirmZhiliaoFinish() {
        HashMap<String,Object> p = new HashMap<>();
        p.put("id",orderId);
        WebCall.getInstance().call(WebKey.func_closeZhi,p).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status){
                    zhToast.showToast("治疗已完成"); /*，恭喜您，该订单已完成！*/
                } else{
                    zhToast.showToast("治疗完成信息提交失败");
                }
                loadOrderContent(orderId);

            }
        });

    }

    @Override
    public void confirmFinishHuizhen() {
        HashMap<String,Object> p = new HashMap<>();
        p.put("id",orderId);
        WebCall.getInstance().call(WebKey.func_intoZhi,p).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status){
                    zhToast.showToast("完成会诊确认成功，该订单进入治疗阶段");
                } else{
                    zhToast.showToast("完成会诊信息提交失败");
                }
                loadOrderContent(orderId);

            }
        });

    }

    private HashMap<String,Object> getOrderParam() {
        HashMap<String,Object> p = new HashMap<>();
        p.put("id",orderId);
        return p;
    }
    @Override
    public void acceptBack() {
        WebCall.getInstance().call(WebKey.func_getRebackMoney,getOrderParam()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    zhToast.showToast("您已同意退款给患者，提交成功");
                } else {
                    zhToast.showToast("提交信息失败");
                }
                loadOrderContent(orderId);
            }
        });
    }


}
