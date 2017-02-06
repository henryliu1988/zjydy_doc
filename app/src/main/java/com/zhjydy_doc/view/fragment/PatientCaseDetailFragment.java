package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.District;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.PatientCaseDetailContract;
import com.zhjydy_doc.presenter.presenterImp.PatientCaseDetailPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewKey;
import com.zhjydy_doc.view.zjview.ImageHorizontalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseDetailFragment extends PageImpBaseFragment implements PatientCaseDetailContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView centerTv;
    @BindView(R.id.name_title)
    TextView nameTitle;
    @BindView(R.id.name_value)
    TextView nameValue;
    @BindView(R.id.sex_title)
    TextView sexTitle;
    @BindView(R.id.sex_value)
    TextView sexValue;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    TextView telValue;
    @BindView(R.id.birth_title)
    TextView birthTitle;
    @BindView(R.id.birth_value)
    TextView birthValue;
    @BindView(R.id.domain_title)
    TextView domainTitle;
    @BindView(R.id.domain_value)
    TextView domainValue;
    @BindView(R.id.hospital_title)
    TextView hospitalTitle;
    @BindView(R.id.hospital_value)
    TextView hospitalValue;
    @BindView(R.id.depart_title)
    TextView departTitle;
    @BindView(R.id.depart_value)
    TextView departValue;
    @BindView(R.id.doc_title)
    TextView docTitle;
    @BindView(R.id.doc_value)
    TextView docValue;
    @BindView(R.id.sick_title)
    TextView sickTitle;
    @BindView(R.id.sick_value)
    TextView sickValue;
    @BindView(R.id.sick_discript_title)
    TextView sickDiscriptTitle;
    @BindView(R.id.sick_discript_value)
    TextView sickDiscriptValue;
    @BindView(R.id.comment_title)
    TextView commentTitle;
    @BindView(R.id.comment_value)
    TextView commentValue;
    @BindView(R.id.iamge_horizontal_list)
    ImageHorizontalView imageHorizontal;
    private PatientCaseDetailContract.Presenter mPresenter;
    // private HorizontalScrollViewAdapter mImageCaseAdapter;

    private String mCaseId;

    private Map<String, Object> mInfo;

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            back();
            return;
        }
        mCaseId = bundle.getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(mCaseId)) {
            back();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_info;
    }

    @Override
    protected void afterViewCreate() {
        new PatientCaseDetailPresenterImp(this, mCaseId);
        centerTv.setText("患者病例");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    public void setPresenter(PatientCaseDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {
        if (mPresenter != null) {
            mPresenter.refreshData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void updateInfo(Map<String, Object> info) {
        mInfo = info;
        String realName = Utils.toString(info.get("realname"));
        String sexName = DicData.getInstance().getSexById(Utils.toString(info.get("sex"))).getName();
        String phoneNum = Utils.toString(info.get("mobile"));
        String doctor = Utils.toString(info.get("doctor"));
        String comment = Utils.toString(info.get("comment"));
        String descript = Utils.toString(info.get("condition"));
        String name = Utils.toString(info.get("name"));
        long ageLong = Utils.toLong(info.get("age"));
        String birth = "";
        if (ageLong > 0) {
            birth = DateUtil.dateToString(DateUtil.getDateBySeconds(ageLong), DateUtil.LONG_DATE_FORMAT);
        }
        String distrcit = "";
        String hospital = "";
        String depart = "";
        String disCode = Utils.toString(info.get("address"));
        String hosCode = Utils.toString(info.get("hospital"));
        String depCode = Utils.toString(info.get("office"));

        if (!TextUtils.isEmpty(disCode)) {
            List<District> list = DicData.getInstance().getDistrictById(disCode);
            if (list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    distrcit += list.get(i).getName() + " ";
                }
            }
        }
        if (!TextUtils.isEmpty(hosCode)) {
            hospital = DicData.getInstance().getHospitalById(hosCode).getHospital();
        }
        if (!TextUtils.isEmpty(depCode)) {
            depart = DicData.getInstance().getOfficeById(depCode).getName();
        }
        nameValue.setText(realName);
        sexValue.setText(sexName);
        telValue.setText(phoneNum);
        birthValue.setText(birth);

        domainValue.setText(distrcit);
        hospitalValue.setText(hospital);
        departValue.setText(depart);
        docValue.setText(doctor);

        sickValue.setText(name);
        sickDiscriptValue.setText(descript);
        commentValue.setText(comment);

        List<Map<String, Object>> cases = Utils.parseObjectToListMapString(info.get("case"));
        initImageList(cases);
    }

    private void initImageList(final List<Map<String, Object>> cases) {
        List<Map<String, Object>> images = new ArrayList<>();
        for (Map<String, Object> c : cases) {
            Map<String, Object> image = new HashMap<>();
            image.put(ViewKey.FILE_KEY_TYPE, ViewKey.TYPE_FILE_URL);
            image.put(ViewKey.FILE_KEY_URL, c.get("path"));
            images.add(image);
        }
        imageHorizontal.initHorizontalImages(images);
        imageHorizontal.setItemClickListener(new ImageHorizontalView.onItemClickListener() {
            @Override
            public void onItemClick(int position,List<Map<String,Object>> images) {
                if (position > -1 && position < images.size()) {
                    ActivityUtils.showImageBrowse(getActivity(),images,position);
                }
            }
        });
    }
}
