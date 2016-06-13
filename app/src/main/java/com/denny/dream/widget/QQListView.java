package com.denny.dream.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.denny.dream.R;

/**
 * Created by Administrator on 2016/6/13.
 */
public class QQListView extends ListView {
    private static final String TAG = "QQListView";
    private int touchSlop;
    private LayoutInflater inflater;
    private Button del_btn;
    private PopupWindow pw;
    private int pwWidth;
    private int pwHeight;

    /**
     * 手指按下的X坐标,Y坐标
     */
    private int downX;
    private int downY;

    private int currenmtPoint;
    private View currentView;
    /**
     * 滑动的X，Y
     */
    private int moveX;
    private int moveY;
    private boolean isSliding;
    private DelButtonClickListener mListener;

    public QQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View view = inflater.inflate(R.layout.delete_btn, null);
        del_btn = (Button) view.findViewById(R.id.btn_del);
        pw = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        pw.getContentView().measure(0, 0);
        pwWidth = pw.getContentView().getMeasuredWidth();
        pwHeight = pw.getContentView().getMeasuredHeight();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                if (pw.isShowing()) {
                    dismissPopWindow();
                    return false;
                }
                //获得当前按下的item
                currenmtPoint = pointToPosition(downX, downY);
                Log.i(TAG,"cur=="+currenmtPoint+"downX=="+downX+"downY=="+downY);
                View view = getChildAt(currenmtPoint - getFirstVisiblePosition());
                currentView = view;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = x;
                moveY = y;
                int dx = moveX - downX;
                int dy = moveY - downY;
                Log.i(TAG,"cur=="+currenmtPoint+"moveY=="+moveY+"moveX=="+moveX+"dx=="+dx+"dy==="+dy);
                if (downX > moveX && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSliding = true;
                }
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (isSliding) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    currentView.getLocationOnScreen(location);
                    pw.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
                    pw.update();
                    int x = location[0] ;
                    int y = location[1] ;
                    pw.showAtLocation(currentView, Gravity.LEFT | Gravity.TOP, location[0] + currentView.getWidth(), location[1] + currentView.getHeight() / 2 - pwHeight / 2);
                    Log.i(TAG, "cur==" + currenmtPoint + "locX==" + x + "locY==" + y);
                    del_btn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.onClick(currenmtPoint);
                                pw.dismiss();
                            }
                        }
                    });
                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void dismissPopWindow() {
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
        }
    }

    public void setDelButtonClickListener(DelButtonClickListener listener) {
        mListener = listener;
    }

    public interface DelButtonClickListener {
         void onClick(int position);
    }
}
