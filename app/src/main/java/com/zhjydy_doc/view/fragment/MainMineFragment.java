package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.entity.TokenInfo;
import com.zhjydy_doc.presenter.contract.MainMineContract;
import com.zhjydy_doc.presenter.presenterImp.MainMinePresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.view.activity.PagerImpActivity;
import com.zhjydy_doc.view.zjview.ImageTipsView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainMineFragment extends StatedFragment implements MainMineContract.MainMineView {

    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.mine_image)
    ImageView mineImage;
    @BindView(R.id.mine_status)
    TextView mineStatus;
    @BindView(R.id.mine_info_layout)
    RelativeLayout mineInfoLayout;
    @BindView(R.id.account_safe_layout)
    RelativeLayout accountSafeLayout;
    @BindView(R.id.mine_confirm_msg_layout)
    RelativeLayout mineConfirmMsgLayout;
    @BindView(R.id.mine_history_layout)
    RelativeLayout mineHistoryLayout;
    @BindView(R.id.mine_common_layout)
    RelativeLayout mineCommonLayout;
    @BindView(R.id.mine_about_layout)
    RelativeLayout mineAboutLayout;
    @BindView(R.id.mine_confirm_msg_status)
    TextView mineConfirmMsgStatus;
    private MainMineContract.MainMinePresenter mPresenter;

    public static MainMineFragment instance() {
        MainMineFragment frag = new MainMineFragment();
        return frag;
    }

    @Override
    protected void initData() {
        centerTv.setText("个人");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_mine;

    }

    @Override
    public void onResume() {
        super.onResume();
        initMinePhotoView();
    }

    @Override
    protected void afterViewCreate() {
        new MainMinePresenterImp(this);
        initMinePhotoView();
    }

    private void initMinePhotoView() {
        TokenInfo tokenInfo  = AppData.getInstance().getToken();
        if (TextUtils.isEmpty(tokenInfo.getId())){
            mineStatus.setText("请登录");
            return;
        }
        if (!TextUtils.isEmpty(tokenInfo.getPhotoUrl())) {
            ImageUtils.getInstance().displayFromRemote(tokenInfo.getPhotoUrl(),mineImage);
        }
        if (!TextUtils.isEmpty(tokenInfo.getNickname())) {
            mineStatus.setText(tokenInfo.getNickname());
        }

    }
    @Override
    public void setPresenter(MainMineContract.MainMinePresenter presenter) {
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

    @OnClick({R.id.mine_info_layout, R.id.account_safe_layout, R.id.mine_confirm_msg_layout, R.id.mine_history_layout, R.id.mine_common_layout, R.id.mine_about_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_info_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.mine_info_fragment, null, false);
                break;
            case R.id.account_safe_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.account_safe_fragment, null, false);
                break;
            case R.id.mine_confirm_msg_layout:
                loadIdentifyInfo();
                break;
            case R.id.mine_history_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.patient_case_fragment, null, false);

                break;
            case R.id.mine_common_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.common_fragment, null, false);

                break;
            case R.id.mine_about_layout:
                //ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.common_fragment,null,false);

                break;
        }
    }

    @Override
    public void updateIdentiFyStatus(int status,String msg) {
        mineConfirmMsgStatus.setVisibility(View.VISIBLE);
        String text = "";
        switch (status) {
            case 0:
            case 1:
                text = "未认证";
                break;
            case 2:
                text="认证审核中";
                break;
            case 3:
                text = "审核未通过";
                break;
            case 4:
                text = "已认证";
                break;
        }
        mineConfirmMsgStatus.setText(text);
    }

    @Override
    public void updateTokenInfo() {
        initMinePhotoView();
    }

    private void loadIdentifyInfo() {
        if (mPresenter != null) {
            Map<String, Object> map = mPresenter.getIdentifyInfo(getContext());
            if (map == null) {
                return;
            }
            if (map.size() < 1) {
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.identify_new_fragment, null, false);
            } else {
                String info = JSONObject.toJSONString(map);
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.identify_info_fragment, info, false);
            }
        }
    }


}
