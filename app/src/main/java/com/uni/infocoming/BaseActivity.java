package com.uni.infocoming;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.uni.infocoming.constants.CommonConstants;
import com.uni.infocoming.utils.Logger;
import com.uni.infocoming.utils.ToastUtils;
import com.uni.infocoming.widget.BitmapCache;


/**
 * Created by Razer on 2015/12/21.
 */
public class BaseActivity extends Activity {

    protected String TAG;

    protected BaseApplication application;
    protected SharedPreferences sp;
    protected RequestQueue queue;
    protected ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        TAG = this.getClass().getSimpleName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        application = (BaseApplication) getApplication();
        sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new BitmapCache());
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    protected void showLog(String msg) {
        Logger.show(TAG, msg);
    }
}