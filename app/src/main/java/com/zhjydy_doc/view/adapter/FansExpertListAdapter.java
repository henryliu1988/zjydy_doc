package com.zhjydy_doc.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.Utils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class FansExpertListAdapter extends MainExpertListAdapter {
    public FansExpertListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas);
    }

    public void updateGuanzhuTv(final Map<String,Object> info,TextView guanzhuTv) {
        boolean guanzhu = Utils.toBoolean(info.get("guanzhu"));
        if (guanzhu) {
            guanzhuTv.setText("已关注");
            guanzhuTv.setBackgroundColor(Color.parseColor("#FFB900"));
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.right_1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            guanzhuTv.setCompoundDrawables(drawable,null,null,null);
        } else {
            guanzhuTv.setText("相互关注");
            guanzhuTv.setBackgroundColor(Color.parseColor("#4466C8"));
            Drawable drawable= mContext.getResources().getDrawable(R.mipmap.right_1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            guanzhuTv.setCompoundDrawables(drawable,null,null,null);
        }
    }

    @Override
    public void refreshData(List<Map<String, Object>> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

}
