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
import com.zhjydy_doc.presenter.contract.SearchInfoContract;
import com.zhjydy_doc.presenter.presenterImp.SearchInfoPresenterImp;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.MainInfoListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SearchInfoFragment extends PageImpBaseFragment implements SearchInfoContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;


    private List<Map<String,Object>> list = new ArrayList<>();
    private MainInfoListAdapter mInfoAdapter;
    public static SearchInfoFragment instance() {
        SearchInfoFragment frag = new SearchInfoFragment();
        return frag;
    }

    private SearchInfoContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_info;

    }

    @Override
    protected void afterViewCreate() {
        titleSearchEdit.setHint("搜索资讯");
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


        mInfoAdapter = new MainInfoListAdapter(getContext(),list);
        mList.setAdapter(mInfoAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(position);
                if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("id")))) {
                    Bundle bundle = new Bundle();
                   // bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                   // ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);

                    gotoFragment(FragKey.detail_info_fragment,bundle);

                }
            }
        });
        new SearchInfoPresenterImp(this);
        initSearchInfo();
    }


    private void initSearchInfo() {
        if (getArguments() == null) {
            return;
        }
        String search = getArguments().getString(IntentKey.FRAG_INFO,"");
        if (!TextUtils.isEmpty(search)) {
            titleSearchEdit.setText(search);
            mPresenter.searchInfo(search);
        }
    }
    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchInfo(condition);
    }

    @Override
    public void setPresenter(SearchInfoContract.Presenter presenter) {
        mPresenter = presenter;
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

    @OnClick({R.id.title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
        }
    }

    @Override
    public void updateInfoList(List<Map<String, Object>> list) {
        mInfoAdapter.refreshData(list);
    }
}
