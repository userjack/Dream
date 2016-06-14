package com.denny.dream.widget.lock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.denny.dream.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/14.
 */
public class GestureLockViewActivity extends Activity {
    @Bind(R.id.lock_container)
    GestureLockViewGroup lockContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_layout);
        ButterKnife.bind(this);
        lockContainer.setAnswer(new int[]{1,2,3,4,5});
        lockContainer.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {

            }

            @Override
            public void onGestureEvent(boolean matched) {
                Toast.makeText(GestureLockViewActivity.this,matched + "",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUnmatchedExceedBoundary() {
                Toast.makeText(GestureLockViewActivity.this, "error 5",Toast.LENGTH_LONG).show();
                lockContainer.setUnMatchExceedBoundary(5);
            }
        });
    }
}
