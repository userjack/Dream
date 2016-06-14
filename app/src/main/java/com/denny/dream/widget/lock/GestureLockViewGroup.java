package com.denny.dream.widget.lock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.denny.dream.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class GestureLockViewGroup extends RelativeLayout {
    private int count;
    private int tryTimes;
    private int colorFingerOn = 0xFF378FC9;
    private int colorFingerUp = 0xFFFF0000;
    private int colorNofingerOn = 0xFF939090;
    private int colorNofingerup = 0xFFE0DBDB;
    private Paint mPaint;
    private Path mPath;

    private GestureLockView[] gestureLockViews;

    private int mWidth;
    private int mHeight;
    private int mGestureLockViewWidth;
    private int mMarginBetweenLockView;
    private static final String TAG = "GestureLockViewGroup";
    /**
     * 保存用户选中的GestureLockView的id
     */
    private List<Integer> mChoose = new ArrayList<Integer>();
    private OnGestureLockViewListener gestureLockViewListener;
    /**
     * 指引下的结束位置
     */
    private Point mTmpTarget = new Point();
    private int mLastPathX;
    private int mLastPathY;
    /**
     * 存储答案
     */
    private int[] mAnswer = { 0, 1, 2, 5, 8 };

    public GestureLockViewGroup(Context context) {
        super(context, null);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockViewGroup);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.GestureLockViewGroup_color_finger_on:
                    colorFingerOn = a.getColor(attr, colorFingerOn);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up:
                    colorFingerUp = a.getColor(attr, colorFingerUp);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle:
                    colorNofingerOn = a.getColor(attr, colorNofingerOn);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle:
                    colorNofingerup = a.getColor(attr, colorNofingerup);
                    break;
                case R.styleable.GestureLockViewGroup_count:
                    count = a.getInt(attr, 3);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    tryTimes = a.getInt(attr, 5);
                    break;

            }
        }
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Log.i(TAG,"GestureLockViewGroup");
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mPath = new Path();
    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG,"onMeasure");
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = mHeight = (mWidth < mHeight) ? mWidth : mHeight;
        if (gestureLockViews == null) {
            gestureLockViews = new GestureLockView[count * count];
            mGestureLockViewWidth = (int) (4 * mWidth * 1.0f / 5 * count + 1);
            mMarginBetweenLockView = (int) (mGestureLockViewWidth * 0.025);

            mPaint.setStrokeWidth(mGestureLockViewWidth * 0.25f);

            for (int i = 0; i < gestureLockViews.length; i++) {
                gestureLockViews[i] = new GestureLockView(getContext(), colorNofingerOn, colorNofingerup, colorFingerOn, colorFingerUp);
                gestureLockViews[i].setId(i + 1);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mGestureLockViewWidth / 15, mGestureLockViewWidth / 15);
                if (i % count != 0) {
                    params.addRule(RelativeLayout.RIGHT_OF, gestureLockViews[i - 1].getId());
                }
                if (i > count - 1) {
                    params.addRule(RelativeLayout.BELOW, gestureLockViews[i - count].getId());
                }
                int rightMargin = mMarginBetweenLockView ;
                int bottomMargin = mMarginBetweenLockView;
                int leftMargin = 0;
                int topMargin = 0;

                if (i >= 0 && i < count) {
                    topMargin = mMarginBetweenLockView;
                }
                if (i % count == 0) {
                    leftMargin = mMarginBetweenLockView;
                }
                params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                gestureLockViews[i].setMode(GestureLockView.Mode.STATUS_NO_FINGER);
                addView(gestureLockViews[i], params);
            }
            Log.i(TAG, "mWidth ==" + mWidth + "mGestureLockViewWidth==" + mGestureLockViewWidth + "mMarginBetweenLockView==" + mMarginBetweenLockView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent");
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //重置
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                mPaint.setColor(colorFingerOn);
                mPaint.setAlpha(50);
                GestureLockView childView = getchildIdPos(x, y);
                if (childView != null){
                    int cId = childView.getId();
                    if (!mChoose.contains(cId)){
                        mChoose.add(cId);
                        childView.setMode(GestureLockView.Mode.STATUS_FINGER_ON);
                        if (gestureLockViewListener != null){
                            gestureLockViewListener.onBlockSelected(cId);
                        }
                        mLastPathX = childView.getLeft() / 2 + childView.getRight() / 2;
                        mLastPathY = childView.getTop() / 2 + childView.getBottom() / 2;

                        if (mChoose.size() == 1){
                            mPath.moveTo(mLastPathX, mLastPathY);
                        }else {
                            mPath.lineTo(mLastPathX, mLastPathY);
                        }

                    }
                }
                mTmpTarget.x = mLastPathX;
                mTmpTarget.y = mLastPathY;
                break;
            case MotionEvent.ACTION_UP:
                mPaint.setColor(colorFingerUp);
                mPaint.setAlpha(50);
                this.tryTimes--;
                if (gestureLockViewListener != null && mChoose.size() > 0 ){
                    gestureLockViewListener.onGestureEvent(chechAnswer());
                    if (tryTimes == 0){
                        gestureLockViewListener.onUnmatchedExceedBoundary();
                    }
                }
                mTmpTarget.x = mLastPathX;
                mTmpTarget.y = mLastPathY;
                changeUpStatus();
                // 计算每个元素中箭头需要旋转的角度
                for(int i = 0;i < mChoose.size() - 1;i++){
                    int  childId = mChoose.get(i);
                    int childNextId = mChoose.get(i + 1);
                    GestureLockView startView = (GestureLockView) findViewById(childId);
                    GestureLockView nextView = (GestureLockView) findViewById(childNextId);

                    int dx = nextView.getLeft() - startView.getLeft();
                    int dy = nextView.getTop() - startView.getTop();

                    int angle = (int) (Math.toDegrees(Math.atan2(dy,dx))+90);
                    startView.setArrowDegree(angle);
                }
                break;
        }
        invalidate();
        return true;
    }

    private void changeUpStatus() {
        for (GestureLockView lockView : gestureLockViews){
            if (mChoose.contains(lockView.getId())){
                lockView.setMode(GestureLockView.Mode.STATUS_FINGER_UP);
            }
        }

    }

    /**
     * 检测用户手势密码是否正确
     * @return
     */
    private boolean chechAnswer() {
        if (mAnswer.length != mChoose.size())
            return false;

        for (int i = 0; i < mAnswer.length; i++)
        {
            if (mAnswer[i] != mChoose.get(i))
                return false;
        }

        return true;
    }

    private GestureLockView getchildIdPos(int x, int y) {
        for (GestureLockView lockView : gestureLockViews) {
            if (checkLockView(lockView, x, y)) {
                return lockView;
            }
        }
        return null;
    }

    private boolean checkLockView(GestureLockView lockView, int x, int y) {
        int padding = (int) (mGestureLockViewWidth * 0.15);
        if (x >= lockView.getLeft() + padding &&
                x <= lockView.getRight() - padding &&
                y >= lockView.getTop() + padding &&
                y <= lockView.getBottom() - padding) {
            return true;
        }
        return false;
    }


    private void reset() {
        mChoose.clear();
        mPath.reset();
        for (GestureLockView lockView : gestureLockViews) {
            lockView.setMode(GestureLockView.Mode.STATUS_NO_FINGER);
            lockView.setArrowDegree(-1);
        }
    }

    /**
     * 对外公布设置答案的方法
     *
     * @param answer
     */
    public void setAnswer(int[] answer)
    {
        this.mAnswer = answer;
    }

    /**
     * 设置最大实验次数
     *
     * @param boundary
     */
    public void setUnMatchExceedBoundary(int boundary)
    {
        this.tryTimes = boundary;
    }

    public void setOnGestureLockViewListener(OnGestureLockViewListener listener){
        this.gestureLockViewListener = listener;
    }

    @Override
    public void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        //绘制GestureLockView间的连线
        if (mPath != null)
        {
            canvas.drawPath(mPath, mPaint);
        }
        //绘制指引线
        if (mChoose.size() > 0)
        {
            if (mLastPathX != 0 && mLastPathY != 0)
                canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,
                        mTmpTarget.y, mPaint);
        }

    }

    public interface OnGestureLockViewListener
    {
        /**
         * 单独选中元素的Id
         *
         * @param cId
         */
        public void onBlockSelected(int cId);

        /**
         * 是否匹配
         *
         * @param matched
         */
        public void onGestureEvent(boolean matched);

        /**
         * 超过尝试次数
         */
        public void onUnmatchedExceedBoundary();
    }
}
