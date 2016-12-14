package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by smyhvae on 2015/5/4.
 */
public class ViewHolderAdd {

    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolderAdd(Context context, int layoutId) {
        this.mViews = new SparseArray<View>();

        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);

        mConvertView.setTag(this);

    }

    public static ViewHolderAdd get(Context context,  int layoutId) {

            return new ViewHolderAdd(context, layoutId );

    }

    /*
    通过viewId获取控件
     */
    //使用的是泛型T,返回的是View的子类
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}