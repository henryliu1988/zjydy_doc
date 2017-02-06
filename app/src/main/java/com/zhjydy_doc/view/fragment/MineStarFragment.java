package com.zhjydy_doc.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.MineStarContract;
import com.zhjydy_doc.presenter.presenterImp.MineStarPresenterImp;
import com.zhjydy_doc.view.zjview.ScoreView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public class MineStarFragment extends PageImpBaseFragment implements MineStarContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.score_textview)
    TextView scoreTextview;
    @BindView(R.id.score_star)
    ScoreView scoreStar;
    @BindView(R.id.score_diff)
    TextView scoreDiff;
    @BindView(R.id.mine_info_title)
    TextView mineInfoTitle;
    @BindView(R.id.mine_info_value)
    TextView mineInfoValue;
    @BindView(R.id.identify_complete_title)
    TextView identifyCompleteTitle;
    @BindView(R.id.identify_complete_value)
    TextView identifyCompleteValue;
    @BindView(R.id.alipay_title)
    TextView alipayTitle;
    @BindView(R.id.alipay_value)
    TextView alipayValue;
    @BindView(R.id.weixin_title)
    TextView weixinTitle;
    @BindView(R.id.weixin_value)
    TextView weixinValue;
    @BindView(R.id.patient_score_title)
    TextView patientScoreTitle;
    @BindView(R.id.patient_score_value)
    TextView patientScoreValue;
    private MineStarContract.Presenter mPresenter;

    @Override
    public void setPresenter(MineStarContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_star;
    }

    @Override
    protected void afterViewCreate() {
        new MineStarPresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("我的星级");
        scoreStar.setScore(0,100);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void updateIdentify(int score) {
        if (score > 0) {
            identifyCompleteValue.setText("+" + score + "分");
            identifyCompleteValue.setTextColor(Color.parseColor("#4379EF"));
        }
    }

    @Override
    public void updateScoreAll(int score) {
        scoreTextview.setText(score + "");
        scoreStar.setScore(score,100);
    }
}
