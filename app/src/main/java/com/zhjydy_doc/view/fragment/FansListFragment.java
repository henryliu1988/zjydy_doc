package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.FansListConTract;
import com.zhjydy_doc.presenter.presenterImp.FansListPresenterImp;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.FansExpertListAdapter;
import com.zhjydy_doc.view.zjview.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class FansListFragment extends PageImpBaseFragment implements FansListConTract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.new_fans_list)
    ListViewForScrollView newFansList;

    @BindView(R.id.fans_list)
    ListViewForScrollView fansList;
    @BindView(R.id.new_fans_layout)
    LinearLayout newFansLayout;
    @BindView(R.id.fans_layout)
    LinearLayout fansLayout;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.null_data_text)
    TextView nullDataText;
    @BindView(R.id.null_data_retrye)
    TextView nullDataRetrye;
    @BindView(R.id.null_data_layout)
    RelativeLayout nullDataLayout;


    private FansExpertListAdapter mNewFanListAdapter;
    private FansExpertListAdapter mFansListAdapter;


    private FansListConTract.Presenter mPresenter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fans_layout;
    }

    @Override
    protected void afterViewCreate() {
        titleCenterTv.setText("我的粉丝");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        mNewFanListAdapter = new FansExpertListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mFansListAdapter = new FansExpertListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        newFansList.setAdapter(mNewFanListAdapter);
        fansList.setAdapter(mFansListAdapter);
        newFansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> item = (Map<String,Object>) parent.getAdapter().getItem(position);
                String expertId = Utils.toString(item.get("memberid"));
                if (mPresenter != null) {
                    mPresenter.readFans(expertId);
                }
                gotoFragment(FragKey.detail_expert_fragment,expertId);
            }
        });

        fansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> item = (Map<String,Object>) parent.getAdapter().getItem(position);
                String expertId = Utils.toString(item.get("memberid"));
                gotoFragment(FragKey.detail_expert_fragment,expertId);
            }
        });
        new FansListPresenterImp(this);
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void setPresenter(FansListConTract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void updateNewFuns(List<Map<String, Object>> list) {
        if (list.size() < 1) {
            newFansLayout.setVisibility(View.GONE);
        } else {
            newFansLayout.setVisibility(View.VISIBLE);
        }
        mNewFanListAdapter.refreshData(list);

    }



    @Override
    public void updateFuns(List<Map<String, Object>> list) {
        if (list.size() < 1) {
            fansLayout.setVisibility(View.GONE);
        } else {
            fansLayout.setVisibility(View.VISIBLE);
        }

        mFansListAdapter.refreshData(list);

    }

    @Override
    public void noDataView(boolean nodata) {
        if (nodata) {
            scrollView.setVisibility(View.GONE);
            nullDataLayout.setVisibility(View.VISIBLE);
            nullDataText.setText("您还没有粉丝");
        } else {
            scrollView.setVisibility(View.VISIBLE);
            nullDataLayout.setVisibility(View.GONE);

        }
    }


}
