package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhjydy_doc.R;
import com.zhjydy_doc.util.ImageUtils;
import com.zhjydy_doc.util.Utils;
import com.zhjydy_doc.util.ViewKey;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/5.
 */
public class ImageHorizontalView extends LinearLayout {

    private ImageView leftImage;
    private ImageView rightImage;
    private HorizontalScrollView mHotizontalScrlView;

    public ImageHorizontalView(Context context) {
        super(context);
        initView();
    }

    public ImageHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.image_horizontal_view, this);
        leftImage = (ImageView) findViewById(R.id.left);
        rightImage = (ImageView) findViewById(R.id.right);
        mHotizontalScrlView = (HorizontalScrollView) findViewById(R.id.sroll_view);
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = mHotizontalScrlView.getScrollX();
                int y = mHotizontalScrlView.getScrollY();
                if (!mHotizontalScrlView.fullScroll(View.FOCUS_LEFT)) {
                    mHotizontalScrlView.scrollTo(x + 10, y);
                }
            }
        });
        rightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = mHotizontalScrlView.getScrollX();
                int y = mHotizontalScrlView.getScrollY();
                if (!mHotizontalScrlView.fullScroll(View.FOCUS_RIGHT)) {
                    mHotizontalScrlView.scrollTo(x - 10, y);
                }
            }
        });
    }

    public void initHorizontalImages(final List<Map<String, Object>> images) {
        mHotizontalScrlView.removeAllViews();
        LinearLayout layout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(HORIZONTAL);
        layout.setPadding(5, 0, 5, 0);
        for (int i = 0 ; i < images.size() ; i ++) {
            Map<String,Object> image = images.get(i);
            int imageType = Utils.toInteger(image.get(ViewKey.FILE_KEY_TYPE));
            String url = Utils.toString(image.get(ViewKey.FILE_KEY_URL));
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int imageHeight = this.getHeight() * 4 / 5;
            int imageWidth = (int) (imageHeight * 1.3);
            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(imageWidth, imageHeight);
            imageView.setLayoutParams(imageParams);
            imageView.setPadding(8, 0, 8, 0);
            imageView.requestLayout();
            if (imageType == ViewKey.TYPE_FILE_PATH) {
                ImageUtils.getInstance().displayFromSDCard(url, imageView);
            } else if (imageType == ViewKey.TYPE_FILE_URL) {
                ImageUtils.getInstance().displayFromRemote(url, imageView);
            }
            imageView.setTag(i);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int tag = Utils.toInteger(v.getTag());
                        if (tag > -1) {
                            mOnItemClickListener.onItemClick(tag,images);
                        }
                    }
                }
            });
            layout.addView(imageView);
        }
        mHotizontalScrlView.addView(layout);
    }

    public onItemClickListener mOnItemClickListener;
    public void setItemClickListener(onItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface  onItemClickListener{
        void onItemClick(int position, List<Map<String, Object>> images);
    }

}
