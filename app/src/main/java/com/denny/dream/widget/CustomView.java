package com.denny.dream.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.denny.dream.R;

/**
 * Created by Administrator on 2016/6/12.
 */
public class CustomView extends View {
    private static final String TAG = "CustomView";
    private  int values;
    private  String titleText;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        int n = a.getIndexCount();
        for (int i = 0;i < n;i++){
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomView_titleText){
                titleText = a.getString(a.getIndex(0));
            }else if (attr == R.styleable.CustomView_titleTextSize){
                values = a.getDimensionPixelSize(a.getIndex(1), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            }
        }
        Log.i(TAG, "titleText===" + titleText + "values===" + values);
        a.recycle();
    }


}
