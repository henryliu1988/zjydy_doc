package com.zhjydy_doc.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.SearchHomeContract;
import com.zhjydy_doc.presenter.presenterImp.SearchHomePresenterImp;
import com.zhjydy_doc.util.DateUtil;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.zjview.ScoreView;
import com.zhjydy_doc.view.zjview.ViewHolderAdd;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class SearchHomeFragment extends PageImpBaseFragment implements SearchHomeContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.expert_list_layout)
    LinearLayout expertListLayout;
    @BindView(R.id.expert_more_layout)
    RelativeLayout expertMoreLayout;
    @BindView(R.id.expert_all_layout)
    LinearLayout expertAllLayout;
    @BindView(R.id.info_list_layout)
    LinearLayout infoListLayout;
    @BindView(R.id.info_more_layout)
    RelativeLayout infoMoreLayout;
    @BindView(R.id.info_all_layout)
    LinearLayout infoAllLayout;
    @BindView(R.id.null_data_text)
    TextView nullDataText;
    @BindView(R.id.null_data_retrye)
    TextView nullDataRetrye;
    @BindView(R.id.null_data_layout)
    RelativeLayout nullDataLayout;
    private SearchHomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(SearchHomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_home;
    }

    @Override
    protected void afterViewCreate() {
        new SearchHomePresenterImp(this);
        expertAllLayout.setVisibility(View.GONE);
        infoAllLayout.setVisibility(View.GONE);
        nullDataLayout.setVisibility(View.GONE);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleSearchEdit.setHint("输入搜索专家和资讯");
        titleSearchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchExpertAndInfo(condition);

    }

    private void moreSeachExpert() {
        String seach = titleSearchEdit.getText().toString();
        Bundle bundle = new Bundle();
      //  bundle.putInt(IntentKey.FRAG_KEY, FragKey.search_expert_fragment);
        bundle.putString(IntentKey.FRAG_INFO, seach);
      //  ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
        gotoFragment(FragKey.search_expert_fragment,bundle);
    }

    private void moreSeachInfo() {
        String seach = titleSearchEdit.getText().toString();
        Bundle bundle = new Bundle();
      //  bundle.putInt(IntentKey.FRAG_KEY, FragKey.search_info_fragment);
        bundle.putString(IntentKey.FRAG_INFO, seach);
      //  ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
        gotoFragment(FragKey.search_info_fragment,bundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onSearchResult(List<Map<String, Object>> experts, List<Map<String, Object>> infos) {

        if (experts.size() <1 && infos.size() <1) {
            nullDataLayout.setVisibility(View.VISIBLE);
            nullDataRetrye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search();
                }
            });
        } else {
            nullDataLayout.setVisibility(View.GONE);
        }
        if (experts.size() < 1) {
            expertAllLayout.setVisibility(View.GONE);
        } else if (experts.size() < 3) {
            expertAllLayout.setVisibility(View.VISIBLE);
            expertMoreLayout.setVisibility(View.GONE);
        } else {
            expertAllLayout.setVisibility(View.VISIBLE);
            expertMoreLayout.setVisibility(View.VISIBLE);
            expertMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moreSeachExpert();
                }
            });
        }
        initExpertListLayout(experts);


        if (infos.size() < 1) {
            infoAllLayout.setVisibility(View.GONE);
        } else if (infos.size() < 3) {
            infoAllLayout.setVisibility(View.VISIBLE);
            infoMoreLayout.setVisibility(View.GONE);
        } else {
            infoAllLayout.setVisibility(View.VISIBLE);
            infoMoreLayout.setVisibility(View.VISIBLE);
            infoMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moreSeachInfo();
                }
            });
        }
        initInfoListLayout(infos);
    }

    private void initExpertListLayout(List<Map<String, Object>> experts) {
        expertListLayout.removeAllViews();
        int size = experts.size();
        if (size > 2) {
            size = 2;
        }
        for (int i = 0; i < size; i++) {
           final  Map<String, Object> expert = experts.get(i);
            ViewHolderAdd holder = ViewHolderAdd.get(getContext(), R.layout.listview_main_expert_info_item);
            ((TextView) holder.getView(R.id.name)).setText(Utils.toString(expert.get("realname")));
            ((TextView) holder.getView(R.id.depart)).setText(DicData.getInstance().getOfficeById(Utils.toString(expert.get("office"))).getName());
            ((TextView) holder.getView(R.id.profession)).setText(DicData.getInstance().getBusinessById(Utils.toString(expert.get("business"))).getName());
            ((TextView) holder.getView(R.id.hospital)).setText(DicData.getInstance().getHospitalById(Utils.toString(expert.get("hospital"))).getHospital());

            ((TextView) holder.getView(R.id.special)).setText(Utils.toString(expert.get("adept")));
            ((TextView) holder.getView(R.id.score)).setText("推荐分数：");
            //((TextView) holder.getView(R.id.star)).setText(info.getStar());
            ScoreView starView = (ScoreView) holder.getView(R.id.star);
            int score = Utils.toInteger(expert.get("stars"));
            if (score > 100) {
                score = 100;
            }
            if (score < 0) {
                score = 0;
            }
            starView.setScore(score, 100);
            ImageUtils.getInstance().displayFromRemote(Utils.toString(expert.get("path")), (ImageView) holder.getView(R.id.photo));
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentKey.FRAG_INFO,Utils.toString(expert.get("id")));
                    gotoFragment(FragKey.detail_expert_fragment,bundle);
                }
            });
            expertListLayout.addView(holder.getConvertView());
        }
    }

    private void initInfoListLayout(List<Map<String, Object>> infos) {
        infoListLayout.removeAllViews();
        int size = infos.size();
        if (size > 2) {
            size = 2;
        }
        for (int i = 0; i < size; i++) {
           final Map<String, Object> info = infos.get(i);
            ViewHolderAdd holder = ViewHolderAdd.get(getContext(), R.layout.listview_main_info_item);

            ((TextView) holder.getView(R.id.title)).setText(Utils.toString(info.get("title")));
            ((TextView) holder.getView(R.id.outline)).setText(Utils.toString(info.get("introduction")));
            ((TextView) holder.getView(R.id.date)).setText(DateUtil.getFullTimeDiffDayCurrent(Utils.toLong(info.get("add_time")), DateUtil.LONG_DATE_FORMAT_1));
            //((TextView) holder.getView(R.id.star)).setText(info.getStar());
            ImageUtils.getInstance().displayFromRemote(Utils.toString(info.get("path")), (ImageView) holder.getView(R.id.image));
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                   // ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    gotoFragment(FragKey.detail_info_fragment,bundle);
                }
            });
            infoListLayout.addView(holder.getConvertView());
        }
    }

}
