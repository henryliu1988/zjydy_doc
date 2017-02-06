package com.zhjydy_doc.view.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.zhjydy_doc.R;
import com.zhjydy_doc.model.entity.IntentKey;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/18 0018.
 */
public class ImageBrowsActivity extends  BaseActivity {
    private ViewPager mPager;

    private int mPosition;
    private List<Map<String,Object>> mImageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browse);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDatas();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 10));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(ImageBrowsActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Map<String,Object> imageItem = mImageList.get(position);
                int type = Utils.toInteger(imageItem.get(ViewKey.FILE_KEY_TYPE));
                String path = Utils.toString(imageItem.get(ViewKey.FILE_KEY_URL));
                if (type == ViewKey.TYPE_FILE_PATH) {
                    ImageUtils.getInstance().displayFromSDCard(path,view);
                } else if (type == ViewKey.TYPE_FILE_URL) {
                    ImageUtils.getInstance().displayFromRemote(path,view);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mPager.setCurrentItem(mPosition);
    }

    private void initDatas() {
        Bundle bundle = getIntent().getBundleExtra(IntentKey.INTENT_BUNDLE);
        if (bundle == null) {
            finish();
            return;
        }
        int position = bundle.getInt(IntentKey.IMAGE_POS,0);
        String listStr = bundle.getString(IntentKey.IMAGE_LIST,"");
        List<Map<String,Object>> imageList = Utils.parseObjectToListMapString(listStr);
        if (imageList.size() < 1) {
            finish();
            return;
        }
        mPosition = position;
        mImageList = imageList;
    }
}
