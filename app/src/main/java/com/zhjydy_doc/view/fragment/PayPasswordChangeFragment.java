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
import com.zhjydy_doc.presenter.contract.PayPasswordChangContract;
import com.zhjydy_doc.presenter.presenterImp.PayPasswordChangePresenterImp;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PayPasswordChangeFragment extends PageImpBaseFragment implements PayPasswordChangContract.View {

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
    private PayPasswordChangContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_paypassword_change;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleCenterTv.setText("修改支付密码");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
        new PayPasswordChangePresenterImp(this);

    }


    private void confirm() {
        String oldPs = editOldPassword.getText().toString();
        String newPs = editNewPassword.getText().toString();
        if (TextUtils.isEmpty(oldPs)) {
            zhToast.showToast("请先输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newPs)) {
            zhToast.showToast("请输入新密码");
            return;
        }
        if (newPs.length() < 6) {
            zhToast.showToast("新密码长度太短");
            return;

        }
        if (newPs.length() > 19) {
            zhToast.showToast("新密码长度太长");
            return;
        }

        mPresenter.confirm(oldPs, newPs);
    }

    @Override
    public void setPresenter(PayPasswordChangContract.Presenter presenter) {
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

    @Override
    public void confirmResult(boolean result, String msg) {
        if (result) {
            zhToast.showToast("修改支付密码成功");
            back();
        } else {
            String error = "修改支付密码失败  ";
            zhToast.showToast(error + msg);
        }
    }
}
