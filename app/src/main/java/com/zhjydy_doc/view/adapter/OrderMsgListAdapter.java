package com.zhjydy_doc.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewHolder;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class OrderMsgListAdapter extends ListViewAdapter<Map<String,Object>> {


    public OrderMsgListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_msg_order_layout);
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> data) {
        ((TextView) holder.getView(R.id.time)).setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get("addtime"))));
        int orderStatus = Utils.toInteger(data.get("orderstatus"));
        if (orderStatus < 0) {
            return;
        }
     //   NormalItem status =  DicData.getInstance().getOrderStatuById(orderStatus);
      //  ((TextView) holder.getView(R.id.content)).setText(Utils.toString(data.get("introduction")));
        int imageRecId = -1;
        String statusName = "";
        String textCorlor = "#FFFFFFFF";
        switch (orderStatus) {
            case 1:
                imageRecId = R.mipmap.order_confirm;
                statusName = "患者提交订单";
                textCorlor = "#60D700";
                break;
            case 3:
                imageRecId = R.mipmap.order_going;
                statusName = "订单进行中";
                textCorlor = "#F8B500";
                break;

            case 4:
                imageRecId = R.mipmap.order_back_ing;
                statusName = "订单退款中";
                textCorlor = "#FF2500";

                break;
            case 5:
                imageRecId = R.mipmap.order_complete;
                statusName = "订单已完成";
                textCorlor = "#6C00BF";
                break;
            case 6:
            case 7:
                imageRecId = R.mipmap.order_cancel;
                statusName = "已取消预约";
                textCorlor = "#383838";

                break;
            case 9:
                imageRecId = R.mipmap.order_back_suc;
                statusName = "订单退款成功";
                textCorlor = "#60D700";
                break;
            case 10:
                imageRecId = R.mipmap.order_back_fail;
                statusName = "订单退款失败";
                textCorlor = "#60D700";
                break;
            case 11:
                imageRecId = R.mipmap.order_cons;
                statusName = "订单会诊中";
                textCorlor = "#527EFA";
                break;
            case 12:
                imageRecId = R.mipmap.order_going;
                statusName = "订单进行中";
                textCorlor = "#F8B500";
                break;
        }
        if(!TextUtils.isEmpty(statusName)) {
            ((TextView) holder.getView(R.id.order_status)).setText(statusName);
        }
        if(imageRecId >0) {
            ImageUtils.getInstance().displayFromDrawable(imageRecId,((ImageView)holder.getView(R.id.status_image)));
        }
        if (!TextUtils.isEmpty(textCorlor) && textCorlor.length() > 1) {
            ((TextView) holder.getView(R.id.content)).setText("订单详情内容");
            ((TextView) holder.getView(R.id.content)).setTextColor(Color.parseColor(textCorlor));
        }
        int isUnread = Utils.toInteger(data.get("status"));
        if (isUnread == 0) {
            View unReadFlag = holder.getView(R.id.unread_flag);
            ViewUtil.setOverViewDrawbleBg(unReadFlag, "#FF0000");
            unReadFlag.setVisibility(View.VISIBLE);
        } else {
            View unReadFlag = holder.getView(R.id.unread_flag);
            unReadFlag.setVisibility(View.GONE);
        }

    }
}
