package com.zhjydy_doc.view.zjview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;


public class CustomProgress extends Dialog
{
    private String msg;
    private boolean cancelable = false;
    private View view;
    public CustomProgress(Context context)
    {
        super(context, R.style.Custom_Progress);
        initView();
    }

    public CustomProgress(Context context, int theme)
    {
        super(context, theme);
        initView();
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus)
    {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景  
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画  
        spinner.start();
    }

    private void initView()
    {
        this.setTitle("");
         view = View.inflate(getContext(),R.layout.progress_custom,null);
        this.setContentView(view);

        if (msg == null || msg.length() == 0)
        {
            view.findViewById(R.id.message).setVisibility(View.GONE);
        } else
        {
            TextView txt = (TextView) view.findViewById(R.id.message);
            txt.setText(msg);
        }
        // 按返回键是否取消
        this.setCancelable(cancelable);
        // 设置居中
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        this.getWindow().setAttributes(lp);
    }


    public void setCancelbale(boolean enable)
    {
        this.cancelable = enable;
        this.setCancelable(cancelable);
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message)
    {
        if (message != null && message.length() > 0)
        {
            view.findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView)view. findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

}  