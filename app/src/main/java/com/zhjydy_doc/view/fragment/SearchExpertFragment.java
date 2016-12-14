package com.zhjydy_doc.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.SearchExpertContract;
import com.zhjydy_doc.presenter.presenterImp.SearchExpertPresenterImp;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.MainExpertListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SearchExpertFragment extends PageImpBaseFragment implements SearchExpertContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;
    protected SearchExpertContract.Presenter mPresenter;
    protected MainExpertListAdapter mExpertListAdapter;

    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_doc;
    }

    @Override
    protected void afterViewCreate() {
        initExpertList();
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        new SearchExpertPresenterImp(this);
        titleSearchEdit.setHint("搜索专家");
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
        initSearchInfo();
    }

    private void initSearchInfo() {
        if (getArguments() == null) {
            return;
        }
        String search = getArguments().getString(IntentKey.FRAG_INFO,"");
        if (!TextUtils.isEmpty(search)) {
            titleSearchEdit.setText(search);
            mPresenter.searchExpert(search);
        }
    }
    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchExpert(condition);
    }
    private void initExpertList() {
        mExpertListAdapter = new MainExpertListAdapter(getContext(), new ArrayList<Map<String,Object>>());
        mList.setAdapter(mExpertListAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> info = (Map<String,Object>) adapterView.getAdapter().getItem(i);

                if (info != null) {
                   // ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.detail_expert_fragment, Utils.toString(info.get("id")), false);
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                    gotoFragment(FragKey.detail_expert_fragment);

                }
            }
        });

    }

    @Override
    public void setPresenter(SearchExpertContract.Presenter presenter) {
        this.mPresenter = presenter;
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
    public void updateExpertList(List<Map<String, Object>> list) {
        mExpertListAdapter.refreshData(list);
    }
}
