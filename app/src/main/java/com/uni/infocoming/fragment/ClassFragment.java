package com.uni.infocoming.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.uni.infocoming.BaseFragment;
import com.uni.infocoming.R;
import com.uni.infocoming.adapter.StatuesAdapter;
import com.uni.infocoming.entity.Status;
import com.uni.infocoming.utils.TitleBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Razer on 2015/12/25.
 */
public class ClassFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private LinearLayout layout;
    private ListView lv_popupwindow_class;
    private PopupWindow popupWindow;
    private ListView lv_class;

    private StatuesAdapter adapter;
    private List<Status> statuses = new ArrayList<Status>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView();

        return view;
    }

    private void initView() {
        view = View.inflate(activity, R.layout.fragment_class,null);
        lv_class = (ListView) view.findViewById(R.id.lv_class);
        adapter = new StatuesAdapter(activity,statuses);
        lv_class.setAdapter(adapter);

        new TitleBuilder(view)
                .setLeftImage(R.mipmap.logo)
                .setTitleText("班级圈")
                .setRightImage(R.mipmap.ic_menu_add)
                .setRightOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.titlebar_iv_right:
               showPopupWindow(v);
                break;
            default:
                break;
        }
    }

    public void showPopupWindow(View parent) {
        //加载布局
        layout = (LinearLayout) LayoutInflater.from(activity).inflate(
                R.layout.popupwindow_class, null);
        //找到布局控件
        lv_popupwindow_class = (ListView) layout.findViewById(R.id.lv_popupwindow_class);
        //设置适配器
        SimpleAdapter menuAdapter = new SimpleAdapter(activity,getMenuData(),
                R.layout.item_menu_popupwindow_class,
                new String[]{"iv_menu","tv_menu"},
                new int[] {R.id.iv_menu_popupwindow_class,R.id.tv_menu_popupwindow_class});
        lv_popupwindow_class.setAdapter(menuAdapter);
        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, 500,500);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null,""));
        WindowManager manager=(WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
        int xpos=manager.getDefaultDisplay().getWidth()/2-popupWindow.getWidth()/2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent,xpos, 0);
        //监听
        lv_popupwindow_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //关闭popupWindow
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }

    private List<Map<String, Object>> getMenuData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("iv_menu", R.mipmap.menu_edit);
        map.put("tv_menu", "发表文字状态");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("iv_menu", R.mipmap.menu_gallery);
        map.put("tv_menu", "发表图文状态");
        list.add(map);

        return list;
    }

}
