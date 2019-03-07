package com.quanying.app.bean;

import java.util.List;

public class ShowRoomDetailsCommentsBean {


    /**
     * errcode : 200
     * errmsg : success
     * ck : {"PHPSESSID":"16u82crl7mpps5ks9d5m451r01"}
     * data : [{"id":"157","userid":"1885279","dataid":"11818","pid":"0","puid":"0","sta":"1","content":"哈哈","addtime":"1539049554","timeline":"54分钟前","face":"http://uc.7192.com/avatar.php?uid=1885279&size=big","nickname":"施华洛婚纱摄影（测试）","repname":""},{"id":"156","userid":"1885279","dataid":"11818","pid":"0","puid":"0","sta":"1","content":"特好看","addtime":"1539049536","timeline":"54分钟前","face":"http://uc.7192.com/avatar.php?uid=1885279&size=big","nickname":"施华洛婚纱摄影（测试）","repname":""}]
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

    public static class CkBean {
    }

    public static class DataBean {
        /**
         * id : 157
         * userid : 1885279
         * dataid : 11818
         * pid : 0
         * puid : 0
         * sta : 1
         * content : 哈哈
         * addtime : 1539049554
         * timeline : 54分钟前
         * face : http://uc.7192.com/avatar.php?uid=1885279&size=big
         * nickname : 施华洛婚纱摄影（测试）
         * repname :
         */

        private String id;
        private String userid;
        private String dataid;
        private String pid;
        private String puid;
        private String sta;
        private String content;
        private String addtime;
        private String timeline;
        private String face;
        private String nickname;
        private String repname;

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

        public String getDataid() {
            return dataid;
        }

        public void setDataid(String dataid) {
            this.dataid = dataid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPuid() {
            return puid;
        }

        public void setPuid(String puid) {
            this.puid = puid;
        }

        public String getSta() {
            return sta;
        }

        public void setSta(String sta) {
            this.sta = sta;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
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

        public String getRepname() {
            return repname;
        }

        public void setRepname(String repname) {
            this.repname = repname;
        }
    }
}
