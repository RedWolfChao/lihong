package com.quanying.app.bean;

import java.util.List;

public class ZbjBean {
    /**
     * errcode : 200
     * errmsg : 直播中
     * title : 小程序-大未来
     * view : 1360
     * room : http://tv.7192.com/detail/34.html
     * vip : http://tv.7192.com/pay/34.html
     * face : https://tva1.sinaimg.cn/crop.0.0.180.180.180/8705f115jw1e8qgp5bmzyj2050050aa8.jpg
     * ad : {"main":"http://staticmv.7192.com/huiyi/ad_34.png","icons":["http://staticmv.7192.com/huiyi/ad_34_0.png","http://staticmv.7192.com/huiyi/ad_34_1.png","http://staticmv.7192.com/huiyi/ad_34_2.png","http://staticmv.7192.com/huiyi/ad_34_3.png"]}
     * playUrl : {"rtmp":"rtmp://live.7192.com/qy/tv34?auth_key=1542547309-0-0-963a29e965df225e6c6c650b1373f707","flv":"http://live.7192.com/qy/tv34.flv?auth_key=1542547309-0-0-991e1701888915925e381a5fd3a53488"}
     */

    private String errcode;
    private String errmsg;
    private String urlmark="";

    private String title;
    private String cj;
    private String view;
    private String room;
    private String vip;
    private String face;
    private AdBean ad;
    private PlayUrlBean playUrl;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getUrlmark() {
        return urlmark;
    }

    public void setUrlmark(String urlmark) {
        this.urlmark = urlmark;
    }
    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTitle() {
        return title;
    }

    public String getCj() {
        return cj;
    }

    public void setCj(String cj) {
        this.cj = cj;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public PlayUrlBean getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(PlayUrlBean playUrl) {
        this.playUrl = playUrl;
    }

    public static class AdBean {
        /**
         * main : http://staticmv.7192.com/huiyi/ad_34.png
         * icons : ["http://staticmv.7192.com/huiyi/ad_34_0.png","http://staticmv.7192.com/huiyi/ad_34_1.png","http://staticmv.7192.com/huiyi/ad_34_2.png","http://staticmv.7192.com/huiyi/ad_34_3.png"]
         */

        private String main;
        private List<String> icons;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public List<String> getIcons() {
            return icons;
        }

        public void setIcons(List<String> icons) {
            this.icons = icons;
        }
    }

    public static class PlayUrlBean {
        /**
         * rtmp : rtmp://live.7192.com/qy/tv34?auth_key=1542547309-0-0-963a29e965df225e6c6c650b1373f707
         * flv : http://live.7192.com/qy/tv34.flv?auth_key=1542547309-0-0-991e1701888915925e381a5fd3a53488
         */

        private String rtmp;
        private String flv;

        public String getRtmp() {
            return rtmp;
        }

        public void setRtmp(String rtmp) {
            this.rtmp = rtmp;
        }

        public String getFlv() {
            return flv;
        }

        public void setFlv(String flv) {
            this.flv = flv;
        }
    }

    /**
     * errcode : 200
     * errmsg : 直播中
     * title : 影楼招聘存在的难题
     * view : 1270
     * room : http://tv.7192.com/room/35.html
     * face : https://tva1.sinaimg.cn/crop.0.0.180.180.180/8705f115jw1e8qgp5bmzyj2050050aa8.jpg
     * ad : {"main":"http://staticmv.7192.com/huiyi/ad_34.png","icons":["http://staticmv.7192.com/huiyi/ad_34_0.png","http://staticmv.7192.com/huiyi/ad_34_1.png","http://staticmv.7192.com/huiyi/ad_34_2.png","http://staticmv.7192.com/huiyi/ad_34_3.png"]}
     * playUrl : {"rtmp":"rtmp://live.7192.com/qy/tv35?auth_key=1541640557-0-0-4824980826f3373a787e02703ea09f8a","flv":"http://live.7192.com/qy/tv35.flv?auth_key=1541640557-0-0-61d70496e566e7bd81b831513da8c731"}
     */

}
