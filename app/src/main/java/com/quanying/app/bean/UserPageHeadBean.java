package com.quanying.app.bean;

public class UserPageHeadBean {


    /**
     * errcode : 200
     * uid : 1897664
     * hrtype : 1
     * isMyself : 1
     * face : http://uc.7192.com/avatar.php?uid=1897664&size=big
     * carenum : 0
     * fansnum : 0
     * info : {"userid":"1897664","nickname":"super","gender":"男","birthday":"1538020800","areaid":"0","sign":"换换签名","labs":"店长","bg":"http://static.7192.com/uploadfile/2018/0927/20180927113522213_real.png","updatetime":"1538186614","sharelink":"http://m.7192.com/","areaname":"","age":"0"}
     * hx_name : hx_1897664
     * hx_pwd : qy_1897664
     */

    private String errcode;
    private String uid;
    private String hrtype;
    private String isMyself;
    private String face;
    private String carenum;
    private String hascare;
    private String fansnum;
    private InfoBean info;
    private String hx_name;
    private String hx_pwd;

    public String getHascare() {
        return hascare;
    }

    public void setHascare(String hascare) {
        this.hascare = hascare;
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
         * userid : 1897664
         * nickname : super
         * gender : 男
         * birthday : 1538020800
         * areaid : 0
         * sign : 换换签名
         * labs : 店长
         * bg : http://static.7192.com/uploadfile/2018/0927/20180927113522213_real.png
         * updatetime : 1538186614
         * sharelink : http://m.7192.com/
         * areaname :
         * age : 0
         */

        private String userid;
        private String nickname;
        private String gender;
        private String birthday;
        private String areaid;
        private String sign;
        private String labs;
        private String bg;
        private String updatetime;
        private String sharelink;
        private String areaname;
        private String age;

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

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
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
    }
}
