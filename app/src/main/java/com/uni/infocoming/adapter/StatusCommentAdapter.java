package com.uni.infocoming.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.uni.infocoming.R;
import com.uni.infocoming.entity.Comment;
import com.uni.infocoming.entity.User;
import com.uni.infocoming.widget.BitmapCache;

import java.util.ArrayList;


/**
 * Created by Razer on 2015/12/29.
 */
public class StatusCommentAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Comment> datas;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public StatusCommentAdapter(Context context, ArrayList<Comment> datas) {
        this.context = context;
        this.datas = datas;
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue,new BitmapCache());
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Comment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_comment_status,null);
            holder.iv_avatar_comment = (ImageView) convertView.findViewById(R.id.iv_avatar_comment);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Comment comment = getItem(position);
        final User user = comment.getUser();

        ImageLoader.ImageListener listener = imageLoader.getImageListener(holder.iv_avatar_comment,
                R.mipmap.timeline_image_loading,R.mipmap.timeline_image_failure);
        imageLoader.get(user.getAvatar(),listener);
        holder.tv_comment.setText(comment.getName()+":   "+comment.getText());

        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_avatar_comment;
        public TextView tv_comment;
    }
}
