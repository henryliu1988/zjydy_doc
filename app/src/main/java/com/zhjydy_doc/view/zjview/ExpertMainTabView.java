package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.entity.DistricPickViewData;
import com.zhjydy_doc.model.entity.District;
import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.HospitalDicItem;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.presenter.contract.MainExpertContract;
import com.zhjydy_doc.presenter.presenterImp.MainExpertTabPresenterImp;
import com.zhjydy_doc.view.adapter.MainExpertListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class ExpertMainTabView extends FrameLayout implements MainExpertContract.TabView {


    private Context mContext;
    private int funcKey;
    private View pageView;
    private OptionsPickerView mBusinessPicker;
    private OptionsPickerView mOfficePicker;
    private OptionsPickerView mCityAndHosPicker;

    private ArrayList<NormalPickViewData> mDepartPickViewData = new ArrayList<>();
    private ArrayList<NormalPickViewData> mBusinessPickViewData = new ArrayList<>();


    private ArrayList<DistricPickViewData> mCityPickViewData1 = new ArrayList<>();
    private ArrayList<ArrayList<HosipitalPickViewData>> mHospiatalPickViewData = new ArrayList<>();

    private PullToRefreshListView mListView;
    private MainExpertListAdapter mAdapter;

    private MapTextView filterDisTv;
    private MapTextView filterBusinessTv;
    private MapTextView filterOfficeTv;


    int dataType = 0;

    private int expertDataType;
    private MainExpertContract.TabPresenter mPresenter;

    public ExpertMainTabView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public ExpertMainTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    public void setFuncKeyAndDataType(int FuncKey,int type) {
        this.dataType = type;
        this.funcKey = FuncKey;
        new MainExpertTabPresenterImp(this, mListView, mAdapter, funcKey);
        mAdapter.setExpertDataType(type);
    }
    public void initView(Context context) {
        pageView = LayoutInflater.from(context).inflate(R.layout.expert_main_viewpage_item, this);
        mListView = (PullToRefreshListView) pageView.findViewById(R.id.m_list);
        filterDisTv = (MapTextView) pageView.findViewById(R.id.filter_dis_tv);
        filterBusinessTv = (MapTextView) pageView.findViewById(R.id.filter_business_tv);
        filterOfficeTv = (MapTextView) pageView.findViewById(R.id.filter_office_tv);

        mAdapter = new MainExpertListAdapter(context, new ArrayList<Map<String, Object>>());
        //mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = (Map<String,Object>)parent.getAdapter().getItem(position);
                if (mListener != null) {
                    mListener.onItemClick(map);
                }
            }
        });
        mAdapter.setGuanzhuListener(new MainExpertListAdapter.onGuanzhuClickListener() {
            @Override
            public void onGuanzhuClick(Map<String, Object> item, int status) {
                if (mPresenter != null) {
                    switch (status) {
                        case ExpertData.GUAN_STAT_NUL:
                            mPresenter.guanExpert(item);
                            break;
                        case ExpertData.GUAN_STAT_GUAN:
                        case ExpertData.GUAN_STAT_MEGUAN:
                            mPresenter.cancelGuanExpert(item);
                    }
                }
            }
        });
        filterDisTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCityPickViewData1.size() > 0) {
                    mCityAndHosPicker.show();
                }
            }
        });

        filterBusinessTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBusinessPickViewData.size() > 0) {
                    mBusinessPicker.show();
                }

            }
        });

        filterOfficeTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDepartPickViewData.size() > 0) {
                    mOfficePicker.show();
                }
            }
        });
        mBusinessPicker = new OptionsPickerView<NormalDicItem>(context);
        mOfficePicker = new OptionsPickerView<NormalDicItem>(context);
        mCityAndHosPicker = new OptionsPickerView(context);


    }

    public View getPageView() {
        return pageView;
    }


    public void updateCityAndHos(Map<String, ArrayList> data) {
        mCityPickViewData1 = (ArrayList<DistricPickViewData>) data.get("city");
        mHospiatalPickViewData = (ArrayList<ArrayList<HosipitalPickViewData>>) data.get("hospital");
        mCityAndHosPicker.setPicker(mCityPickViewData1, mHospiatalPickViewData, true);
        initCityAndHospicker();
    }

    private void initCityAndHospicker() {
        mCityAndHosPicker.setCyclic(false);
        mCityAndHosPicker.setCancelable(true);
        mCityAndHosPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                District city = mCityPickViewData1.get(options1).getDistrict();
                HospitalDicItem hos = mHospiatalPickViewData.get(options1).get(option2).getHospitalDicItem();
                filterDisTv.clear();
                if (hos != null && !TextUtils.isEmpty(hos.getId())) {
                    filterDisTv.setMap(hos.getId(), hos.getHospital());
                    filterDisTv.setMoreMap(city.getId(), city.getName());
                } else if (city != null && !TextUtils.isEmpty(city.getId())) {
                    filterDisTv.setMoreMap(city.getId(), city.getName());
                } else {
                    filterDisTv.setMoreMap("", "全部地区");
                }
                // mPresenter.reloadExperts();
            }
        });

    }


    public void updateOffice(ArrayList<NormalPickViewData> officeData) {
        mDepartPickViewData = officeData;
        mOfficePicker.setPicker(mDepartPickViewData);
        mOfficePicker.setCyclic(false);
        mOfficePicker.setSelectOptions(0);
        mOfficePicker.setCancelable(true);
        mOfficePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String officeName = mDepartPickViewData.get(options1).getmItem().getName();
                String officeId = mDepartPickViewData.get(options1).getmItem().getId();
                if (TextUtils.isEmpty(officeId)) {
                    filterOfficeTv.setMap("", "科室");
                } else {
                    filterOfficeTv.setMap(officeId, officeName);
                }
                mPresenter.reloadExperts();
            }
        });

    }

    public void updateBusiness(ArrayList<NormalPickViewData> officeData) {
        mBusinessPickViewData = officeData;
        mBusinessPicker.setPicker(mBusinessPickViewData);
        mBusinessPicker.setCyclic(false);
        mBusinessPicker.setSelectOptions(0);
        mBusinessPicker.setCancelable(true);
        mBusinessPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String busName = mBusinessPickViewData.get(options1).getmItem().getName();
                String busId = mBusinessPickViewData.get(options1).getmItem().getId();
                if (TextUtils.isEmpty(busId)) {
                    filterBusinessTv.setMap("", "职称");
                } else {
                    filterBusinessTv.setMap(busId, busName);
                }
                mPresenter.reloadExperts();
            }
        });

    }

    @Override
    public Map<String, Object> getFilterConditions() {
        String hosId = filterDisTv.getTextId();
        String cityId = filterDisTv.getMoreTextId();
        String officeId = filterOfficeTv.getTextId();
        String businessId = filterBusinessTv.getTextId();
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(hosId)) {
            params.put("hospital", hosId);
        }
        if (!TextUtils.isEmpty(cityId)) {
            params.put("address", cityId);
        }
        if (!TextUtils.isEmpty(officeId)) {
            params.put("office", officeId);
        }
        if (!TextUtils.isEmpty(businessId)) {
            params.put("business", businessId);
        }
        return params;
    }

    @Override
    public void onGuanzhuChange() {
       mPresenter.reloadExperts();
    }


    @Override
    public void setPresenter(MainExpertContract.TabPresenter presenter) {
        mPresenter = presenter;
    }



    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(Map<String, Object> item);
    }


    private OnGuanZhuClickListener mGuanListener;

    public void setOnGuanZhuClickListener(OnGuanZhuClickListener listener) {
        this.mGuanListener = listener;
    }

    public interface OnGuanZhuClickListener {
        public void onItemClick(Map<String, Object> item,int status);
    }
}
