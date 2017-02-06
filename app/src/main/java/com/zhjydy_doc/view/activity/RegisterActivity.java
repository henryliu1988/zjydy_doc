package com.zhjydy_doc.view.activity;

import android.content.Context;
import android.graphics.Paint;
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
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.RegisterContract;
import com.zhjydy_doc.presenter.presenterImp.RegisterPresenterImp;
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
public class RegisterActivity extends BaseActivity implements RegisterContract.View{

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.phonenum_edit)
    EditText phonenumEdit;
    @BindView(R.id.confirm_code_edit)
    EditText confirmCodeEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.register_now)
    TextView registerNow;
    @BindView(R.id.confirm_code_get)
    Button confirmCodeGet;
    @BindView(R.id.legend)
    TextView legend;


    private RegisterContract.Presenter mPresenter;
    private MyCountTimer mCountTimer;

    private String mConfirSmsCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);
        ButterKnife.bind(this);
        titleCenterTv.setText("注册");
        legend.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        new RegisterPresenterImp(this);
        mCountTimer = new MyCountTimer(confirmCodeGet,0xff658dff,0xff658dff);
    }

    @OnClick({R.id.title_back, R.id.register_now, R.id.confirm_code_get, R.id.legend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.register_now:
                tryToRegister();
                break;
            case R.id.confirm_code_get:
                tryGetConfirmCode();
                break;
            case R.id.legend:
                break;
        }
    }

    private void tryToRegister() {
        String phoneNum = phonenumEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请输入手机号");
            return;
        }
        if(!Utils.isPhone(phoneNum)) {
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
        if(passoword.length() <6) {
            zhToast.showToast("密码长度太短");
            return;
        }
        if(passoword.length() > 19) {
            zhToast.showToast("密码长度太长");
            return;
        }
        HashMap<String,Object> param = new HashMap<>();
        param.put("mobile",phoneNum);
        param.put("password", MD5.GetMD5Code(passoword));
        param.put("yanzheng",confirmCode);

        mPresenter.register(param);
    }
    private void tryGetConfirmCode() {
        String phoneNum = phonenumEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请输入手机号");
            return;
        }
        if(!Utils.isPhone(phoneNum)) {
            zhToast.showToast("请输入正确的手机号");
            return;
        }
        if (mPresenter != null) {
            mPresenter.getConfirmCode(phoneNum).subscribe(new BaseSubscriber<WebResponse>(this,"") {
                @Override
                public void onNext(WebResponse webResponse) {
                    int errot = webResponse.getError();
                    if (errot == 0) {
                        mConfirSmsCode = webResponse.getData();
                        zhToast.showToast(mConfirSmsCode);
                        mCountTimer.start();
                    } else {
                        zhToast.showToast(webResponse.getInfo());
                    }
                }
            });
        }
    }

    @Override
    public void registerOK(String msg) {
        if (!UserData.getInstance().isExpertInfoSubmit()) {
            ActivityUtils.transActivity(this,UserInfoNewActivity.class,true);
        } else {
            ActivityUtils.transActivity(this,LoginActivity.class,true);
        }
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
