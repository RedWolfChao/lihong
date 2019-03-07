package com.qyw.newdemo.app.entity;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class ShowRoomEntity {

    private String head_Img;    //头像
    private String name_Text;   //用户名
    private String time_Text;   //作品存在时间
    private String production_Img;  //作品封面图片链接
    private String production_Title;    //作品标题
    private String praise_Text;     // 作品点赞数
    private String message_Text;    //  作品评论数
    private String production_Id;   //  作品ID

    public ShowRoomEntity() {
    }

    public ShowRoomEntity(String head_Img, String name_Text, String time_Text, String production_Img, String production_Title, String praise_Text, String message_Text, String production_Id) {
        this.head_Img = head_Img;
        this.name_Text = name_Text;
        this.time_Text = time_Text;
        this.production_Img = production_Img;
        this.production_Title = production_Title;
        this.praise_Text = praise_Text;
        this.message_Text = message_Text;
        this.production_Id = production_Id;
    }

    public String getHead_Img() {
        return head_Img;
    }

    public void setHead_Img(String head_Img) {
        this.head_Img = head_Img;
    }

    public String getName_Text() {
        return name_Text;
    }

    public void setName_Text(String name_Text) {
        this.name_Text = name_Text;
    }

    public String getTime_Text() {
        return time_Text;
    }

    public void setTime_Text(String time_Text) {
        this.time_Text = time_Text;
    }

    public String getProduction_Img() {
        return production_Img;
    }

    public void setProduction_Img(String production_Img) {
        this.production_Img = production_Img;
    }

    public String getProduction_Title() {
        return production_Title;
    }

    public void setProduction_Title(String production_Title) {
        this.production_Title = production_Title;
    }

    public String getPraise_Text() {
        return praise_Text;
    }

    public void setPraise_Text(String praise_Text) {
        this.praise_Text = praise_Text;
    }

    public String getMessage_Text() {
        return message_Text;
    }

    public void setMessage_Text(String message_Text) {
        this.message_Text = message_Text;
    }

    public String getProduction_Id() {
        return production_Id;
    }

    public void setProduction_Id(String production_Id) {
        this.production_Id = production_Id;
    }

    @Override
    public String toString() {
        return "ShowRoomEntity{" +
                "head_Img='" + head_Img + '\'' +
                ", name_Text='" + name_Text + '\'' +
                ", time_Text='" + time_Text + '\'' +
                ", production_Img='" + production_Img + '\'' +
                ", production_Title='" + production_Title + '\'' +
                ", praise_Text='" + praise_Text + '\'' +
                ", message_Text='" + message_Text + '\'' +
                ", production_Id='" + production_Id + '\'' +
                '}';
    }
}
