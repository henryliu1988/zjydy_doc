package com.zhjydy_doc.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.DicData;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.presenter.contract.MainHomeContract;
import com.zhjydy_doc.presenter.presenterImp.MainHomePresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.ScreenUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewFindUtils;
import com.zhjydy_doc.view.activity.MainTabsActivity;
import com.zhjydy_doc.view.activity.PagerImpActivity;
import com.zhjydy_doc.view.adapter.MainHomeInfoListAdapter;
import com.zhjydy_doc.view.zjview.BannerLayout;
import com.zhjydy_doc.view.zjview.HorizontalOfficeHomeView;
import com.zhjydy_doc.view.zjview.ImageTipsView;
import com.zhjydy_doc.view.zjview.ListViewForScrollView;
import com.zhjydy_doc.view.zjview.ScoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainHomeFragment extends StatedFragment implements MainHomeContract.MainHomeView {

    ConvenientBanner<List<Map<String, Object>>> expertList;
    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.banner_home)
    ConvenientBanner bannerHome;
    @BindView(R.id.expert_more)
    TextView expertMore;
    @BindView(R.id.info_more)
    TextView infoMore;
    @BindView(R.id.info_list)
    ListViewForScrollView infoList;
    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.m_search_icon)
    ImageView mSearchIcon;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.expert_banner_layout)
    BannerLayout expertBannerLayout;
    @BindView(R.id.horizontal_ScrollView)
    HorizontalOfficeHomeView horizontalScrollView;
    private MainHomeContract.MainHomePresenter mPresenter;

    private MainHomeInfoListAdapter mInfoListAdapter;

    private int curTitle = 0;

    public static MainHomeFragment instance() {
        MainHomeFragment frag = new MainHomeFragment();
        return frag;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void afterViewCreate() {
        ButterKnife.bind(this, mRootView);
        new MainHomePresenterImp(this);
        leftImg.setVisibility(View.GONE);
        // leftImg.setImageResource(R.mipmap.titlebar_search);
        centerTv.setText("专家医对壹");
        initMsgCountImage();
        scrollView.scrollTo(0, 0);
    }

    private void initMsgCountImage() {
        rightImg.setImageResource(R.mipmap.title_msg);

    }

    @Override
    protected void initData() {
    }


    @Override
    public void setPresenter(MainHomeContract.MainHomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void updateBanner(final List<Map<String, Object>> images) {
        bannerHome.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, images).startTurning(3000)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        bannerHome.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position >= images.size()) {
                    return;
                }
                Map<String, Object> item = images.get(position);
                String id = Utils.toString(item.get("newsid"));
                if (!TextUtils.isEmpty(id)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, id);
                    ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
    }

    @Override
    public void updateMsg(final List<String> msg) {

    }


    public void updateUnReadMsgCount(int count) {
        String text = "";
        if (count != 0) {
            text = count + "";
        }
        rightImg.setTipText(text);
    }

    @Override
    public void updateOfficeList(List<NormalDicItem> items) {
        if (items != null && items.size() > 0) {
            horizontalScrollView.initDatas(items);
            horizontalScrollView.setOnItemClickListener(new HorizontalOfficeHomeView.onItemClickListener() {
                @Override
                public void onItemClick(NormalDicItem item) {
                    if (item == null || TextUtils.isEmpty(item.getId())) {
                        return;
                    }
                    Map<String, Object> condition = new HashMap<String, Object>();
                    condition.put("id", item.getId());
                    condition.put("name", item.getName());
                    String infp = JSONObject.toJSONString(condition);
                    ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.expert_office_filter_list_fragment, infp, false);
                    // ((MainTabsActivity) getActivity()).gotoTabCondition(MainTabsActivity.VIEW_SECOND, condition);
                }
            });
        }
    }

    @Override
    public void updateExpert(List<Map<String, Object>> experts) {
        int size = experts.size();
        int pageCount = size / 3;
        if (size % 3 != 0) {
            pageCount += 1;
        }
        List<View> pageViews = new ArrayList<>();
        for (int p = 0; p < pageCount; p++) {
            LinearLayout pageLayout = new LinearLayout(getContext());
            int startIndex = p * 3;
            int endIndex = (p + 1) * 3;
            if (size % 3 != 0 && pageCount - p == 1) {
                endIndex = size;
            }
            for (int i = startIndex; i < endIndex; i++) {
                View childView = LayoutInflater.from(getContext()).inflate(R.layout.main_home_expert_item, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtils.getScreenWidth() - 8) / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
                childView.setLayoutParams(params);
                Map<String, Object> item = experts.get(i);
                String photoUrl = Utils.toString(item.get("path"));
                String name = Utils.toString(item.get("realname"));
                String hospitaId = Utils.toString(item.get("hospital"));
                String officeId = Utils.toString(item.get("office"));
                String professId = Utils.toString(item.get("business"));
                String id = Utils.toString(item.get("id"));
                childView.setTag(id);
                ImageView imageView = ViewFindUtils.find(childView, R.id.image);
                TextView nameTv = ViewFindUtils.find(childView, R.id.name);
                TextView hospitalTv = ViewFindUtils.find(childView, R.id.hospital);
                TextView officeTv = ViewFindUtils.find(childView, R.id.office);
                TextView professTv = ViewFindUtils.find(childView, R.id.profess);
                ScoreView scroreView = ViewFindUtils.find(childView, R.id.star);
                int score = Utils.toInteger(item.get("stars"));
                if (score > 100) {
                    score = 100;
                }
                if (score < 0) {
                    score = 0;
                }
                scroreView.setScore(score, 100);

                ImageUtils.getInstance().displayFromRemoteOver(photoUrl, imageView);
                nameTv.setText(name);
                hospitalTv.setText(DicData.getInstance().getHospitalById(hospitaId).getHospital());
                officeTv.setText(DicData.getInstance().getOfficeById(officeId).getName());
                professTv.setText(DicData.getInstance().getBusinessById(professId).getName());
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = Utils.toString(view.getTag());
                        Bundle bundle = new Bundle();
                        bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_expert_fragment);
                        bundle.putString(IntentKey.FRAG_INFO, id);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);

                    }
                });
                pageLayout.addView(childView);
                if (i < endIndex - 1) {
                    View view = new View(getContext());
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.gray_bg));
                    view.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
                    pageLayout.addView(view);
                }
            }
            pageViews.add(pageLayout);
        }
        expertBannerLayout.initPageViews(pageViews);
    }

    @Override
    public void updateInfo(List<Map<String, Object>> infos) {
        mInfoListAdapter = new MainHomeInfoListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        infoList.setAdapter(mInfoListAdapter);
        infoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (info != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                    ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
        mInfoListAdapter.refreshData(infos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.expert_more, R.id.info_more, R.id.right_img, R.id.left_img, R.id.search_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expert_more:
                ((MainTabsActivity) getActivity()).gotoTab(MainTabsActivity.VIEW_SECOND);
                break;
            case R.id.info_more:
                ((MainTabsActivity) getActivity()).gotoTab(MainTabsActivity.VIEW_THIRD);
                break;
            case R.id.right_img:
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
                break;
            case R.id.left_img:
            case R.id.search_layout:
                Bundle bundleSearch = new Bundle();
                bundleSearch.putInt("key", FragKey.search_home_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleSearch, false);
                break;
        }
    }

    class NetworkImageHolderView implements Holder<Map<String, Object>> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Map<String, Object> data) {
            ImageUtils.getInstance().displayFromRemote(Utils.toString(data.get("path")), imageView);
        }
    }

    /*
    class ExpertHolderView implements Holder<List<Map<String, Object>>> {
        private ImageView mView;

        @Override
        public View createView(Context context) {
            mView = new ImageView(getContext());
            return mView;
        }

        @Override
        public void UpdateUI(Context context, int position, List<Map<String, Object>> data) {
            if (data == null || data.size() < 1) {
                return;
            }
            ImageUtils.getInstance().displayFromRemote(Utils.toString(data.get(0).get()));
            /*
            for (int i = 0; i < data.size(); i++) {
                View childView = mView.findViewWithTag(i);
                if (! (childView instanceof  LinearLayout)) {
                    break;
                }
                Map<String, Object> item = data.get(i);
                String photoUrl = Utils.toString(item.get("path"));
                String name = Utils.toString(item.get("realname"));
                String hospital = Utils.toString(item.get("hospital"));
                String office = Utils.toString(item.get("office"));
                String profess = Utils.toString(item.get("business"));
                ImageView imageView = ViewFindUtils.find(childView, R.id.image);
                TextView nameTv = ViewFindUtils.find(childView, R.id.name);
                TextView hospitalTv = ViewFindUtils.find(childView, R.id.hospital);
                TextView officeTv = ViewFindUtils.find(childView, R.id.office);
                TextView professTv = ViewFindUtils.find(childView, R.id.profess);
                ImageUtils.getInstance().displayFromRemote(photoUrl, imageView);
                nameTv.setText(name);
                hospitalTv.setText(hospital);
                officeTv.setText(office);
                professTv.setText(profess);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = 0;
                    }
                });
            }

        }


    }
       */
}
