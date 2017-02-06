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
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.BackRejectReasonContract;
import com.zhjydy_doc.presenter.presenterImp.BackRejectReasonPresenterImp;
import com.zhjydy_doc.view.zjview.ViewUtil;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class BackRejectReasonFragment extends PageImpBaseFragment implements BackRejectReasonContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.reson_edit)
    EditText resonEdit;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.confirm)
    TextView confirm;
    private BackRejectReasonContract.Presenter mPresenter;
    private String mOrderId;

    @Override
    public void setPresenter(BackRejectReasonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frament_back_order_reject;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            return;
        }
        mOrderId = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(mOrderId)) {
            return;
        }
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("拒绝退款原因");

        ViewUtil.setCornerViewDrawbleBg(container, "#EEEEEE");
        ViewUtil.setCornerViewDrawbleBg(confirm, "#F16A19");
        confirm.setText("提交");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = resonEdit.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    zhToast.showToast("请输入拒绝退款给原因");
                    return;
                }
                if (text.length() > 30) {
                    zhToast.showToast("原因字数最多30字");
                    return;
                }
                mPresenter.reject(text);
            }
        });
        new BackRejectReasonPresenterImp(this, mOrderId);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void onReject(boolean ok) {
        back();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
