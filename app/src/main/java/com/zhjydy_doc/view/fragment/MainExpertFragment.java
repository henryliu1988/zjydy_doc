package com.zhjydy_doc.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.ExpertData;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.model.entity.NormalPickViewData;
import com.zhjydy_doc.model.net.WebKey;
import com.zhjydy_doc.presenter.contract.MainExpertContract;
import com.zhjydy_doc.presenter.presenterImp.MainExpertPresenterImp;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.view.activity.PagerImpActivity;
import com.zhjydy_doc.view.zjview.ExpertMainTabView;
import com.zhjydy_doc.view.zjview.ImageTipsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainExpertFragment extends StatedFragment implements MainExpertContract.MainExpertView {


    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;

    protected MainExpertContract.MainExpertPresenter mPresenter;

    private String[] mTitles = {"全部专家", "我的关注", "相互关注"};
    private int[] funkey = {WebKey.func_getExpertsList, WebKey.func_getGuanExperts, WebKey.func_getMeAndExpert};
    private int[] dataType = {ExpertData.GUAN_STAT_NUL, ExpertData.GUAN_STAT_GUAN, ExpertData.GUAN_STAT_MEGUAN};

    private ArrayList<CustomTabEntity> tabs = new ArrayList<>();

    private List<ExpertMainTabView> pagerViews = new ArrayList<>();
    private PagerAdapter mPagerAdapter;

    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_doc;
    }

    @Override
    protected void afterViewCreate() {
        titleSearchText.setText("搜索专家");
        rightImg.setImageResource(R.mipmap.title_msg);
        rightLImg.setVisibility(View.GONE);
        initTabLayout();
        new MainExpertPresenterImp(this);
    }

    private void initTabLayout() {
        for (int i = 0; i < mTitles.length; i++) {
            tabs.add(new TabEntity(mTitles[i]));
        }
        tabLayout.setTabData(tabs);
        tabLayout.setCurrentTab(0);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpage.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        initViewPager();
    }

    private void initViewPager() {
        for (int i = 0; i < mTitles.length; i++) {
            ExpertMainTabView tab = new ExpertMainTabView(getContext());
            tab.setFuncKeyAndDataType(funkey[i],dataType[i]);
            tab.setOnItemClickListener(new ExpertMainTabView.OnItemClickListener() {
                @Override
                public void onItemClick(Map<String, Object> info) {
                    if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("memberid")))) {
                        ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.detail_expert_fragment, Utils.toString(info.get("memberid")), false);
                    }
                }
            });
            pagerViews.add(tab);
        }
        mPagerAdapter = new PageAdapter();
        viewpage.setAdapter(mPagerAdapter);
        viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void refreshViewWidthCondition(Map<String, NormalDicItem> condition) {
        if (condition == null || condition.size() < 1) {
            return;
        }

    }


    @Override
    public void setPresenter(MainExpertContract.MainExpertPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    public void updateUnReadMsgCount(int count) {
        String text = "";
        if (count != 0) {
            text = count + "";
        }
        rightImg.setTipText(text);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void updateCityAndHos(Map<String, ArrayList> data) {
        for (ExpertMainTabView t : pagerViews) {
            t.updateCityAndHos(data);
        }
    }

    @Override
    public void reloadData() {

    }


    @Override
    public void updateDistrict(Map<String, ArrayList> distrctData) {

    }

    @Override
    public void updateOffice(ArrayList<NormalPickViewData> officeData) {
        for (ExpertMainTabView t : pagerViews) {
            t.updateOffice(officeData);
        }
    }

    @Override
    public void updateBusiness(ArrayList<NormalPickViewData> data) {
        for (ExpertMainTabView t : pagerViews) {
            t.updateBusiness(data);
        }
    }


    @OnClick({R.id.title_search_ly, R.id.right_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_search_ly:
                Bundle bundle = new Bundle();
                bundle.putInt("key", FragKey.search_expert_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                break;
            case R.id.right_img:
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
                break;
        }
    }

    public class TabEntity implements CustomTabEntity {
        public String title;

        public TabEntity(String title) {
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return R.mipmap.ic_page_indicator;
        }

        @Override
        public int getTabUnselectedIcon() {
            return R.mipmap.ic_page_indicator;
        }
    }

    // 指引页面数据适配器
    class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagerViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(pagerViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(pagerViews.get(arg1));
            return pagerViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }
}
