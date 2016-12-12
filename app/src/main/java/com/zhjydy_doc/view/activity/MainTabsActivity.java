package com.zhjydy_doc.view.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.zhjydy_doc.R;
import com.zhjydy_doc.presenter.contract.MainTabsContract;
import com.zhjydy_doc.presenter.presenterImp.MainTabsPrensenter;
import com.zhjydy_doc.view.fragment.MainExpertFragment;
import com.zhjydy_doc.view.fragment.MainHomeFragment;
import com.zhjydy_doc.view.fragment.MainInfoFragment;
import com.zhjydy_doc.view.fragment.MainMineFragment;
import com.zhjydy_doc.view.fragment.MainOrderFragment;
import com.zhjydy_doc.view.zjview.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainTabsActivity extends BaseActivity implements ViewPager.OnPageChangeListener,MainTabsContract.View {

    @BindView(R.id.main_tabs)
    AdvancedPagerSlidingTabStrip mainTabs;
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        ButterKnife.bind(this);
        new MainTabsPrensenter(this);
        initView();
    }

    private void initView() {
        mainViewpager.setOffscreenPageLimit(VIEW_SIZE);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
        mainTabs.setViewPager(mainViewpager);
        mainTabs.setOnPageChangeListener(this);
        mainViewpager.setCurrentItem(VIEW_FIRST);

    }


    public void gotoTab(int index) {
        if (index > VIEW_SIZE - 1) {
            return;
        }
        mainViewpager.setCurrentItem(index);
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
    public void refreshOrderList()
    {
        if (mFourthFragment != null) {
            mFourthFragment.refreshView();
        }
    }

    @Override
    public void setPresenter(MainTabsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return null;
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter implements AdvancedPagerSlidingTabStrip.IconTabProvider {

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
                        return mFirstFragment;

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

        @Override
        public Integer getPageIcon(int index) {
            if (index >= 0 && index < VIEW_SIZE) {
                switch (index) {
                    case VIEW_FIRST:
                        return R.mipmap.main_tab_home_off;
                    case VIEW_SECOND:
                        return R.mipmap.main_tab_expert_off;
                    case VIEW_THIRD:
                        return R.mipmap.main_tab_info_off;
                    case VIEW_FOURTH:
                        return R.mipmap.main_tab_order_off;
                    case VIEW_FIVE:
                        return R.mipmap.main_tab_mine_off;

                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Integer getPageSelectIcon(int index) {
            if (index >= 0 && index < VIEW_SIZE) {
                switch (index) {
                    case VIEW_FIRST:
                        return R.mipmap.main_tab_home_on;
                    case VIEW_SECOND:
                        return R.mipmap.main_tab_expert_on;
                    case VIEW_THIRD:
                        return R.mipmap.main_tab_info_on;
                    case VIEW_FOURTH:
                        return R.mipmap.main_tab_order_on;
                    case VIEW_FIVE:
                        return R.mipmap.main_tab_mine_on;

                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Rect getPageIconBounds(int position) {
            return null;
        }
    }

}
