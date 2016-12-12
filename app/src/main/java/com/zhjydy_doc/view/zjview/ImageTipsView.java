package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.ImageUtils;


/**
 * Created by admin on 2016/12/5.
 */
public class ImageTipsView extends FrameLayout
{
    private Context mContext;
    private ImageView mImage;
    private TextView mTips;

    public ImageTipsView(Context context)
    {
        super(context);
        mContext = context;
        initView();
    }

    public ImageTipsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView()
    {
        LayoutInflater.from(mContext).inflate(R.layout.image_tip_view, this);
        mImage = (ImageView) findViewById(R.id.imageView);
        mTips = (TextView) findViewById(R.id.tips);
    }

    public void setImageResource(int resId)
    {
        ImageUtils.getInstance().displayFromDrawable(resId, mImage);
    }

    public void setTipText(String tip)
    {
        if (TextUtils.isEmpty(tip) || "0".equals(tip))
        {
            mTips.setVisibility(View.GONE);
        } else
        {
            mTips.setVisibility(View.VISIBLE);
            mTips.setText(tip);
        }

    }
}
