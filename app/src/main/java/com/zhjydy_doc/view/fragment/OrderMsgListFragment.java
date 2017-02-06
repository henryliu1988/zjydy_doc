package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.OrderMsgListContract;
import com.zhjydy_doc.presenter.presenterImp.OrderMsgListPresenterImp;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.OrderMsgListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class OrderMsgListFragment extends PageImpBaseFragment implements OrderMsgListContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    private OrderMsgListAdapter mAdapter;
    private OrderMsgListContract.Presenter mPresenter;
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
        mAdapter = new OrderMsgListAdapter(getContext(),orderList);
        mList.setAdapter(mAdapter);
        titleCenterTv.setText("订单消息");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        new OrderMsgListPresenterImp(this);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> order = (Map<String,Object>)parent.getAdapter().getItem(position);
                if (order != null) {
                    String msgId = Utils.toString(order.get("id"));

                    int status = Utils.toInteger(order.get("status"));
                    if (mPresenter != null && status ==0) {
                        mPresenter.readOrder(msgId);
                    }
                    String orderId = Utils.toString(order.get("orderid"));

                    gotoFragment(FragKey.detail_order_fragment,orderId);
                }
            }
        });

    }

    @Override
    public void setPresenter(OrderMsgListContract.Presenter presenter) {
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
    public void updateOrderList(List<Map<String, Object>> list) {
        mAdapter.refreshData(list);
    }
}
