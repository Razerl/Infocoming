package com.uni.infocoming.constants;

/**
 * Created by Razer on 2015/12/30.
 */
public class UrlConstants {

    private UrlConstants(){};

    public static final String BaseUrl = "http://www.infocoming.com";
    public static final String RequestStatusUrl = BaseUrl+"/status/";//获取状态信息
    public static final String RequestUploadStatus = BaseUrl+"/uploadstatus/";//上传朋友圈
    public static final String RequestUploadStatusPic = BaseUrl+"/uploadstatuspic/";//上传图片名字
    public static final String RequestUploadPicture = BaseUrl+"/uploadpicture/";//上传图片名字
    public static final String RequestCertificationUrl = BaseUrl+"/ppdstudents/";//认证信息
    public static final String RequestGetUserUrl = BaseUrl+"/gstudents/";//获取user
    public static final String RequestLoginUrl = BaseUrl+"/logverification/";//登录

}
