package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.CommonContract;
import com.zhjydy_doc.presenter.presenterImp.CommonPresenterImp;
import com.zhjydy_doc.util.DataCleanManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class CommonFragment extends PageImpBaseFragment implements CommonContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.clear_title)
    TextView clearTitle;
    @BindView(R.id.clear_flag)
    ImageView clearFlag;
    @BindView(R.id.clear_layout)
    RelativeLayout clearLayout;
    private CommonContract.Presenter mPresenter;

    @Override
    protected void initData() {
        titleCenterTv.setText("通用");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void afterViewCreate() {
        new CommonPresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }


    @Override
    public void setPresenter(CommonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.title_back, R.id.clear_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.clear_layout:

                DataCleanManager.cleanApplicationData(getContext(),"");
                break;
        }
    }
}
