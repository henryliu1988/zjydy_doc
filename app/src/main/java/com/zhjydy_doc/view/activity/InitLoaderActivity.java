package com.zhjydy_doc.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshListener;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithData;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.InitLoaderContract;
import com.zhjydy_doc.presenter.presenterImp.InitLoaderPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/18 0018.
 */
public class InitLoaderActivity extends BaseActivity implements InitLoaderContract.View, RefreshWithData
{

    private InitLoaderContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        new InitLoaderPresenterImp(this);
    }

    @Override
    public void setPresenter(InitLoaderContract.Presenter presenter)
    {
        this.mPresenter = presenter;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        RefreshManager.getInstance().addNewListener(RefreshKey.LOGIN_RESULT_BACK, this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        RefreshManager.getInstance().removeListner(RefreshKey.LOGIN_RESULT_BACK, this);
    }

    @Override
    public Context getContext()
    {
        return this;
    }

    @Override
    public void gotoMainTabs()
    {
        ActivityUtils.showHome(this, true);
    }

    @Override
    public void gotoInfoSubmit()
    {
        ActivityUtils.transActivity(this, UserInfoNewActivity.class, true);
        ;
    }

    @Override
    public void gotoLogIn()
    {

    }


    @Override
    public void onRefreshWithData(int key, Object data)
    {
        if (mPresenter != null)
        {
            mPresenter.onRefreshWithData(key, data);
        }
    }
}
