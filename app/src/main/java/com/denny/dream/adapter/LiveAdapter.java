package com.denny.dream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denny.dream.R;
import com.denny.dream.bean.LiveModal;
import com.denny.dream.utils.ImageManager;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.LiveViewHolder>{
    private static final String TAG = "LiveAdapter";
    private Context context;
    private List<LiveModal.DataBean> dataBeans;
    public LiveAdapter(Context context,List<LiveModal.DataBean> dataBeans){
        this.context = context;
        this.dataBeans = dataBeans;
        Log.i(TAG,"size==="+dataBeans.size());
    }
    @Override
    public LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.live_items_fragment,parent,false);
        LiveViewHolder viewHolder = new LiveViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LiveViewHolder holder, int position) {
        Log.i(TAG, "nick===" + dataBeans.get(position).getNick() + "Avatar===" + dataBeans.get(position).getAvatar() + "size==" + dataBeans.size());
        holder.title.setText(dataBeans.get(position).getNick());
        ImageManager.loadNetImg(context,dataBeans.get(position).getAvatar(),holder.scan);
//        Glide.with(context)
//                .load(dataBeans.get(position).getAvatar())
//                .into(holder.scan);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class LiveViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView scan;
        public LiveViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            scan = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
