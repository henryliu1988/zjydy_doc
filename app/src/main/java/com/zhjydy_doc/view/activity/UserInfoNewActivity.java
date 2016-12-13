package com.zhjydy_doc.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.DistricPickViewData;
import com.zhjydy_doc.model.entity.District;
import com.zhjydy_doc.model.entity.HosipitalPickViewData;
import com.zhjydy_doc.model.entity.HospitalDicItem;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.presenter.contract.UserInfoNewContract;
import com.zhjydy_doc.presenter.presenterImp.UserInfoNewPresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ActivityResultView;
import com.zhjydy_doc.view.zjview.MapTextView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class UserInfoNewActivity extends BaseActivity implements UserInfoNewContract.View, ActivityResultView {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.photo_title)
    TextView photoTitle;
    @BindView(R.id.photo_more)
    ImageView photoMore;
    @BindView(R.id.photo_image)
    ImageView photoImage;
    @BindView(R.id.photo_image_layout)
    RelativeLayout photoImageLayout;
    @BindView(R.id.name_title)
    TextView nameTitle;
    @BindView(R.id.name_value)
    EditText nameValue;
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
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    EditText telValue;
    @BindView(R.id.next_station)
    TextView nextStation;


    private UserInfoNewContract.Presenter mPresenter;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_new);

        ButterKnife.bind(this);

        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleCenterTv.setText("专家信息");
        TokenInfo info = AppData.getInstance().getToken();
        if (info == null || TextUtils.isEmpty(info.getId())) {
            finish();
            return;
        }
        String id = info.getId();
        new UserInfoNewPresenterImp(this, id);
        mDistricePicker = new OptionsPickerView<DistricPickViewData>(getContext());
        mHospitalPicker = new OptionsPickerView<HosipitalPickViewData>(getContext());
        mOfficePicker = new OptionsPickerView<NormalDicItem>(getContext());
        mBusinessPicker = new OptionsPickerView<NormalDicItem>(getContext());
        initDatas(info);
    }

    private void initDatas(TokenInfo info) {
        String name = info.getNickname();
        String photoUrl = info.getPhotoUrl();
        String addressId = info.getNickname();
        String officeId = info.getNickname();
        String hospitalid = info.getNickname();
        String businessId = info.getNickname();
        String phoneNum = info.getMobile();

        nameValue.setText(name);
        ImageUtils.getInstance().displayFromRemote(photoUrl, photoImage);
        if (TextUtils.isEmpty(addressId)) {
            String addressName = DicData.getInstance().getDistrictStrById(addressId);
            domainValue.setMap(addressId, addressName);
        }
        if (TextUtils.isEmpty(hospitalid)) {
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
        if (!TextUtils.isEmpty(phoneNum)) {
            telValue.setText(phoneNum);
        }
    }

    @Override
    public void updateDistrict(Map<String, ArrayList> distrctData) {
        mProPickViewData = (ArrayList<DistricPickViewData>) distrctData.get("pro");
        mCityPickViewData = (ArrayList<ArrayList<DistricPickViewData>>) distrctData.get("city");
        mQuPickViewData = (ArrayList<ArrayList<ArrayList<DistricPickViewData>>>) distrctData.get("qu");
        mDistricePicker.setPicker(mProPickViewData, mCityPickViewData, mQuPickViewData, true);
        initDistricePicker();

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
                if (mQuPickViewData.size() > options1 && mQuPickViewData.get(options1).size() > option2 && mQuPickViewData.get(options1).get(option2).size() > options3){
                    qu = mQuPickViewData.get(options1).get(option2).get(options3).getDistrict();
                }
                if (pro == null) {
                    return;
                } else if (city == null) {
                    domainValue.setMap(pro.getId(),pro.getName());
                } else if (qu == null ) {
                    domainValue.setMap(city.getId(),pro.getName() + " " + city.getName());
                } else{
                    domainValue.setMap(qu.getId(), pro.getName() + " " + city.getName() + " " + qu.getName());
                }
                mHospitalPickViewData.clear();
                mPresenter.updateHospitalList(domainValue.getTextId());
            }
        });
    }

    @Override
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
                departValue.setMap(officeId, officeName);
            }
        });

    }

    @Override
    public void updateBusiness(ArrayList<NormalPickViewData> business) {
        mBusinessPickViewData = business;
        mBusinessPicker.setPicker(mDepartPickViewData);
        mBusinessPicker.setCyclic(false);
        mBusinessPicker.setSelectOptions(0);
        mBusinessPicker.setCancelable(true);
        mBusinessPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String officeName = mBusinessPickViewData.get(options1).getmItem().getName();
                String officeId = mBusinessPickViewData.get(options1).getmItem().getId();
                departValue.setMap(officeId, officeName);
            }
        });

    }

    @Override
    public void updateHospitalByAddress(final ArrayList<HosipitalPickViewData> hosData) {
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
                hospitalValue.setMap(hos.getId(), hos.getHospital());
            }
        });

    }

    @Override
    public void setPresenter(UserInfoNewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick({R.id.title_back, R.id.photo_image_layout, R.id.domain_value, R.id.hospital_value, R.id.depart_value, R.id.business_value, R.id.next_station})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.photo_image_layout:
                selectImg();
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
            case R.id.next_station:
                checkMsg();
                break;
        }
    }

    private void checkMsg() {
        String name = nameValue.getText().toString();
        String phone = telValue.getText().toString();
        String districtId = domainValue.getTextId();
        String hosId = hospitalValue.getTextId();
        String officeId = departValue.getTextId();
        String businessId = businessValue.getTextId();
        String photoUrl = mPhotoPath;

        if (TextUtils.isEmpty(name)) {
            zhToast.showToast("真实姓名不能为空");
            return;

        }
        if (TextUtils.isEmpty(districtId)) {
            zhToast.showToast("请选择您所在地区");
            return;
        }
        if (TextUtils.isEmpty(hosId)) {
            zhToast.showToast("请选择您所属医院");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            zhToast.showToast("联系方式不能为空");
            return;
        }

        Map<String,Object> info = new HashMap<>();
        mPresenter.submitUserInfo(info);
    }

    public static final int SELECT_PICTURE = 1;
    public static final int SELECT_CAMER = 0;
    protected File mCameraPath = null;
    private String mPhotoPath;
    @Override
    public void onActivityResult1(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            //从相册选择
            case SELECT_PICTURE:
                if (data != null) {
                    Uri uri = data.getData();
                    String path = Utils.getPath(uri);
                    //   ImageUtils.getInstance().displayFromRemote(path,userPhoto);
                    ImageUtils.getInstance().displayFromSDCard(path,photoImage);
                    mPhotoPath = path;
                }
                break;
            //拍照添加图片
            case SELECT_CAMER:
                if (mCameraPath != null)
                {
                    String p = mCameraPath.toString();
                    //   ImageUtils.getInstance().displayFromRemote(p,userPhoto);
                    ImageUtils.getInstance().displayFromSDCard(p,photoImage);
                    mPhotoPath = p;
                    mCameraPath = null;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> permissionList = Arrays.asList(permissions);
        if (permissionList.contains(Manifest.permission.CAMERA))
        {
            toGetCameraImage();
        } else if (permissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            toGetLocalImage();
        }

    }

    /**
     * 照相选择图片
     */
    public void toGetCameraImage()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        String time = DateUtil.getCurrent();
        File path = ImageUtils.getInstance().getAppImageFilePath(time + ".jpg");
        if (path == null)
        {
            zhToast.showToast("创建路径失败");
        }
        mCameraPath = path;

        Uri uri = Uri.fromFile(mCameraPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, SELECT_CAMER);
        // finish();
    }

    /**
     * 选择本地图片
     */
    public void toGetLocalImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    protected void selectImg()
    {
        final CharSequence[] items = {"拍照上传", "从相册选择"};
        new AlertDialog.Builder(getContext()).setTitle("选择图片来源")
                .setItems(items, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == SELECT_PICTURE)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PICTURE);
                            } else
                            {
                                toGetLocalImage();
                            }
                        } else
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            {
                                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_CAMER);
                            } else
                            {
                                toGetCameraImage();
                            }
                            //
                        }
                    }
                }).create().show();
    }
}
