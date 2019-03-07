package com.quanying.app.bean;

import java.util.List;

public class BuyVipBean {


    /**
     * errcode : 200
     * data : [{"pc":"hr1","title":"一个月VIP会员","price":"500元/30天","dsp":"http://api.7192.com/product_show/0.html"},{"pc":"hr2","title":"两个月VIP会员","price":"700元/60天","dsp":"http://api.7192.com/product_show/1.html"},{"pc":"hr3","title":"半年VIP会员","price":"1200元/半年","dsp":"http://api.7192.com/product_show/2.html"},{"pc":"hr4","title":"年费VIP会员","price":"1800元/全年","dsp":"http://api.7192.com/product_show/3.html"},{"pc":"hr5","title":"VIP签约影楼一年","price":"2200元/一年","dsp":"http://api.7192.com/product_show/4.html"},{"pc":"hr6","title":"VIP签约影楼两年","price":"3200元/两年","dsp":"http://api.7192.com/product_show/5.html"}]
     */

    private String errcode;
    private List<DataBean> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pc : hr1
         * title : 一个月VIP会员
         * price : 500元/30天
         * dsp : http://api.7192.com/product_show/0.html
         */

        private String pc;
        private String title;
        private String price;
        private String dsp;

        public String getPc() {
            return pc;
        }

        public void setPc(String pc) {
            this.pc = pc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDsp() {
            return dsp;
        }

        public void setDsp(String dsp) {
            this.dsp = dsp;
        }
    }
}
