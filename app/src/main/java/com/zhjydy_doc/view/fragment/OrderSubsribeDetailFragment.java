package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.OrderSubSribeDetailContract;
import com.zhjydy_doc.presenter.presenterImp.OrderSubSribeDetailPresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public class OrderSubsribeDetailFragment extends PageImpBaseFragment implements OrderSubSribeDetailContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.patient_name)
    TextView patientName;
    @BindView(R.id.patient_hospital)
    TextView patientHospital;
    @BindView(R.id.patient_serialNum)
    TextView patientSerialNum;
    @BindView(R.id.patient_time)
    TextView patientTime;
    @BindView(R.id.accept)
    TextView accept;
    @BindView(R.id.reject)
    TextView reject;
    @BindView(R.id.to_patient_detail)
    TextView toPatientDetail;


    private String orderId = "";
    private OrderSubSribeDetailContract.Presenter mPresenter;
    @Override
    public void setPresenter(OrderSubSribeDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_subsribe;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("预约详情");
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            orderId = getArguments().getString(IntentKey.FRAG_INFO);
        }
        new OrderSubSribeDetailPresenterImp(this, orderId);
        ViewUtil.setCornerViewDrawbleBg(accept, "#537FFa",10);
        ViewUtil.setCornerViewDrawbleBg(reject, "#F16A19",10);
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateOrderMsg(Map<String, Object> info) {
        patientName.setText("患者：" + Utils.toString(info.get("patientname")));
        patientHospital.setText("患者所在医院：" + Utils.toString(info.get("patienthospital")));

        patientSerialNum.setText("预约单号：" + Utils.toString(info.get("orderid")));
        patientTime.setText("预约时间：" + DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("showtime"))));
        final String patientId = Utils.toString(info.get("patientid"));
        toPatientDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(FragKey.patient_case_detail_fragment,patientId);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(FragKey.order_accept_fragment,orderId);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(FragKey.order_reject_fragment,orderId);
            }
        });
    }

    @Override
    public void onAcccept(boolean status) {

    }

    @Override
    public void onreject(boolean status) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
