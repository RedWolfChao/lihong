package com.quanying.app.zhibo;

public class AnyEventType {

  private String status="";

  private  String callBackStatus ="";

  public String getCallBackStatus() {
    return callBackStatus;
  }

  public void setCallBackStatus(String callBackStatus) {
    this.callBackStatus = callBackStatus;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public AnyEventType(){}

  public AnyEventType(String status) {
    this.status = status;
  }
}