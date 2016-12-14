package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.MsgAllListContract;
import com.zhjydy_doc.presenter.presenterImp.MsgAllListPresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.List;
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
    @BindView(R.id.msg_order_layout)
    LinearLayout msgOrderLayout;
    @BindView(R.id.msg_comment_layout)
    LinearLayout msgCommentLayout;


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
        new MsgAllListPresenterImp(this);
    }


    @Override
    public void updateOrderList(List<Map<String, Object>> data) {
        msgOrderLayout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            final Map<String, Object> item = data.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
            ImageView imagew = (ImageView) view.findViewById(R.id.image);
            TextView title = (TextView) view.findViewById(R.id.msg_title);
            TextView content = (TextView) view.findViewById(R.id.msg_content);
            TextView timeTv = (TextView) view.findViewById(R.id.msg_time);

            ImageUtils.getInstance().displayFromDrawable(Utils.toInteger(data.get(i).get("image")), imagew);
            title.setText(Utils.toString(data.get(i).get("title")));
            content.setText(Utils.toString(data.get(i).get("content")));
            if (!TextUtils.isEmpty(Utils.toString(data.get(i).get("time")))) {
                timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get(i).get("time"))));
            }
            view.setTag(Utils.toInteger(data.get(i).get("type")));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = Utils.toInteger(v.getTag());
                    if (type == 0) {
                        String orderId = Utils.toString(item.get("orderid"));
                        int status = Utils.toInteger(item.get("status"));
                        if (mPresenter != null && status == 0) {
                            mPresenter.readOrder(orderId);
                        }
                        gotoFragment(FragKey.msg_order_list_fragment);
                     //   ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    } else if (type == 1) {
                        Bundle bundle = new Bundle();
                        //bundle.putInt("key", FragKey.system_order_list_fragment);
                        gotoFragment(FragKey.system_order_list_fragment);
                      //  ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    }
                }
            });
            boolean isUnread = Utils.toBoolean(data.get(i).get("status"));
            if (isUnread) {
                View unReadFlag = view.findViewById(R.id.unread_flag);
                ViewUtil.setOverViewDrawbleBg(unReadFlag, "#FF0000");
                unReadFlag.setVisibility(View.VISIBLE);
            } else {
                View unReadFlag = view.findViewById(R.id.unread_flag);
                unReadFlag.setVisibility(View.GONE);
            }
            msgOrderLayout.addView(view);
        }
    }


    @Override
    public void updateChatList(List<Map<String, Object>> data) {
        msgCommentLayout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
            ImageView imagew = (ImageView) view.findViewById(R.id.image);
            TextView title = (TextView) view.findViewById(R.id.msg_title);
            TextView content = (TextView) view.findViewById(R.id.msg_content);
            TextView timeTv = (TextView) view.findViewById(R.id.msg_time);
            String photoUrl = Utils.toString(data.get(i).get("path"));
            if (!TextUtils.isEmpty(photoUrl)) {
                ImageUtils.getInstance().displayFromRemote(photoUrl, imagew);
            } else {
                ImageUtils.getInstance().displayFromDrawable(R.mipmap.photo, imagew);
            }

            String expertId = Utils.toString(data.get(i).get("expertid"));
            String getId = Utils.toString(data.get(i).get("getid"));
            String sendId = Utils.toString(data.get(i).get("sendid"));
            String titleName;

            if (!TextUtils.isEmpty(expertId) && expertId.equals(getId)) {
                titleName = Utils.toString(data.get(i).get("getname"));
            } else {
                titleName = Utils.toString(data.get(i).get("sendname"));
            }
            title.setText(titleName);
            content.setText(Utils.toString(data.get(i).get("content")));
            if (!TextUtils.isEmpty(Utils.toString(data.get(i).get("addtime")))) {
                timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get(i).get("addtime"))));
            }
            view.setTag(data.get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if (tag != null && tag instanceof Map) {

                        Map<String, Object> data = (Map<String, Object>) tag;
                        String id = Utils.toString(data.get("id"));
                        int status = Utils.toInteger(data.get("status"));
                        if (!TextUtils.isEmpty(id) && status == 0) {
                            mPresenter.readComment(id);
                        }
                        Bundle bundle = new Bundle();
                        String info = JSONObject.toJSONString(data);
                        bundle.putString(IntentKey.FRAG_INFO, info);
                       // bundle.putInt("key", FragKey.doc_chat_record_fragment);
                       // ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);

                        gotoFragment(FragKey.doc_chat_record_fragment,bundle);
                    }
                }
            });
            boolean isUnread = Utils.toInteger(data.get(i).get("status")) == 0;
            if (isUnread) {
                View unReadFlag = view.findViewById(R.id.unread_flag);
                ViewUtil.setOverViewDrawbleBg(unReadFlag, "#FF0000");
                unReadFlag.setVisibility(View.VISIBLE);
            } else {
                View unReadFlag = view.findViewById(R.id.unread_flag);
                unReadFlag.setVisibility(View.GONE);
            }
            msgCommentLayout.addView(view);
        }
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
