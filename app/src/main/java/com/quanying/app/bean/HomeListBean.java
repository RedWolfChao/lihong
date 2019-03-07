package com.quanying.app.bean;

import java.util.List;

public class HomeListBean {


    /**
     * errcode : 200
     * errmsg : success
     * ck : {"PHPSESSID":"vlhn26joob0nhv73ijp08e0eg4"}
     * other : {"m":"api","mod":"photo","c":"cases","token":"","offset":"3"}
     * get : {"m":"api","mod":"photo","c":"cases"}
     * post : {"token":"","offset":"3"}
     * data : [{"id":"11788","userid":"1885279","areaid":"1428","tp":"3","dataid":"57","title":"公司案例","dsp":"案例内容1364664848949484884849497654545jgjgmkgjgjtgjgjpjpwptwptpwpwpwpxptpmgmgngmgmpnpmgmgmgngmgmgjgjgjwgwgmgmjgjgjgmjgjgwgwgwgwgkgm","thumb":"http://static.7192.com/upload/zp/001/885/279/57/20180917110035583_real.png","addtime":"09月17日","zannum":"0","plnum":"0","imgnum":1,"images":[{"imgid":"125","userid":"1885279","contentid":"57","thumb":"http://static.7192.com/upload/zp/001/885/279/57/20180917110035583_real_thumb.png"}],"face":"http://uc.7192.com/avatar.php?uid=1885279&size=big","nickname":"施华洛婚纱摄影（测试）"},{"id":"11684","userid":"1900891","areaid":"963","tp":"3","dataid":"56","title":"招聘儿童摄影","dsp":"需要全面儿童摄影师 只要你足够优秀 工资不是问题 坐标温州","thumb":"http://static.7192.com/upload/zp/001/900/891/56/20180722232308571_real.png","addtime":"07月22日","zannum":"0","plnum":"0","imgnum":3,"images":[{"imgid":"122","userid":"1900891","contentid":"56","thumb":"http://static.7192.com/upload/zp/001/900/891/56/20180722232308571_real_thumb.png"},{"imgid":"123","userid":"1900891","contentid":"56","thumb":"http://static.7192.com/upload/zp/001/900/891/56/20180722232309923_real_thumb.png"},{"imgid":"124","userid":"1900891","contentid":"56","thumb":"http://static.7192.com/upload/zp/001/900/891/56/20180722232310247_real_thumb.png"}],"face":"http://uc.7192.com/avatar.php?uid=1900891&size=big","nickname":"哆啦A梦儿童摄影"},{"id":"11660","userid":"1926790","areaid":"10","tp":"3","dataid":"55","title":"藝视觉数码工作室","dsp":"客片展示","thumb":"http://static.7192.com/upload/zp/001/926/790/55/20180614105319553_real.png","addtime":"06月14日","zannum":"1","plnum":"0","imgnum":7,"images":[{"imgid":"115","userid":"1926790","contentid":"55","thumb":"http://static.7192.com/upload/zp/001/926/790/55/20180614105319553_real_thumb.png"},{"imgid":"116","userid":"1926790","contentid":"55","thumb":"http://static.7192.com/upload/zp/001/926/790/55/20180614105324531_real_thumb.png"},{"imgid":"117","userid":"1926790","contentid":"55","thumb":"http://static.7192.com/upload/zp/001/926/790/55/20180614105331279_real_thumb.png"}],"face":"http://uc.7192.com/avatar.php?uid=1926790&size=big","nickname":"app13717518130"}]
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
        /**
         * PHPSESSID : vlhn26joob0nhv73ijp08e0eg4
         */

        private String PHPSESSID;

        public String getPHPSESSID() {
            return PHPSESSID;
        }

        public void setPHPSESSID(String PHPSESSID) {
            this.PHPSESSID = PHPSESSID;
        }
    }


    public static class DataBean {
        /**
         * id : 11788
         * userid : 1885279
         * areaid : 1428
         * tp : 3
         * dataid : 57
         * title : 公司案例
         * dsp : 案例内容1364664848949484884849497654545jgjgmkgjgjtgjgjpjpwptwptpwpwpwpxptpmgmgngmgmpnpmgmgmgngmgmgjgjgjwgwgmgmjgjgjgmjgjgwgwgwgwgkgm
         * thumb : http://static.7192.com/upload/zp/001/885/279/57/20180917110035583_real.png
         * addtime : 09月17日
         * zannum : 0
         * plnum : 0
         * imgnum : 1
         * images : [{"imgid":"125","userid":"1885279","contentid":"57","thumb":"http://static.7192.com/upload/zp/001/885/279/57/20180917110035583_real_thumb.png"}]
         * face : http://uc.7192.com/avatar.php?uid=1885279&size=big
         * nickname : 施华洛婚纱摄影（测试）
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
        private String zannum;
        private String plnum;
        private int imgnum;
        private String face;
        private String nickname;
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

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * imgid : 125
             * userid : 1885279
             * contentid : 57
             * thumb : http://static.7192.com/upload/zp/001/885/279/57/20180917110035583_real_thumb.png
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
