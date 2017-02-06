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
import com.zhjydy_doc.presenter.contract.AboutAdviceContract;
import com.zhjydy_doc.presenter.presenterImp.AboutAdvicePresenterImp;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class AboutAdviceFragment extends PageImpBaseFragment implements AboutAdviceContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.advice_content)
    EditText adviceContent;
    @BindView(R.id.tel)
    EditText tel;
    @BindView(R.id.confirm)
    TextView confirm;


    private AboutAdviceContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_advice;
    }

    @Override
    protected void afterViewCreate() {
        titleCenterTv.setText("意见反馈");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String advice = adviceContent.getText().toString();
                String telNul = tel.getText().toString();

                if (TextUtils.isEmpty(advice)) {
                    zhToast.showToast("请输入您的反馈意见");
                    return;
                }
                mPresenter.confirm(advice, telNul);

            }
        });
        new AboutAdvicePresenterImp(this);
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
    public void setPresenter(AboutAdviceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void submitresult(boolean ok) {
        if (ok) {
            zhToast.showToast("提交意见成功，感谢你的建议");
            back();
        } else {
            zhToast.showToast("提交意见失败，请检查您的网络");
        }
    }
}
