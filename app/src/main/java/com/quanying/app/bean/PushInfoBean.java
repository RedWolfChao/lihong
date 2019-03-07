package com.quanying.app.bean;

public class PushInfoBean {

        private PushCreationsJsonBean info;

        public PushCreationsJsonBean getInfo() {
            return info;
        }

        public void setInfo(PushCreationsJsonBean info) {
            this.info = info;
        }

    @Override
    public String toString() {
        return "PushInfoBean{" +
                "info=" + info +
                '}';
    }
}
