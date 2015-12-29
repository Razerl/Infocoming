package com.uni.infocoming.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.uni.infocoming.R;
import com.uni.infocoming.widget.BitmapCache;

import java.util.ArrayList;

/**
 * Created by Razer on 2015/12/29.
 */
public class StatusGridImgsAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> datas;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public StatusGridImgsAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
        RequestQueue queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue,new BitmapCache());
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_grid_image, null);
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridView gv = (GridView) parent;
        int horizontalSpacing = gv.getHorizontalSpacing();
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns-1) * horizontalSpacing
                - gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
        holder.iv_image.setLayoutParams(params);

        String picUrl = getItem(position);
        ImageLoader.ImageListener listener = imageLoader.getImageListener(holder.iv_image,R.mipmap.timeline_image_loading,
                R.mipmap.timeline_image_failure);
        imageLoader.get(picUrl,listener);
        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_image;
    }
}
