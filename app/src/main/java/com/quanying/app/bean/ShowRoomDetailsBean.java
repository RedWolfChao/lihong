package com.quanying.app.bean;

import java.util.List;

public class ShowRoomDetailsBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * isdel : no
     * data : {"id":"11813","userid":"1936910","areaid":"0","tp":"1","dataid":"93061","title":"中式新娘","dsp":"中式新娘","thumb":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png","addtime":"1538741198","face":"http://uc.7192.com/avatar.php?uid=1936910&size=big","nickname":"Elsa","timeline":"2天前","zannum":"1","plnum":"4","favsta":"no","zansta":"yes","hitsnum":"0","hrtype":"0","images":[{"imgid":"513111","userid":"1936910","contentid":"93061","thumb":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real_thumb.png","imageurl":"/20181005200635452_real.png","intro":"图片描述","w":"1000","h":"1334","views":"0","comments":"0","favs":"0","good":"0","bad":"0","sort":"0","status":"1","src":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png"},{"imgid":"513112","userid":"1936910","contentid":"93061","thumb":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200637315_real_thumb.png","imageurl":"/20181005200637315_real.png","intro":"图片描述","w":"1000","h":"1334","views":"0","comments":"0","favs":"0","good":"0","bad":"0","sort":"1","status":"1","src":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200637315_real.png"}],"sharelink":"http://m.7192.com/m1936910_93061"}
     */

    private String errcode;
    private int uid;
    private String hrtype;
    private String errmsg;
    private String isdel;
    private DataBean data;

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

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11813
         * userid : 1936910
         * areaid : 0
         * tp : 1
         * dataid : 93061
         * title : 中式新娘
         * dsp : 中式新娘
         * thumb : http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png
         * addtime : 1538741198
         * face : http://uc.7192.com/avatar.php?uid=1936910&size=big
         * nickname : Elsa
         * timeline : 2天前
         * zannum : 1
         * plnum : 4
         * favsta : no
         * zansta : yes
         * hitsnum : 0
         * hrtype : 0
         * images : [{"imgid":"513111","userid":"1936910","contentid":"93061","thumb":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real_thumb.png","imageurl":"/20181005200635452_real.png","intro":"图片描述","w":"1000","h":"1334","views":"0","comments":"0","favs":"0","good":"0","bad":"0","sort":"0","status":"1","src":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png"},{"imgid":"513112","userid":"1936910","contentid":"93061","thumb":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200637315_real_thumb.png","imageurl":"/20181005200637315_real.png","intro":"图片描述","w":"1000","h":"1334","views":"0","comments":"0","favs":"0","good":"0","bad":"0","sort":"1","status":"1","src":"http://static.7192.com/upload/zp/001/936/910/93061/20181005200637315_real.png"}]
         * sharelink : http://m.7192.com/m1936910_93061
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
        private String face;
        private String nickname;
        private String timeline;
        private String zannum;
        private String plnum;
        private String favsta;
        private String zansta;
        private String hitsnum;
        private String hrtype;
        private String sharelink;
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

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
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

        public String getFavsta() {
            return favsta;
        }

        public void setFavsta(String favsta) {
            this.favsta = favsta;
        }

        public String getZansta() {
            return zansta;
        }

        public void setZansta(String zansta) {
            this.zansta = zansta;
        }

        public String getHitsnum() {
            return hitsnum;
        }

        public void setHitsnum(String hitsnum) {
            this.hitsnum = hitsnum;
        }

        public String getHrtype() {
            return hrtype;
        }

        public void setHrtype(String hrtype) {
            this.hrtype = hrtype;
        }

        public String getSharelink() {
            return sharelink;
        }

        public void setSharelink(String sharelink) {
            this.sharelink = sharelink;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * imgid : 513111
             * userid : 1936910
             * contentid : 93061
             * thumb : http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real_thumb.png
             * imageurl : /20181005200635452_real.png
             * intro : 图片描述
             * w : 1000
             * h : 1334
             * views : 0
             * comments : 0
             * favs : 0
             * good : 0
             * bad : 0
             * sort : 0
             * status : 1
             * src : http://static.7192.com/upload/zp/001/936/910/93061/20181005200635452_real.png
             */

            private String imgid;
            private String userid;
            private String contentid;
            private String thumb;
            private String imageurl;
            private String intro;
            private String w;
            private String h;
            private String views;
            private String comments;
            private String favs;
            private String good;
            private String bad;
            private String sort;
            private String status;
            private String src;

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

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }

            public String getH() {
                return h;
            }

            public void setH(String h) {
                this.h = h;
            }

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public String getFavs() {
                return favs;
            }

            public void setFavs(String favs) {
                this.favs = favs;
            }

            public String getGood() {
                return good;
            }

            public void setGood(String good) {
                this.good = good;
            }

            public String getBad() {
                return bad;
            }

            public void setBad(String bad) {
                this.bad = bad;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }
}
