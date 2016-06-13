package com.denny.dream.widget.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by Administrator on 2016/6/13.
 */
public class GestureLockView extends View {
    private static final String TAG = "GestureLockView";

    /**
     * lockview有3种状态
     */
    enum Mode {
        STATUS_NO_FINGER, STATUS_FINGER_ON, STATUS_FINGER_UP;
    }

    private Mode currentMode = Mode.STATUS_NO_FINGER;
    //宽高
    private int mWidth;
    private int mHeight;
    // 半径
    private int mRadius;
    private int mStrokeWidth;

    private int mCenterX;
    private int mCenterY;
    private Paint mPaint;
    /**
     * 箭头（小三角最长边的一半长度 = mArrawRate * mWidth / 2 ）
     */
    private float mArrowRate = 0.333f;
    private int mArrowDegree = -1;
    private Path mArrowPath;

    private float mInnerCircleRadiusRate = 0.333f;

    private int mColorNoFingerInner;
    private int mColorNoFingerOutter;
    private int mColorFingerInner;
    private int mColorFingerOutter;

    public GestureLockView(Context context, int mColorNoFingerInner, int mColorNoFingerOutter, int mColorFingerInner, int mColorFingerOutter) {
        super(context);
        this.mColorNoFingerInner = mColorNoFingerInner;
        this.mColorNoFingerOutter = mColorNoFingerOutter;
        this.mColorFingerInner = mColorFingerInner;
        this.mColorFingerOutter = mColorFingerOutter;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth = (mWidthSize < mHeightSize) ? mWidthSize : mHeightSize;
        mRadius = mCenterX = mCenterY = mWidth / 2;
        mStrokeWidth -= mRadius / 2;

        float mArrowLength = mWidth / 2 * mArrowRate;
        mArrowPath.moveTo(mWidth / 2 ,mStrokeWidth = 2);
        mArrowPath.lineTo(mWidth / 2 - mArrowLength, mStrokeWidth + 2 + mArrowLength);
        mArrowPath.lineTo(mWidth / 2 + mArrowLength, mStrokeWidth + 2 + mArrowLength);
        mArrowPath.close();
        mArrowPath.setFillType(Path.FillType.WINDING);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (currentMode){
            case STATUS_FINGER_ON:
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorFingerInner);
                mPaint.setStrokeWidth(2);
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX,mCenterY,mRadius*mInnerCircleRadiusRate,mPaint);
                break;
            case STATUS_FINGER_UP:
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorFingerOutter);
                mPaint.setStrokeWidth(2);
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);

                drawArrow(canvas);
                break;
            case STATUS_NO_FINGER:
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mColorNoFingerOutter);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);

                mPaint.setColor(mColorNoFingerInner);
                canvas.drawCircle(mCenterX,mCenterY,mRadius*mInnerCircleRadiusRate,mPaint);

                break;
        }
    }

    private void drawArrow(Canvas canvas) {
        if (mArrowDegree != -1){
            mPaint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.rotate(mArrowDegree, mCenterX, mCenterY);
            canvas.drawPath(mArrowPath, mPaint);
            canvas.restore();
        }
    }

    /**
     *
     * @param mode
     */
    public void setMode(Mode mode){
        this.currentMode = mode;
        invalidate();
    }

    public void setArrowDegree(int romateDegree){
        mArrowDegree = romateDegree;
    }
    public int getArrowDegree(){
        return mArrowDegree;
    }
}
