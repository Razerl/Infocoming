package com.uni.infocoming.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.uni.infocoming.BaseApplication;

import java.util.Map;

/**
 * Created by Razer on 2015/12/30.
 */
public class VolleyRequestUtil {
    public static StringRequest stringRequest;
    public static JsonObjectRequest jsonObjectRequest;
    public static JsonArrayRequest jsonArrayRequest;
    public static Context context;

    /*
    * 获取GET请求内容
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestGet(Context context, String url, String tag, VolleyListenerInterface volleyListenerInterface) {
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的请求，获取字符串内容
        stringRequest = new StringRequest(Request.Method.GET, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener());
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        //BaseApplication.getRequestQueue().start();
    }

    /*
    * 获取POST请求内容（请求的代码为Map）
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * params：POST请求内容；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestPost(Context context, String url, String tag, final Map<String, String> params, VolleyListenerInterface volleyListenerInterface) {
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的POST请求，并将请求内容写入Map中
        stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        //BaseApplication.getRequestQueue().start();
    }

    /**
     *
     * @param context   上下文
     * @param url       请求地址
     * @param tag       tag
     * @param params    请求参数
     * @param volleyJsonArrayListenerInterface
     */
    public static void RequestJsonArrayPost(Context context, String url, String tag, final Map<String, String> params, VolleyJsonArrayListenerInterface volleyJsonArrayListenerInterface) {
        // 清除请求队列中的tag标记请求
        BaseApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的POST请求，并将请求内容写入Map中
        jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,url,volleyJsonArrayListenerInterface.mListener,volleyJsonArrayListenerInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        // 为当前请求添加标记
        jsonArrayRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        BaseApplication.getRequestQueue().add(jsonArrayRequest);
        // 重启当前请求队列
        //BaseApplication.getRequestQueue().start();
    }
}
