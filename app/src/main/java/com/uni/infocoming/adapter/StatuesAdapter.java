package com.uni.infocoming.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uni.infocoming.R;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.entity.Comment;
import com.uni.infocoming.entity.Status;
import com.uni.infocoming.entity.User;
import com.uni.infocoming.utils.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Razer on 2015/12/28.
 */
public class StatuesAdapter extends BaseAdapter {

    private Context context;
    private List<Status> datas;

    public StatuesAdapter(Context context ,List<Status> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Status getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_status,null);

            holder.ll_status_item = (LinearLayout) convertView.findViewById(R.id.ll_status_item);

            holder.iv_avatar_content = (ImageView) convertView.findViewById(R.id.iv_avatar_content);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_locate = (ImageView) convertView.findViewById(R.id.iv_locate);
            holder.tv_locate  = (TextView) convertView.findViewById(R.id.tv_locate);
            holder.tv_status_content = (TextView) convertView.findViewById(R.id.tv_status_content);

            holder.include_status_image = (FrameLayout) convertView.findViewById(R.id.include_status_image);
            holder.gv_images = (GridView) holder.include_status_image.findViewById(R.id.gv_images);
            holder.iv_image = (ImageView) holder.include_status_image.findViewById(R.id.iv_image);

            holder.lv_comment = (ListView) convertView.findViewById(R.id.lv_comment);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //绑定数据
        final Status status = getItem(position);
        final User user = status.getUser();

        Log.i("TAG",status.toString());
        //绑定顶部内容数据
        ImageLoaderUtil.setImageLoader(UrlConstants.BaseUrl+user.getAvatar(),holder.iv_avatar_content);

        holder.tv_name.setText(user.getName());
        if(status.getPlace()!=null){
            holder.iv_locate.setVisibility(View.VISIBLE);
            holder.tv_locate.setText(status.getPlace());
        }
        holder.tv_status_content.setText(status.getText());
        setImages(status,holder.include_status_image,holder.gv_images,holder.iv_image);
        //绑定评论数据
        ArrayList<Comment> comments = status.getComment_statu();
        StatusCommentAdapter commentAdapter = new StatusCommentAdapter(context,comments);
        holder.lv_comment.setAdapter(commentAdapter);

        return convertView;
    }

    private void setImages(Status status, FrameLayout imgContainer, GridView gv_images, ImageView iv_image) {
        ArrayList<String> pic_url = status.getPic_url();
        String pic ;

        if(pic_url != null && pic_url.size() > 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.GONE);

            StatusGridImgsAdapter gvAdapter = new StatusGridImgsAdapter(context, pic_url);
            gv_images.setAdapter(gvAdapter);
        } else if(pic_url.size() == 1) {
            pic = pic_url.get(0);
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.GONE);
            iv_image.setVisibility(View.VISIBLE);

            ImageLoaderUtil.setImageLoader(UrlConstants.BaseUrl+pic,iv_image);
        } else {
            imgContainer.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder {
        public LinearLayout ll_status_item;

        public ImageView iv_avatar_content;
        public TextView tv_name;
        public ImageView iv_locate;
        public TextView tv_locate;
        public TextView tv_status_content;

        public GridView gv_images;
        public ImageView iv_image;

        public ListView lv_comment;
        public FrameLayout include_status_image;
    }
}
