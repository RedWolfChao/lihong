package com.quanying.app.bean;

import java.util.List;

public class ZpInteractiveBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * data : [{"id":"133","userid":"1885279","touserid":"1897664","dataid":"11813","noteid":"158","sta":"1","addtime":"1539049699","timeline":"10月09日","face":"http://uc.7192.com/avatar.php?uid=1885279&size=big","nickname":"施华洛婚纱摄影（测试）","content":"哈哈","repnote":"好看","title":"中式新娘","thumb":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png"}]
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
         * id : 133
         * userid : 1885279
         * touserid : 1897664
         * dataid : 11813
         * noteid : 158
         * sta : 1
         * addtime : 1539049699
         * timeline : 10月09日
         * face : http://uc.7192.com/avatar.php?uid=1885279&size=big
         * nickname : 施华洛婚纱摄影（测试）
         * content : 哈哈
         * repnote : 好看
         * title : 中式新娘
         * thumb : http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png
         */

        private String id;
        private String userid;
        private String touserid;
        private String dataid;
        private String noteid;
        private String sta;
        private String addtime;
        private String timeline;
        private String face;
        private String nickname;
        private String content;
        private String repnote;
        private String title;
        private String thumb;

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

        public String getTouserid() {
            return touserid;
        }

        public void setTouserid(String touserid) {
            this.touserid = touserid;
        }

        public String getDataid() {
            return dataid;
        }

        public void setDataid(String dataid) {
            this.dataid = dataid;
        }

        public String getNoteid() {
            return noteid;
        }

        public void setNoteid(String noteid) {
            this.noteid = noteid;
        }

        public String getSta() {
            return sta;
        }

        public void setSta(String sta) {
            this.sta = sta;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRepnote() {
            return repnote;
        }

        public void setRepnote(String repnote) {
            this.repnote = repnote;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
