package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.AppData;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.model.net.BaseSubscriber;
import com.zhjydy_doc.presenter.contract.ExpertDetailContract;
import com.zhjydy_doc.presenter.presenterImp.ExpertDetailPresenterImp;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.ExperDetaiCommentListAdapter;
import com.zhjydy_doc.view.zjview.ListViewForScrollView;
import com.zhjydy_doc.view.zjview.ScoreView;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class ExpertDetailFragment extends PageImpBaseFragment implements ExpertDetailContract.View {

    @BindView(R.id.comment_make_edit)
    EditText commentMakeEdit;
    @BindView(R.id.comment_make_btn)
    TextView commentMakeBtn;
    @BindView(R.id.write_words)
    LinearLayout writeWords;
    @BindView(R.id.bottom_div)
    View bottomDiv;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.depart)
    TextView depart;
    @BindView(R.id.profession)
    TextView profession;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.save_image)
    ImageView saveImage;
    @BindView(R.id.save_text)
    TextView saveText;
    @BindView(R.id.save_layout)
    LinearLayout saveLayout;
    @BindView(R.id.score_image)
    ImageView scoreImage;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.score_star)
    ScoreView scoreStar;
    @BindView(R.id.reason)
    LinearLayout reason;
    @BindView(R.id.specical)
    LinearLayout specical;
    @BindView(R.id.word_listview)
    ListViewForScrollView wordListview;
    @BindView(R.id.subscribe_expert)
    TextView subscribeExpert;
    @BindView(R.id.reason_tv)
    TextView reasonTv;
    @BindView(R.id.specical_tv)
    TextView specicalTv;
    private ExpertDetailContract.Presenter mPresenter;

    private ExperDetaiCommentListAdapter mCommentListAdapter;
    private View mCommentListHeaderView;

    private String id;


    private boolean isCollect = false;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_expert_detail;
    }

    @Override
    protected void afterViewCreate() {
        if(getArguments() == null) {
            return;
        }
        id = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        initCommentListView();
        new ExpertDetailPresenterImp(this, id);
        updateFavStatus();
    }

    private void initCommentListView() {
        mCommentListAdapter = new ExperDetaiCommentListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mCommentListHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.listview_expert_comment_header_layout, null);
        wordListview.addHeaderView(mCommentListHeaderView);
        wordListview.setAdapter(mCommentListAdapter);
    }


    private void updateFavStatus() {
        String collect = AppData.getInstance().getToken().getCollectExperts();
        List<String> coList = new ArrayList<String>();
        if (!TextUtils.isEmpty(collect)) {
            coList = Arrays.asList(collect.split(","));
        }
    }
    @Override
    public void updateExpertInfos(Map<String, Object> expertInfo) {
        name.setText(Utils.toString(expertInfo.get("realname")));
        String office = DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("office"))).getName();
        String business = DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("business"))).getName();
        if(!TextUtils.isEmpty(office) && !TextUtils.isEmpty(business)) {
            depart.setText(DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("office"))).getName() + " | ");
        } else {
            depart.setText(DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("office"))).getName());
        }
        hospital.setText(DicData.getInstance().getHospitalById(Utils.toString(expertInfo.get("hospital"))).getHospital());
        profession.setText(DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("business"))).getName());
        reasonTv.setText(Utils.toString(expertInfo.get("reason")));
        specicalTv.setText(Utils.toString(expertInfo.get("adept")));
        int score = Utils.toInteger(expertInfo.get("stars"));
        if (score > 100) {
            score = 100;
        }
        if (score < 0) {
            score = 0;
        }
        scoreText.setText("推荐分数：" + score + "分");
        scoreStar.setScore(score,100);
        ImageUtils.getInstance().displayFromRemote(Utils.toString(expertInfo.get("path")), image);

    }
    @Override
    public void updateComments(List<Map<String, Object>> comments) {
        mCommentListAdapter.refreshData(comments);
        if (comments.size() < 1) {
            wordListview.setVisibility(View.GONE);
            return;
        }
        TextView commentCoutTv = (TextView)mCommentListHeaderView.findViewById(R.id.comment_count);
        commentCoutTv.setText("留言（" + comments.size() + "）");
    }

    @Override
    public void updateFavStatus(boolean isCollect) {
        if (isCollect) {
        } else{
        }
    }

    @Override
    public void setPresenter(ExpertDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {
        mPresenter.reloadData();
    }

    @Override
    public void makeCommentSuccess() {
        commentMakeEdit.setText("");
    }


    private void trySubsribExpert() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.comment_make_btn, R.id.title_back,R.id.subscribe_expert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_make_btn:
                String commentNew = commentMakeEdit.getText().toString();
                if (TextUtils.isEmpty(commentNew)) {
                    zhToast.showToast("留言内容不能为空");
                    return;
                }
                mPresenter.makeNewComment(commentNew);
                break;
            case R.id.title_back:
                back();
                break;
            case R.id.subscribe_expert:
                trySubsribExpert();
        }
    }
}
