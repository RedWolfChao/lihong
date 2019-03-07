package com.quanying.app.bean;

import java.util.List;

public class GetTagBean {


    /**
     * errcode : 200
     * errmsg : lab list
     * labs : ["摄影师","化妆师","数码师","模特","店长","摄像师","主持人","门市"]
     */

    private String errcode;
    private String errmsg;
    private List<String> labs;

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

    public List<String> getLabs() {
        return labs;
    }

    public void setLabs(List<String> labs) {
        this.labs = labs;
    }
}
