package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.LoginPasswordChangeContract;
import com.zhjydy_doc.presenter.presenterImp.LoginPasswordChangePresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class LoginPasswordChangeFragment extends PageImpBaseFragment implements LoginPasswordChangeContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.confirm)
    TextView confirm;
    private LoginPasswordChangeContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loginpassword_change;
    }

    @Override
    protected void afterViewCreate() {
        new LoginPasswordChangePresenterImp(this);
        titleCenterTv.setText("修改登录密码");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    public void setPresenter(LoginPasswordChangeContract.Presenter presenter) {
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

    @OnClick(R.id.confirm)
    public void onClick() {
        confirmChange();
    }

    private void confirmChange() {
        String oldPassword = editOldPassword.getText().toString();
        String newPassword = editNewPassword.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            zhToast.showToast("请输入原密码");
            return;
        }
        if(TextUtils.isEmpty(newPassword )) {
            zhToast.showToast("请输入新密码");
            return;

        }
        if(newPassword.length() < 6) {
            zhToast.showToast("新密码长度太短");
            return;

        }
        if(newPassword.length() > 19) {
            zhToast.showToast("新密码长度太长");
            return;
        }
        mPresenter.confirmUpdate(oldPassword,newPassword);
    }

    @Override
    public void updatePassWordOk() {
        zhToast.showToast("修改密码成功");
        ActivityUtils.showLogin(getActivity(),true);
    }
}
