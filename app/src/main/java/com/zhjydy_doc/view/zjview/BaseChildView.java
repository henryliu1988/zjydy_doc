package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public abstract  class BaseChildView extends ViewGroup {
    protected  Context mContext;
    public BaseChildView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public BaseChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(getLayoutId(),this);
    }
    protected abstract  int getLayoutId();

}
