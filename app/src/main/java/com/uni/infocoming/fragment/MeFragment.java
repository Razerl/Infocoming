package com.uni.infocoming.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.uni.infocoming.BaseFragment;
import com.uni.infocoming.R;
import com.uni.infocoming.activity.LoginActivity;
import com.uni.infocoming.adapter.MeListviewAdapter;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.entity.User;
import com.uni.infocoming.network.VolleyUtil;
import com.uni.infocoming.utils.ImageLoaderUtil;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Razer on 2015/12/25.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ListView lv_me;
    private ImageView iv_logout;
    private Button btn_quitcommittee;
    private Button btn_applytocommittee;
    private ImageView iv_avatar_me;
    private TextView tv_name_me;

    private MeListviewAdapter adpter;
    private List<Map<String,Object>> list;

    private SharedPreferences sp;
    private User user;//用户信息
    private Spinner spi_diaog;
    private String studentNumber;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView();
        loadUserInfo();
        return view;
    }

    private void loadUserInfo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("studentNumber",studentNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //从服务器拿取User的信息
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlConstants.RequestGetUserUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                user = new Gson().fromJson(jsonObject.toString(),User.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("JSON",volleyError.getMessage()==null?"NetWork Error":volleyError.getMessage());
            }
        });
        VolleyUtil.getRequestQueue().add(jsonObjectRequest);
        //设置用户信息
        setUserInfo();
    }

    private void initView() {
        view = View.inflate(activity, R.layout.fragment_me,null);

        iv_avatar_me = (ImageView) view.findViewById(R.id.iv_avatar_me);
        tv_name_me = (TextView) view.findViewById(R.id.tv_name_me);
        lv_me = (ListView) view.findViewById(R.id.lv_me);
        iv_logout = (ImageView) view.findViewById(R.id.iv_logout);
        btn_quitcommittee = (Button) view.findViewById(R.id.btn_quitcommittee);
        btn_applytocommittee = (Button) view.findViewById(R.id.btn_applytocommittee);
        spi_diaog = (Spinner) view.findViewById(R.id.spi_dialog);
        iv_avatar_me.setOnClickListener(this);

        adpter = new MeListviewAdapter(activity,getData());
        lv_me.setAdapter(adpter);
        iv_logout.setOnClickListener(this);
        btn_applytocommittee.setOnClickListener(this);
        btn_quitcommittee.setOnClickListener(this);
        new TitleBuilder(view)
                .setLeftImage(R.mipmap.logo)
                .setTitleText("我的");

        sp = activity.getSharedPreferences(CommonConstants.SP_NAME, activity.MODE_PRIVATE);
        studentNumber = sp.getString("studentNumber","0");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            loadUserInfo();
        }
    }

    private void setUserInfo() {
        if(user==null){
            iv_avatar_me.setImageResource(R.mipmap.avatar_default);
            tv_name_me.setText("未登录");
            btn_applytocommittee.setVisibility(View.INVISIBLE);
            btn_quitcommittee.setVisibility(View.INVISIBLE);
        }else{
            ImageLoaderUtil.setImageLoader(UrlConstants.BaseUrl+user.getAvatar(),iv_avatar_me);
            if(TextUtils.isEmpty(user.getAvatar())){
                iv_avatar_me.setImageResource(R.mipmap.avatar_default);
            }
            tv_name_me.setText(user.getName());
            btn_quitcommittee.setVisibility(View.VISIBLE);
            btn_applytocommittee.setVisibility(View.VISIBLE);
        }
    }

    private List<Map<String,Object>> getData() {
        list = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("iv_item",R.mipmap.information);
        map.put("tv_item","我的资料");
        list.add(map);

        map = new HashMap<String,Object>();
        map.put("iv_item",R.mipmap.feedback);
        map.put("tv_item","用户反馈");
        list.add(map);

        map = new HashMap<String,Object>();
        map.put("iv_item",R.mipmap.setting);
        map.put("tv_item","设置");
        list.add(map);
        return list;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.iv_avatar_me:

                break;
            case R.id.iv_logout:
                //退出登录
                sp = activity.getSharedPreferences(CommonConstants.SP_NAME, activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("user");
                edit.remove("isLogin");
                edit.putBoolean("isLogin",false);
                edit.commit();
                Intent logoutIntent = new Intent(activity, LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                break;
            case R.id.btn_quitcommittee :
                //退出班委
                new MaterialDialog.Builder(activity)
                    .title("目前职务:"+user.getDuty())
                    .positiveText("确定")
                    .negativeText("取消")
                    .content("是否退出班委，退出后就不能发送置顶消息，确定吗？")
                    .onPositive(new MaterialDialog.SingleButtonCallback(){

                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            if(user.getDuty().equals("无")){
                                ToastUtils.showToast(activity,"很遗憾，您目前还没有职务",0);
                                return;
                            }
                            user.setDuty("无");
                            //向服务器发送更新用户信息请求

                            ToastUtils.showToast(activity,"退出成功",0);
                        }
                    })
                    .show();
                break;
            case R.id.btn_applytocommittee :
                //申请成为班委
                new MaterialDialog.Builder(activity)
                        .title("申请班委")
                        .positiveText("确定")
                        .negativeText("取消")
                        .customView(R.layout.spinnerdialog,true)
                        .onPositive(new MaterialDialog.SingleButtonCallback(){

                            @Override
                            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                user.setDuty("");
                                //向服务器发送更新用户信息请求
                                String select = spi_diaog.getSelectedItem().toString();
                                Log.i("TAG",select);
                                ToastUtils.showToast(activity,"申请发送成功，等待审核",0);
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
