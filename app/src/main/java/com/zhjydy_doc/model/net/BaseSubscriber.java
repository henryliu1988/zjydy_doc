package com.zhjydy_doc.model.net;

import android.app.Activity;
import android.content.Context;


import com.zhjydy_doc.util.NetworkUtil;
import com.zhjydy_doc.view.zjview.CustomProgress;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T>
{


    private boolean showProgress;
    private Context mContext;

    public CustomProgress waitDlg;

    private String msg = "请稍后,正在加载数据";
    public BaseSubscriber()
    {
    }

    public BaseSubscriber(Context context, boolean showProgress)
    {
        this.mContext = context;
        this.showProgress = showProgress;
    }

    public BaseSubscriber(Context context, String msg)
    {
        this.mContext = context;
        this.showProgress = true;
        this.msg = msg;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (!NetworkUtil.isNetworkAvailable())
        {

         //   SimsToast.showToast("当前网络不可用，请检查网络情况");

            // 一定好主动调用下面这一句
            onCompleted();

            return;
        }
        // 显示进度条
        if (showProgress)
        {
            showLoadingProgress();
        }

    }

    @Override
    public void onCompleted()
    {
        //关闭等待进度条
        if (showProgress)
        {
            closeLoadingProgress();
        }


    }

    @Override
    public void onError(Throwable e)
    {
        closeLoadingProgress();
    }

    public void showLoadingProgress()
    {
        if (mContext != null && mContext instanceof Activity)
        {
            waitDlg = new CustomProgress(mContext);
            waitDlg.setMessage(msg);
            waitDlg.show();
        }
    }

    public void closeLoadingProgress()
    {
        if (waitDlg != null)
        {
            waitDlg.dismiss();
        }
    }

}