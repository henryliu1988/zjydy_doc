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
import com.zhjydy_doc.presenter.contract.IdentityInfoContract;
import com.zhjydy_doc.presenter.presenterImp.IdentityInfoPresenterImp;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.ScreenUtils;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class IdentityInfoFragment extends PageImpBaseFragment implements IdentityInfoContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.step_upload)
    TextView stepUpload;
    @BindView(R.id.step_wait_verify)
    TextView stepWaitVerify;
    @BindView(R.id.step_wait)
    TextView stepWait;
    @BindView(R.id.step_verify)
    TextView stepVerify;
    @BindView(R.id.edit_info)
    TextView editInfo;
    @BindView(R.id.photo1)
    ImageView photo1;
    @BindView(R.id.photo2)
    ImageView photo2;
    @BindView(R.id.photo_yi)
    ImageView photoYi;
    private IdentityInfoContract.Presenter mPresenter;

    private int status = 1;
    private List<String> idpath = new ArrayList<>();
    private String yiPath = "";

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identity_info;
    }

    @Override
    protected void afterViewCreate() {
        new IdentityInfoPresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleCenterTv.setText("认证信息");
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(IntentKey.FRAG_INFO,"edit");
                gotoFragment(FragKey.identify_new_fragment,bundle);
            }
        });
        initInfoImage();
        initStatusView();
    }


    private void initInfoImage() {
        int size = idpath.size();
        if (size == 1) {
            ImageUtils.getInstance().displayFromRemote(idpath.get(0), photo1);
        } else if (size > 1) {
            ImageUtils.getInstance().displayFromRemote(idpath.get(0), photo1);
            ImageUtils.getInstance().displayFromRemote(idpath.get(1), photo2);
        }


        if (!TextUtils.isEmpty(yiPath)) {
            ImageUtils.getInstance().displayFromRemote(yiPath, photoYi);
        }
    }

    private void initStatusView() {
        stepUpload.setText("未上传");
        stepWaitVerify.setText("未审核");
        stepWait.setText("审核中");
        stepVerify.setText("审核\n结果");
        int strokWidth = ScreenUtils.getScreenWidth() / 100;
        ViewUtil.setOverViewDrawbleBg(stepUpload, "#CCCCCC", "#EEEEEE", strokWidth);
        ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#CCCCCC", "#EEEEEE", strokWidth);
        ViewUtil.setOverViewDrawbleBg(stepWait, "#CCCCCC", "#EEEEEE", strokWidth);
        ViewUtil.setOverViewDrawbleBg(stepVerify, "#CCCCCC", "#EEEEEE", strokWidth);
        if (status == 1) {
            stepUpload.setText("上传" + "\n" + "成功");
            stepWaitVerify.setText("等待\n审核");
            stepWait.setText("等待中");
            stepVerify.setText("审核\n通过");

            ViewUtil.setOverViewDrawbleBg(stepUpload, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWait, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepVerify, "#FFAD0E", "#FFE3B9", strokWidth);
        } else if (status == 2) {
            stepUpload.setText("上传" + "\n" + "成功");
            stepWaitVerify.setText("等待\n审核");
            stepWait.setText("等待中");
            stepVerify.setText("审核\n结果");
            ViewUtil.setOverViewDrawbleBg(stepUpload, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWait, "#FFAD0E", "#FFE3B9", strokWidth);
        } else  if (status == 3) {

        } else if (status == 4) {

            stepUpload.setText("上传" + "\n" + "成功");
            stepWaitVerify.setText("等待\n审核");
            stepWait.setText("等待中");
            stepVerify.setText("审核\n失败");
            ViewUtil.setOverViewDrawbleBg(stepUpload, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWait, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepVerify, "#FFAD0E", "#FFE3B9", strokWidth);

        }

    }

    @Override
    public void setPresenter(IdentityInfoContract.Presenter presenter) {
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


    @Override
    public void updateIdentifyInfo(int status, List<String> idPath, List<String> yIPath) {
        this.status = status;
        this.idpath = idPath;
        if (yIPath != null && yIPath.size() > 0) {
            yiPath = yIPath.get(0);
        }
        initInfoImage();
        initStatusView();
    }
}
