package com.zhjydy_doc.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewHolder;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class OrderListAdapter extends  ListViewAdapter<Map<String,Object>> {


    public static final int OPERATE_DETAIL = 0;
    public static final int OPERATE_REPLY = 1;
    public static final int OPERATE_ZHILIAO = 2;

    public OrderListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.order_list_item);
    }
    public void setData( List<Map<String, Object>> datas) {
        super.refreshData(datas);
    }

    private OperateListener mOperateListener;

    public void setOperateListener(OperateListener listener) {
        this.mOperateListener = listener;
    }
    public interface OperateListener{
        void onOperate(Map<String, Object> item);
    }
    @Override
    public void convert(ViewHolder holder, final Map<String, Object> map) {
        String photoUrl = Utils.toString(map.get("path"));
        if (!TextUtils.isEmpty(photoUrl)){
            ImageUtils.getInstance().displayFromRemoteOver(photoUrl,(ImageView)holder.getView(R.id.photo));
        }
        ( (TextView)holder.getView(R.id.patient_name)).setText(Utils.toString(map.get("nickname")));
        ( (TextView)holder.getView(R.id.serialNum)).setText("预约单号：" +Utils.toString(map.get("orderid")));
        ( (TextView)holder.getView(R.id.time)).setText("预约时间：" + DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(map.get("showtime"))));

        ( (TextView)holder.getView(R.id.patientName)).setText("患者：" +Utils.toString(map.get("patientname")));
        ( (TextView)holder.getView(R.id.patientHospital)).setText("患者所在医院：" + DicData.getInstance().getHospitalById(Utils.toString(map.get("patienthospital"))).getHospital());

        int status = Utils.toInteger(Utils.toString(map.get("status")));
        RelativeLayout operateLayout = (RelativeLayout)holder.getView(R.id.operate_layout);
        TextView operateTv = (TextView)holder.getView(R.id.operate);
        TextView statusTv = (TextView)holder.getView(R.id.status);
        boolean isOperateVisible = true;
        String operateText = "查看详情";
        String backGroudColor = "#527EFA";
        String statusColor = "#383838";
        String statusText = "";
        int operateType = 0;
        switch (status){
            case 1: //预约中，操作为取消预约
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#F8B500";
                statusColor = "#F8B500";
                statusText = "预约申请";
                operateType = OPERATE_DETAIL;
                break;
            case 2:  //专家确认状态，可以马上支付
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#F8B500";
                statusColor = "#F8B500";
                statusText = "患者待支付";
                operateType = OPERATE_DETAIL;
                break;

            case 3:
                isOperateVisible = true;
                operateText = "马上回复";
                backGroudColor = "#60D701";
                statusColor = "#60D701";
                statusText = "患者已支付";
                operateType = OPERATE_REPLY;
                break;

            case 4:
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#60D701";
                statusColor = "#60D701";
                statusText = "患者申请退单";
                operateType = OPERATE_DETAIL;
                break;
            case 5:
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#6D00BE";
                statusColor = "#6D00BE";
                statusText = "已完成";
                operateType = OPERATE_DETAIL;
                break;
            case 6:
            case 7:
            case 9:
            case 10:
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#527Ef9";
                statusColor = "#383838";
                statusText = "订单关闭";
                operateType = OPERATE_DETAIL;
                break;
            case 8:
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#FF2501";
                statusColor = "#FF2501";
                statusText = "退款中";
                operateType = OPERATE_DETAIL;
                break;

            case 11: //
                isOperateVisible = true;
                operateText = "马上治疗";
                backGroudColor = "#60D701";
                statusColor = "#60D701";
                statusText = "会诊中";
                operateType = OPERATE_ZHILIAO;
                break;
            case 12:
                isOperateVisible = true;
                operateText = "查看详情";
                backGroudColor = "#60D701";
                statusColor = "#60D701";
                statusText = "治疗中";
                operateType = OPERATE_DETAIL;
                break;
        }
        statusTv.setText(statusText);
        statusTv.setTextColor(Color.parseColor(statusColor));
        if (!isOperateVisible) {
            operateLayout.setVisibility(View.GONE);
        }else {
            operateLayout.setVisibility(View.VISIBLE);
            operateTv.setText(operateText);
            ViewUtil.setCornerViewDrawbleBg(operateTv,backGroudColor);
        }
        operateTv.setTag(operateType);


        operateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOperateListener != null) {
                    mOperateListener.onOperate(map);
                }
            }
        });

    }
}
