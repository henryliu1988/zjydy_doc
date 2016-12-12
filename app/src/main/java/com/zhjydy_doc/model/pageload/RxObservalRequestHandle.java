package com.zhjydy_doc.model.pageload;

import com.shizhefei.mvc.RequestHandle;

import rx.Subscription;

/**
 * Created by admin on 2016/12/1.
 */
public class RxObservalRequestHandle implements RequestHandle
{

    private final Subscription subscription ;

    public RxObservalRequestHandle(Subscription subscription)
    {
        super();
        this.subscription = subscription;

    }

    @Override
    public void cancle()
    {
        subscription.unsubscribe();
    }

    @Override
    public boolean isRunning()
    {
        return false;
    }
}
