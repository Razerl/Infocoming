package com.uni.infocoming.entity;

/**
 * Created by Razer on 2015/12/29.
 */
public class Comment extends BaseEntity {
    private int id;//对应状态的id
    private String created_at;//时间
    private String studentNumber;//学号
    private String text;//内容
    private User user;//user

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", created_at='" + created_at + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
