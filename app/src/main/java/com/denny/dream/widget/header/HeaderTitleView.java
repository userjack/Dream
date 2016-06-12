package com.denny.dream.widget.header;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.denny.dream.R;
import com.denny.dream.adapter.HeaderTitleAdapter;
import com.denny.dream.bean.TitleModal;
import com.denny.dream.utils.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/2.
 */
public class HeaderTitleView extends HeaderViewInterface<List<TitleModal>> {
    List<String> list;
    ImageManager manager;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    HeaderTitleAdapter adapter;

    public HeaderTitleView(Activity context) {
        super(context);
        list = new ArrayList<>();
        manager = new ImageManager(mContext);
    }

    @Override
    protected void getView(List<TitleModal> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_titel_layout, listView, false);
        ButterKnife.bind(this, view);

        dealWithThisView(list);
        listView.addHeaderView(view);

    }

    private void dealWithThisView(List<TitleModal> list) {
        adapter = new HeaderTitleAdapter(mContext,list,manager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}
