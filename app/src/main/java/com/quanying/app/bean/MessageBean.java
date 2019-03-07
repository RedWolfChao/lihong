package com.quanying.app.bean;

import java.util.List;

public class MessageBean {


    /**
     * errcode : 200
     * errmsg : succes
     * data : [{"title":"相约上海展会","intro":"第35届中国·上海国际婚纱摄影器材展会","timeline":"2019-01-09","link":"http://m.7192.com/news/167682.shtml"},{"title":"全影智慧节","intro":"2018互联网+摄影行业发展大会（第三届）","timeline":"2018-12-19","link":"http://m.7192.com/news/167683.shtml"},{"title":"中国儿童摄影行业","intro":"第十届中国儿童摄影行业发展峰会","timeline":"2018-11-11","link":"http://m.7192.com/news/167683.shtml"}]
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

    public static class DataBean {
        /**
         * title : 相约上海展会
         * intro : 第35届中国·上海国际婚纱摄影器材展会
         * timeline : 2019-01-09
         * link : http://m.7192.com/news/167682.shtml
         */

        private String title;
        private String intro;
        private String timeline;
        private String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
