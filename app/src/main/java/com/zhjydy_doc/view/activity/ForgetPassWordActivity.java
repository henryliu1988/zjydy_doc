package com.zhjydy_doc.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.ForgetPassWordContract;
import com.zhjydy_doc.presenter.presenterImp.ForgetPassWordPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.MD5;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.MyCountTimer;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class ForgetPassWordActivity extends BaseActivity implements ForgetPassWordContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.phonenum_edit)
    EditText phonenumEdit;
    @BindView(R.id.confirm_code_edit)
    EditText confirmCodeEdit;
    @BindView(R.id.confirm_code_get)
    Button confirmCodeGet;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    private MyCountTimer mCountTimer;

    private String mConfirSmsCode;

    private ForgetPassWordContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        titleCenterTv.setText("忘记密码");
        new ForgetPassWordPresenterImp(this);
        mCountTimer = new MyCountTimer(confirmCodeGet, 0xff658dff, 0xff658dff);
    }

    @OnClick({R.id.title_back, R.id.confirm_code_get, R.id.btnConfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.confirm_code_get:
                tryGetConfirmCode();
                break;
            case R.id.btnConfirm:
                tryConfirmReset();
                break;
        }
    }

    private void tryGetConfirmCode() {
        String phoneNum = phonenumEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请输入手机号");
            return;
        }
        if (!Utils.isPhone(phoneNum)) {
            zhToast.showToast("请输入正确的手机号");
            return;
        }
        if (mPresenter != null) {
            mPresenter.getConfirmCode(phoneNum).subscribe(new BaseSubscriber<WebResponse>(this, "") {
                @Override
                public void onNext(WebResponse webResponse) {
                    mConfirSmsCode = webResponse.getData();
                    zhToast.showToast(mConfirSmsCode);
                    mCountTimer.start();
                }
            });
        }
    }

    private void tryConfirmReset() {
        String phoneNum = phonenumEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请输入手机号");
            return;
        }
        if (!Utils.isPhone(phoneNum)) {
            zhToast.showToast("请输入正确的手机号");
            return;
        }
        String confirmCode = confirmCodeEdit.getText().toString();
        if (TextUtils.isEmpty(confirmCode)) {
            zhToast.showToast("请输入验证码");
            return;
        }


        String passoword = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(passoword)) {
            zhToast.showToast("请输入密码");
            return;
        }
        if (passoword.length() < 6) {
            zhToast.showToast("密码长度太短");
            return;
        }
        if (passoword.length() > 19) {
            zhToast.showToast("密码长度太长");
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile", phoneNum);
        param.put("password", MD5.GetMD5Code(passoword));
        param.put("yanzheng", confirmCode);

        mPresenter.resetPassWord(param);

    }

    @Override
    public void resetOk(String msg) {
        zhToast.showToast("重置密码成功");
        ActivityUtils.transActivity(this, LoginActivity.class, true);
    }

    @Override
    public void setPresenter(ForgetPassWordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
