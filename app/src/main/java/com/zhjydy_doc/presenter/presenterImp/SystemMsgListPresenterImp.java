package com.zhjydy_doc.presenter.presenterImp;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.pageload.PageLoadDataSource;
import com.zhjydy_doc.model.pageload.RxObservalRequestHandle;
import com.zhjydy_doc.presenter.contract.SystemMsgListContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.PageLoadListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class SystemMsgListPresenterImp extends PageLoadDataSource implements SystemMsgListContract.Presenter {

    private SystemMsgListContract.View mView;


    public SystemMsgListPresenterImp(SystemMsgListContract.View view, PullToRefreshListView listView, PageLoadListAdapter adapter) {
        this.mView = view;
        setPageView(listView, adapter);
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

        loadSystemList();
    }

    private void loadSystemList() {
        refreshData();
    }

    @Override
    public void finish() {
    }


    @Override
    public RequestHandle loadListData(final ResponseSender<List<Map<String, Object>>> sender, final  int page) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("pagesize", PAGE_SIZE);
        final Subscription subscription = WebCall.getInstance().call(WebKey.func_getSysMsg, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                int total = Utils.toInteger(map.get("count"));
                int currentPage = Utils.toInteger(map.get("page"));
                mPage = page;
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
}
