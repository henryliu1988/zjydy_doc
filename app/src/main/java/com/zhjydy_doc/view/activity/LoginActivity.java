package com.zhjydy_doc.view.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithData;
import com.zhjydy_doc.presenter.contract.LoginContract;
import com.zhjydy_doc.presenter.presenterImp.LoginPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View,RefreshWithData{


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.phonenum_edit)
    EditText phonenumEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.forget_psw_tv)
    TextView forgetPswTv;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    @BindView(R.id.rigister_now)
    TextView rigisterNow;

    private LoginContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        titleCenterTv.setText("登录");
        rigisterNow.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        new LoginPresenterImp(this);
    }

    @OnClick({R.id.title_back, R.id.forget_psw_tv, R.id.btnLogin, R.id.rigister_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.forget_psw_tv:
                ActivityUtils.transActivity(LoginActivity.this,ForgetPassWordActivity.class,false);
                break;
            case R.id.btnLogin:
                tryLogin();
                break;
            case R.id.rigister_now:
                ActivityUtils.transActivity(LoginActivity.this,RegisterActivity.class,false);
                break;
        }
    }

    private void tryLogin() {
        String name = phonenumEdit.getText().toString();
        String psw = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            zhToast.showToast("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(psw))
        {
            zhToast.showToast("密码不能为空!");
            return;
        }
        mPresenter.tryLogin(name, psw);

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoginSucess() {
        ActivityUtils.transActivity(LoginActivity.this,MainTabsActivity.class,true);
    }

    @Override
    public void onLoginFail() {
        zhToast.showToast("登录失败");
    }

    @Override
    public void initPreferenceInfo(String phone, String password) {
        phonenumEdit.setText(phone);
        passwordEdit.setText(password);
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
    public void onRefreshWithData(int key, Object data)
    {
        if (mPresenter != null)
        {
            mPresenter.onRefreshWithData(key, data);
        }

    }

}
