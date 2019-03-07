package com.quanying.app.bean;

import java.util.List;

public class ShowRoomBean {

    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * data : [{"id":"12037","userid":"1942156","areaid":"636","tp":"1","dataid":"93867","title":"测试","dsp":"测试","thumb":"http://static.7192.com/upload/zp/001/942/156/93867http://api.7192.com/static/temp/20190216","addtime":"2分钟前","zaned":"2","zannum":"0","plnum":"0","imgnum":1,"images":[{"imgid":"519966","userid":"1942156","contentid":"93867","thumb":"http://api.7192.com/static/temp/20190216165707375_real_thumb.jpg"}],"face":"http://uc.7192.com/avatar.php?uid=1942156&size=big","nickname":"吴照智","hrtype":"1"},{"id":"12036","userid":"1897664","areaid":"908","tp":"1","dataid":"93866","title":"哈哈","dsp":"1","thumb":"http://static.7192.com/upload/zp/001/897/664/93866http://api.7192.com/static/temp/20190216","addtime":"13分钟前","zaned":"2","zannum":"0","plnum":"0","imgnum":1,"images":[{"imgid":"519964","userid":"1897664","contentid":"93866","thumb":"http://api.7192.com/static/temp/20190216164555369_real_thumb.jpg"}],"face":"http://uc.7192.com/avatar.php?uid=1897664&size=big","nickname":"技术部王凯文测试","hrtype":"1"},{"id":"12035","userid":"1942156","areaid":"636","tp":"1","dataid":"93865","title":"测试","dsp":"测试","thumb":"http://static.7192.com/upload/zp/001/942/156/93865http://api.7192.com/static/temp/20190216","addtime":"22分钟前","zaned":"2","zannum":"0","plnum":"0","imgnum":0,"images":{"thumb":"http://static.7192.com/upload/zp/001/942/156/93865http://api.7192.com/static/temp/20190216"},"face":"http://uc.7192.com/avatar.php?uid=1942156&size=big","nickname":"吴照智","hrtype":"1"}]
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
         * id : 12037
         * userid : 1942156
         * areaid : 636
         * tp : 1
         * dataid : 93867
         * title : 测试
         * dsp : 测试
         * thumb : http://static.7192.com/upload/zp/001/942/156/93867http://api.7192.com/static/temp/20190216
         * addtime : 2分钟前
         * zaned : 2
         * zannum : 0
         * plnum : 0
         * imgnum : 1
         * images : [{"imgid":"519966","userid":"1942156","contentid":"93867","thumb":"http://api.7192.com/static/temp/20190216165707375_real_thumb.jpg"}]
         * face : http://uc.7192.com/avatar.php?uid=1942156&size=big
         * nickname : 吴照智
         * hrtype : 1
         */

        private String id;
        private String userid;
        private String areaid;
        private String tp;
        private String dataid;
        private String title;
        private String dsp;
        private String thumb;
        private String addtime;
        private String zaned;
        private String zannum;
        private String plnum;
        private int imgnum;
        private String face;
        private String nickname;
        private String hrtype;
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

        public String getZaned() {
            return zaned;
        }

        public void setZaned(String zaned) {
            this.zaned = zaned;
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

        public String getHrtype() {
            return hrtype;
        }

        public void setHrtype(String hrtype) {
            this.hrtype = hrtype;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * imgid : 519966
             * userid : 1942156
             * contentid : 93867
             * thumb : http://api.7192.com/static/temp/20190216165707375_real_thumb.jpg
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
}
