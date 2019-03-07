package com.quanying.app.bean;

import java.util.List;

public class SearchBean {


    /**
     * errcode : 200
     * errmsg : success
     * ck : {"PHPSESSID":"vlhn26joob0nhv73ijp08e0eg4"}
     * data : [{"uid":"103410","username":"唐则已","nickname":"ggffgh","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=103410&size=big"},{"uid":"1940305","username":"萧山至上摄影","nickname":"萧山至上摄影","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=1940305&size=big"},{"uid":"1934818","username":"sky8329","nickname":"sky8329","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=1934818&size=big"},{"uid":"719422","username":"丁雨yyy","nickname":"丁雨yyy","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=719422&size=big"},{"uid":"1883282","username":"yanzi6122","nickname":"真善美婚纱摄影概念店","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=1883282&size=big"},{"uid":"1677191","username":"sunbao123天","nickname":"sunbao123天","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=1677191&size=big"},{"uid":"741600","username":"大理施华洛婚纱","nickname":"大理施华洛国际婚纱","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=741600&size=big"},{"uid":"810014","username":"爱情城堡","nickname":"爱情城堡","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=810014&size=big"},{"uid":"1940452","username":"懿美阁","nickname":"懿美阁","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=1940452&size=big"},{"uid":"1939509","username":"游美户外文化","nickname":"游美户外文化","hrtype":"2","face":"http://uc.7192.com/avatar.php?uid=1939509&size=big"}]
     */

    private String errcode;
    private String errmsg;

    private List<DataBean> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
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
         * uid : 103410
         * username : 唐则已
         * nickname : ggffgh
         * hrtype : 2
         * face : http://uc.7192.com/avatar.php?uid=103410&size=big
         */

        private String uid;
        private String username;
        private String nickname;
        private String hrtype;
        private String face;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
