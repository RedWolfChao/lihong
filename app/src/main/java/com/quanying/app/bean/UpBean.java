package com.quanying.app.bean;

import java.util.List;

public class UpBean {

    private String id;
    private String userid;
    private String areaid;
    private String tp;
    private String dataid;
    private String title;
    private String dsp;
    private String thumb;
    private String addtime;
    private String zannum;
    private String plnum;
    private int imgnum;
    private String face;
    private String nickname;

    private List<ImagesBean> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDsp() {
        return dsp;
    }

    public void setDsp(String dsp) {
        this.dsp = dsp;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getZannum() {
        return zannum;
    }

    public void setZannum(String zannum) {
        this.zannum = zannum;
    }

    public String getPlnum() {
        return plnum;
    }

    public void setPlnum(String plnum) {
        this.plnum = plnum;
    }

    public int getImgnum() {
        return imgnum;
    }

    public void setImgnum(int imgnum) {
        this.imgnum = imgnum;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * imgid : 511531
         * userid : 1897664
         * contentid : 92881
         * thumb : http://static.7192.com/upload/zp/001/897/664/92881/20180915084559287_real_thumb.png
         */

        private String imgid;
        private String userid;
        private String contentid;
        private String thumb;

        public String getImgid() {
            return imgid;
        }

        public void setImgid(String imgid) {
            this.imgid = imgid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getContentid() {
            return contentid;
        }

        public void setContentid(String contentid) {
            this.contentid = contentid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}