package com.denny.dream.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denny.dream.R;
import com.denny.dream.adapter.LiveAdapter;
import com.denny.dream.bean.LiveModal;
import com.denny.dream.data.DataInterface;
import com.denny.dream.data.Utils;
import com.denny.dream.data.WebURL;
import com.denny.dream.decoration.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 直播fragment
 * Created by Administrator on 2016/5/31.
 */
public class LiveFragment extends Fragment {
    private static final String TAG = "LiveFragment";
    private GridLayoutManager gridLayoutManager;
    private List<LiveModal.DataBean> data = new ArrayList<>();
    private LiveAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView bar;
    private AnimationDrawable anim;
    private boolean isLoading = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        bar = (ImageView) view.findViewById(R.id.bar);
        bar.setImageResource(R.drawable.loading_anim);
        anim = (AnimationDrawable) bar.getDrawable();
        anim.start();
        initData();
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity(), false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new LiveAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private void initData() {
        String date_time = Utils.getDataTime();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebURL.BASE_JSON_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataInterface apiService = retrofit.create(DataInterface.class);
        Call<LiveModal> call = apiService.getData("_2",date_time);
        call.enqueue(new Callback<LiveModal>() {
            @Override
            public void onResponse(Response<LiveModal> response, Retrofit retrofit) {
                data.clear();
                data.addAll(response.body().getData());
                Log.i(TAG, "nick===" + data.get(0).getNick() + "Avatar===" + data.get(0).getAvatar() + "size==" + data.size()+response.body().getSize()+"+++"+response.body().getPage()+"===="+response.body().getPageCount());
                if (data.size() > 0) {
                    anim.stop();
                    bar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
