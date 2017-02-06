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
import com.zhjydy_doc.presenter.contract.OrderMoneyRejectContract;
import com.zhjydy_doc.presenter.presenterImp.OrderMoneyRejectPresenterImp;
import com.zhjydy_doc.view.zjview.ViewUtil;
import com.zhjydy_doc.view.zjview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class OrderMoneyRejectFragment extends PageImpBaseFragment implements OrderMoneyRejectContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.comment_edit_value)
    EditText commentEditValue;
    @BindView(R.id.cancel_comment_layout)
    LinearLayout cancelCommentLayout;
    @BindView(R.id.confirm)
    TextView confirm;
    private OrderMoneyRejectContract.Presenter mPresenter;


    private String mOrderId;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_money_reject;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("拒绝退款原因");
        ViewUtil.setCornerViewDrawbleBg(cancelCommentLayout, "#EEEEEE");
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            mOrderId = getArguments().getString(IntentKey.FRAG_INFO);
        }
        new OrderMoneyRejectPresenterImp(this, mOrderId);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCancel();
            }
        });
    }

    private void confirmCancel() {
        String commentStr = commentEditValue.getText().toString();
        if(TextUtils.isEmpty(commentStr)) {
            zhToast.showToast("请输入拒绝原因");
            return;
        }
        if (commentStr.length() > 30) {
            zhToast.showToast("拒绝原因长度不能超过30！");
            return;
        }

        mPresenter.confirmCancel( commentStr);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void cancelResult(boolean result) {
        if (result) {
            zhToast.showToast("提交拒绝退款成功");
            back(2);
            gotoFragment(FragKey.detail_order_fragment, mOrderId);
        } else {
            zhToast.showToast("提交拒绝退款信息失败");
        }
    }


    @Override
    public void setPresenter(OrderMoneyRejectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
