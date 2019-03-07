package com.quanying.app.bean;

import java.util.List;

public class MyFansBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * data : [{"userid":1885279,"face":"http://uc.7192.com/avatar.php?uid=1885279&size=big","sign":"我们是最棒的","hf":"one","id":"1539425663","nickname":"施华洛婚纱摄影（测试）","hrtype":"2"}]
     */

    private String errcode;
    private int uid;
    private String hrtype;
    private String errmsg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userid : 1885279
         * face : http://uc.7192.com/avatar.php?uid=1885279&size=big
         * sign : 我们是最棒的
         * hf : one
         * id : 1539425663
         * nickname : 施华洛婚纱摄影（测试）
         * hrtype : 2
         */

        private int userid;
        private String face;
        private String sign;
        private String hf;
        private String id;
        private String nickname;
        private String hrtype;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getHf() {
            return hf;
        }

        public void setHf(String hf) {
            this.hf = hf;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHrtype() {
            return hrtype;
        }

        public void setHrtype(String hrtype) {
            this.hrtype = hrtype;
        }
    }
}
