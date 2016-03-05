package com.uni.infocoming.network;

import android.util.Log;

import com.android.volley.Request;
import com.uni.infocoming.constants.UrlConstants;
import com.uni.infocoming.entity.FormImage;

import java.util.ArrayList;
import java.util.List;

public class UploadApi {

    /**
     * 上传图片接口
     * @param mPaths 需要上传的图片的路径
     * @param listener 请求回调
     */
    public static void uploadImg(List <String>mPaths,ResponseListener listener){
        List<FormImage> imageList = new ArrayList<FormImage>() ;
        FormImage formImage;
        for (int i=0;i<mPaths.size();i++){
            formImage = new FormImage(mPaths.get(i));
            formImage.setmName("pic"+i);
            Log.i("IMG","name:"+formImage.getName()+" filename:"+formImage.getFileName()+
                    " value"+formImage.getValue()+" mime:"+formImage.getMime());
            imageList.add(formImage);
        }
        Request request = new PostUploadRequest(UrlConstants.RequestUploadPicture,imageList,listener) ;
        VolleyUtil.getRequestQueue().add(request) ;
    }
}
