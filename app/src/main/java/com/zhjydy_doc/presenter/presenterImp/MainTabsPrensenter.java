package com.zhjydy_doc.presenter.presenterImp;


import com.zhjydy_doc.model.data.MsgData;
import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MainTabsContract;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MainTabsPrensenter implements MainTabsContract.Presenter, RefreshWithKey {

    private MainTabsContract.View mView;

    public MainTabsPrensenter(MainTabsContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.NEW_COMMENT_EXPERT_DATA_READ, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.NEW_COMMENT_PATIENT_DATA_READ, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.FANS_LSIT_CHANGE, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.ORDER_MSG_CHANGE, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.SYSTEM_DATA_READ, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.ORDET_LIST_CHANGE, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.KEY_FAV_INFO, this);

        start();
    }

    @Override
    public void start() {
        MsgData.getInstance().loadData();
        loadFavList();
    }

    @Override
    public void finish() {

    }

    public void loadMsgCount() {
        int count = MsgData.getInstance().getUnReadMsgCount();
        if (mView != null) {
            mView.updateMsgCount(count);
        }
    }

    public void loadFavList() {

    }

    @Override
    public void onRefreshWithKey(int key) {
        switch (key) {
            case RefreshKey.ORDER_MSG_CHANGE:
            case RefreshKey.NEW_COMMENT_EXPERT_DATA_READ:
            case RefreshKey.NEW_COMMENT_PATIENT_DATA_READ:
            case RefreshKey.FANS_LSIT_CHANGE:
            case RefreshKey.SYSTEM_DATA_READ:
                loadMsgCount();
                break;
            case RefreshKey.ORDET_LIST_CHANGE:
                if (mView != null) {
                    mView.refreshOrderList();
                }
                break;
            case RefreshKey.KEY_FAV_INFO:
                if (mView != null) {
                    mView.refreshNewsCollectList();
                }
        }
    }
}
