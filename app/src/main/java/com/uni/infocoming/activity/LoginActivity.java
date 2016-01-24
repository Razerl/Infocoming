package com.uni.infocoming.activity;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uni.infocoming.BaseActivity;
import com.uni.infocoming.R;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.utils.ToastUtils;

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
            //如果登陆成功返回班级号存入sharedpreference
            sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isLOgin",true);
            editor.commit();
            intent2Activity(MainActivity.class);
        }
    }
}
