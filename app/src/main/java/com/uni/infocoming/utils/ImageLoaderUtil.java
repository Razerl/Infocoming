package com.uni.infocoming.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.uni.infocoming.BaseApplication;
import com.uni.infocoming.R;
import com.uni.infocoming.widget.BitmapCache;

/**
 * Created by Razer on 2015/12/30.
 */
public class ImageLoaderUtil {
    /*
   * 通过ImageRequest来显示网络图片
   * */
    public static void setImageRequest(String url, final ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(R.mipmap.timeline_image_failure);
            }
        });
        BaseApplication.getRequestQueue().add(imageRequest);
    }

    /*
    * 通过ImageLoader来显示网络图片
    * */
    public static void setImageLoader(String url, ImageView imageView) {
        ImageLoader loader = new ImageLoader(BaseApplication.getRequestQueue(), new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.timeline_image_loading, R.mipmap.timeline_image_failure);
        loader.get(url, imageListener);
    }

    /*
    * 通过Volley的NetWorkImageView来显示网络图片
    * */
    public static void setNetWorkImageView(String url, NetworkImageView netWorkImageView) {
        ImageLoader loader = new ImageLoader(BaseApplication.getRequestQueue(), new BitmapCache());

        netWorkImageView.setDefaultImageResId(R.mipmap.timeline_image_loading);
        netWorkImageView.setErrorImageResId(R.mipmap.timeline_image_failure);
        netWorkImageView.setImageUrl(url, loader);
    }
}
