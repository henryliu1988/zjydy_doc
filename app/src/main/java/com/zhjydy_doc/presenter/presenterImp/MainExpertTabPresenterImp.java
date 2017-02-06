package com.zhjydy_doc.presenter.presenterImp;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.model.net.WebCall;
import com.zhjydy_doc.model.net.WebResponse;
import com.zhjydy_doc.model.pageload.PageLoadDataSource;
import com.zhjydy_doc.model.pageload.RxObservalRequestHandle;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MainExpertContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.PageLoadListAdapter;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainExpertTabPresenterImp extends  PageLoadDataSource implements  MainExpertContract.TabPresenter,RefreshWithKey
{

    private MainExpertContract.TabView mView;
    private int funcKey;
    public MainExpertTabPresenterImp(MainExpertContract.TabView view, PullToRefreshListView listView, PageLoadListAdapter adapter,int funcKey)
    {
        mView = view;
        this.funcKey = funcKey;
        setPageView(listView, adapter);
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.GUAN_ALL_LIST_CHANGE,this);
        start();
    }

    @Override
    public void start()
    {
        reloadExperts();
    }


    public void reloadExperts()
    {
        refreshData();
    }



    @Override
    public void finish()
    {
    }


    @Override
    public void guanExpert(Map<String, Object> item) {
        String id = Utils.toString(item.get("memberid"));
        ExpertData.getInstance().guanzhuExpert(id).subscribe(new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean status) {
                ExpertData.getInstance().loadGuanZhuExperts();
                ExpertData.getInstance().loadMeAndExperts();
                if (status) {
                    zhToast.showToast("关注成功");
                } else{
                    zhToast.showToast("关注失败");
                }
            }
        });
    }
    @Override
    public void cancelGuanExpert(Map<String, Object> item) {
        String id = Utils.toString(item.get("memberid"));
        ExpertData.getInstance().canCelGuanzhuExpert(id).subscribe(new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean status) {
                ExpertData.getInstance().loadGuanZhuExperts();
                ExpertData.getInstance().loadMeAndExperts();
                if (status) {
                    zhToast.showToast("取消关注成功");
                } else{
                    zhToast.showToast("取消关注失败");
                }

            }
        });
    }


    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.GUAN_ALL_LIST_CHANGE) {
            mView.onGuanzhuChange();
        }

    }
    @Override
    public RequestHandle loadListData(final ResponseSender<List<Map<String, Object>>> sender, final int page)
    {
        HashMap<String, Object> params = new HashMap<>();

        if (mView != null)
        {
            params.putAll(mView.getFilterConditions());
        }
        params.put("page", page);
        params.put("pagesize", PAGE_SIZE);
        params.put("memberid", UserData.getInstance().getToken().getId());
        final Subscription subscription = WebCall.getInstance().call(funcKey, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                int total = Utils.toInteger(map.get("count"));
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
