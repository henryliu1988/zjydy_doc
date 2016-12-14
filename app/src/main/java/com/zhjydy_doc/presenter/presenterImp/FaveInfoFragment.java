package com.zhjydy_doc.presenter.presenterImp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.presenter.contract.FavInfoContract;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.adapter.FaveInfoListAdapter;
import com.zhjydy_doc.view.fragment.FragKey;
import com.zhjydy_doc.view.fragment.PageImpBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class FaveInfoFragment extends PageImpBaseFragment implements FavInfoContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;
    @BindView(R.id.null_data_text)
    TextView nullDataText;
    @BindView(R.id.null_data_retrye)
    TextView nullDataRetrye;
    @BindView(R.id.null_data_layout)
    RelativeLayout nullDataLayout;

    private FaveInfoListAdapter mAdapter;

    public static FaveInfoFragment instance() {
        FaveInfoFragment frag = new FaveInfoFragment();
        return frag;
    }

    private FavInfoContract.Presenter mPresenter;

    @Override
    protected void initData() {
        mAdapter = new FaveInfoListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mList.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_info;

    }

    @Override
    protected void afterViewCreate() {
        titleSearchEdit.setHint("搜索资讯");
        titleSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    search();
                }

                return false;
            }
        });
        nullDataLayout.setVisibility(View.GONE);
        new FaveInfoPresenterImp(this);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("id")))) {
                    Bundle bundle = new Bundle();
                   // bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                 //   ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);


                 //   bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                    // ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    gotoFragment(FragKey.detail_info_fragment,bundle);

                }
            }
        });
        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                search();
            }
        });
    }

    @Override
    public void setPresenter(FavInfoContract.Presenter presenter) {
        mPresenter = presenter;
        mAdapter.setPresenter(mPresenter);

    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateInfoList(List<Map<String, Object>> infos) {
        mList.onRefreshComplete();
        mAdapter.refreshData(infos);
        if (infos != null && infos.size() > 0) {
            nullDataLayout.setVisibility(View.GONE);
            mList.setVisibility(View.VISIBLE);
        } else {
            mList.setVisibility(View.GONE);
            nullDataLayout.setVisibility(View.VISIBLE);
            nullDataRetrye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search();
                }
            });
        }
    }

    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchFavInfos(condition);
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
}
