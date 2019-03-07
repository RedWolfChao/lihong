package com.qyw.newdemo.app.entity;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class MessageEntity {

    private String user_name;
    private String content_text;
    private String time_text;
    private String Usericon_url;
    private String mesId;

    public MessageEntity() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }

    public String getUsericon_url() {
        return Usericon_url;
    }

    public void setUsericon_url(String usericon_url) {
        Usericon_url = usericon_url;
    }

    public String getMesId() {
        return mesId;
    }

    public void setMesId(String mesId) {
        this.mesId = mesId;
    }
}
