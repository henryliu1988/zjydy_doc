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
import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.ChatRecordContract;
import com.zhjydy_doc.presenter.presenterImp.ChatRecordPresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class DocChatRecordFragment extends PageImpBaseFragment implements ChatRecordContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.chat_layout)
    LinearLayout chatLayout;
    private ChatRecordContract.Presenter mPresenter;


    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doc_chat_record;
    }

    @Override
    protected void afterViewCreate() {

        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        if (getArguments() == null) {
            return;
        }
        if (TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            return;
        }
        Map<String, Object> item = Utils.parseObjectToMapString(getArguments().getString(IntentKey.FRAG_INFO));
        new ChatRecordPresenterImp(this, item);
    }

    @Override
    public void setPresenter(ChatRecordContract.Presenter presenter) {
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
    public void setChatMsgs(List<Map<String, Object>> msg,String expertPhoto) {
        for (Map<String, Object> item : msg) {
            String sendId = Utils.toString(item.get("sendid"));
            String userId = AppData.getInstance().getToken().getId();
            boolean isUser = (!TextUtils.isEmpty(userId) && userId.equals(sendId));
            View view;
            String photoUrl;
            if (isUser) {
                view =  LayoutInflater.from(getContext()).inflate(R.layout.chat_record_right, null);
                photoUrl = AppData.getInstance().getToken().getPhotoUrl();
            } else {
                view =  LayoutInflater.from(getContext()).inflate(R.layout.chat_record_left, null);
                photoUrl = expertPhoto;
            }
            ImageView photImage = (ImageView)view.findViewById(R.id.photo);
            TextView timeTv = (TextView)view.findViewById(R.id.time);
            TextView contentTv = (TextView)view.findViewById(R.id.msg);

            if (TextUtils.isEmpty(photoUrl)) {
                ImageUtils.getInstance().displayFromDrawable(R.mipmap.photo,photImage);
            } else {
                ImageUtils.getInstance().displayFromRemote(photoUrl,photImage);
            }
            timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(item.get("addtime"))));
            contentTv.setText(Utils.toString(item.get("content")));

            chatLayout.addView(view);
        }
    }

    @Override
    public void updateExpertName(String name) {
        titleCenterTv.setText(name);
    }
}
