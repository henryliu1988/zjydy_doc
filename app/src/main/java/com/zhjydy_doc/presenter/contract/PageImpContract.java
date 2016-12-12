package com.zhjydy_doc.presenter.contract;

import android.content.Intent;

import com.zhjydy_doc.presenter.BaseView;


/**
 * Created by admin on 2016/8/9.
 */
public interface PageImpContract
{

    interface View extends BaseView<Presenter>
    {
    }

    interface Presenter
    {
        void onActivityResult(int requestCode, int resultCode, Intent data);

    }
}
