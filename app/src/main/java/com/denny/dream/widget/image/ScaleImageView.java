package com.denny.dream.widget.image;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ScaleImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ScaleImageView.class.getName();
    public static final float SCALE_MAX = 4.0f;
    public static final float SCALE_MID = 2.0f;
    private float initScale = 0.5f;
    private float[] matrixValues = new float[9];
    private boolean once = true;
    //缩放手势
    private ScaleGestureDetector scaleGestureDetector = null;
    private GestureDetector gestureDetector = null;

    private Matrix matrix = new Matrix();
    private int lastPointerCount = 0;
    private boolean isCanDrag;
    private float lastX;
    private float lastY;
    private int mTouchSlop;
    private boolean isCheckLeftAndRight = true;
    private boolean isCheckTopAndBottom = true;
    private boolean isAtuoScale;

    public ScaleImageView(Context context) {
        super(context, null);

    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAtuoScale) {
                    return true;
                }
                float x = e.getX();
                float y = e.getY();
                Log.i(TAG, getScale() + "initScale===" + initScale);
                if (getScale() < SCALE_MID) {
                    ScaleImageView.this.postDelayed(new AutoScaleRunnable(SCALE_MID, x, y), 16);
                } else if (getScale() >= SCALE_MID && getScale() < SCALE_MAX) {
                    ScaleImageView.this.postDelayed(new AutoScaleRunnable(SCALE_MAX, x, y), 16);
                } else {
                    ScaleImageView.this.postDelayed(new AutoScaleRunnable(initScale, x, y), 16);
                }
                return true;
            }
        });
        this.setOnTouchListener(this);
    }

    private class AutoScaleRunnable implements Runnable {
        private static final float BIGGER = 1.07f;
        private static final float SMALLER = 0.93f;
        private float targetScale;
        private float tmpScale;
        private float x;
        private float y;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < this.targetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }
        }

        @Override
        public void run() {
            matrix.postScale(tmpScale, tmpScale, x, y);
            checkBounderAndCenterWhenScale();
            setImageMatrix(matrix);

            final float currentScale = getScale();
            //值在有效范围，继续缩放
            if ((tmpScale > 1.0f && currentScale < targetScale) || (tmpScale < 1.0f && targetScale < currentScale)) {
                ScaleImageView.this.postDelayed(this, 16);
            } else {
                final float scaleTarget = targetScale / currentScale;
                matrix.postScale(scaleTarget, scaleTarget, x, y);
                checkBounderAndCenterWhenScale();
                setImageMatrix(matrix);
                isAtuoScale = false;
            }

        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null) {
            return true;
        }
        //缩放的范围控制
        if ((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)) {
            /**
             * 最大最小值判断
             */
            Log.i(TAG, "scale==" + scale + "scaleFactor===" + scaleFactor + "scalemax===" + SCALE_MAX + "initScale===" + scaleFactor);
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBounderAndCenterWhenScale();
            setImageMatrix(matrix);

        }
        return true;
    }

    private void checkBounderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float delaX = 0;
        float delaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                delaX = -rectF.left;
            }
            if (rectF.right < width) {
                delaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                delaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                delaY = height - rectF.bottom;
            }
        }

        //如果小于屏幕宽度，就居中
        if (rectF.width() < width) {
            delaX = width * 0.5f - rectF.right * 0.5f + rectF.width() * 0.5f;
        }
        if (rectF.height() < height) {
            delaY = height * 0.5f - rectF.bottom * 0.5f + rectF.height() * 0.5f;
        }
        matrix.postTranslate(delaX, delaY);


    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix scaleMatrix = matrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            scaleMatrix.mapRect(rect);
        }
        return rect;
    }

    private float getScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        float x = 0, y = 0;
        final int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            lastX = x;
            lastY = y;
        }
        lastPointerCount = pointerCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - lastX;
                float dy = y - lastY;
                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        matrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(matrix);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;

        }
        return true;
    }

    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        matrix.postTranslate(deltaX, deltaY);
    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;
            Log.i(TAG, "width===" + d.getIntrinsicWidth() + "height===" + d.getIntrinsicHeight());
            int width = getWidth();
            int height = getHeight();
            //图片的宽高

            int imgWidth = d.getIntrinsicWidth();
            int imgHeight = d.getIntrinsicHeight();

            float scale = 1.0f;
            if (imgWidth > width && imgHeight <= height) {
                scale = width * 1.0f / imgWidth;
            }
            if (imgHeight > height && imgWidth <= width) {
                scale = height * 1.0f / imgHeight;
            }
            if (imgWidth > width && imgHeight > height) {
                scale = Math.min(imgWidth * 1.0f / width, imgHeight * 1.0f / height);
            }
            initScale = scale;
            matrix.postTranslate((width - imgWidth) / 2, (height - imgHeight) / 2);
            matrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(matrix);
            once = false;

        }
    }
}
