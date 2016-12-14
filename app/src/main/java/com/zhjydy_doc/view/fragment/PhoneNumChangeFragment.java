package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.presenter.contract.PhoneNumChangContract;
import com.zhjydy_doc.presenter.presenterImp.PhoneNumChangePresenterImp;
import com.zhjydy_doc.util.MD5;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.MyCountTimer;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PhoneNumChangeFragment extends PageImpBaseFragment implements PhoneNumChangContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.confirm)
    TextView confirm;
    private PhoneNumChangContract.Presenter mPresenter;

    private View mLoginPsView;
    private View mPhoneView;
    private View mConfirmView;


    private EditText mLoginPsEdit;
    private EditText mNewPhoneEdit;
    private MyCountTimer mCountTimer;

    private EditText mConfirmCodeEdit;
    private int mCurrentStep;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phonenum_change;
    }

    @Override
    protected void afterViewCreate() {
        new PhoneNumChangePresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentStep >0) {
                    mCurrentStep --;
                    switchStep(mCurrentStep);
                } else {
                    back();
                }
            }
        });
        titleCenterTv.setText("修改手机号码");
        initLoginLayout();
        initNewPhoneLayout();
        initConfirmLayout();
        switchStep(0);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClick();
            }
        });
    }


    private void initLoginLayout() {
        mLoginPsView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_phone_change_login, null);
        mLoginPsEdit = (EditText)mLoginPsView.findViewById(R.id.edit_old_password);
    }

    private void initNewPhoneLayout() {
        mPhoneView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_phone_change_newphone, null);
        mNewPhoneEdit = (EditText)mPhoneView.findViewById(R.id.new_phone_edit);
        TextView mNewPhoneTv = (TextView)mPhoneView.findViewById(R.id.new_phone_tv);
        mCountTimer = new MyCountTimer(mNewPhoneTv,0xff658dff,0xff658dff);
        mNewPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryGetConfirmCode();
            }
        });
    }
    private void initConfirmLayout() {
        mConfirmView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_phone_change_confirm, null);
        mConfirmCodeEdit = (EditText)mConfirmView.findViewById(R.id.edit_confirm_code);
    }

    private void tryGetConfirmCode() {
        String phoneNum = mNewPhoneEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请输入手机号");
            return;
        }
        if(!Utils.isPhone(phoneNum)) {
            zhToast.showToast("请输入正确的手机号");
            return;
        }
        if (mPresenter != null) {
            mPresenter.getConfirmCode(phoneNum).subscribe(new BaseSubscriber<WebResponse>(getContext(),"") {
                @Override
                public void onNext(WebResponse webResponse) {
                   String  mConfirSmsCode = webResponse.getData();
                    zhToast.showToast(mConfirSmsCode);
                    mCountTimer.start();
                }
            });
        }
    }


    private void switchStep(int step) {
        mCurrentStep = step;
        inputLayout.removeAllViews();
        LinearLayout.LayoutParams parasm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (step) {
            case 0:
                inputLayout.addView(mLoginPsView);
                mLoginPsView.setLayoutParams(parasm);
                confirm.setText("下一步");
                break;
            case 1:
                inputLayout.addView(mPhoneView);
                mPhoneView.setLayoutParams(parasm);
                confirm.setText("下一步");
                break;
            case 2:
                inputLayout.addView(mConfirmView);
                mConfirmView.setLayoutParams(parasm);
                confirm.setText("确认");
                break;

        }
    }
    private void confirmClick() {
        switch (mCurrentStep) {
            case 0:
                loginPassConfirm();
                break;
            case 1:
                newPhoneConfirm();
                break;
            case 2:
                allConfirm();
        }
    }

    private void loginPassConfirm() {
        if (mLoginPsEdit == null){
            return;
        }
        String inputPs = mLoginPsEdit.getText().toString();
        if (TextUtils.isEmpty(inputPs)) {
            zhToast.showToast("请输入登录密码");
            return;
        }
        String inputMd5 = MD5.GetMD5Code(inputPs);
        String password = AppData.getInstance().getToken().getPassoword();
        if (inputMd5.equals(password)) {
            switchStep(1);
        } else{
            zhToast.showToast("请输入正确的登录密码");
            return;
        }

    }

    private void newPhoneConfirm() {
        if (mNewPhoneEdit == null) {
            return;
        }

        String newPhone = mNewPhoneEdit.getText().toString();
        if (TextUtils.isEmpty(newPhone)) {
            zhToast.showToast("请输入手机号码并获取验证码");
            return;
        }
        if(!Utils.isPhone(newPhone)) {
            zhToast.showToast("请输入正确的手机号");
            return;
        }

        switchStep(2);
    }
    private void allConfirm() {
        if (mConfirmCodeEdit == null || mNewPhoneEdit == null) {
            return;
        }
        String confirmCode = mConfirmCodeEdit.getText().toString();
        String phoneNum = mNewPhoneEdit.getText().toString();
        if (TextUtils.isEmpty(confirmCode)) {
            zhToast.showToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请返回上一步输入手机号码");
            return;
        }
        mPresenter.submitChangeConfirm(phoneNum,confirmCode);

    }
    @Override
    public void setPresenter(PhoneNumChangContract.Presenter presenter) {
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
    public void submitResult(boolean result, String msg,String phoneNum)
    {
        if (result) {
            AppData.getInstance().getToken().setMobile(phoneNum);
            int key[] = {FragKey.account_safe_fragment};
            back(key);
        }
        if (!TextUtils.isEmpty(msg)) {
            zhToast.showToast(msg);
        } else {
            zhToast.showToast(result? "修改手机号码成功":"修改手机号码失败");
        }


    }
}
