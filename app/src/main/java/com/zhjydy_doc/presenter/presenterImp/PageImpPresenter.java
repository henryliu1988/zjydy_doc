package com.zhjydy_doc.presenter.presenterImp;

import android.content.Intent;


import com.zhjydy_doc.presenter.contract.PageImpContract;
import com.zhjydy_doc.view.zjview.ActivityResultView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/9.
 */
public  class PageImpPresenter implements PageImpContract.Presenter
{
    private final PageImpContract.View view;

    private List<ActivityResultView> mActivityResultViews = new ArrayList<>();

    public PageImpPresenter(PageImpContract.View view)
    {
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        for (ActivityResultView view:mActivityResultViews) {
            if (view != null) {
                view.onActivityResult1(requestCode,resultCode,data);
            }
        }
    }
}
