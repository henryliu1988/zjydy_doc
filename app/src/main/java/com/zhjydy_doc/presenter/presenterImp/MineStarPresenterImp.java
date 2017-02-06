package com.zhjydy_doc.presenter.presenterImp;

import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.presenter.contract.MineStarContract;
import com.zhjydy_doc.util.Utils;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public class MineStarPresenterImp implements MineStarContract.Presenter {
    private MineStarContract.View mView;


    private int score;
    private int identyScore = 0;
    private int infoScore = 0;
    private int aliPayScore = 0;
    private int weixinScore  = 0 ;
    private int comPatientScore = 0;
    public MineStarPresenterImp(MineStarContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        score = 0;
        loadIdentifyStatus();
    }

    @Override
    public void finish() {

    }

    private void loadIdentifyStatus() {
        int status = Utils.toInteger(UserData.getInstance().getToken().getmExpertInfo().getStatus_z());

        if(status== 1) {
            identyScore = 5;
            mView.updateIdentify(identyScore);
            score += 5;
            mView.updateScoreAll(score);
        }

    }
}
