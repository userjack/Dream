package com.denny.dream.custom;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.denny.dream.R;
import com.denny.dream.widget.QQListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/13.
 */
public class QQlistActivity extends Activity {
    @Bind(R.id.ls)
    QQListView ls;
    private List<String> mData;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_listview_layout);
        ButterKnife.bind(this);
        mData = new ArrayList<>(Arrays.asList("nihao","go away","zaijian","shoubula","hold buzhu","gaoshiqin","laiyiba"));
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
        ls.setAdapter(adapter);
        ls.setDelButtonClickListener(new QQListView.DelButtonClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(QQlistActivity.this,"position==="+position,Toast.LENGTH_LONG).show();
                    adapter.remove(adapter.getItem(position));
            }
        });

    }


}
