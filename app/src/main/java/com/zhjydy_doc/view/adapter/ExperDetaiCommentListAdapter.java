package com.zhjydy_doc.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class ExperDetaiCommentListAdapter extends ListViewAdapter<Map<String, Object>> {


    public ExperDetaiCommentListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_expert_comment_item_layout);
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> comment) {
        // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        String expertId = Utils.toString(comment.get("expertid"));
        String sendName = Utils.toString(comment.get("sendname"));
        String sendId = Utils.toString(comment.get("sendid"));
        if (!TextUtils.isEmpty(expertId) && expertId.equals(sendId)) {
            holder.getView(R.id.reply_tv).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.reply_tv).setVisibility(View.GONE);
        }

        ((TextView) holder.getView(R.id.name)).setText(sendName);
        ((TextView) holder.getView(R.id.content)).setText(Utils.toString(comment.get("content")));
        ((TextView) holder.getView(R.id.time)).setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(comment.get("addtime"))));
        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
    }
}
