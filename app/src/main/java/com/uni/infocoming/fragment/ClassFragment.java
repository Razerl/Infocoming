package com.uni.infocoming.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.uni.infocoming.BaseFragment;
import com.uni.infocoming.R;
import com.uni.infocoming.activity.WriteStatus;
import com.uni.infocoming.activity.WriteStatusWithPic;
import com.uni.infocoming.adapter.StatuesAdapter;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.entity.Status;
import com.uni.infocoming.network.VolleyUtil;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.widget.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Razer on 2015/12/25.
 */
public class ClassFragment extends BaseFragment implements View.OnClickListener {

    private static int REFRESH_CODE = 0;

    private View view;
    private LinearLayout layout;
    private ListView lv_popupwindow_class;
    private PopupWindow popupWindow;

    private RefreshLayout mRefreshlayout;
    private ListView lv_class;

    private String classNumber;
    private int lastItemId;

    private StatuesAdapter adapter;
    private List<Status> statuses = new ArrayList<Status>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        classNumber = "10061312";
        initView();
        loadData(REFRESH_CODE,classNumber);

        return view;
    }

    private void initView() {
        view = View.inflate(activity, R.layout.fragment_class,null);
        mRefreshlayout = (RefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        lv_class = (ListView) view.findViewById(R.id.lv_class);
        adapter = new StatuesAdapter(activity,statuses);
        lv_class.setAdapter(adapter);
        //设置下拉刷新组件
        mRefreshlayout.setColorSchemeResources(R.color.md_light_green_A400
                ,R.color.md_light_blue_A200,R.color.md_red_600,R.color.md_amber_900);
        //下拉刷新监听
        mRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(activity, "Refresh", Toast.LENGTH_SHORT).show();

                mRefreshlayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        loadData(REFRESH_CODE,classNumber);
                        adapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        mRefreshlayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        //上拉加载监听
        mRefreshlayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                Toast.makeText(activity, "Load", Toast.LENGTH_SHORT).show();

                mRefreshlayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadData(lastItemId,classNumber);
                        adapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        mRefreshlayout.setLoading(false);
                    }
                }, 1500);

            }
        });

        new TitleBuilder(view)
                .setLeftImage(R.mipmap.logo)
                .setTitleText("班级圈")
                .setRightImage(R.mipmap.add)
                .setRightOnClickListener(this);
    }

    //加载数据
    private void loadData(final int lastItemId,String classNumber) {
//        if(classNumber==null){
//            ToastUtils.showToast(activity,"加载失败",0);
//            return;
//        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("uid",lastItemId);
            obj.put("cnumber",classNumber);
            String s = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlConstants.RequestStatusUrl,obj, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                if(lastItemId==0) {
                    statuses.clear();
                }
                JSONObject obj = new JSONObject();
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        obj = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    addData(new Gson().fromJson(obj.toString(),Status.class));

                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Internet-ERROR", volleyError.getMessage(), volleyError);
            }
        });
        VolleyUtil.getRequestQueue().add(jsonArrayRequest);
    }

    private void addData(Status status) {
        if(!statuses.contains(status)) {
            statuses.add(status);
            lastItemId = status.getId();
        }
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
            public void onItemClick(AdapterView<?> arg0, View arg1, int select,
                                    long arg3) {
                Intent intent ;
                if(select==0){
                    //发表文字状态
                    intent = new Intent(activity, WriteStatus.class);
                    startActivity(intent);
                }else if(select==1){
                    //发表图文状态
                    intent = new Intent(activity, WriteStatusWithPic.class);
                    startActivity(intent);
                }
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

    @Override
    public void onStop() {
        super.onStop();
        VolleyUtil.getRequestQueue().cancelAll("tag");
    }
}
