package com.uni.infocoming.entity;

/**
 * Created by Razer on 2015/12/28.
 */
public class User extends BaseEntity {
    private String university;//学校
    private String name;//姓名
    private String studentNumber;//学号
    private String password;//密码
    private String classNumber;//班号
    private String major;//专业
    private String duty;//职务
    private String tel;//手机号
    private String qq;//QQ号
    private String wechat;//微信号
    private String avatar;//头像地址

    @Override
    public String toString() {
        return "User{" +
                "university='" + university + '\'' +
                ", name='" + name + '\'' +
                ", studentnumber='" + studentNumber + '\'' +
                ", password='" + password + '\'' +
                ", classnumber='" + classNumber + '\'' +
                ", major='" + major + '\'' +
                ", duty='" + duty + '\'' +
                ", tel='" + tel + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getClassnumber() {
        return classNumber;
    }

    public void setClassnumber(String classnumber) {
        this.classNumber = classnumber;
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

    public String getStudentnumber() {
        return studentNumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentNumber = studentnumber;
    }

}
