package com.qyw.newdemo.app.entity;

import java.util.List;

public class HomePageHeaderBean {


    /**
     * isvip : no
     * errcode : 200
     * uid : 114197
     * hrtype : 2
     * isMyself : 1
     * face : http://uc.7192.com/avatar.php?uid=114197&size=big
     * carenum : 59
     * fansnum : 0
     * info : {"userid":"114197","nickname":"潍坊高端婚纱摄影1","gender":"男","birthday":"0","areaid":26,"sign":"","labs":"","bg":"","updatetime":"0","images":[{"thumb":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936906_thumb.jpg","src":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936906.jpg"},{"thumb":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936766_thumb.jpg","src":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936766.jpg"},{"thumb":"http://static.7192.com/upload/job/000/114/197/190669/000114197_082045677_thumb.jpg","src":"http://static.7192.com/upload/job/000/114/197/190669/000114197_082045677.jpg"}],"areaname":"天津市 红桥区","age":"47"}
     */

    private String isvip;
    private String errcode;
    private String uid;
    private String hrtype;
    private String isMyself;
    private String face;
    private String carenum;
    private String fansnum;
    private InfoBean info;

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHrtype() {
        return hrtype;
    }

    public void setHrtype(String hrtype) {
        this.hrtype = hrtype;
    }

    public String getIsMyself() {
        return isMyself;
    }

    public void setIsMyself(String isMyself) {
        this.isMyself = isMyself;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getCarenum() {
        return carenum;
    }

    public void setCarenum(String carenum) {
        this.carenum = carenum;
    }

    public String getFansnum() {
        return fansnum;
    }

    public void setFansnum(String fansnum) {
        this.fansnum = fansnum;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * userid : 114197
         * nickname : 潍坊高端婚纱摄影1
         * gender : 男
         * birthday : 0
         * areaid : 26
         * sign :
         * labs :
         * bg :
         * updatetime : 0
         * images : [{"thumb":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936906_thumb.jpg","src":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936906.jpg"},{"thumb":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936766_thumb.jpg","src":"http://static.7192.com/upload/job/000/114/197/190239/000114197_162936766.jpg"},{"thumb":"http://static.7192.com/upload/job/000/114/197/190669/000114197_082045677_thumb.jpg","src":"http://static.7192.com/upload/job/000/114/197/190669/000114197_082045677.jpg"}]
         * areaname : 天津市 红桥区
         * age : 47
         */

        private String userid;
        private String nickname;
        private String gender;
        private String birthday;
        private int areaid;
        private String sign;
        private String labs;
        private String bg;
        private String updatetime;
        private String areaname;
        private String age;
        private List<ImagesBean> images;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getAreaid() {
            return areaid;
        }

        public void setAreaid(int areaid) {
            this.areaid = areaid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getLabs() {
            return labs;
        }

        public void setLabs(String labs) {
            this.labs = labs;
        }

        public String getBg() {
            return bg;
        }

        public void setBg(String bg) {
            this.bg = bg;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * thumb : http://static.7192.com/upload/job/000/114/197/190239/000114197_162936906_thumb.jpg
             * src : http://static.7192.com/upload/job/000/114/197/190239/000114197_162936906.jpg
             */

            private String thumb;
            private String src;

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
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
