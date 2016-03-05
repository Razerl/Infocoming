package com.uni.infocoming.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.uni.infocoming.R;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.network.VolleyUtil;
import com.uni.infocoming.utils.TimeUtils;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteStatus extends AppCompatActivity implements View.OnClickListener {

    private static final int LOCATE_REQUEST = 1;
    private CheckBox cb_isTop;
    private TextView tv_locate;
    private RelativeLayout rl_locate;
    private EditText et_message;

    private boolean isTop;
    private String locate;
    private String message;
    private String created_at;
    private String classNumber;
    private String studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_status);

        initView();
    }

    private void initView() {
        cb_isTop = (CheckBox) findViewById(R.id.cb_isTop);
        et_message = (EditText) findViewById(R.id.et_message);

        tv_locate = (TextView) findViewById(R.id.tv_locate);
        rl_locate = (RelativeLayout) findViewById(R.id.rl_locate);
        rl_locate.setOnClickListener(this);

        new TitleBuilder(WriteStatus.this)
                .setLeftImage(R.mipmap.btn_back)
                .setRightImage(R.mipmap.button_submit)
                .setLeftOnClickListener(this)
                .setRightOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.titlebar_iv_right:
                //提交
                sendJsonData();
                break;
            case R.id.titlebar_iv_left:
                //返回
                finish();
                break;
            case R.id.rl_locate:
                Intent intent = new Intent(this,LocateActivity.class);
                startActivityForResult(intent,LOCATE_REQUEST);
                break;
            default:
                break;
        }

    }

    private void sendJsonData() {
        isTop = cb_isTop.isChecked();
        message = et_message.getText().toString();
        locate = tv_locate.getText().toString();
        created_at = TimeUtils.formatStatusDate(System.currentTimeMillis());
        SharedPreferences sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
        studentNumber = sp.getString("studentNumber","0");
        classNumber = sp.getString("classNumber","0");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("classNumber",classNumber);
            jsonObject.put("created_at",created_at);
            jsonObject.put("isTop",isTop);
            jsonObject.put("studentNumber",studentNumber);
            jsonObject.put("place",locate);
            jsonObject.put("text",message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("JSON",jsonObject.toString());
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.POST, UrlConstants.RequestUploadStatus,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String s = jsonObject.getString("register");
                    int status_id = jsonObject.getInt("status_id");
                    if ("success".equals(s)){
                        ToastUtils.showToast(WriteStatus.this,"发送成功！！",0);
                        finish();
                    }else {
                        ToastUtils.showToast(WriteStatus.this,"发送失败，请重新发送",0);
                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(WriteStatus.this,"发送失败",0);
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToast(WriteStatus.this,"发送失败，请重新发送",0);
                Log.i("JSON",volleyError.getMessage()==null?"NetWork Error":volleyError.getMessage());
            }
        });
        VolleyUtil.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==LOCATE_REQUEST){
            locate = data.getStringExtra(LocateActivity.EXTRA_LOCATE);
            tv_locate.setText(locate);
        }
    }
}
