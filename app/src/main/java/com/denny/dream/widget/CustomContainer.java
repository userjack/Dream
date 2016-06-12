package com.denny.dream.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/6/12.
 */
public class CustomContainer extends ViewGroup {
    public CustomContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //测量所有的childView
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        /**
         * 记录wrap_content的宽高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;

        MarginLayoutParams cParams = null;
        //计算左右两个view的高度，取它们的最大值
        int lHeight = 0;
        int rHeight = 0;

        //计算上下两个view的宽度，取它们的最大值
        int tWidth = 0;
        int bWidth = 0;

        for (int i = 0;i < cCount;i++){
            View child = getChildAt(i);
            cWidth = child.getMeasuredWidth();
            cHeight = child.getMeasuredHeight();
            cParams = (MarginLayoutParams) child.getLayoutParams();

            if (i == 0 || i == 1){
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 2 || i == 3){
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 0 || i == 2){
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
            if (i == 1 || i == 3){
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }
        width = Math.max(tWidth,bWidth);
        height = Math.max(lHeight,rHeight);
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width,(modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for (int i = 0; i < cCount;i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0,ct = 0,cr = 0,cb = 0;
            switch (i){
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.rightMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cParams.bottomMargin - cHeight;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cParams.bottomMargin - cHeight;
                    break;
            }
            cr = cl + cWidth;
            cb = ct + cHeight;
            childView.layout(cl,ct,cr,cb);
        }

    }


    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet p) {
        return new MarginLayoutParams(getContext(),p);
    }
}
