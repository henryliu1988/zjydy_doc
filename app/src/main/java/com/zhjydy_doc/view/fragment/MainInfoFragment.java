package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.MainInfoContract;
import com.zhjydy_doc.presenter.presenterImp.MainInfoPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.activity.PagerImpActivity;
import com.zhjydy_doc.view.zjview.ImageTipsView;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainInfoFragment extends StatedFragment implements MainInfoContract.MainInfoView {
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    public static MainInfoFragment instance() {
        MainInfoFragment frag = new MainInfoFragment();
        return frag;
    }

    private MainInfoContract.MainInfoPresenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_info;

    }

    @Override
    protected void afterViewCreate() {
        initView();
        new MainInfoPresenterImp(this, mList);
        titleSearchText.setText("搜索资讯");
        rightImg.setImageResource(R.mipmap.title_msg);
        rightLImg.setImageResource(R.mipmap.shoucang);
        rightLImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("key", FragKey.fave_info_list_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
            }
        });
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
            }
        });
        titleSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.search_info_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);

            }
        });

    }

    private void initView() {
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("id")))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                    ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
    }

    @Override
    public void setPresenter(MainInfoContract.MainInfoPresenter presenter) {
        mPresenter = presenter;
    }

    public void refrehCollectCount() {
        if (mPresenter != null) {
            mPresenter.loadFavMsgCount();
        }
    }
    public void updateUnReadMsgCount(int count) {
        String text = "";
        if (count != 0) {
            text = count + "";
        }
        rightImg.setTipText(text);
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateFavInfoCount(int count) {
        String text = "";
        if (count != 0) {
            text = count + "";
        }
        rightLImg.setTipText(text);
    }
}
