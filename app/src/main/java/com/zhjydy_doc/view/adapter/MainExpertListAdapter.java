package com.zhjydy_doc.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ScoreView;
import com.zhjydy_doc.view.zjview.ViewHolder;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class MainExpertListAdapter extends PageLoadListAdapter {

    protected Context mContext;
    private int expertDataType = ExpertData.GUAN_STAT_NUL;
    public MainExpertListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_main_expert_info_item);
        this.mContext = context;
    }

    public int getExpertDataType() {
        return expertDataType;
    }

    public void setExpertDataType(int expertDataType) {
        this.expertDataType = expertDataType;
    }

    @Override
    public void convert(ViewHolder holder, final Map<String, Object> info) {
        // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.name)).setText(Utils.toString(info.get("realname")));
        ((TextView) holder.getView(R.id.depart)).setText(DicData.getInstance().getOfficeById(Utils.toString(info.get("office"))).getName());
        ((TextView) holder.getView(R.id.profession)).setText(DicData.getInstance().getBusinessById(Utils.toString(info.get("business"))).getName());
        ((TextView) holder.getView(R.id.hospital)).setText(DicData.getInstance().getHospitalById(Utils.toString(info.get("hospital"))).getHospital());

        ((TextView) holder.getView(R.id.special)).setText(Utils.toString(info.get("adept")));
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
        ScoreView starView = (ScoreView) holder.getView(R.id.star);
        int score = Utils.toInteger(info.get("stars"));
        if (score > 100) {
            score = 100;
        }
        if (score < 0) {
            score = 0;
        }
        ((TextView) holder.getView(R.id.score)).setText("推荐分数：" + score + "分");
        starView.setScore(score, 100);

        TextView identiFyTv = (TextView) holder.getView(R.id.identify);
        View guanzhuLayout = holder.getView(R.id.guanzhu_layout);
        TextView guanzhuTv = (TextView) holder.getView(R.id.guanzhu);

        int identifyStatus = Utils.toInteger(info.get("status_z"));
        if (identifyStatus == 1) {
            identiFyTv.setText("已认证");
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.identify_ok);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            identiFyTv.setCompoundDrawables(drawable, null, null, null);
            identiFyTv.setCompoundDrawablePadding(3);
            identiFyTv.setTextColor(mContext.getResources().getColor(R.color.score_text));
        } else {
            identiFyTv.setText("未认证");
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.identify_no);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            identiFyTv.setCompoundDrawables(drawable,null,null,null);
            identiFyTv.setTextColor(mContext.getResources().getColor(R.color.black_text2));
        }
        updateGuanzhuTv(info, guanzhuLayout, guanzhuTv);
        ImageUtils.getInstance().displayFromRemoteOver(Utils.toString(info.get("path")), (ImageView) holder.getView(R.id.photo));
    }


    public void updateGuanzhuTv(final Map<String, Object> info, final View layout, final TextView guanzhuTv) {
        String id = Utils.toString(info.get("id"));

        if (expertDataType == ExpertData.GUAN_STAT_NUL) {
            ExpertData.getInstance().getGuanStatus(id).subscribe(new BaseSubscriber<Integer>() {
                @Override
                public void onNext(final Integer guanzhuStatus) {
                    switch (guanzhuStatus) {
                        case ExpertData.GUAN_STAT_GUAN:
                            guanzhuTv.setText("已关注");
                            ViewUtil.setCornerViewDrawbleBg(layout,"#60D701",1);
                            Drawable drawableGuan = mContext.getResources().getDrawable(R.mipmap.guan_ok);
                            drawableGuan.setBounds(0, 0, drawableGuan.getMinimumWidth(), drawableGuan.getMinimumHeight());
                            guanzhuTv.setCompoundDrawables(drawableGuan, null, null, null);
                            break;
                        case ExpertData.GUAN_STAT_MEGUAN:
                            guanzhuTv.setText("相互关注");
                            ViewUtil.setCornerViewDrawbleBg(layout,"##4466C8",1);
                            Drawable drawablemeGuan = mContext.getResources().getDrawable(R.mipmap.guan_all);
                            drawablemeGuan.setBounds(0, 0, drawablemeGuan.getMinimumWidth(), drawablemeGuan.getMinimumHeight());
                            guanzhuTv.setCompoundDrawables(drawablemeGuan, null, null, null);
                            break;
                        case ExpertData.GUAN_STAT_NUL:
                            guanzhuTv.setText("加关注");
                            ViewUtil.setCornerViewDrawbleBg(layout,"#FFB900",1);
                            Drawable drawableNul = mContext.getResources().getDrawable(R.mipmap.guan_nul);
                            drawableNul.setBounds(0, 0, drawableNul.getMinimumWidth(), drawableNul.getMinimumHeight());
                            guanzhuTv.setCompoundDrawables(drawableNul, null, null, null);
                            break;
                    }
                    guanzhuTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mGuanzhuListener != null) {
                                mGuanzhuListener.onGuanzhuClick(info,guanzhuStatus);
                            }
                        }
                    });
                }
            });
        } else if (expertDataType == ExpertData.GUAN_STAT_GUAN) {
            guanzhuTv.setText("已关注");
            ViewUtil.setCornerViewDrawbleBg(layout,"#60D701",1);
            Drawable drawableGuan = mContext.getResources().getDrawable(R.mipmap.guan_ok);
            drawableGuan.setBounds(0, 0, drawableGuan.getMinimumWidth(), drawableGuan.getMinimumHeight());
            guanzhuTv.setCompoundDrawables(drawableGuan, null, null, null);
            guanzhuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGuanzhuListener != null) {
                        mGuanzhuListener.onGuanzhuClick(info,ExpertData.GUAN_STAT_GUAN);
                    }
                }
            });
        }else if (expertDataType == ExpertData.GUAN_STAT_MEGUAN) {
            guanzhuTv.setText("相互关注");
            ViewUtil.setCornerViewDrawbleBg(layout,"##4466C8",1);
            Drawable drawablemeGuan = mContext.getResources().getDrawable(R.mipmap.guan_all);
            drawablemeGuan.setBounds(0, 0, drawablemeGuan.getMinimumWidth(), drawablemeGuan.getMinimumHeight());
            guanzhuTv.setCompoundDrawables(drawablemeGuan, null, null, null);
            guanzhuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGuanzhuListener != null) {
                        mGuanzhuListener.onGuanzhuClick(info,ExpertData.GUAN_STAT_MEGUAN);
                    }
                }
            });

        }



    }

    public void setGuanzhuListener(onGuanzhuClickListener listener) {
        this.mGuanzhuListener = listener;
    }

    private onGuanzhuClickListener mGuanzhuListener;

    public interface onGuanzhuClickListener {
        public void onGuanzhuClick(Map<String, Object> item,int status);
    }


}
