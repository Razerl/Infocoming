package com.uni.infocoming.entity;

import java.util.ArrayList;

/**
 * Created by Razer on 2015/12/28.
 * 每条状态信息
 */
public class Status extends BaseEntity {
    private int id ;
    private String created_at;//创建时间
    private boolean isTop;//是否置顶
    private String studentNumber;//学号
    private String place;//发送地点
    private String text;//发送内容
    private ArrayList<String> pic_url;//图片集合
    private ArrayList<Comment> comment_statu;//评论状态集合
    private User user;//User集合
    private String classNumber;

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", created_at='" + created_at + '\'' +
                ", isTop=" + isTop +
                ", studentNumber='" + studentNumber + '\'' +
                ", place='" + place + '\'' +
                ", text='" + text + '\'' +
                ", pic_url=" + pic_url +
                ", comment_statu=" + comment_statu +
                ", user=" + user +
                ", classNumber='" + classNumber + '\'' +
                '}';
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getPic_url() {
        return pic_url;
    }

    public void setPic_url(ArrayList<String> pic_url) {
        this.pic_url = pic_url;
    }

    public ArrayList<Comment> getComment_statu() {
        return comment_statu;
    }

    public void setComment_statu(ArrayList<Comment> comment_statu) {
        this.comment_statu = comment_statu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
