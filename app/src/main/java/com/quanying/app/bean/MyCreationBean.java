package com.quanying.app.bean;

import java.util.List;

public class MyCreationBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * data : [{"id":"11786","userid":"1897664","areaid":"0","tp":"1","dataid":"92881","title":"啦啦啦","dsp":"内容","thumb":"http://static.7192.com/upload/zp/001/897/664/92881/20180915084559287_real.png","addtime":"09月15日","zannum":"0","plnum":"0","imgnum":4,"images":[{"imgid":"511531","userid":"1897664","contentid":"92881","thumb":"http://static.7192.com/upload/zp/001/897/664/92881/20180915084559287_real_thumb.png"},{"imgid":"511532","userid":"1897664","contentid":"92881","thumb":"http://static.7192.com/upload/zp/001/897/664/92881/20180915084600533_real_thumb.png"},{"imgid":"511533","userid":"1897664","contentid":"92881","thumb":"http://static.7192.com/upload/zp/001/897/664/92881/20180915084600734_real_thumb.png"}],"face":"http://uc.7192.com/avatar.php?uid=1897664&size=big","nickname":"super"}]
     */

    private String errcode;
    private int uid;
    private String hrtype;
    private String errmsg;
    private List<UpBean> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHrtype() {
        return hrtype;
    }

    public void setHrtype(String hrtype) {
        this.hrtype = hrtype;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<UpBean> getData() {
        return data;
    }

    public void setData(List<UpBean> data) {
        this.data = data;
    }


}
