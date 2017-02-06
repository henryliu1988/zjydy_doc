package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhjydy_doc.R;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class ScoreView extends LinearLayout {

    private Context mContext;

    private int starWidth = 15;
    private int starheight = 15;

    public void setStartSize(int width, int height) {
        this.starWidth = width;
        this.starheight = height;
    }

    public ScoreView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.setOrientation(HORIZONTAL);
    }

    private ImageView getStartImage() {
        ImageView star = new ImageView(getContext());
        star.setScaleType(ImageView.ScaleType.FIT_CENTER);
        star.setPadding(5, 0, 0, 0);
        star.setImageResource(R.mipmap.star);
        return star;
    }

    public void setScore(int score, int all) {
        this.removeAllViews();
        float startScore = all / 5;
        if (score > all) {
            score = all;
        }
        if (score < 0) {
            score = 0;
        }
        int starCount = (int) (score / startScore);
        if (starCount < 1) {
            starCount = 1;
        }
        for (int i = 0; i < starCount; i++) {
            ImageView starImage = getStartImage();
            this.addView(starImage);
        }
    }

    public void setScore(int score) {
        setScore(score, 100);
    }

}
