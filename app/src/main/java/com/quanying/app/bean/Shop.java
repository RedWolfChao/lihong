package com.quanying.app.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Shop{

    //id
    @Id(autoincrement = true)
    private Long id;
    //用户名
    @Unique
    private String name;
    //头像
    @Property(nameInDb = "price")
    private String face;
    //可用
    private String  sendTime;
    private String  getTime;

    private String  hxId;

    private String  yuliu1;
    private String  yuliu2;


    //可用
    private String image_url;

    private int type;

    private int sell_num;

    @Generated(hash = 1233974246)
    public Shop(Long id, String name, String face, String sendTime, String getTime,
            String hxId, String yuliu1, String yuliu2, String image_url, int type,
            int sell_num) {
        this.id = id;
        this.name = name;
        this.face = face;
        this.sendTime = sendTime;
        this.getTime = getTime;
        this.hxId = hxId;
        this.yuliu1 = yuliu1;
        this.yuliu2 = yuliu2;
        this.image_url = image_url;
        this.type = type;
        this.sell_num = sell_num;
    }

    @Generated(hash = 633476670)
    public Shop() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return this.face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getSell_num() {
        return this.sell_num;
    }

    public void setSell_num(int sell_num) {
        this.sell_num = sell_num;
    }

    public String getImage_url() {
        return this.image_url;
    }



    /*
    * 环信账号
    * */
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getGetTime() {
        return this.getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getHxId() {
        return this.hxId;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public String getYuliu1() {
        return this.yuliu1;
    }

    public void setYuliu1(String yuliu1) {
        this.yuliu1 = yuliu1;
    }

    public String getYuliu2() {
        return this.yuliu2;
    }

    public void setYuliu2(String yuliu2) {
        this.yuliu2 = yuliu2;
    }
}
