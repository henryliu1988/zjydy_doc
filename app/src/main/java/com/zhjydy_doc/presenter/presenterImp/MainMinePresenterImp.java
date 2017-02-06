package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.refresh.RefreshKey;
import com.zhjydy_doc.model.refresh.RefreshManager;
import com.zhjydy_doc.model.refresh.RefreshWithKey;
import com.zhjydy_doc.presenter.contract.MainMineContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainMinePresenterImp implements MainMineContract.MainMinePresenter,RefreshWithKey {

    private MainMineContract.MainMineView mView;

    public MainMinePresenterImp(MainMineContract.MainMineView view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.EXPERT_INFO_CHANGE,this);
        start();
    }

    @Override
    public void start() {
    }


    @Override
    public void finish() {

    }




    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.EXPERT_INFO_CHANGE) {
            mView.updateTokenInfo();
        }

    }
}
