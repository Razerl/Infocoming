package com.uni.infocoming.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.infocoming.R;
import com.uni.infocoming.activity.UserInfoActivity;
import com.uni.infocoming.utils.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Razer on 2016/1/26.
 */
public class MeListviewAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String,Object>> data;

    public MeListviewAdapter(Context context, List<Map<String,Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String,Object> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_listview_common,null);
            holder.ll_me = (LinearLayout) convertView.findViewById(R.id.ll_mefragment);
            holder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item_listview);
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item_listview);
            holder.iv_rightarrow = (ImageView) convertView.findViewById(R.id.titlebar_iv_right);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_item.setImageResource((Integer) data.get(position).get("iv_item"));
        holder.tv_item.setText((String) data.get(position).get("tv_item"));
        holder.ll_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent ;
                String item_name ;
                item_name = data.get(position).get("tv_item").toString();
                switch (item_name){
                    case "我的资料":
                        intent = new Intent(context, UserInfoActivity.class);
                        context.startActivity(intent);
                        break;
                    case "用户反馈":
                        ToastUtils.showToast(context,"feedback",0);
                        break;
                    case "设置":
                        ToastUtils.showToast(context,"setting",0);
                        break;
                    default:
                        break;
                }

            }
        });
        return convertView;
    }

    private class ViewHolder {
        public LinearLayout ll_me;
        public ImageView iv_item;
        public TextView tv_item;
        public ImageView iv_rightarrow;
    }
}
