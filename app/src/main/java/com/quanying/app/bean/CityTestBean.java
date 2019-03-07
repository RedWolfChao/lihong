package com.quanying.app.bean;

import java.util.List;

public class CityTestBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * errmsg : success
     * data : [{"id":"4721","userid":"193357","areaid":"1944","tp":"1","dataid":"84123","title":"2014","dsp":"","thumb":"http://static.7192.com/upload/zp/000/193/357/84123/000193357_050846725.jpg","addtime":"04月26日","zaned":"2","zannum":"0","plnum":"0","imgnum":0,"images":{"thumb":"http://static.7192.com/upload/zp/000/193/357/84123/000193357_050846725.jpg"},"face":"http://uc.7192.com/avatar.php?uid=193357&size=big","nickname":"大侠","hrtype":"1"},{"id":"2561","userid":"810077","areaid":"1944","tp":"1","dataid":"81804","title":"旗袍","dsp":"","thumb":"http://static.7192.com/upload/zp/000/810/077/81804/000810077_184628748.jpg","addtime":"12月05日","zaned":"2","zannum":"0","plnum":"0","imgnum":8,"images":[{"imgid":"402010","userid":"810077","contentid":"81804","thumb":"http://static.7192.com/upload/zp/000/810/077/81804/000810077_184621417_thumb.jpg"},{"imgid":"402011","userid":"810077","contentid":"81804","thumb":"http://static.7192.com/upload/zp/000/810/077/81804/000810077_184622747_thumb.jpg"},{"imgid":"402012","userid":"810077","contentid":"81804","thumb":"http://static.7192.com/upload/zp/000/810/077/81804/000810077_184623652_thumb.jpg"}],"face":"http://uc.7192.com/avatar.php?uid=810077&size=big","nickname":"阿德","hrtype":"1"},{"id":"1772","userid":"723674","areaid":"1944","tp":"1","dataid":"80949","title":"作品","dsp":"","thumb":"http://static.7192.com/upload/zp/000/723/674/80949/000723674_005027740.jpg","addtime":"09月23日","zaned":"2","zannum":"0","plnum":"0","imgnum":12,"images":[{"imgid":"393396","userid":"723674","contentid":"80949","thumb":"http://static.7192.com/upload/zp/000/723/674/80949/000723674_005023562_thumb.jpg"},{"imgid":"393398","userid":"723674","contentid":"80949","thumb":"http://static.7192.com/upload/zp/000/723/674/80949/000723674_005027740_thumb.jpg"},{"imgid":"393399","userid":"723674","contentid":"80949","thumb":"http://static.7192.com/upload/zp/000/723/674/80949/000723674_005033338_thumb.jpg"}],"face":"http://uc.7192.com/avatar.php?uid=723674&size=big","nickname":"李鹏","hrtype":"1"}]
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
         * id : 4721
         * userid : 193357
         * areaid : 1944
         * tp : 1
         * dataid : 84123
         * title : 2014
         * dsp :
         * thumb : http://static.7192.com/upload/zp/000/193/357/84123/000193357_050846725.jpg
         * addtime : 04月26日
         * zaned : 2
         * zannum : 0
         * plnum : 0
         * imgnum : 0
         * images : {"thumb":"http://static.7192.com/upload/zp/000/193/357/84123/000193357_050846725.jpg"}
         * face : http://uc.7192.com/avatar.php?uid=193357&size=big
         * nickname : 大侠
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
        private ImagesBean images;
        private String face;
        private String nickname;
        private String hrtype;

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

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
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

        public static class ImagesBean {
            /**
             * thumb : http://static.7192.com/upload/zp/000/193/357/84123/000193357_050846725.jpg
             */

            private String thumb;

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }
}
