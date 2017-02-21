package com.zhjydy_doc.view.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.ExpertDetailContract;
import com.zhjydy_doc.presenter.presenterImp.ExpertDetailPresenterImp;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.ExperDetaiCommentListAdapter;
import com.zhjydy_doc.view.zjview.ListViewForScrollView;
import com.zhjydy_doc.view.zjview.ScoreView;
import com.zhjydy_doc.view.zjview.ViewUtil;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class ExpertDetailFragment extends PageImpBaseFragment implements ExpertDetailContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.comment_make_edit)
    EditText commentMakeEdit;
    @BindView(R.id.comment_make_btn)
    TextView commentMakeBtn;
    @BindView(R.id.write_words)
    LinearLayout writeWords;
    @BindView(R.id.bottom_div)
    View bottomDiv;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.identify)
    TextView identify;
    @BindView(R.id.guanzhu_tv)
    TextView guanzhuTv;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.star)
    ScoreView star;
    @BindView(R.id.depart)
    TextView depart;
    @BindView(R.id.profession)
    TextView profession;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.tel_image)
    ImageView telImage;
    @BindView(R.id.tel_text)
    TextView telText;
    @BindView(R.id.tel_button)
    TextView telButton;
    @BindView(R.id.reason_tv)
    TextView reasonTv;
    @BindView(R.id.reason)
    LinearLayout reason;
    @BindView(R.id.specical_tv)
    TextView specicalTv;
    @BindView(R.id.specical)
    LinearLayout specical;
    @BindView(R.id.word_listview)
    ListViewForScrollView wordListview;
    @BindView(R.id.guanzhu_layout)
    RelativeLayout guanzhuLayout;
    private ExpertDetailContract.Presenter mPresenter;

    private ExperDetaiCommentListAdapter mCommentListAdapter;
    private View mCommentListHeaderView;

    private String id;


    private boolean isGuanzhu = false;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_expert_detail;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            return;
        }
        id = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        initCommentListView();
        new ExpertDetailPresenterImp(this, id);
    }

    private void initCommentListView() {
        mCommentListAdapter = new ExperDetaiCommentListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mCommentListHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.listview_expert_comment_header_layout, null);
        wordListview.addHeaderView(mCommentListHeaderView);
        wordListview.setAdapter(mCommentListAdapter);
    }


    @Override
    public void updateExpertInfos(Map<String, Object> expertInfo) {
        name.setText(Utils.toString(expertInfo.get("realname")));
        String office = DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("office"))).getName();
        String business = DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("business"))).getName();
        if (!TextUtils.isEmpty(office) && !TextUtils.isEmpty(business)) {
            depart.setText(DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("office"))).getName() + " | ");
        } else {
            depart.setText(DicData.getInstance().getOfficeById(Utils.toString(expertInfo.get("office"))).getName());
        }
        hospital.setText(DicData.getInstance().getHospitalById(Utils.toString(expertInfo.get("hospital"))).getHospital());
        profession.setText(DicData.getInstance().getBusinessById(Utils.toString(expertInfo.get("business"))).getName());
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
        star.setScore(score, 100);
        ImageUtils.getInstance().displayFromRemoteOver(Utils.toString(expertInfo.get("path")), image);
        int guanzhuStatus = Utils.toInteger(expertInfo.get("guanzhu"));
        String phone = Utils.toString(expertInfo.get("phone"));
        updateGuanZhuStatus(guanzhuStatus,phone);
    }



    @Override
    public void updateIdentyStatus(int status) {
        if (status == 1) {
            identify.setText("已认证");
            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.identify_ok_white);
            ViewUtil.setCornerViewDrawbleBg(identify, "#4466C8");
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            identify.setCompoundDrawables(drawable, null, null, null);
            identify.setTextColor(getContext().getResources().getColor(R.color.white_text));
        } else {
            identify.setText("未认证");
            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.identify_no);
            ViewUtil.setCornerViewDrawbleBg(identify, "#7988B1");
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            identify.setCompoundDrawables(drawable, null, null, null);
            identify.setTextColor(getContext().getResources().getColor(R.color.white_text));
        }
    }

    public void updateGuanZhuStatus(int status, final String phone) {
        switch (status) {
            case ExpertData.GUAN_STAT_NUL:
                guanzhuTv.setText("加关注");
                ViewUtil.setCornerViewDrawbleBg(guanzhuLayout, "#FFB900", 1);
                Drawable drawableNul = getContext().getResources().getDrawable(R.mipmap.guan_nul);
                drawableNul.setBounds(0, 0, drawableNul.getMinimumWidth(), drawableNul.getMinimumHeight());
                guanzhuTv.setCompoundDrawables(drawableNul, null, null, null);
                guanzhuTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mPresenter != null) {
                            mPresenter.guanzhuExpert();
                        }
                    }
                });
                telText.setText("相互关注后可查看联系方式");
                telText.setTextColor(getContext().getResources().getColor(R.color.black_text2));
                telImage.setImageResource(R.mipmap.tel_no);
                ViewUtil.setCornerViewDrawbleBg(telButton, "#CCCCCC");
                telButton.setText("一键通话");
                break;
            case ExpertData.GUAN_STAT_GUAN:
                guanzhuTv.setText("已关注");
                ViewUtil.setCornerViewDrawbleBg(guanzhuLayout, "#60D701", 1);
                Drawable drawableGuan = getContext().getResources().getDrawable(R.mipmap.guan_ok);
                drawableGuan.setBounds(0, 0, drawableGuan.getMinimumWidth(), drawableGuan.getMinimumHeight());
                guanzhuTv.setCompoundDrawables(drawableGuan, null, null, null);
                guanzhuTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mPresenter != null) {
                            mPresenter.cancelGuanzhuExpert();
                        }
                    }
                });

                telText.setText("相互关注后可查看联系方式");
                telText.setTextColor(getContext().getResources().getColor(R.color.black_text2));
                telImage.setImageResource(R.mipmap.tel_no);
                ViewUtil.setCornerViewDrawbleBg(telButton, "#CCCCCC");
                telButton.setText("一键通话");

                break;
            case ExpertData.GUAN_STAT_MEGUAN:
                guanzhuTv.setText("相互关注");
                ViewUtil.setCornerViewDrawbleBg(guanzhuLayout,"#4467C8",1);
                Drawable drawablemeGuan = getContext().getResources().getDrawable(R.mipmap.guan_all);
                drawablemeGuan.setBounds(0, 0, drawablemeGuan.getMinimumWidth(), drawablemeGuan.getMinimumHeight());
                guanzhuTv.setCompoundDrawables(drawablemeGuan, null, null, null);
                guanzhuTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mPresenter != null) {
                            mPresenter.cancelGuanzhuExpert();
                        }
                    }
                });

                if (!TextUtils.isEmpty(phone)) {
                    telText.setText(phone);
                    telText.setTextColor(getContext().getResources().getColor(R.color.black_text1));
                    telImage.setImageResource(R.mipmap.tel);
                    ViewUtil.setCornerViewDrawbleBg(telButton, "#FF6634");
                    telButton.setText("一键通话");
                    telButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            telExpert(phone);
                        }
                    });
                }
                break;
        }
    }


    public void telExpert(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void updateComments(List<Map<String, Object>> comments) {
        mCommentListAdapter.refreshData(comments);
        if (comments.size() < 1) {
            wordListview.setVisibility(View.GONE);
        } else {
            wordListview.setVisibility(View.VISIBLE);
        }
        TextView commentCoutTv = (TextView) mCommentListHeaderView.findViewById(R.id.comment_count);
        commentCoutTv.setText("留言（" + comments.size() + "）");
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

    @Override
    public void updateGuanZhuStatus(int status) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.comment_make_btn, R.id.title_back, R.id.tel_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_make_btn:
                String commentNew = commentMakeEdit.getText().toString();
                if (TextUtils.isEmpty(commentNew)) {
                    zhToast.showToast("留言内容不能为空");
                    return;
                }
                mPresenter.makeNewComment(commentNew);
                closeKeyBoard(commentMakeEdit);
                break;
            case R.id.title_back:
                back();
                break;
        }
    }
}
