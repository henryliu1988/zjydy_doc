package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.Utils;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class OrderPatientInfoView  extends LinearLayout{

    TextView patientName;
    TextView patientHospital;
    TextView patientSerialNum;
    TextView patientTime;
    TextView toPatientDetail;

    public OrderPatientInfoView(Context context) {
        super(context);
        initView();
    }

    public OrderPatientInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.order_patient_info_view, this);
        patientName = (TextView)findViewById(R.id.patient_name);
        patientHospital = (TextView)findViewById(R.id.patient_hospital);
        patientSerialNum = (TextView)findViewById(R.id.patient_serialNum);
        patientTime = (TextView)findViewById(R.id.patient_time);
        toPatientDetail = (TextView)findViewById(R.id.to_patient_detail);
    }


    public void setPatientInfo(Map<String,Object> info) {
        patientName.setText("患者：" + Utils.toString(info.get("patientname")));
        patientHospital.setText("患者所在医院：" + Utils.toString(info.get("patienthospital")));

        patientSerialNum.setText("预约单号：" + Utils.toString(info.get("orderid")));
        patientTime.setText("预约时间：" + DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("showtime"))));
        final String patientId = Utils.toString(info.get("patientid"));
        toPatientDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPatientDetail(patientId);;
                }
            }
        });

    }


    public PatientDetailLisenter mListener;
    public void setToPatientDetailLisenter(PatientDetailLisenter lisenter) {
        mListener = lisenter;
    }
    public interface PatientDetailLisenter{
        void onPatientDetail(String patientId);
    }
}
