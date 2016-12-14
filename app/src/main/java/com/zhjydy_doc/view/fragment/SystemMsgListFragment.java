package com.zhjydy_doc.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.SystemMsgListContract;
import com.zhjydy_doc.presenter.presenterImp.SystemMsgListPresenterImp;
import com.zhjydy_doc.view.adapter.MsgSystemListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class SystemMsgListFragment extends PageImpBaseFragment implements SystemMsgListContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    private MsgSystemListAdapter mAdapter;
    private SystemMsgListContract.Presenter mPresenter;
    private List<Map<String,Object>> orderList = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void afterViewCreate() {
        titleCenterTv.setText("系统消息");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        mAdapter = new MsgSystemListAdapter(getContext(),new ArrayList<Map<String, Object>>());
        new SystemMsgListPresenterImp(this,mList,mAdapter);
    }

    @Override
    public void setPresenter(SystemMsgListContract.Presenter presenter) {
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
}
