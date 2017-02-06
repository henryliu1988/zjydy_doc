package com.zhjydy_doc.presenter.presenterImp;

import android.text.TextUtils;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.pageload.PageLoadDataSource;
import com.zhjydy_doc.model.pageload.RxObservalRequestHandle;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MainInfoContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.MainInfoListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainInfoPresenterImp extends PageLoadDataSource implements MainInfoContract.MainInfoPresenter,RefreshWithKey
{

    private MainInfoContract.MainInfoView mView;

    public MainInfoPresenterImp(MainInfoContract.MainInfoView view, PullToRefreshListView listView)
    {
        this.mView = view;
        setPageView(listView, new MainInfoListAdapter(mView.getContext(), new ArrayList<Map<String, Object>>()));
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.KEY_FAV_INFO,this);
        start();
    }

    @Override
    public void start()
    {
        refreshData();
        loadMsg();
        loadFavMsgCount();
    }

    private void loadMsg()
    {
    }

    public  void loadFavMsgCount()
    {
        String collect = UserData.getInstance().getToken().getCollectNews();
        int count = 0;
        if (!TextUtils.isEmpty(collect) && collect.length() > 0)
        {
            count = Utils.getCountOfString(collect, ",");
            count++;
        }
        if (mView != null)
        {
            mView.updateFavInfoCount(count);
        }

    }

    @Override
    public void finish()
    {

    }

    @Override
    public RequestHandle loadListData(final ResponseSender<List<Map<String, Object>>> sender, int page)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("pagesize", PAGE_SIZE);
        final Subscription subscription = WebCall.getInstance().call(WebKey.func_getNewsList, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                int total = Utils.toInteger(map.get("count"));
                int currentPage = Utils.toInteger(map.get("page"));
                mPage = currentPage;
                mTotalCount = total;
                sender.sendData(list);

            }

            @Override
            public void onError(Throwable e)
            {
                super.onError(e);
                Exception exption = new Exception(new Throwable());
                sender.sendError(exption);
            }
        });
        RxObservalRequestHandle handle = new RxObservalRequestHandle(subscription);
        return handle;
    }

    @Override
    public void onRefreshWithKey(int key) {
        loadFavMsgCount();
    }
}
