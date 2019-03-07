package com.quanying.app.bean;

import java.util.List;

public class QyUserPageBean {


    /**
     * errcode : 200
     * uid : 1885279
     * hrtype : 2
     * isMyself : 2
     * iscert : 1
     * face : http://uc.7192.com/avatar.php?uid=1885279&size=big
     * carenum : 3
     * fansnum : 1
     * hascare : 1
     * info : {"userid":"1885279","nickname":"婚纱摄影（测试）","gender":"男","birthday":"0","areaid":1823,"sign":"我们是最棒的","labs":"","bg":"http://static.7192.com/uploadfile/2018/0809/20180809201526919_real.png","updatetime":"1533817034","sharelink":"http://m.7192.com/","images":[{"id":"132","userid":"1885279","src":"http://static.7192.com/uploadfile/2018/1011/20181011140227575_real.png","sta":"1","addtime":"1539237747"},{"id":"135","userid":"1885279","src":"http://static.7192.com/uploadfile/2018/1022/20181022144353247_real.png","sta":"1","addtime":"1540190632"},{"id":"136","userid":"1885279","src":"http://static.7192.com/uploadfile/2018/1022/20181022144407637_real.png","sta":"1","addtime":"1540190647"}],"isvip":"yes","areaname":"","age":"48"}
     * hx_name : hx_1885279
     * hx_pwd : qy_1885279
     */

    private String errcode;
    private String uid;
    private String hrtype;
    private String isMyself;
    private String iscert;
    private String face;
    private String carenum;
    private String fansnum;
    private String hascare;
    private InfoBean info;
    private String hx_name;
    private String hx_pwd;

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

    public String getIscert() {
        return iscert;
    }

    public void setIscert(String iscert) {
        this.iscert = iscert;
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

    public String getHascare() {
        return hascare;
    }

    public void setHascare(String hascare) {
        this.hascare = hascare;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getHx_name() {
        return hx_name;
    }

    public void setHx_name(String hx_name) {
        this.hx_name = hx_name;
    }

    public String getHx_pwd() {
        return hx_pwd;
    }

    public void setHx_pwd(String hx_pwd) {
        this.hx_pwd = hx_pwd;
    }

    public static class InfoBean {
        /**
         * userid : 1885279
         * nickname : 婚纱摄影（测试）
         * gender : 男
         * birthday : 0
         * areaid : 1823
         * sign : 我们是最棒的
         * labs :
         * bg : http://static.7192.com/uploadfile/2018/0809/20180809201526919_real.png
         * updatetime : 1533817034
         * sharelink : http://m.7192.com/
         * images : [{"id":"132","userid":"1885279","src":"http://static.7192.com/uploadfile/2018/1011/20181011140227575_real.png","sta":"1","addtime":"1539237747"},{"id":"135","userid":"1885279","src":"http://static.7192.com/uploadfile/2018/1022/20181022144353247_real.png","sta":"1","addtime":"1540190632"},{"id":"136","userid":"1885279","src":"http://static.7192.com/uploadfile/2018/1022/20181022144407637_real.png","sta":"1","addtime":"1540190647"}]
         * isvip : yes
         * areaname :
         * age : 48
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
        private String sharelink;
        private String isvip;
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

        public String getSharelink() {
            return sharelink;
        }

        public void setSharelink(String sharelink) {
            this.sharelink = sharelink;
        }

        public String getIsvip() {
            return isvip;
        }

        public void setIsvip(String isvip) {
            this.isvip = isvip;
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
             * id : 132
             * userid : 1885279
             * src : http://static.7192.com/uploadfile/2018/1011/20181011140227575_real.png
             * sta : 1
             * addtime : 1539237747
             */

            private String id;
            private String userid;
            private String src;
            private String sta;
            private String addtime;

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

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
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
        }
    }
}
