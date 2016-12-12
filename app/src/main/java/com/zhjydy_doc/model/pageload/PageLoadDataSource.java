package com.zhjydy_doc.model.pageload;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.mvc.MVCPullrefshHelper;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy_doc.view.adapter.PageLoadListAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/2.
 */
public abstract class PageLoadDataSource implements IAsyncDataSource<List<Map<String, Object>>>
{
    protected static final int PAGE_SIZE = 10;
    protected int mTotalCount;
    protected int mPage;

    protected PullToRefreshListView mListView;
    protected PageLoadListAdapter mADapter;
    protected MVCHelper<List<Map<String, Object>>> mvcHelper;


    public void setPageView(PullToRefreshListView listView, PageLoadListAdapter adapter)
    {
        this.mListView = listView;
        this.mADapter = adapter;
        mvcHelper = new MVCPullrefshHelper<List<Map<String, Object>>>(mListView);
        mvcHelper.setAdapter(mADapter);
        mvcHelper.setDataSource(this);
        mPage = 0;
    }

    protected  void refreshData() {
        mvcHelper.refresh();
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<Map<String, Object>>> sender) throws Exception
    {
        return loadListData(sender, 1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<Map<String, Object>>> sender) throws Exception
    {
        return loadListData(sender, mPage + 1);
    }

    public abstract RequestHandle loadListData(final ResponseSender<List<Map<String, Object>>> sender, final int page);

    @Override
    public boolean hasMore()
    {
        int currentData = mPage * PAGE_SIZE;
        return currentData < mTotalCount;
    }
}
