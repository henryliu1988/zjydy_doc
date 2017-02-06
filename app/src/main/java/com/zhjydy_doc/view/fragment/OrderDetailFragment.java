package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.OrderDetailContract;
import com.zhjydy_doc.presenter.presenterImp.OrderDetailPresenterImp;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.OrderHuizhenInfoView;
import com.zhjydy_doc.view.zjview.OrderPatientInfoView;
import com.zhjydy_doc.view.zjview.OrderTuikuanInfoView;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class OrderDetailFragment extends PageImpBaseFragment implements OrderDetailContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.patient_info)
    OrderPatientInfoView patientInfo;
    @BindView(R.id.huizhen_info)
    OrderHuizhenInfoView huizhenInfo;
    @BindView(R.id.operate_id)
    LinearLayout operateId;
    @BindView(R.id.tuikuan_info)
    OrderTuikuanInfoView tuikuanInfo;
    private OrderDetailContract.Presenter mPresenter;

    String orderId = "";

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("订单详情");
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            orderId = getArguments().getString(IntentKey.FRAG_INFO);
        }
        new OrderDetailPresenterImp(this, orderId);
    }

    @Override
    public void update(Map<String, Object> info) {
        int status = Utils.toInteger(info.get("status"));
        patientInfo.setPatientInfo(info);
        patientInfo.setToPatientDetailLisenter(new OrderPatientInfoView.PatientDetailLisenter() {
            @Override
            public void onPatientDetail(String patientId) {
                gotoFragment(FragKey.patient_case_detail_fragment, patientId);
            }
        });
        Map<String, Object> huizhenData = Utils.parseObjectToMapString(info.get("hui_comment"));

        if (huizhenData != null && huizhenData.size() > 0) {
            huizhenInfo.setHuiZhenInfo(huizhenData);
            if (status == 2 || status == 3) {
                huizhenInfo.setEditAble(true);
                huizhenInfo.setHuizhenEditListener(new OrderHuizhenInfoView.HuizhenEditListener() {
                    @Override
                    public void onHuiZhenEdit() {
                        Bundle bundle = new Bundle();
                        bundle.putString(IntentKey.FRAG_INFO, orderId);
                        bundle.putString(IntentKey.FRAG_OPERATE, "edit");
                        gotoFragment(FragKey.order_accept_fragment, bundle);
                    }
                });
            } else {
                huizhenInfo.setEditAble(false);
            }
            if (status == 4) {
                Map<String, Object> backInfo = new HashMap<>();
                backInfo.put("reback_reason", info.get("reback_reason"));
                backInfo.put("money", huizhenData.get("money"));
                tuikuanInfo.setVisibility(View.VISIBLE);
                tuikuanInfo.setRebackReson(backInfo);
            }
        } else {
            huizhenInfo.setVisibility(View.GONE);
        }


        initOperateLayout(status);
        String statuText = "";
        String textColorBg = "#FFFFFF";
        switch (status) {
            case 1:
                statuText = "预约中，等待专家确认预约";
                textColorBg = "#FFB81F";
                break;
            case 2:
                statuText = "等待患者支付";
                textColorBg = "#FFB81F";
                break;
            case 3:
                statuText = "订单已支付完成，请再次确认会诊信息";
                textColorBg = "#30D701";
                break;
            case 4:
                statuText = "患者申请退款";
                textColorBg = "#FFB81F";
                break;
            case 5:
                statuText = "订单已完成";
                textColorBg = "#FFB81F";
                break;
            case 6:
                statuText = "患者取消预约，订单已结束";
                textColorBg = "#FFB81F";
                break;
            case 7:
                statuText = "专家未接受预约，订单已结束";
                textColorBg = "#FFB81F";
                break;

            case 9:
                statuText = "专家拒绝退款";
                textColorBg = "#FFB81F";
                break;

            case 10:
                statuText = "退款成功，订单已结束";
                textColorBg = "#FFB81F";
                break;

            case 11:
                statuText = "会诊中";
                textColorBg = "#FFB81F";
                break;

            case 12:
                statuText = "治疗中";
                textColorBg = "#FFB81F";
                break;
            case 13:
                statuText = "治疗已结束";
                textColorBg = "#FFB81F";
                break;
        }
        this.status.setText(statuText);
        ViewUtil.setCornerViewDrawbleBg(this.status, textColorBg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initOperateLayout(int status) {
        operateId.removeAllViews();
        switch (status) {
            case 1:
                View acceptView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView acceptButton = (TextView) acceptView.findViewById(R.id.operate);
                acceptButton.setText("接受预约");
                ViewUtil.setCornerViewDrawbleBg(acceptButton, "#537FFa", 10);
                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoFragment(FragKey.order_accept_fragment,orderId);
                    }
                });

                View cancelView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView cancelButton = (TextView) cancelView.findViewById(R.id.operate);
                cancelButton.setText("拒绝预约");
                ViewUtil.setCornerViewDrawbleBg(cancelButton, "#F16A19", 10);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoFragment(FragKey.order_reject_fragment,orderId);
                    }
                });
                operateId.addView(acceptView);
                operateId.addView(cancelView);
                break;

            case 3:
                View confirmView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView confirmHuizhen = (TextView) confirmView.findViewById(R.id.operate);
                confirmHuizhen.setText("确认");
                ViewUtil.setCornerViewDrawbleBg(confirmHuizhen, "#537FFa", 10);
                confirmHuizhen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.confirmHuizhen();
                    }
                });
                operateId.addView(confirmView);
                break;
            case 4:
                View acceptBackView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView acceptBack = (TextView) acceptBackView.findViewById(R.id.operate);
                acceptBack.setText("接受");
                ViewUtil.setCornerViewDrawbleBg(acceptBack, "#537FFa", 10);
                acceptBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.acceptBack();
                    }
                });

                View rejectBackView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView rejectBack = (TextView) rejectBackView.findViewById(R.id.operate);
                rejectBack.setText("拒绝");
                ViewUtil.setCornerViewDrawbleBg(rejectBack, "#F16A19", 10);
                rejectBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mPresenter.rejectBack();
                        gotoFragment(FragKey.order_money_reject_fragment,orderId);
                    }
                });
                operateId.addView(acceptBackView);
                operateId.addView(rejectBackView);
                break;
            case 11:
                View confirmHuizhenView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView confirmtoHuizhen = (TextView) confirmHuizhenView.findViewById(R.id.operate);
                confirmtoHuizhen.setText("会诊完成");
                ViewUtil.setCornerViewDrawbleBg(confirmtoHuizhen, "#537FFa", 10);
                confirmtoHuizhen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.confirmFinishHuizhen();
                    }
                });
                operateId.addView(confirmHuizhenView);
                break;

            case 12:
                View confirmZhiliaoView = LayoutInflater.from(getContext()).inflate(R.layout.operate_button, null);
                TextView confirmToZhiliao = (TextView) confirmZhiliaoView.findViewById(R.id.operate);
                confirmToZhiliao.setText("治疗完成");
                ViewUtil.setCornerViewDrawbleBg(confirmToZhiliao, "#537FFa", 10);
                confirmToZhiliao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.confirmZhiliaoFinish();
                    }
                });
                operateId.addView(confirmZhiliaoView);
                break;

        }
    }
}
