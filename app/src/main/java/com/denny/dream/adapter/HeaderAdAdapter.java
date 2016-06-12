package com.denny.dream.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class HeaderAdAdapter extends PagerAdapter {
    private Context mContext;
    private List<ImageView> mList;
    int count;

    public HeaderAdAdapter(Context context, List<ImageView> list){
        this.mContext = context;
        this.mList = list;
        if (this.mList != null && this.mList.size() > 0){
            count = list.size();
        }
    }
    @Override
    public int getCount() {
        if (count ==1){
            return 1;
        }else{
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % count;
        // 先移除在添加，更新图片在container中的位置（把iv放至container末尾）
        ImageView imageView = mList.get(newPosition);
        container.removeView(imageView);
        container.addView(imageView);
        return imageView;
    }
}
