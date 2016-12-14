package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.presenter.contract.AccountSafeContract;
import com.zhjydy_doc.presenter.presenterImp.AccountSafePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class AccountSafeFragment extends PageImpBaseFragment implements AccountSafeContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.layout_change_phonenum)
    RelativeLayout layoutChangePhonenum;
    @BindView(R.id.layout_change_payword)
    RelativeLayout layoutChangePayword;
    @BindView(R.id.layout_change_loginpass)
    RelativeLayout layoutChangeLoginpass;
    @BindView(R.id.phoneNum_tv)
    TextView phoneNumTv;
    private AccountSafeContract.Presenter mPresenter;

    @Override
    protected void initData() {
        titleCenterTv.setText("账户安全");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_safe;
    }

    @Override
    protected void afterViewCreate() {
        new AccountSafePresenterImp(this);
    }

    @Override
    public void setPresenter(AccountSafeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {
        if (mPresenter != null) {
            mPresenter.reloadData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.layout_change_phonenum, R.id.layout_change_payword, R.id.layout_change_loginpass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_change_phonenum:
                gotoFragment(FragKey.phone_num_change_fragment);
                break;
            case R.id.layout_change_payword:
                String patPass = AppData.getInstance().getToken().getPaypass();
                if (TextUtils.isEmpty(patPass)) {
                    gotoFragment(FragKey.pay_password_add_fragment);
                } else {
                    gotoFragment(FragKey.pay_password_change_fragment);
                }
                break;
            case R.id.layout_change_loginpass:
                gotoFragment(FragKey.login_password_change_fragment);
                break;
        }
    }

    @Override
    public void updatePhoneNum(String phoneNum) {
        phoneNumTv.setText(phoneNum);
    }
}
