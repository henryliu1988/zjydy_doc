package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
public class BannerLayout extends FrameLayout {

    private WrapContentHeightViewPager mViewPager;
    private LinearLayout mGuidLayout;

    private List<View> pageViews = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    private PagerAdapter mAdapter;
    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BannerLayout(Context context) {
        super(context);
        initView();
    }

    public void initView(){
        inflate(getContext(), R.layout.zh_banner_layout, this);
        mViewPager = (WrapContentHeightViewPager)findViewById(R.id.guidePages);
        mGuidLayout = (LinearLayout)findViewById(R.id.guideLayout);
        mAdapter = new GuidePageAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new GuidePageChangeListener());

    }

    public void initPageViews(List<View> pageViews) {
        this.pageViews = pageViews;
        if (pageViews.size() < 1) {
            return;
        }
        if (pageViews.size() < 2) {
            mGuidLayout.setVisibility(View.GONE);
        }
        for (int i = 0; i < pageViews.size(); i++) {
            ImageView  imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setPadding(ScreenUtils.getScreenWidth()/80, 0, ScreenUtils.getScreenWidth()/80, 0);
            imageViews.add(imageView);

            if (i == 0) {
                // 默认选中第一张图片
                imageViews.get(i).setImageResource(R.mipmap.page_indicator_focused);
            } else {
                imageViews.get(i).setImageResource(R.mipmap.page_indicator);
            }
            mGuidLayout.addView(imageViews.get(i));
        }

        mAdapter.notifyDataSetChanged();
    }
    // 指引页面数据适配器
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
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
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
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

    // 指引页面更改事件监听器
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.size(); i++) {
                imageViews.get(arg0)
                        .setImageResource(R.mipmap.page_indicator_focused);
                if (arg0 != i) {
                    imageViews.get(i)
                            .setImageResource(R.mipmap.page_indicator);
                }
            }
        }
    }

}
