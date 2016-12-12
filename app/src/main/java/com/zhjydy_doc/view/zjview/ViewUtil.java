package com.zhjydy_doc.view.zjview;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class ViewUtil {

    public static void setCornerViewDrawbleBg(View view, String strokeColor, String fillCorlor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        int strokeWidth = 1;     // 1dp 边框宽度
        int roundRadius = 5;     // 5dp 圆角半径
        int strokeColorInt = Color.parseColor(strokeColor);//边框颜色
        int fillColorInt = Color.parseColor(fillCorlor); //内部填充颜色
        gd.setColor(fillColorInt);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColorInt);
        view.setBackgroundDrawable(gd);
    }

    public static void setCornerViewDrawbleBg(View view,String fillCorlor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        int strokeWidth = 1;     // 1dp 边框宽度
        int roundRadius = 8;     // 5dp 圆角半径
        int strokeColorInt = Color.parseColor(fillCorlor);//边框颜色
        int fillColorInt = Color.parseColor(fillCorlor); //内部填充颜色
        gd.setColor(fillColorInt);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColorInt);
        view.setBackgroundDrawable(gd);
    }

    public static void setOverViewDrawbleBg(View view,String fillColor,String strokColor,int strokWidth) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setShape(GradientDrawable.OVAL);
        int strokeColorInt = Color.parseColor(strokColor);//边框颜色
        int fillColorInt = Color.parseColor(fillColor); //内部填充颜色
        gd.setColor(fillColorInt);
        gd.setStroke(strokWidth, strokeColorInt);
        view.setBackgroundDrawable(gd);
    }

    public static void setOverViewDrawbleBg(View view,String fillColor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setShape(GradientDrawable.OVAL);
        int fillColorInt = Color.parseColor(fillColor); //内部填充颜色
        gd.setColor(fillColorInt);
        view.setBackgroundDrawable(gd);
    }

}
