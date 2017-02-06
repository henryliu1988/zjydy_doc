package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.Utils;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class OrderHuizhenInfoView extends LinearLayout{


    TextView countTv;
    TextView moneyTv;
    TextView dateTv;
    TextView addressTv;
    TextView expertTv;
    TextView mainDocTv;
    TextView telTv;
    TextView commentTv;

    TextView huizhenEdit;

    public OrderHuizhenInfoView(Context context) {
        super(context);
        initView();
    }

    public OrderHuizhenInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_huizhen_info_view, this);
        countTv = (TextView)findViewById(R.id.count);
        moneyTv = (TextView)findViewById(R.id.money);
        addressTv = (TextView)findViewById(R.id.address);
        dateTv = (TextView)findViewById(R.id.date);
        expertTv = (TextView)findViewById(R.id.expert);
        mainDocTv = (TextView)findViewById(R.id.main_doc);
        telTv = (TextView)findViewById(R.id.tel);
        commentTv = (TextView)findViewById(R.id.comment);
        huizhenEdit = (TextView)findViewById(R.id.edit);
        addressTv.setVisibility(View.GONE);
        huizhenEdit.setVisibility(View.GONE);
    }


    public void setEditAble(boolean eanble) {
        if (eanble) {
            huizhenEdit.setVisibility(View.VISIBLE);
            huizhenEdit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onHuiZhenEdit();
                }
            });
        } else {
            huizhenEdit.setVisibility(View.GONE);
        }
    }

    public void setHuiZhenInfo(Map<String,Object> info) {
        countTv.setText("会诊次数：" + Utils.toString(info.get("nums")));
        moneyTv.setText("会诊费用：" + Utils.toString(info.get("money")));

        dateTv.setText("会诊时间：" + DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("time"))));
        addressTv.setText("会诊地点：" + Utils.toString(info.get("address")));
        expertTv.setText("参与专家：" + Utils.toString(info.get("experts")));
        mainDocTv.setText("主要负责医生：" + Utils.toString(info.get("main_experts")));
        telTv.setText("联系方式：" + Utils.toString(info.get("mobile")));
        commentTv.setText("备注信息：" + Utils.toString(info.get("comment")));
    }

    public HuizhenEditListener mListener;
    public void setHuizhenEditListener(HuizhenEditListener lisenter) {
        mListener = lisenter;
    }
    public interface HuizhenEditListener{
        void onHuiZhenEdit();
    }

}
