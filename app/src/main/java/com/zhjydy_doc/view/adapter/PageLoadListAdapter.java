package com.zhjydy_doc.view.adapter;

import android.content.Context;

import com.shizhefei.mvc.IDataAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/2.
 */
public abstract class PageLoadListAdapter extends ListViewAdapter<Map<String,Object>> implements IDataAdapter<List<Map<String,Object>>>
{
    public PageLoadListAdapter(Context context, List datas, int layoutId)
    {
        super(context, datas, layoutId);
    }

    @Override
    public List<Map<String,Object>> getData()
    {
        return mDatas;
    }

    @Override
    public void notifyDataChanged(List<Map<String,Object>> maps, boolean isRefresh)
    {
        if (isRefresh)
        {
            mDatas.clear();
        }
        mDatas.addAll(maps);
        notifyDataSetChanged();

    }
}
