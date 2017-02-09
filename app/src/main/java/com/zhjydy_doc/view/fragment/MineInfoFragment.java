package com.zhjydy_doc.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.soundcloud.android.crop.Crop;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.DistricPickViewData;
import com.zhjydy_doc.model.entity.District;
import com.zhjydy_doc.model.entity.ExpertInfo;
import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.HospitalDicItem;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.entity.PickViewData;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.presenter.contract.MineInfoContract;
import com.zhjydy_doc.presenter.presenterImp.MineInfoPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.ScreenUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ActivityResultView;
import com.zhjydy_doc.view.zjview.MapTextView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class MineInfoFragment extends PageImpBaseFragment implements MineInfoContract.View, ActivityResultView
{


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.real_name)
    TextView realName;
    @BindView(R.id.item_more_flag)
    ImageView itemMoreFlag;
    @BindView(R.id.user_photo)
    ImageView userPhoto;
    @BindView(R.id.user_layout)
    RelativeLayout userLayout;
    @BindView(R.id.user_name_title)
    TextView userNameTitle;
    @BindView(R.id.user_name_value)
    TextView userNameValue;
    @BindView(R.id.user_sex_title)
    TextView userSexTitle;
    @BindView(R.id.user_sex_value)
    MapTextView userSexValue;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    TextView telValue;
    @BindView(R.id.domain_title)
    TextView domainTitle;
    @BindView(R.id.domain_value)
    MapTextView domainValue;
    @BindView(R.id.hospital_title)
    TextView hospitalTitle;
    @BindView(R.id.hospital_value)
    MapTextView hospitalValue;
    @BindView(R.id.depart_title)
    TextView departTitle;
    @BindView(R.id.depart_value)
    MapTextView departValue;
    @BindView(R.id.business_title)
    TextView businessTitle;
    @BindView(R.id.business_value)
    MapTextView businessValue;
    @BindView(R.id.job_title)
    TextView jobTitle;
    @BindView(R.id.job_value)
    TextView jobValue;
    @BindView(R.id.job_layout)
    LinearLayout jobLayout;
    @BindView(R.id.zhuanchang_title)
    TextView zhuanchangTitle;
    @BindView(R.id.zhuanchang_value)
    TextView zhuanchangValue;
    @BindView(R.id.zhuangchang_layout)
    LinearLayout zhuangchangLayout;
    private MineInfoContract.Presenter mPresenter;
    private OptionsPickerView<PickViewData> mSexPicker;
    private ArrayList<PickViewData> mSexPickViewData = new ArrayList<>();


    private OptionsPickerView mDistricePicker;
    private OptionsPickerView mHospitalPicker;
    private OptionsPickerView mOfficePicker;
    private OptionsPickerView mBusinessPicker;
    private ArrayList<DistricPickViewData> mProPickViewData = new ArrayList<>();
    private ArrayList<ArrayList<DistricPickViewData>> mCityPickViewData = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<DistricPickViewData>>> mQuPickViewData = new ArrayList<>();
    private ArrayList<HosipitalPickViewData> mHospitalPickViewData = new ArrayList<>();
    private ArrayList<NormalPickViewData> mDepartPickViewData = new ArrayList<>();
    private ArrayList<NormalPickViewData> mBusinessPickViewData = new ArrayList<>();

    @Override
    protected void initData()
    {
        mSexPicker = new OptionsPickerView<PickViewData>(getContext());
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_mine_info;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mPresenter != null)
        {
            mPresenter.refreshView();
        }
    }

    @Override
    protected void afterViewCreate()
    {
        addOnActivityResultView(this);
        titleCenterTv.setText("个人信息");

        mDistricePicker = new OptionsPickerView<DistricPickViewData>(getContext());
        mHospitalPicker = new OptionsPickerView<HosipitalPickViewData>(getContext());
        mOfficePicker = new OptionsPickerView<NormalDicItem>(getContext());
        mBusinessPicker = new OptionsPickerView<NormalDicItem>(getContext());
        new MineInfoPresenterImp(this);

    }

    @Override
    public void updateSexPick(ArrayList<PickViewData> sexData)
    {
        mSexPickViewData = sexData;
        mSexPicker.setPicker(sexData);
        mSexPicker.setCyclic(false);
        mSexPicker.setSelectOptions(0);
        mSexPicker.setCancelable(true);
        mSexPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener()
        {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3)
            {
                String sexId = mSexPickViewData.get(options1).getId();
                mPresenter.updateExpertInfo("sex",sexId);
            }
        });
    }

    @Override
    public void updateDistrict(Map<String, ArrayList> distrctData)
    {
        mProPickViewData = (ArrayList<DistricPickViewData>) distrctData.get("pro");
        mCityPickViewData = (ArrayList<ArrayList<DistricPickViewData>>) distrctData.get("city");
        mQuPickViewData = (ArrayList<ArrayList<ArrayList<DistricPickViewData>>>) distrctData.get("qu");
        mDistricePicker.setPicker(mProPickViewData, mCityPickViewData, mQuPickViewData, true);
        initDistricePicker();

    }

    @Override
    public void updateOffice(ArrayList<NormalPickViewData> officeData)
    {
        mDepartPickViewData = officeData;
        mOfficePicker.setPicker(mDepartPickViewData);
        mOfficePicker.setCyclic(false);
        mOfficePicker.setSelectOptions(0);
        mOfficePicker.setCancelable(true);
        mOfficePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String officeId = mDepartPickViewData.get(options1).getmItem().getId();
                mPresenter.updateExpertInfo("office",officeId);
            }
        });

    }

    @Override
    public void updateBusiness(ArrayList<NormalPickViewData> business)
    {
        mBusinessPickViewData = business;
        mBusinessPicker.setPicker(mBusinessPickViewData);
        mBusinessPicker.setCyclic(false);
        mBusinessPicker.setSelectOptions(0);
        mBusinessPicker.setCancelable(true);
        mBusinessPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String busName = mBusinessPickViewData.get(options1).getmItem().getName();
                String busId = mBusinessPickViewData.get(options1).getmItem().getId();
                mPresenter.updateExpertInfo("business",busId);
            }
        });

    }

    @Override
    public void updateHospitalByAddress(final ArrayList<HosipitalPickViewData> hosData)
    {
        if (hosData == null || hosData.size() < 1) {
            return;
        }
        mHospitalPickViewData = hosData;
        mHospitalPicker.setPicker(hosData);
        mHospitalPicker.setCyclic(false);
        mHospitalPicker.setCancelable(true);
        mHospitalPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                HospitalDicItem hos = hosData.get(options1).getHospitalDicItem();
                mPresenter.updateExpertInfo("hospital",hos.getId());
            }
        });

    }



    private void confirmLogOut()
    {
        mPresenter.logout();
        ActivityUtils.showLogin(getActivity(), true);
    }

    @Override
    public void setPresenter(MineInfoContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void refreshView()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void updateInfo(TokenInfo info)
    {
        ExpertInfo expertInfo = info.getmExpertInfo();
        String realNameText = info.getmExpertInfo().getRealname();
        userNameValue.setText(realNameText);


        String photoPath = info.getPhotoUrl();
        if (!TextUtils.isEmpty(photoPath))
        {
            ImageUtils.getInstance().displayFromRemoteOver(photoPath, userPhoto);
        }
        String addressId = expertInfo.getAddress();
        String officeId = expertInfo.getOffice();
        String hospitalid = expertInfo.getHospital();
        String businessId = expertInfo.getBusiness();
        String phoneNum = expertInfo.getPhone();
        String soFunc = expertInfo.getSofunc();
        String adpter = expertInfo.getAdept();
        int sex = Utils.toInteger(expertInfo.getSex());
        if (sex > 0)
        {
            NormalItem item = DicData.getInstance().getSexById(sex + "");
            userSexValue.setMap(item.getId(), item.getName());
        }
        if (!TextUtils.isEmpty(phoneNum)) {
            telValue.setText(phoneNum);
        }
        if (!TextUtils.isEmpty(addressId)) {
            String addressName = DicData.getInstance().getDistrictStrById(addressId);
            domainValue.setMap(addressId, addressName);
        }
        if (!TextUtils.isEmpty(hospitalid)) {
            HospitalDicItem item = DicData.getInstance().getHospitalById(hospitalid);
            if (item != null) {
                hospitalValue.setMap(item.getId(), item.getHospital());
            }
        }
        if (!TextUtils.isEmpty(officeId)) {
            NormalDicItem item = DicData.getInstance().getOfficeById(officeId);
            if (item != null) {
                departValue.setMap(item.getId(), item.getName());
            }
        }
        if (!TextUtils.isEmpty(businessId)) {
            NormalDicItem item = DicData.getInstance().getBusinessById(businessId);
            if (item != null) {
                businessValue.setMap(item.getId(), item.getName());
            }
        }
        if (!TextUtils.isEmpty(soFunc)) {
            jobValue.setText(soFunc);
        }
        if (!TextUtils.isEmpty(adpter)) {
            zhuanchangValue.setText(adpter);
        }
    }
    private void initDistricePicker() {
        mDistricePicker.setCyclic(false);
        mDistricePicker.setCancelable(true);
        mDistricePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                District pro = null;
                District city = null;
                District qu = null;
                if (mProPickViewData.size() > options1) {
                    pro = mProPickViewData.get(options1).getDistrict();
                }
                if (mCityPickViewData.size() > options1 && mCityPickViewData.get(options1).size() > option2) {
                    city = mCityPickViewData.get(options1).get(option2).getDistrict();
                }
                if (mQuPickViewData.size() > options1 && mQuPickViewData.get(options1).size() > option2 && mQuPickViewData.get(options1).get(option2).size() > options3) {
                    qu = mQuPickViewData.get(options1).get(option2).get(options3).getDistrict();
                }
                String addresId = "";
                if (pro == null) {
                    return;
                } else if (city == null) {
                    addresId = pro.getId();
                } else if (qu == null) {
                    addresId = city.getId();
                } else {
                    addresId = qu.getId();
                }
                mHospitalPickViewData.clear();
                mPresenter.updateExpertInfo("address",addresId);
                mPresenter.updateHospitalList(addresId);

            }
        });
    }
    @OnClick({R.id.title_back, R.id.item_more_flag, R.id.user_photo, R.id.user_name_value, R.id.user_sex_value, R.id.logout, R.id.tel_value
            , R.id.domain_value, R.id.hospital_value, R.id.depart_value, R.id.business_value, R.id.job_layout, R.id.zhuangchang_layout})
    public void onClick(View view)
    {
        Bundle bundle = new Bundle();
        switch (view.getId())
        {
            case R.id.title_back:
                back();
                break;
            case R.id.item_more_flag:
            case R.id.user_photo:
                selectImg();
                break;
            case R.id.user_name_value:
                bundle = new Bundle();
                bundle.putString(IntentKey.FRAG_INFO, "realname");
                gotoFragment(FragKey.mine_name_change_fragment, bundle);
                break;
            case R.id.user_sex_value:
                if (mSexPickViewData.size() > 0)
                {
                    mSexPicker.show();
                }
                break;
            case R.id.tel_value:
                bundle = new Bundle();
                bundle.putString(IntentKey.FRAG_INFO, "phone");
                gotoFragment(FragKey.mine_name_change_fragment, bundle);
                break;
            case R.id.domain_value:
                if (mProPickViewData.size() > 0) {
                    mDistricePicker.show();
                }

                break;
            case R.id.hospital_value:
                if (mHospitalPickViewData.size() > 0) {
                    mHospitalPicker.show();
                }
                break;

            case R.id.depart_value:
                if (mDepartPickViewData.size() > 0) {
                    mOfficePicker.show();
                }
                break;

            case R.id.business_value:
                if (mBusinessPickViewData.size() > 0) {
                    mBusinessPicker.show();
                }
                break;

            case R.id.job_layout:
                bundle = new Bundle();
                bundle.putString(IntentKey.FRAG_INFO, "sofunc");
                gotoFragment(FragKey.mine_name_change_fragment, bundle);
                break;
            case R.id.zhuangchang_layout:
                bundle = new Bundle();
                bundle.putString(IntentKey.FRAG_INFO, "adept");
                gotoFragment(FragKey.mine_name_change_fragment, bundle);
                break;
            case R.id.logout:
                final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                dialog.show();
                dialog.getWindow().setContentView(R.layout.confirm_dlg_layout);
                ((TextView) dialog.getWindow().findViewById(R.id.dlg_msg)).setText("确定要退出登录吗？");
                dialog.getWindow().setLayout(ScreenUtils.getScreenWidth() / 4 * 3, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        confirmLogOut();
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }

    @Override
    public void onActivityResult1(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            //从相册选择
            case SELECT_PICTURE:
                if (data != null)
                {
                    Uri uri = data.getData();
                    String path = Utils.getPath(uri);
                    beginCrop(uri);

                }
                break;
            //拍照添加图片
            case SELECT_CAMER:
                if (mCameraPath != null)
                {
                    String p = mCameraPath.toString();
                    Uri uri = Uri.fromFile(new File(p));
                    beginCrop(uri);
                }
                break;
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);

            default:
                break;
        }
    }

    private void beginCrop(Uri source)
    {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity());
    }

    private void handleCrop(int resultCode, Intent result)
    {
        if (resultCode == getActivity().RESULT_OK)
        {
            Uri uri = Crop.getOutput(result);
            String path = Utils.getPath(uri);
            mPresenter.updateMemberPhoto(path);
        } else if (resultCode == Crop.RESULT_ERROR)
        {
            zhToast.showToast(Crop.getError(result).getMessage());
        }
    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        List<String> permissionList = Arrays.asList(permissions);
        if (permissionList.contains(Manifest.permission.CAMERA))
        {
            toGetCameraImage();
        } else if (permissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            toGetLocalImage();
        }

    }
}
