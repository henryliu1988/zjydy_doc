package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.util.ScreenUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
public class HorizontalOfficeHomeView extends HorizontalScrollView {


    final static int[] colors = {
            Color.parseColor("#5D76F4"),
            Color.parseColor("#AFC853"),
            Color.parseColor("#4FB5DD"),
            Color.parseColor("#E6BB2E"),
            Color.parseColor("#4ED2AB"),
            Color.parseColor("#E25365"),
            Color.parseColor("#7DCF60"),
            Color.parseColor("#C95582"),
            Color.parseColor("#5D76F4"),
            Color.parseColor("#AFC853"),
            Color.parseColor("#E6BB2E"),
            Color.parseColor("#4ED2AB"),
            Color.parseColor("#E25365"),
            Color.parseColor("#5D76F4"),
            Color.parseColor("#AFC853"),
            Color.parseColor("#4FB5DD"),
            Color.parseColor("#E6BB2E"),
            Color.parseColor("#4ED2AB"),
            Color.parseColor("#E25365"),
            Color.parseColor("#7DCF60"),
            Color.parseColor("#C95582"),
            Color.parseColor("#5D76F4"),
            Color.parseColor("#AFC853"),
            Color.parseColor("#E6BB2E"),
            Color.parseColor("#4ED2AB"),
            Color.parseColor("#E25365")
    };

    private LinearLayout layout;

    public HorizontalOfficeHomeView(Context context) {
        super(context);
        initView(context);
    }

    public HorizontalOfficeHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(ScreenUtils.getScreenWidth() / 100, 0, ScreenUtils.getScreenWidth() / 100, 0);
        addView(layout);
    }

    public void initDatas(List<NormalDicItem> datas) {
        if (datas == null || datas.size() < 1) {
            return;
        }
        int scrollSize = datas.size() / 2;
        if (datas.size() % 2 != 0) {
            scrollSize += 1;
        }

        for (int i = 0; i < scrollSize; i++) {
            View itemLayout = LayoutInflater.from(getContext()).inflate(R.layout.horizontal_office_filter, null);
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            MapTextView topTv = (MapTextView) itemLayout.findViewById(R.id.top);
            MapTextView bottomTv = (MapTextView) itemLayout.findViewById(R.id.bottom);
            if (i < datas.size() / 2) {
                topTv.setVisibility(VISIBLE);
                bottomTv.setVisibility(VISIBLE);
                 NormalDicItem topOffice = datas.get(i * 2);
                 NormalDicItem bottomOffice = datas.get(i * 2 + 1);
                topTv.setMap(topOffice.getId(), topOffice.getName());
                bottomTv.setMap(bottomOffice.getId(), bottomOffice.getName());
                if ((i * 2) < colors.length) {
                    topTv.setBackgroundColor(colors[i * 2]);
                } else {
                    topTv.setBackgroundColor(colors[i * 2 / colors.length]);
                }
                if ((i * 2 + 1) < colors.length) {
                    bottomTv.setBackgroundColor(colors[i * 2 + 1]);
                } else {
                    bottomTv.setBackgroundColor(colors[(i * 2 + 1) / colors.length]);
                }
                topTv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            if (v instanceof  MapTextView) {
                                MapTextView view = (MapTextView)v;
                                NormalDicItem item = new NormalDicItem();
                                item.setId(view.getTextId());
                                item.setName(view.getTextValue());
                                mListener.onItemClick(item);
                            }
                        }
                    }
                });
                bottomTv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            MapTextView view = (MapTextView)v;
                            NormalDicItem item = new NormalDicItem();
                            item.setId(view.getTextId());
                            item.setName(view.getTextValue());
                            mListener.onItemClick(item);
                        }
                    }
                });
            } else if (i == datas.size() / 2 && i * 2 < datas.size()) {
                topTv.setVisibility(VISIBLE);
                bottomTv.setVisibility(INVISIBLE);
                 NormalDicItem topOffice = datas.get(i * 2);
                topTv.setMap(topOffice.getId(), topOffice.getName());
                if ((i * 2) < colors.length) {
                    topTv.setBackgroundColor(colors[i * 2]);
                } else {
                    topTv.setBackgroundColor(colors[i * 2 / colors.length]);
                }
                topTv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            MapTextView view = (MapTextView)v;
                            NormalDicItem item = new NormalDicItem();
                            item.setId(view.getTextId());
                            item.setName(view.getTextValue());
                            mListener.onItemClick(item);
                        }
                    }
                });
            }
            layout.addView(itemLayout);
        }
    }


    private onItemClickListener mListener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

   public interface onItemClickListener {
        public void onItemClick(NormalDicItem item);
    }
}
