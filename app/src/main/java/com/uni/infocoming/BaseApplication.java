package com.uni.infocoming;

import android.app.Application;
import android.content.Context;

import com.uni.infocoming.network.VolleyUtil;

/**
 * Created by Razer on 2015/12/21.
 */
public class BaseApplication extends Application {

    private Context mContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext() ;
        initialize() ;
    }

    private void initialize(){
        initRequestQueue();
    }

    private void initRequestQueue(){
        VolleyUtil.initialize(mContext);
    }
}
