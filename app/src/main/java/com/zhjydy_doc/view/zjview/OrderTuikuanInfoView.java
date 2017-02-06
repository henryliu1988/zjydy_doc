package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.Utils;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class OrderTuikuanInfoView extends LinearLayout{

    TextView rebackReson;
    TextView comment;
    TextView money;

    public OrderTuikuanInfoView(Context context) {
        super(context);
        initView();
    }

    public OrderTuikuanInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_reback_info_view, this);
        rebackReson = (TextView)findViewById(R.id.rebackReson);
        comment = (TextView)findViewById(R.id.comment);
        money = (TextView)findViewById(R.id.money);
    }


    public void setRebackReson(Map<String,Object> info) {
        rebackReson.setText("退款原因：" + Utils.toString(info.get("reback_reason")));
        comment.setText("备注信息：" + Utils.toString(info.get("comment")));
        String moneyText = Utils.toString(info.get("money"));
        if (!moneyText.contains("￥")) {
            moneyText = "￥" + moneyText;
        }
        money.setText("退款金额：" + moneyText);

    }

}
