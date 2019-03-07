package com.quanying.app.bean;

import java.util.List;

public class UserCollectionBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * data : [{"userid":"1897664","dataid":"11838","sort":"78","addtime":"1539425596","timeline":"1天前","face":"http://uc.7192.com/avatar.php?uid=1940475&size=big","thumb":"http://static.7192.com/upload/zp/001/940/475/93124/20181013111441538_real.png","title":"作品","nickname":"app17633163328"},{"userid":"1897664","dataid":"11818","sort":"77","addtime":"1538981792","timeline":"10月08日","face":"http://uc.7192.com/avatar.php?uid=1939307&size=big","thumb":"http://static.7192.com/upload/zp/001/939/307/93074/20181008103552413_real.png","title":"作品","nickname":"朱家敏"},{"userid":"1897664","dataid":"11804","sort":"76","addtime":"1538730233","timeline":"10月05日","face":"http://uc.7192.com/avatar.php?uid=1938891&size=big","thumb":"http://static.7192.com/upload/zp/001/938/891/92964/20180925181542722_real.png","title":"婚纱照","nickname":"李晓英"},{"userid":"1897664","dataid":"11302","sort":"43","addtime":"1508397309","timeline":"10月19日","face":"http://uc.7192.com/avatar.php?uid=1900009&size=big","thumb":"http://static.7192.com/upload/zp/001/900/009/90286/20171009235950279_real.png","title":"自己的作品","nickname":"张琪"}]
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
         * userid : 1897664
         * dataid : 11838
         * sort : 78
         * addtime : 1539425596
         * timeline : 1天前
         * face : http://uc.7192.com/avatar.php?uid=1940475&size=big
         * thumb : http://static.7192.com/upload/zp/001/940/475/93124/20181013111441538_real.png
         * title : 作品
         * nickname : app17633163328
         */

        private String userid;
        private String dataid;
        private String sort;
        private String addtime;
        private String timeline;
        private String face;
        private String thumb;
        private String title;
        private String nickname;

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
