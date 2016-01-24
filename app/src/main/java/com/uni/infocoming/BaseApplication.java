package com.uni.infocoming;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Razer on 2015/12/21.
 */
public class BaseApplication extends Application {

    public static RequestQueue volleyQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        volleyQueue = Volley.newRequestQueue(getApplicationContext());
    }

    // 开放Volley的HTTP请求队列接口
    public static RequestQueue getRequestQueue() {
        return volleyQueue;
    }
}
