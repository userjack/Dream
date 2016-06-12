package com.denny.dream.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.denny.dream.R;

/**
 * Created by Administrator on 2016/6/6.
 */
public class CustomTitleView_01 extends View {

    private String mTitleText;
    private int mTitleColor;
    private int mTitleSize;

    private Paint mPaint;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;

    public CustomTitleView_01(Context context) {
        super(context, null);
    }

    public CustomTitleView_01(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomTitleView_01(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);

        mTitleText = a.getString(R.styleable.CustomTitleView_titleText);
        mTitleColor = a.getColor(R.styleable.CustomTitleView_titleCircleTextColor, Color.BLACK);
        mTitleSize = a.getDimensionPixelSize(R.styleable.CustomTitleView_titleTextSize, 35);

        a.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleSize);

        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        //测量宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft() + getPaddingRight() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                width = getPaddingLeft() + getPaddingRight() + mBound.width();
                break;
        }

        //测量高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = getPaddingTop() + getPaddingBottom() + mBound.height();
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
