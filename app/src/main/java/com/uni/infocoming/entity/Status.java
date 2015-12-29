package com.uni.infocoming.entity;

import java.util.ArrayList;

/**
 * Created by Razer on 2015/12/28.
 * 每条状态信息
 */
public class Status extends BaseEntity {
    private String created_at;//创建时间
    private boolean isTop;//是否置顶
    private String name;//姓名
    private String place;//发送地点
    private String text;//发送内容
    private ArrayList<String> pic_urls;//图片集合
    private String pic;//图片
    private ArrayList<Comment> comment_statu;//评论状态集合
    private User user;//User集合

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<String> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(ArrayList<String> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
}
