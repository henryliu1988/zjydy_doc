package com.zhjydy_doc.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.MainOrderContract;
import com.zhjydy_doc.presenter.presenterImp.MainOrderPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.activity.PagerImpActivity;
import com.zhjydy_doc.view.adapter.OrderListAdapter;
import com.zhjydy_doc.view.zjview.ImageTipsView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainOrderFragment extends StatedFragment implements MainOrderContract.MainOrderView
{
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;
    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.null_data_text)
    TextView nullDataText;
    @BindView(R.id.null_data_retrye)
    TextView nullDataRetrye;
    @BindView(R.id.null_data_layout)
    RelativeLayout nullDataLayout;

    private MainOrderContract.MainOrderPresenter mPresenter;


    private String[] mTitles = {"全部订单", "进行中", "已完成", "退款"};

    private ArrayList<CustomTabEntity> tabs = new ArrayList<>();
    private OrderListAdapter mAdapter;
    private List<Map<String, Object>> mOrderList = new ArrayList<>();
    private List<Map<String, Object>> mOnGoOrderList = new ArrayList<>();
    private List<Map<String, Object>> mOkOrderList = new ArrayList<>();
    private List<Map<String, Object>> mRetrackOrderList = new ArrayList<>();

    public static MainOrderFragment instance()
    {
        MainOrderFragment frag = new MainOrderFragment();
        return frag;
    }

    @Override
    protected void initData()
    {
        for (int i = 0; i < mTitles.length; i++)
        {
            tabs.add(new TabEntity(mTitles[i]));
        }
        centerTv.setText("订单");
        rightImg.setImageResource(R.mipmap.title_msg);
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
            }
        });
        tabLayout.setTabData(tabs);
        tabLayout.setCurrentTab(0);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener()
        {
            @Override
            public void onTabSelect(int position)
            {
                updateAdapter();
            }

            @Override
            public void onTabReselect(int position)
            {
                updateAdapter();
            }
        });
        mAdapter = new OrderListAdapter(getActivity(), mOrderList);
        mList.setAdapter(mAdapter);
        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
        {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView)
            {
                mPresenter.reloadOrders();
            }
        });
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Map<String, Object> item = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (item != null && item.size() > 0)
                {
                    String id = Utils.toString(item.get("orderid"));
                    //   ActivityUtils.transToFragPagerActivity(getActivity(),PagerImpActivity.class,FragKey.detail_order_fragment,id,false);
                }
            }
        });
        mAdapter.setOperateListener(new OrderListAdapter.OperateListener()
        {
            @Override
            public void onOperate(Map<String, Object> item, int operate)
            {
                String id = Utils.toString(item.get("id"));
                switch (operate)
                {
                    case OrderListAdapter.OPERATE_DETAIL:
                        ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.detail_order_fragment, id, false);
                        break;
                    case OrderListAdapter.OPERATE_CANCEL:
                        ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.order_cancel_fragment, id, false);
                        break;

                    case OrderListAdapter.OPERATE_PAY:
                        break;

                }
            }
        });
    }


    private void onOperateClick()
    {

    }
    public void updateUnReadMsgCount(int count) {
        String text = "";
        if (count != 0) {
            text = count + "";
        }
        rightImg.setTipText(text);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_main_order;
    }

    @Override
    protected void afterViewCreate()
    {
        nullDataLayout.setVisibility(View.GONE);
        new MainOrderPresenterImp(this);
    }


    @Override
    public void update(List<Map<String, Object>> orders)
    {
        mOrderList.clear();
        mOrderList.addAll(orders);
        mList.onRefreshComplete();
        mOnGoOrderList.clear();
        mOkOrderList.clear();
        mRetrackOrderList.clear();
        for (Map<String, Object> order : mOrderList)
        {
            int status = Utils.toInteger(order.get("status"));
            switch (status)
            {
                case 1:
                case 2:
                case 3:
                case 11:
                case 12:
                    mOnGoOrderList.add(order);
                    break;
                case 5:
                case 6:
                case 7:
                    mOkOrderList.add(order);
                    break;
                case 4:
                case 9:
                case 10:
                    mRetrackOrderList.add(order);
                    break;
            }
        }
        updateAdapter();
    }

    @Override
    public void onNetError()
    {
        mList.onRefreshComplete();
        zhToast.showToast("网络访问错误");
    }

    public void updateAdapter()
    {
        int tab = tabLayout.getCurrentTab();
        List<Map<String, Object>> list = new ArrayList<>();
        switch (tab)
        {
            case 0:
                list.addAll(mOrderList);
                break;
            case 1:
                list.addAll(mOnGoOrderList);
                break;
            case 2:
                list.addAll(mOkOrderList);
                break;
            case 3:
                list.addAll(mRetrackOrderList);
                break;
            default:
                break;
        }
        if (list == null || list.size() < 1) {
            nullDataLayout.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
            nullDataRetrye.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    refreshView();
                }
            });
        } else {
            nullDataLayout.setVisibility(View.GONE);
            mList.setVisibility(View.VISIBLE);
        }
        mAdapter.setData(list);
    }

    @Override
    public void setPresenter(MainOrderContract.MainOrderPresenter presenter)
    {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView()
    {
        mPresenter.reloadOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    class OrderListView extends ListView
    {

        public OrderListView(Context context)
        {
            super(context);
        }


    }

    public class TabEntity implements CustomTabEntity
    {
        public String title;

        public TabEntity(String title)
        {
            this.title = title;
        }

        @Override
        public String getTabTitle()
        {
            return title;
        }

        @Override
        public int getTabSelectedIcon()
        {
            return R.mipmap.ic_page_indicator;
        }

        @Override
        public int getTabUnselectedIcon()
        {
            return R.mipmap.ic_page_indicator;
        }
    }

}
