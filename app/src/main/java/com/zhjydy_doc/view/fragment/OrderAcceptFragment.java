package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.OrderAcceptContract;
import com.zhjydy_doc.presenter.presenterImp.OrderAcceptPresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.CashierInputFilter;
import com.zhjydy_doc.view.zjview.MapTextView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class OrderAcceptFragment extends PageImpBaseFragment implements OrderAcceptContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.count_title)
    TextView countTitle;
    @BindView(R.id.count_value)
    EditText countValue;
    @BindView(R.id.money_title)
    TextView moneyTitle;
    @BindView(R.id.money_value)
    EditText moneyValue;
    @BindView(R.id.date_title)
    TextView dateTitle;
    @BindView(R.id.date_value)
    MapTextView dateValue;
    @BindView(R.id.address_title)
    TextView addressTitle;
    @BindView(R.id.address_value)
    MapTextView addressValue;
    @BindView(R.id.expert_title)
    TextView expertTitle;
    @BindView(R.id.expert_value)
    EditText expertValue;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.main_value)
    EditText mainValue;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    EditText telValue;
    @BindView(R.id.comment_title)
    TextView commentTitle;
    @BindView(R.id.comment_value)
    EditText commentValue;
    @BindView(R.id.confirm)
    TextView confirm;


    private TimePickerView mTimePickView;

    private String morderId;
    private String type = "new";
    private OrderAcceptContract.Presenter mPresenter;
    @Override
    public void setPresenter(OrderAcceptContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            back();
            return;
        }

        morderId = bundle.getString(IntentKey.FRAG_INFO);
        type = bundle.getString(IntentKey.FRAG_OPERATE);
        if (TextUtils.isEmpty(morderId)) {
            back();
        }
        if (TextUtils.isEmpty(type)) {
            type = "new";
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_accept;
    }

    @Override
    protected void afterViewCreate() {


        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("接受预约");
        if ("edit".equals(type)) {
            titleCenterTv.setText("修改会诊信息");
        }
        InputFilter[] filters = {new CashierInputFilter()};
        moneyValue.setFilters(filters);
        mTimePickView = new TimePickerView(getContext(),TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                String dateStr = DateUtil.dateToString(date, DateUtil.LONG_DATE_FORMAT);
                dateValue.setText(dateStr);
            }
        });
        dateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePickView.show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
            }
        });
        new OrderAcceptPresenterImp(this,morderId);
    }

    @Override
    public void refreshView() {

    }


    private void checkInfo() {
        String ccount = countValue.getText().toString();
        String money = moneyValue.getText().toString();
        String date = dateValue.getText().toString();
        String expert = expertValue.getText().toString();
        String mainDoc = mainValue.getText().toString();
        String tel = telValue.getText().toString();
        String comment  = commentValue.getText().toString();

        if (TextUtils.isEmpty(ccount)) {
            zhToast.showToast("请输入会诊次数");
            return;
        }
        if (TextUtils.isEmpty(money)) {
            zhToast.showToast("请输入您需要的费用"); return;
        }
        if (TextUtils.isEmpty(date)) {
            zhToast.showToast("请选择会诊时间"); return;
        }
        if (TextUtils.isEmpty(expert)) {
            zhToast.showToast("请输入专家姓名"); return;
        }

        if (TextUtils.isEmpty(tel)) {
            zhToast.showToast("请输入您的联系方式"); return;
        }
        HashMap<String,Object> parasm = new HashMap<>();
        if ("edit".equals(type)) {
            parasm.put("type",2);
        } else {
            parasm.put("type",1);
        }
        parasm.put("nums",ccount);
        parasm.put("money",money);
        parasm.put("time",DateUtil.getDiffOfBaseTime(date, DateUtil.LONG_DATE_FORMAT));
        parasm.put("experts",expert);
        parasm.put("main_experts",mainDoc);
        parasm.put("mobile",tel);
        parasm.put("comment",comment);
        mPresenter.accept(parasm);
    }
    @Override
    public void onAccept(boolean ok) {

        if ("edit".equals(type)) {
            if (ok) {
                zhToast.showToast("修改会诊信息成功");
                back();
            } else{
                zhToast.showToast("修改会诊信息失败");
            }

        } else {
            if (ok) {
                zhToast.showToast("您已经成功接受该患者预约，请等待患者支付费用");
                back(2);
                gotoFragment(FragKey.detail_order_fragment,morderId);
            } else{
                zhToast.showToast("接受预约提交失败");
            }
        }
    }

    @Override
    public void updateHuizhen(Map<String, Object> info) {
        countValue.setText(Utils.toString(info.get("nums")));
        moneyValue.setText( Utils.toString(info.get("money")));

        dateValue.setText( DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("time"))));
       // expertValue.setText("会诊地点：" + Utils.toString(info.get("address")));
        expertValue.setText(Utils.toString(info.get("experts")));
        mainValue.setText( Utils.toString(info.get("main_experts")));
        telValue.setText(Utils.toString(info.get("mobile")));
        commentValue.setText( Utils.toString(info.get("comment")));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
