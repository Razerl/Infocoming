package com.uni.infocoming.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.uni.infocoming.BaseActivity;
import com.uni.infocoming.R;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.entity.User;
import com.uni.infocoming.network.VolleyUtil;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class CertificationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_info_schoolname;
    private EditText et_info_username;
    private EditText et_info_studentid;
    private EditText et_info_classnumber;
    private EditText et_info_major;
    private EditText et_info_password;
    private EditText et_info_password2;
    private Spinner spi_info_duty;
    private TextView tv_info_info;
    private EditText et_info_phonenumber;
    private EditText et_info_QQ;
    private EditText et_info_wechat;

    private String password2;
    private User user;
    private Gson gson;

    private ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);

        initView();
    }

    private void initView() {
        new TitleBuilder(this)
                .setLeftImage(R.mipmap.logo)
                .setTitleText("校园认证")
                .setRightImage(R.mipmap.button_submit)
                .setRightOnClickListener(this);

        et_info_schoolname = (EditText) findViewById(R.id.et_info_schoolname);//学校
        et_info_username = (EditText) findViewById(R.id.et_info_username);//姓名
        et_info_studentid = (EditText) findViewById(R.id.et_info_studentid);//学号
        et_info_classnumber = (EditText) findViewById(R.id.et_info_classnumber);//班号
        et_info_major = (EditText) findViewById(R.id.et_info_major);//专业
        et_info_password = (EditText) findViewById(R.id.et_info_password);//密码
        et_info_password2 = (EditText) findViewById(R.id.et_info_password2);//确认密码
        spi_info_duty = (Spinner) findViewById(R.id.spi_info_duty);//班级内职务
        tv_info_info = (TextView) findViewById(R.id.tv_info_info);//提示信息
        et_info_phonenumber = (EditText) findViewById(R.id.et_info_phonenumber);//手机号
        et_info_QQ = (EditText) findViewById(R.id.et_info_QQ);//QQ号
        et_info_wechat = (EditText) findViewById(R.id.et_info_wechat);//微信号

        //自定义Spinner样式
        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.duties, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spi_info_duty.setAdapter(spinnerAdapter);

        //替换*的颜色
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tv_info_info.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.md_red_800));
        spannableStringBuilder.setSpan(redSpan, 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_info_info.setText(spannableStringBuilder);
    }


    @Override
    public void onClick(View v) {
        int id;
        id = v.getId();
        switch (id){
            case R.id.titlebar_iv_right:
                formValidation();

                break;
            default:
                break;
        }
    }

    public void formValidation() {
        user = new User();
        user.setUniversity(et_info_schoolname.getText().toString());
        user.setName(et_info_username.getText().toString());
        user.setStudentnumber(et_info_studentid.getText().toString());
        user.setClassnumber(et_info_classnumber.getText().toString());
        user.setMajor(et_info_major.getText().toString());
        user.setPassword(et_info_password.getText().toString());
        password2 = et_info_password2.getText().toString();
        user.setDuty(spi_info_duty.getSelectedItem().toString());
        user.setTel(et_info_phonenumber.getText().toString());
        user.setQq(et_info_QQ.getText().toString());
        user.setWechat(et_info_wechat.getText().toString());
        //验证非空
        if(TextUtils.isEmpty(user.getUniversity())){
            ToastUtils.showToast(this,"学校名不能为空！",0);
        }else if(TextUtils.isEmpty(user.getName())){
            ToastUtils.showToast(this,"姓名不能为空！",0);
        }else if(TextUtils.isEmpty(user.getStudentnumber())){
            ToastUtils.showToast(this,"学号不能为空！",0);
        }else if(TextUtils.isEmpty(user.getClassnumber())){
            ToastUtils.showToast(this,"班号不能为空！",0);
        }else if(TextUtils.isEmpty(user.getMajor())){
            ToastUtils.showToast(this,"专业不能为空！",0);
        }else if(TextUtils.isEmpty(user.getPassword())){
            ToastUtils.showToast(this,"密码不能为空！",0);
        }else if(TextUtils.isEmpty(password2)){
            ToastUtils.showToast(this,"确认密码不能为空！",0);
        }
        //验证两次密码是否一致
        else if (!user.getPassword().equals(password2)){
            ToastUtils.showToast(this,"两次密码不一致",0);
        }
        //验证职务是否符合
        else{
            gson = new Gson();
            String jsonString = gson.toJson(user);
            try {
                JSONObject obj = new JSONObject(jsonString);
                //存储User信息
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlConstants.RequestCertificationUrl, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String response = jsonObject.getString("register");
                            if(TextUtils.equals(response,"success")){
                                ToastUtils.showToast(CertificationActivity.this,"认证成功!",0);
                                SharedPreferences sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("studentNumber",user.getStudentnumber());
                                editor.putString("classNumber",user.getClassnumber());
                                editor.commit();
                                intent2Activity(LoginActivity.class);

                            }else{
                                ToastUtils.showToast(CertificationActivity.this,"认证失败！",0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        byte[] htmlBodyBytes = volleyError.networkResponse.data;
                        Log.e("LOGIN-ERROR", new String(htmlBodyBytes), volleyError);
                    }
                });
                VolleyUtil.getRequestQueue().add(request);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
