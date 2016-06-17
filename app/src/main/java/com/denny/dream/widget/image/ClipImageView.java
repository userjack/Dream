package com.denny.dream.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2016/6/17.
 */
public class ClipImageView extends View {
    //view水平方向的边距
    private int mHorizontalPadding = 20;
    //view垂直方向的边距
    private int mVerticalPadding;
    private Paint mPaint;
    //绘制view的宽度
    private int mWidth;
    //边框的颜色
    private int mBorderColor = Color.parseColor("#ffffff");
    private int mBorderWidth = 1;
    public ClipImageView(Context context){
        super(context,null);
    }
    public ClipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mHorizontalPadding, getResources().getDisplayMetrics());
        mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mBorderWidth,getResources().getDisplayMetrics());

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth() - 2 * mHorizontalPadding;
        mVerticalPadding = (getHeight() - mWidth) / 2;
        mPaint.setColor(Color.parseColor("#aa000000"));
        mPaint.setStyle(Paint.Style.FILL);
        //画左边第一个
        canvas.drawRect(0,0,mHorizontalPadding,getHeight(),mPaint);
        //画右边第二个
        canvas.drawRect(getWidth() - mHorizontalPadding,0,getWidth(),getHeight(),mPaint);
        //画上面第一个
        canvas.drawRect(mHorizontalPadding,0,getWidth() - mHorizontalPadding,mVerticalPadding,mPaint);
        //画下面第二个
        canvas.drawRect(mHorizontalPadding,getHeight() - mVerticalPadding,getWidth() - mHorizontalPadding,getHeight(),mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        canvas.drawRect(mHorizontalPadding,mVerticalPadding,getWidth() - mHorizontalPadding,getHeight() - mVerticalPadding,mPaint);

    }
}
