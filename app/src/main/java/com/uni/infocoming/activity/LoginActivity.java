package com.uni.infocoming.activity;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.uni.infocoming.BaseActivity;
import com.uni.infocoming.R;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.network.VolleyUtil;
import com.uni.infocoming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_login_number;
    private EditText et_login_password;
    private TextView tv_login_certification;
    private Button btn_login_login;

    private String login_number;
    private String login_password;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        et_login_number = (EditText) findViewById(R.id.et_login_number);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_login_certification = (TextView) findViewById(R.id.tv_login_certification);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);

        tv_login_certification.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_login_certification.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id ;
        id = v.getId();
        switch (id){
            case R.id.tv_login_certification:
                intent2Activity(CertificationActivity.class);
                break;
            case R.id.btn_login_login:
                validation();
                break;
            default:
                break;
        }
    }

    private void validation() {
        login_number = et_login_number.getText().toString();
        login_password = et_login_password.getText().toString();
        //验证用户名和密码
        boolean isLogin = true;
        if(TextUtils.isEmpty(login_number)){
            ToastUtils.showToast(this,"账号不能为空！",0);
        }else if(TextUtils.isEmpty(login_password)){
            ToastUtils.showToast(this,"密码不能为空！",0);
        }else if(!isLogin){
            ToastUtils.showToast(this,"账号或密码错误，请重新尝试",0);
        }else {
            //与服务器匹配账号和密码
            JSONObject obj = new JSONObject();
            try {
                obj.put("studentNumber",login_number);
                obj.put("password",login_password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("JSON",obj.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlConstants.RequestLoginUrl, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        Log.i("JSON",jsonObject.toString());
                        String response =  jsonObject.getString("login");
                        if(TextUtils.equals(response,"success")){
                            sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("isLogin",true);
                            editor.putString("studentNumber",login_number);
                            editor.commit();
                            intent2Activity(MainActivity.class);
                        }else if(TextUtils.equals(response,"fail")){
                            ToastUtils.showToast(LoginActivity.this,"用户名和密码不匹配,请检查后重试",0);
                        }
                    } catch (JSONException e) {
                        ToastUtils.showToast(LoginActivity.this,"登陆失败",0);
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtils.showToast(LoginActivity.this,"登陆失败",0);
                }
            });
            VolleyUtil.getRequestQueue().add(jsonObjectRequest);
        }
    }
}
