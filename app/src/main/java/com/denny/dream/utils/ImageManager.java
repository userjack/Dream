package com.denny.dream.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.denny.dream.R;
import com.denny.dream.widget.glide.GlideCircleTransform;
import com.denny.dream.widget.glide.GlideRectTransform;

/**
 * Created by Administrator on 2016/6/1.
 */
public class ImageManager {
    private Context mContext;
    public ImageManager(Context c){
        this.mContext = c;
    }
    public static void loadNetImg(Context context,String netUrl,ImageView iv){
        Glide.with(context)
                .load(netUrl)
                .transform(new GlideRectTransform(context))
                .into(iv);
    }
    // 加载网络图片
    public void loadUrlImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.color.font_black_6)
                .error(R.color.font_black_6)
                .crossFade()
                .into(imageView);
    }
    //加载网络圆形图片
    public void loadUrlCircleImage(String url,ImageView imageView){
        Glide.with(mContext)
                .load(url)
                .placeholder(R.color.font_black_5)
                .error(R.color.font_black_5)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
}
