package com.uni.infocoming.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.uni.infocoming.R;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.network.ResponseListener;
import com.uni.infocoming.network.UploadApi;
import com.uni.infocoming.network.VolleyUtil;
import com.uni.infocoming.utils.TimeUtils;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WriteStatusWithPic extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int REQUEST_IMAGE = 2;
    private static final int DISPLAY_WIDTH = 300;
    private static final int DISPLAY_HEIGHT = 300;
    private static final int LOCATE_REQUEST = 3;
    private GridView gv_imgs ;
    private CheckBox cb_isTop;
    private TextView tv_locate;
    private RelativeLayout rl_locate;
    private EditText et_message;

    private SimpleAdapter adpter;
    private Bitmap bmp_add;
    private ArrayList<HashMap<String,Object>> data;
    private ArrayList<String> mSelectPath;
    private ArrayList<String> uploadImgPath;
    private int status_id;

    private boolean isTop;
    private String locate;
    private String message;
    private String created_at;
    private String classNumber;
    private String studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_status_with_pic);

        initView();
    }

    private void initView() {
        gv_imgs = (GridView) findViewById(R.id.gv_images);
        et_message = (EditText) findViewById(R.id.et_message);
        cb_isTop = (CheckBox) findViewById(R.id.cb_isTop);
        tv_locate = (TextView) findViewById(R.id.tv_locate);
        rl_locate = (RelativeLayout) findViewById(R.id.rl_locate);
        mSelectPath = new ArrayList<String>();
        uploadImgPath = new ArrayList<String>();

        bmp_add = BitmapFactory.decodeResource(getResources(),R.mipmap.gridview_addpic);
        data = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("item_img",bmp_add);
        data.add(map);
        setGridAdapter(data);
        gv_imgs.setOnItemClickListener(this);
        rl_locate.setOnClickListener(this);

        new TitleBuilder(WriteStatusWithPic.this)
                .setLeftImage(R.mipmap.btn_back)
                .setRightImage(R.mipmap.button_submit)
                .setLeftOnClickListener(this)
                .setRightOnClickListener(this);
    }

    private void setGridAdapter(ArrayList<HashMap<String, Object>> data) {
        adpter =new SimpleAdapter(this,data,R.layout.griditem_addpic,new String[]{"item_img"},new int[]{R.id.iv_image});
        adpter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gv_imgs.setAdapter(adpter);
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

    private void sendImg() {
        if (!uploadImgPath.isEmpty()){
            //上传status_id,pic0-8(图片名字)
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("status_id",status_id);
                for (int i =0;i<uploadImgPath.size();i++){
                    String mFileName ;
                    int position1 = uploadImgPath.get(i).lastIndexOf("/");
                    if (position1>0){
                        mFileName = uploadImgPath.get(i).substring(position1+1);
                    }else {
                        mFileName = "defaultName";
                    }
                    jsonObject.put("pic"+i,mFileName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("JSON",jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlConstants.RequestUploadStatusPic, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String s = jsonObject.getString("register");
                    if ("success".equals(s)){
                        UploadApi.uploadImg(uploadImgPath, new ResponseListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                ToastUtils.showToast(WriteStatusWithPic.this,"发送失败，请重新发送！！",0);
                            }
                            @Override
                            public void onResponse(Object o) {
                                    ToastUtils.showToast(WriteStatusWithPic.this,"发送成功！！",0);
                                    finish();
                            }
                        });
                    }else {
                        ToastUtils.showToast(WriteStatusWithPic.this,"发送失败，请重新发送",0);
                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(WriteStatusWithPic.this,"JsonError",0);
                    e.printStackTrace();
                }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtils.showToast(WriteStatusWithPic.this, "VolleyError", 0);
                    Log.i("JSONERROR", volleyError.getMessage() == null ? "NetWork Error" : volleyError.getMessage());
                }
            });
            VolleyUtil.getRequestQueue().add(jsonObjectRequest);
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
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.POST, UrlConstants.RequestUploadStatus,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String s = jsonObject.getString("register");
                    if ("success".equals(s)){
                        status_id =jsonObject.getInt("status_id");
                        sendImg();
                    }else {
                        ToastUtils.showToast(WriteStatusWithPic.this,"发送失败，请重新发送",0);
                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(WriteStatusWithPic.this,"JsonError",0);
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToast(WriteStatusWithPic.this,"VolleyError",0);
                Log.i("JSONERROR",volleyError.getMessage()==null?"NetWork Error":volleyError.getMessage());
            }
        });
        VolleyUtil.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            if (data.size()==10){
                Toast.makeText(this, "图片数9张已满", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "添加图片", Toast.LENGTH_SHORT).show();
                //选择图片
                Intent intent = new Intent(this, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 最大可选择图片数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                // 默认选择
                if(mSelectPath != null && mSelectPath.size()>0){
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        }else {
            dialog(position);
        }
    }
    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if(resultCode==RESULT_OK){
            if (requestCode==REQUEST_IMAGE){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                imgpath2Bmp(mSelectPath);
            }else if (requestCode ==LOCATE_REQUEST){
                locate = data.getStringExtra(LocateActivity.EXTRA_LOCATE);
                tv_locate.setText(locate);
            }
        }
    }

    private void imgpath2Bmp(ArrayList<String> mSelectPath) {
        if(!mSelectPath.isEmpty()){
            for (String path : mSelectPath){
                Bitmap bmp = decodeBitmap(path);
                uploadImgPath.add(path);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("item_img",bmp);
                data.add(map);
            }
            Log.i("DATA",data.toString());
            setGridAdapter(data);
            adpter.notifyDataSetChanged();
            mSelectPath.clear();
        }

    }

    private Bitmap decodeBitmap(String path){
        BitmapFactory.Options op = new BitmapFactory.Options();
        //inJustDecodeBounds
        //If set to true, the decoder will return null (no bitmap), but the out…
        op.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, op); //获取尺寸信息
        //获取比例大小
        int wRatio = (int)Math.ceil(op.outWidth/DISPLAY_WIDTH);
        int hRatio = (int)Math.ceil(op.outHeight/DISPLAY_HEIGHT);
        //如果超出指定大小，则缩小相应的比例
        if(wRatio > 1 && hRatio > 1){
            if(wRatio > hRatio){
                op.inSampleSize = wRatio;
            }else{
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, op);
        return bmp;
    }

    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    protected void dialog(final int position) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认移除已添加图片吗？")
                .positiveText("确认")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        data.remove(position);
                        adpter.notifyDataSetChanged();
                        uploadImgPath.remove(position-1);
                    }
                })
                .show();

    }
}
