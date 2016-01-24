package com.uni.infocoming.utils;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Created by Razer on 2015/12/30.
 */
public abstract  class VolleyJsonArrayListenerInterface {
    public Context mContext;
    public static Response.Listener<JSONArray> mListener;
    public static Response.ErrorListener mErrorListener;

    public VolleyJsonArrayListenerInterface(Context context, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        this.mContext = context;
        this.mErrorListener = errorListener;
        this.mListener = listener;
    }

    // 请求成功时的回调函数
    public abstract void onMySuccess(JSONArray result);

    // 请求失败时的回调函数
    public abstract void onMyError(VolleyError error);

    // 创建请求的事件监听
    public Response.Listener<JSONArray> responseListener() {
        mListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                onMySuccess(response);
            }
        };
        return mListener;
    }

    // 创建请求失败的事件监听
    public Response.ErrorListener errorListener() {
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyError(volleyError);
            }
        };
        return mErrorListener;
    }
}
