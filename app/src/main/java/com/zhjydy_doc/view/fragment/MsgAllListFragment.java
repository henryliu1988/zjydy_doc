package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.MsgAllListContract;
import com.zhjydy_doc.presenter.presenterImp.MsgAllListPresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class MsgAllListFragment extends PageImpBaseFragment implements MsgAllListContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.msg_all_layout)
    LinearLayout msgAllLayout;


    private View mOrderItemView;
    private View mSysItemView;
    private View mFansItemView;
    private View mPatientCommentView;
    private View mDocCommentView;

    private MsgAllListContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_list;
    }

    @Override
    protected void afterViewCreate() {
        ButterKnife.bind(this, mRootView);
        titleCenterTv.setText("消息");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        initMsgItemLayout();
        new MsgAllListPresenterImp(this);
    }


    private void initMsgItemLayout() {
        msgAllLayout.removeAllViews();
        mOrderItemView =  LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
        mSysItemView =  LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
        mFansItemView =  LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
        mPatientCommentView =  LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
        mDocCommentView =  LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);

        mFansItemView.findViewById(R.id.msg_content).setVisibility(View.GONE);
        mPatientCommentView.findViewById(R.id.msg_content).setVisibility(View.GONE);
        mDocCommentView.findViewById(R.id.msg_content).setVisibility(View.GONE);
        mFansItemView.findViewById(R.id.msg_time).setVisibility(View.GONE);
        mPatientCommentView.findViewById(R.id.msg_time).setVisibility(View.GONE);
        mDocCommentView.findViewById(R.id.msg_time).setVisibility(View.GONE);

        ImageUtils.getInstance().displayFromDrawable( R.mipmap.msg_order_img,(ImageView)mOrderItemView.findViewById(R.id.image));
        ImageUtils.getInstance().displayFromDrawable( R.mipmap.msg_system_img,(ImageView)mSysItemView.findViewById(R.id.image));
        ImageUtils.getInstance().displayFromDrawable( R.mipmap.msg_fans,(ImageView)mFansItemView.findViewById(R.id.image));
        ImageUtils.getInstance().displayFromDrawable( R.mipmap.msg_huan,(ImageView)mPatientCommentView.findViewById(R.id.image));
        ImageUtils.getInstance().displayFromDrawable( R.mipmap.msg_zhuan,(ImageView)mDocCommentView.findViewById(R.id.image));


        ((TextView)mOrderItemView.findViewById(R.id.msg_title)).setText("订单");
        ((TextView)mSysItemView.findViewById(R.id.msg_title)).setText("系统消息");
        ((TextView)mFansItemView.findViewById(R.id.msg_title)).setText("我的粉丝");
        ((TextView)mPatientCommentView.findViewById(R.id.msg_title)).setText("患者留言信息");
        ((TextView)mDocCommentView.findViewById(R.id.msg_title)).setText("专家留言信息");

        mSysItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(FragKey.system_order_list_fragment);
            }
        });
        mPatientCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(IntentKey.FRAG_INFO, 1);
                gotoFragment(FragKey.comment_list_fragment,bundle);
            }
        });
        mDocCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(IntentKey.FRAG_INFO, 2);
                gotoFragment(FragKey.comment_list_fragment,bundle);
            }
        });
        mFansItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFragment(FragKey.fans_list_fragment);
            }
        });
        msgAllLayout.addView(mOrderItemView);
        msgAllLayout.addView(mSysItemView);
        msgAllLayout.addView(mFansItemView);
        msgAllLayout.addView(mPatientCommentView);
        msgAllLayout.addView(mDocCommentView);
    }

    @Override
    public void updateOrderList(final Map<String, Object> order) {
        TextView title = (TextView) mOrderItemView.findViewById(R.id.msg_title);
        TextView content = (TextView) mOrderItemView.findViewById(R.id.msg_content);
        TextView timeTv = (TextView) mOrderItemView.findViewById(R.id.msg_time);
        title.setText(Utils.toString(order.get("title")));
        content.setText(Utils.toString(order.get("content")));
        if (!TextUtils.isEmpty(Utils.toString(order.get("time")))) {
            timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(order.get("time")),DateUtil.LONG_DATE_FORMAT_1));
        }
        mOrderItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String orderId = Utils.toString(order.get("orderid"));
                    int status = Utils.toInteger(order.get("status"));
                    if (mPresenter != null && status == 0) {
                        mPresenter.readOrder(orderId);
                    }
                    gotoFragment(FragKey.msg_order_list_fragment);
            }
        });
        boolean isUnread = Utils.toBoolean(order.get("status"));
        if (isUnread) {
           // View unReadFlag = mOrderItemView.findViewById(R.id.unread_flag);
            //ViewUtil.setOverViewDrawbleBg(unReadFlag, "#FF0000");
            //unReadFlag.setVisibility(View.VISIBLE);
        } else {
            View unReadFlag = mOrderItemView.findViewById(R.id.unread_flag);
            unReadFlag.setVisibility(View.GONE);
        }

    }

    @Override
    public void updateSystemList(final Map<String, Object> order) {
        TextView title = (TextView) mOrderItemView.findViewById(R.id.msg_title);
        TextView content = (TextView) mOrderItemView.findViewById(R.id.msg_content);
        TextView timeTv = (TextView) mOrderItemView.findViewById(R.id.msg_time);
        title.setText(Utils.toString(order.get("title")));
        content.setText(Utils.toString(order.get("content")));
        if (!TextUtils.isEmpty(Utils.toString(order.get("time")))) {
            timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(order.get("time"))));
        }
    }


    private void updateUnreadFlag(View view,boolean isUnread) {
        if (isUnread) {
            View unReadFlag = view.findViewById(R.id.unread_flag);
            ViewUtil.setOverViewDrawbleBg(unReadFlag, "#FF0000");
            unReadFlag.setVisibility(View.VISIBLE);
        } else {
            View unReadFlag = view.findViewById(R.id.unread_flag);
            unReadFlag.setVisibility(View.GONE);
        }
    }
    @Override
    public void updateFans(boolean isUnread) {
        updateUnreadFlag(mFansItemView,isUnread);
    }

    @Override
    public void updatePatientComment(boolean isUnread) {
        updateUnreadFlag(mPatientCommentView,isUnread);
    }

    @Override
    public void updateDocComment(boolean isUnread) {
        updateUnreadFlag(mDocCommentView,isUnread);
    }


    @Override
    public void setPresenter(MsgAllListContract.Presenter presenter) {
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
