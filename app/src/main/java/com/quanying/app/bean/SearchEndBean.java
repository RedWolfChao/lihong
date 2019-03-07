package com.quanying.app.bean;

import java.util.List;

public class SearchEndBean {


    /**
     * isvip : yes
     * errcode : 200
     * uid : 1885279
     * hrtype : 2
     * errmsg : success
     * data : [{"uid":"1878757","username":"人才网个人","nickname":"人才网","face":"http://uc.7192.com/avatar.php?uid=1878757&size=big","hrtype":"1"},{"uid":"1613920","username":"聪惠","nickname":"人员测试","face":"http://uc.7192.com/avatar.php?uid=1613920&size=big","hrtype":"1"},{"uid":"1464463","username":"人才测试","nickname":"人才测试","face":"http://uc.7192.com/avatar.php?uid=1464463&size=big","hrtype":"1"},{"uid":"732092","username":"guoqianyu","nickname":"人才网（测试）","face":"http://uc.7192.com/avatar.php?uid=732092&size=big","hrtype":"2"},{"uid":"732070","username":"zxd123456","nickname":"人测试","face":"http://uc.7192.com/avatar.php?uid=732070&size=big","hrtype":"2"},{"uid":"732065","username":"zhoudonghaha","nickname":"人才网（测试）","face":"http://uc.7192.com/avatar.php?uid=732065&size=big","hrtype":"2"},{"uid":"628679","username":"广州人和影楼","nickname":"人和天长地久","face":"http://uc.7192.com/avatar.php?uid=628679&size=big","hrtype":"2"},{"uid":"486854","username":"人像志婚纱摄影","nickname":"人像志婚纱摄影","face":"http://uc.7192.com/avatar.php?uid=486854&size=big","hrtype":"2"},{"uid":"431485","username":"1164791433","nickname":"人才","face":"http://uc.7192.com/avatar.php?uid=431485&size=big","hrtype":"1"},{"uid":"387645","username":"ilythsfd","nickname":"人生如水","face":"http://uc.7192.com/avatar.php?uid=387645&size=big","hrtype":"1"},{"uid":"277341","username":"xiaosexpig","nickname":"人才公司","face":"http://uc.7192.com/avatar.php?uid=277341&size=big","hrtype":"2"},{"uid":"266977","username":"吴西","nickname":"人见人爱摄影","face":"http://uc.7192.com/avatar.php?uid=266977&size=big","hrtype":"2"},{"uid":"244501","username":"人民婚纱摄影","nickname":"人民数码婚纱摄影","face":"http://uc.7192.com/avatar.php?uid=244501&size=big","hrtype":"2"},{"uid":"229886","username":"千祥人民摄丶影","nickname":"人民摄影","face":"http://uc.7192.com/avatar.php?uid=229886&size=big","hrtype":"2"},{"uid":"222376","username":"gzparis","nickname":"人和巴黎婚纱","face":"http://uc.7192.com/avatar.php?uid=222376&size=big","hrtype":"2"},{"uid":"218032","username":"人和巴黎婚纱","nickname":"人和巴黎婚纱","face":"http://uc.7192.com/avatar.php?uid=218032&size=big","hrtype":"2"},{"uid":"205951","username":"542173754","nickname":"人人","face":"http://uc.7192.com/avatar.php?uid=205951&size=big","hrtype":"1"},{"uid":"205432","username":"人像学会晓霞","nickname":"人像摄影学会","face":"http://uc.7192.com/avatar.php?uid=205432&size=big","hrtype":"2"},{"uid":"202760","username":"leeedwards","nickname":"人像","face":"http://uc.7192.com/avatar.php?uid=202760&size=big","hrtype":"1"},{"uid":"198847","username":"8065743795","nickname":"人生入戏","face":"http://uc.7192.com/avatar.php?uid=198847&size=big","hrtype":"1"}]
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
         * uid : 1878757
         * username : 人才网个人
         * nickname : 人才网
         * face : http://uc.7192.com/avatar.php?uid=1878757&size=big
         * hrtype : 1
         */

        private String uid;
        private String username;
        private String nickname;
        private String face;
        private String hrtype;

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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getHrtype() {
            return hrtype;
        }

        public void setHrtype(String hrtype) {
            this.hrtype = hrtype;
        }
    }
}
