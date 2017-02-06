package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.CommentListContract;
import com.zhjydy_doc.presenter.presenterImp.CommentListPresenterIml;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class CommentListFragment extends PageImpBaseFragment implements CommentListContract.View {
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.null_data_text)
    TextView nullDataText;
    @BindView(R.id.null_data_retrye)
    TextView nullDataRetrye;
    @BindView(R.id.null_data_layout)
    RelativeLayout nullDataLayout;


    private int id = 1;

    private CommentListContract.Presenter mPresenter;

    @Override
    public void setPresenter(CommentListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            return;
        }
        id = getArguments().getInt(IntentKey.FRAG_INFO, 1);
        if (id < 1) {
            return;
        }
        if (id == 1) {
            titleCenterTv.setText("患者留言");
        } else if (id == 2) {
            titleCenterTv.setText("专家留言");
        }

        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        new CommentListPresenterIml(this, id);

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
    public void updateCommentList(List<Map<String, Object>> data) {
        contentLayout.removeAllViews();

        if (data == null || data.size()< 1) {
            contentLayout.setVisibility(View.GONE);
            nullDataLayout.setVisibility(View.VISIBLE);
            return;
        } else {
            contentLayout.setVisibility(View.VISIBLE);
            nullDataLayout.setVisibility(View.GONE);
        }
        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
            ImageView imagew = (ImageView) view.findViewById(R.id.image);
            TextView title = (TextView) view.findViewById(R.id.msg_title);
            TextView content = (TextView) view.findViewById(R.id.msg_content);
            TextView timeTv = (TextView) view.findViewById(R.id.msg_time);
            String photoUrl = Utils.toString(data.get(i).get("path"));
            if (!TextUtils.isEmpty(photoUrl)) {
                ImageUtils.getInstance().displayFromRemoteOver(photoUrl, imagew);
            } else {
                ImageUtils.getInstance().displayFromDrawableOver(R.mipmap.photo, imagew);
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

                        gotoFragment(FragKey.doc_chat_record_fragment, bundle);
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
            contentLayout.addView(view);
        }

    }
}
