package com.uni.infocoming.entity;

/**
 * Created by Razer on 2015/12/28.
 */
public class User extends BaseEntity {
    private String university;//学校
    private String name;//姓名
    private String id;//学号
    private String acadamy;//学院
    private String major;//专业
    private String duty;//职务
    private String tel;//手机号
    private String qq;//QQ号
    private String wechat;//微信号
    private String avatar;//头像地址

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAcadamy(String acadamy) {
        this.acadamy = acadamy;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getUniversity() {
        return university;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAcadamy() {
        return acadamy;
    }

    public String getMajor() {
        return major;
    }

    public String getDuty() {
        return duty;
    }

    public String getTel() {
        return tel;
    }

    public String getQq() {
        return qq;
    }

    public String getWechat() {
        return wechat;
    }
}
