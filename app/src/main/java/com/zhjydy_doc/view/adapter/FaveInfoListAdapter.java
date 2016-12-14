package com.zhjydy_doc.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.FavInfoContract;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class FaveInfoListAdapter extends ListViewAdapter<Map<String, Object>> {


    private FavInfoContract.Presenter mPresenter;

    public FaveInfoListAdapter(Context context, List<Map<String, Object>> datas) {
        super(context, datas, R.layout.listview_fave_info_item);
    }

    public void setPresenter(FavInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void convert(ViewHolder holder, Map<String, Object> info) {
        // ((TextView) holder.getView(R.id.photo)).setText(info.getPhotoUrl());
        ((TextView) holder.getView(R.id.title)).setText(Utils.toString(info.get("title")));
        ((TextView) holder.getView(R.id.outline)).setText(Utils.toString(info.get("introduction")));
        ((TextView) holder.getView(R.id.date)).setText(DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("add_time")),DateUtil.LONG_DATE_FORMAT_1));
        ImageUtils.getInstance().displayFromRemote(Utils.toString(info.get("path")), (ImageView) holder.getView(R.id.image));

        //((TextView) holder.getView(R.id.star)).setText(info.getStar());
        final String id = Utils.toString(info.get("id"));
        holder.getView(R.id.fave_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null)
                    mPresenter.cancelFav(id);
            }
        });
    }
}
