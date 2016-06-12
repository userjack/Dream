package com.denny.dream.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.denny.dream.bean.TitleModal;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class RecommandAdapater extends BaseAdapter {
    private Context mContext;
    private List<TitleModal> mList;
    public RecommandAdapater(Context context,List<TitleModal> list){
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
