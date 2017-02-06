package com.zhjydy_doc.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.data.UserData;
import com.zhjydy_doc.model.entity.NormalDicItem;
import com.zhjydy_doc.presenter.contract.MainTabsContract;
import com.zhjydy_doc.presenter.presenterImp.MainTabsPrensenter;
import com.zhjydy_doc.util.ActivityUtils;
import com.zhjydy_doc.view.fragment.MainExpertFragment;
import com.zhjydy_doc.view.fragment.MainHomeFragment;
import com.zhjydy_doc.view.fragment.MainInfoFragment;
import com.zhjydy_doc.view.fragment.MainMineFragment;
import com.zhjydy_doc.view.fragment.MainOrderFragment;
import com.zhjydy_doc.view.zjview.NoScrollViewPager;
import com.zhjydy_doc.view.zjview.zhToast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainTabsActivity extends BaseActivity implements ViewPager.OnPageChangeListener,MainTabsContract.View {
    @BindView(R.id.main_tabs)
    CommonTabLayout mainTabs;
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;

    private boolean mBackKeyPressed = false;

    public static final int VIEW_FIRST = 0;
    public static final int VIEW_SECOND = 1;
    public static final int VIEW_THIRD = 2;
    public static final int VIEW_FOURTH = 3;
    public static final int VIEW_FIVE = 4;

    private static final int VIEW_SIZE = 5;
    private MainHomeFragment mFirstFragment = null;
    private MainExpertFragment mSecondFragment = null;
    private MainInfoFragment mThirdFragment = null;
    private MainOrderFragment mFourthFragment = null;
    private MainMineFragment mFifthFragment = null;


    private MainTabsContract.Presenter mPresenter;
    private int lastIndex;
    private String[] tabTitles = {"首页", "专家", "资讯", "订单","个人"};
    private int[] mIconUnselectIds = {
            R.mipmap.main_tab_home_off, R.mipmap.main_tab_expert_off, R.mipmap.main_tab_info_off, R.mipmap.main_tab_order_off,R.mipmap.main_tab_mine_off
    };

    private int[] mIconSelectIds = {
            R.mipmap.main_tab_home_on, R.mipmap.main_tab_expert_on, R.mipmap.main_tab_info_on, R.mipmap.main_tab_order_on,R.mipmap.main_tab_mine_on
    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        ButterKnife.bind(this);
        new MainTabsPrensenter(this);
        initView();
    }
    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }

            }, 2000);
        } else {
            super.onBackPressed();
        }

    }

    private void initView() {
        mFirstFragment = MainHomeFragment.instance();
        mSecondFragment = MainExpertFragment.instance();
        mThirdFragment = MainInfoFragment.instance();
        mFourthFragment = MainOrderFragment.instance();
        mFifthFragment = MainMineFragment.instance();
        for (int i = 0; i < VIEW_SIZE; i++)
        {
            mTabEntities.add(new TabEntity(tabTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mainViewpager.setOffscreenPageLimit(VIEW_SIZE);
        mainTabs.setTabData(mTabEntities);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
        mainTabs.setOnTabSelectListener(new OnTabSelectListener()
        {
            @Override
            public void onTabSelect(int position)
            {
                if (position == VIEW_FOURTH || position == VIEW_FIVE) {
                    if (!UserData.getInstance().isLogin()) {
                        ActivityUtils.showLogin(MainTabsActivity.this,false);
                        if (lastIndex == VIEW_FIRST || lastIndex == VIEW_SECOND || lastIndex == VIEW_THIRD) {
                            mainTabs.setCurrentTab(lastIndex);
                        } else {
                            mainTabs.setCurrentTab(VIEW_FIRST);
                        }
                        zhToast.showToast("请先登录");
                        return;
                    }
                }
                lastIndex = position;
                mainViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position)
            {

            }
        });
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        mainViewpager.setCurrentItem(0);
        mainViewpager.setNoScroll(true);
    }


    public void gotoTab(int index) {
        if (index > VIEW_SIZE - 1) {
            return;
        }
        mainTabs.setCurrentTab(index);
        mainViewpager.setCurrentItem(index);
    }
    public void gotoTabCondition(int index, Map<String,NormalDicItem> condition) {
        if (index > VIEW_SIZE - 1) {
            return;
        }
        mainTabs.setCurrentTab(index);
        mainViewpager.setCurrentItem(index);
        if (index == VIEW_SECOND) {
            mSecondFragment.refreshViewWidthCondition( condition);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void updateMsgCount(int count) {
        if (mFirstFragment != null) {
            mFirstFragment.updateUnReadMsgCount(count);
        }
        if (mSecondFragment != null) {
            mSecondFragment.updateUnReadMsgCount(count);
        }
        if (mThirdFragment != null) {
            mThirdFragment.updateUnReadMsgCount(count);
        }
        if (mFourthFragment != null) {
            mFourthFragment.updateUnReadMsgCount(count);
        }
    }

    @Override
    public void refreshOrderList() {
        if (mFourthFragment != null) {
            mFourthFragment.refreshView();
        }
    }

    @Override
    public void refreshNewsCollectList() {
        if (mThirdFragment != null) {
            mThirdFragment.refrehCollectCount();
        }
    }

    @Override
    public void setPresenter(MainTabsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        if (null == mFirstFragment)
                            mFirstFragment = MainHomeFragment.instance();
                        return  mFirstFragment;
                    case VIEW_SECOND:
                        if (null == mSecondFragment)
                            mSecondFragment = MainExpertFragment.instance();
                        return mSecondFragment;

                    case VIEW_THIRD:
                        if (null == mThirdFragment)
                            mThirdFragment = MainInfoFragment.instance();
                        return mThirdFragment;

                    case VIEW_FOURTH:
                        if (null == mFourthFragment)
                            mFourthFragment = MainOrderFragment.instance();
                        return mFourthFragment;
                    case VIEW_FIVE:
                        if (null == mFifthFragment)
                            mFifthFragment = MainMineFragment.instance();
                        return mFifthFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        return "首页";
                    case VIEW_SECOND:
                        return "专家";
                    case VIEW_THIRD:
                        return "资讯";
                    case VIEW_FOURTH:
                        return "订单";
                    case VIEW_FIVE:
                        return "个人";
                    default:
                        break;
                }
            }
            return null;
        }
    }
    class TabEntity implements CustomTabEntity
    {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon)
        {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle()
        {
            return title;
        }

        @Override
        public int getTabSelectedIcon()
        {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon()
        {
            return unSelectedIcon;
        }
    }


}
