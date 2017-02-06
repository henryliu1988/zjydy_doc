package com.zhjydy_doc.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.IdentityInfoNewContract;
import com.zhjydy_doc.presenter.presenterImp.IdentityInfoNewPresenterImp;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ActivityResultView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class IdentityInfoNewFragment extends PageImpBaseFragment implements IdentityInfoNewContract.View, ActivityResultView {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.identify_1)
    ImageView identify1;
    @BindView(R.id.camera_postive)
    ImageView cameraPostive;
    @BindView(R.id.identify_2)
    ImageView identify2;
    @BindView(R.id.camera_nagtive)
    ImageView cameraNagtive;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.identify_yi)
    ImageView identifyYi;
    @BindView(R.id.identify_yi_img)
    ImageView identifyYiImg;
    @BindView(R.id.tips)
    TextView tips;
    private IdentityInfoNewContract.Presenter mPresenter;


    private int imgPos = 0;

    private Map<Integer, String> mImageMap = new HashMap<>();

    private String yiImage = null;


    private String type = "new";

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString(IntentKey.FRAG_INFO);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identity_new;
    }

    @Override
    protected void afterViewCreate() {
        new IdentityInfoNewPresenterImp(this);
        addOnActivityResultView(this);
        titleCenterTv.setText("认证信息");

        if ("edit".equals(type)) {
            tips.setVisibility(View.GONE);
        }
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        identify1.setVisibility(View.GONE);
        identify2.setVisibility(View.GONE);
        identifyYi.setVisibility(View.VISIBLE);

        identifyYiImg.setVisibility(View.VISIBLE);
        cameraPostive.setVisibility(View.VISIBLE);
        cameraNagtive.setVisibility(View.VISIBLE);
        cameraPostive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPos = 0;
                selectImg();
            }
        });
        cameraNagtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPos = 1;
                selectImg();
            }
        });
        identifyYiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPos = 2;
                selectImg();
            }
        });
        identify1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPos = 0;
                selectImg();

            }
        });
        identify2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPos = 1;
                selectImg();
            }
        });
        identifyYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPos = 2;
                selectImg();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageMap.size() < 2) {
                    zhToast.showToast("请检查身份证正面和背面是否都已经上传");
                    return;
                }
                if (TextUtils.isEmpty(yiImage)) {
                    zhToast.showToast("请上传您的执业医师资格证");
                    return;
                }
                mPresenter.submitIdentifymsg(mImageMap, yiImage);
            }
        });
    }

    @Override
    public void onSubmitSuccess(boolean status) {
        if (status) {
            zhToast.showToast("上传认证信息成功");
            int fragKey[] = {FragKey.identify_info_fragment};
            back(fragKey);
        } else {
            zhToast.showToast("上传认证信息失败");
        }
    }

    @Override
    public void setPresenter(IdentityInfoNewContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void updateImageMap(String path) {
        switch (imgPos) {
            case 0:
                identify1.setVisibility(View.VISIBLE);
                cameraPostive.setVisibility(View.GONE);
                ImageUtils.getInstance().displayFromSDCard(path, identify1);
                mImageMap.put(0, path);
                break;
            case 1:
                identify2.setVisibility(View.VISIBLE);
                cameraNagtive.setVisibility(View.GONE);
                ImageUtils.getInstance().displayFromSDCard(path, identify2);
                mImageMap.put(1, path);
                break;
            case 2:
                identifyYi.setVisibility(View.VISIBLE);
                identifyYiImg.setVisibility(View.GONE);
                ImageUtils.getInstance().displayFromSDCard(path, identifyYi);
                yiImage = path;
        }
    }

    @Override
    public void onActivityResult1(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册选择
            case SELECT_PICTURE:
                if (data != null) {
                    Uri uri = data.getData();
                    String path = Utils.getPath(uri);
                    updateImageMap(path);
                }
                break;
            //拍照添加图片
            case SELECT_CAMER:
                if (mCameraPath != null) {
                    String p = mCameraPath.toString();
                    updateImageMap(p);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> permissionList = Arrays.asList(permissions);
        if (permissionList.contains(Manifest.permission.CAMERA)) {
            toGetCameraImage();
        } else if (permissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            toGetLocalImage();
        }
    }
}
