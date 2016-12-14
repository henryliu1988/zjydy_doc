package com.zhjydy_doc.view.zjview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zhjydy_doc.R;

/**
 * Created by Administrator on 2016/12/14 0014.
 */
public class ConerTextView extends TextView {

    private int mBgColor = 0;
    private int mCornerSize = 0;
    private int mPressColor = 0;
    private boolean isPress = false;
    public ConerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        this.setClickable(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(!isPress) {
            init();
        }
        super.onDraw(canvas);
    }

    private void init() {
        setBackgroundRounded(this.getMeasuredWidth(), this.getMeasuredHeight(), this,mBgColor);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Cornertextview);
        mBgColor = ta.getColor(R.styleable.Cornertextview_normal_color, Color.BLUE);
        mCornerSize = ta.getInt(R.styleable.Cornertextview_corner_size, 0);
        mPressColor = ta.getColor(R.styleable.Cornertextview_press_color,mBgColor);
        ta.recycle();
    }

    public void setBackgroundRounded(int w, int h, View v, int color)
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        double dH = (metrics.heightPixels / 100) * 1.5;
        int iHeight = (int)dH;
        iHeight = mCornerSize;
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(color);
        RectF rec = new RectF(0, 0, w, h);
        c.drawRoundRect(rec, iHeight, iHeight, paint);
        v.setBackgroundDrawable(new BitmapDrawable(getResources(), bmp));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isPress = true;
                setBackgroundRounded(this.getMeasuredWidth(), this.getMeasuredHeight(), this, mPressColor);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPress = false;
                init();
                break;
        }
        return super.onTouchEvent(event);
    }

}
