package com.quanying.app.bean;

import java.util.List;

public class NewsBean {


    /**
     * isvip : yes
     * errcode : 200
     * uid : 1901470
     * hrtype : 2
     * errmsg : success
     * data : [{"messageid":"32784","send_from_id":"0","send_to_id":"1901470","folder":"all","status":"0","message_time":"1521185487","subject":"微信认证 - <font color=\"green\">已通过<\/font>","is_ad":"0","replyid":"0","system":"0","is_link":"0","timeline":"03月16日","title":"微信认证 - <font color=\"green\">已通过<\/font>","link":"http://m.7192.com","thumb":"http://static.7192.com/img/app/news_icon.jpg"},{"messageid":"22000","send_from_id":"0","send_to_id":"1901470","folder":"all","status":"0","message_time":"1458439205","subject":"全影人才网VIP服务即将到期提醒","is_ad":"0","replyid":"0","system":"0","is_link":"0","timeline":"03月20日","title":"全影人才网VIP服务即将到期提醒","link":"http://m.7192.com","thumb":"http://static.7192.com/img/app/news_icon.jpg"}]
     */

    private String isvip;
    private String errcode;
    private int uid;
    private String hrtype;
    private String errmsg;
    private List<DataBean> data;

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
         * messageid : 32784
         * send_from_id : 0
         * send_to_id : 1901470
         * folder : all
         * status : 0
         * message_time : 1521185487
         * subject : 微信认证 - <font color="green">已通过</font>
         * is_ad : 0
         * replyid : 0
         * system : 0
         * is_link : 0
         * timeline : 03月16日
         * title : 微信认证 - <font color="green">已通过</font>
         * link : http://m.7192.com
         * thumb : http://static.7192.com/img/app/news_icon.jpg
         */

        private String messageid;
        private String send_from_id;
        private String send_to_id;
        private String folder;
        private String status;
        private String message_time;
        private String subject;
        private String is_ad;
        private String replyid;
        private String system;
        private String is_link;
        private String timeline;
        private String title;
        private String link;
        private String thumb;

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getSend_from_id() {
            return send_from_id;
        }

        public void setSend_from_id(String send_from_id) {
            this.send_from_id = send_from_id;
        }

        public String getSend_to_id() {
            return send_to_id;
        }

        public void setSend_to_id(String send_to_id) {
            this.send_to_id = send_to_id;
        }

        public String getFolder() {
            return folder;
        }

        public void setFolder(String folder) {
            this.folder = folder;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage_time() {
            return message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getIs_ad() {
            return is_ad;
        }

        public void setIs_ad(String is_ad) {
            this.is_ad = is_ad;
        }

        public String getReplyid() {
            return replyid;
        }

        public void setReplyid(String replyid) {
            this.replyid = replyid;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getIs_link() {
            return is_link;
        }

        public void setIs_link(String is_link) {
            this.is_link = is_link;
        }

        public String getTimeline() {
            return timeline;
        }

        public void setTimeline(String timeline) {
            this.timeline = timeline;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
