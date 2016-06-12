package com.denny.dream.widget.header;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.denny.dream.R;
import com.denny.dream.adapter.HeaderAdAdapter;
import com.denny.dream.bean.TitleModal;
import com.denny.dream.utils.DensityUtil;
import com.denny.dream.utils.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/2.
 */
public class HeaderADView extends HeaderViewInterface<List<TitleModal>> {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.ad_titles)
    TextView adTitles;
    @Bind(R.id.ads_container)
    LinearLayout adsContainer;
    private Thread thread;
    List<ImageView> mList;
    ImageManager manager;
    private boolean isStop = false;
    private static final int TYPE_CHANGE_AD = 0;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TYPE_CHANGE_AD:
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        }
    };

    public HeaderADView(Activity context) {
        super(context);
        mList = new ArrayList<>();
        manager = new ImageManager(context);
    }

    @Override
    protected void getView(List<TitleModal> mobileIndexBeans, ListView listView) {
        View view = mInflate.inflate(R.layout.recommand_adview_layout, listView, false);
        ButterKnife.bind(this, view);

        dealWithTheView(mobileIndexBeans);
        listView.addHeaderView(view);

    }

    private void dealWithTheView(List<TitleModal> mobileIndexBeans) {
        mList.clear();
        Log.i("","size==="+mobileIndexBeans.size());
        for (int i = 0; i < mobileIndexBeans.size(); i++) {
            mList.add(creatImageView(mobileIndexBeans.get(i).getThumb()));
        }
        HeaderAdAdapter adAdapter = new HeaderAdAdapter(mContext, mList);
        viewpager.setAdapter(adAdapter);
        addIndicatorImageViews(mList.size());
        setViewPagerChangeListener(mList.size());
        startADRotate();
    }

    private void startADRotate() {
        if (mList == null && mList.size() == 0) {
            return;
        }
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!isStop) {
                        SystemClock.sleep(3000);
                        mhandler.sendEmptyMessage(TYPE_CHANGE_AD);
                    }
                }
            });
            thread.start();
        }
    }

    public void stopADRotate(){
        if (mhandler != null && mhandler.hasMessages(TYPE_CHANGE_AD)){
            mhandler.removeMessages(TYPE_CHANGE_AD);
        }
    }

    private void setViewPagerChangeListener(final int size) {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mList != null && mList.size() > 0) {
                    int newPosition = position % size;
                    for (int i = 0; i < mList.size(); i++) {
                        adsContainer.getChildAt(i).setEnabled(false);
                        if (i == newPosition) {
                            adsContainer.getChildAt(i).setEnabled(true);
                        }
                    }

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addIndicatorImageViews(int size) {
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 5));
            if (i != 0) {
                params.leftMargin = DensityUtil.dip2px(mContext, 7);
            }
            iv.setLayoutParams(params);
            iv.setBackgroundResource(R.drawable.xml_round_orange_grey_sel);
            iv.setEnabled(false);
            if (i == 0) {
                iv.setEnabled(true);
            }
            adsContainer.addView(iv);
        }
    }

    private ImageView creatImageView(String bean) {
            ImageView imageView = new ImageView(mContext);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            manager.loadUrlImage(bean, imageView);
            return imageView;


    }


}
