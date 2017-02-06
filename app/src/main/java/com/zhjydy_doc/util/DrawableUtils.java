package com.zhjydy_doc.util;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class DrawableUtils {
    public static GradientDrawable getSolidCornerBakGroud(int corner, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(3);
        drawable.setColor(color);
        return drawable;
    }
}
