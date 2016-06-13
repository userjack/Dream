package com.denny.dream.widget.lock;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.denny.dream.R;

/**
 * Created by Administrator on 2016/6/13.
 */
public class GestureLockViewGroup extends RelativeLayout {
    private int count;
    private int tryTimes;
    public GestureLockViewGroup(Context context) {
        super(context, null);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockViewGroup, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.GestureLockViewGroup_color_finger_on:
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up:
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle:
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle:
                    break;
                case R.styleable.GestureLockViewGroup_count:
                    count = a.getInt(attr,3);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    tryTimes = a.getInt(attr,5);
                    break;

            }
        }
    }
}
