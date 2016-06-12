package com.denny.dream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denny.dream.R;
import com.denny.dream.bean.TitleModal;
import com.denny.dream.utils.ImageManager;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class HeaderTitleAdapter extends RecyclerView.Adapter<HeaderTitleAdapter.TitleViewHolder> {
    public Context mContext;
    private ImageManager manager;
    private List<TitleModal> list;

    public HeaderTitleAdapter(Context context, List<TitleModal> data,ImageManager manager) {
        this.mContext = context;
        this.list = data;
        this.manager = manager;
    }

    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_title_items_layout, parent, false);
        TitleViewHolder holder = new TitleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position) {
        holder.titleName.setText(list.get(position).getName());
        manager.loadUrlCircleImage(list.get(position).getImage(),holder.ivTitleIcon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTitleIcon;
        TextView titleName;
        public TitleViewHolder(View itemView) {
            super(itemView);
            ivTitleIcon = (ImageView) itemView.findViewById(R.id.iv_title_icon);
            titleName = (TextView) itemView.findViewById(R.id.title_name);

        }
    }
}
