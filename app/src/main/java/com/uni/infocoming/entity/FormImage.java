package com.uni.infocoming.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;

public class FormImage {
    //参数名称
    private String mName ;
    //文件名称
    private String mFileName ;

    private String mValue ;
    //文件的mime
    private String mMime ;
    //需要上传的图片资源，
    private Bitmap mBitmap ;
    //需要上传的图片路径
    private String mPath;

    public FormImage(String mPath) {
        this.mPath = mPath;
        mBitmap = BitmapFactory.decodeFile(mPath);
    }

    public String getName() {
        return mName;
    }

    public String getFileName() {
        int position1 = mPath.lastIndexOf("/");
        if (position1>0){
            mFileName = mPath.substring(position1+1);
        }else {
            mFileName = "defaultName";
        }
        return mFileName;
    }

    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        mBitmap.compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getMime() {
        int position = mPath.lastIndexOf(".");
        if (position>0){
            String extension = mPath.substring(position+1);
            mMime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mMime;
    }
}
