package com.uni.infocoming.activity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.uni.infocoming.BaseActivity;
import com.uni.infocoming.R;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.utils.ToastUtils;

public class CertificationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_info_schoolname;
    private EditText et_info_username;
    private EditText et_info_studentid;
    private EditText et_info_academy;
    private EditText et_info_major;
    private EditText et_info_password;
    private EditText et_info_password2;
    private Spinner spi_info_duty;
    private TextView tv_info_info;

    private String schoolName;
    private String userName;
    private String studentId;
    private String academy;
    private String major;
    private String password;
    private String password2;

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
        et_info_academy = (EditText) findViewById(R.id.et_info_academy);//学院
        et_info_major = (EditText) findViewById(R.id.et_info_major);//专业
        et_info_password = (EditText) findViewById(R.id.et_info_password);//密码
        et_info_password2 = (EditText) findViewById(R.id.et_info_password2);//确认密码
        spi_info_duty = (Spinner) findViewById(R.id.spi_info_duty);//班级内职务
        tv_info_info = (TextView) findViewById(R.id.tv_info_info);//提示信息

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

    private void formValidation() {
        schoolName = et_info_schoolname.getText().toString();
        userName = et_info_username.getText().toString();
        studentId = et_info_studentid.getText().toString();
        academy = et_info_academy.getText().toString();
        major = et_info_major.getText().toString();
        password = et_info_password.getText().toString();
        password2 = et_info_password2.getText().toString();
        //验证非空
        if(TextUtils.isEmpty(schoolName)){
            ToastUtils.showToast(this,"学校名不能为空！",0);
        }else if(TextUtils.isEmpty(userName)){
            ToastUtils.showToast(this,"姓名不能为空！",0);
        }else if(TextUtils.isEmpty(studentId)){
            ToastUtils.showToast(this,"学号不能为空！",0);
        }else if(TextUtils.isEmpty(academy)){
            ToastUtils.showToast(this,"学院不能为空！",0);
        }else if(TextUtils.isEmpty(major)){
            ToastUtils.showToast(this,"专业不能为空！",0);
        }else if(TextUtils.isEmpty(password)){
            ToastUtils.showToast(this,"密码不能为空！",0);
        }else if(TextUtils.isEmpty(password2)){
            ToastUtils.showToast(this,"确认密码不能为空！",0);
        }
        //验证两次密码是否一致
        else if (!password.equals(password2)){
            ToastUtils.showToast(this,"两次密码不一致",0);
        }
        //验证职务是否符合
        else{
           intent2Activity(LoginActivity.class);
        }
    }
}
