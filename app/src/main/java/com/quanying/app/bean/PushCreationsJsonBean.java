package com.quanying.app.bean;

import java.util.ArrayList;

public class PushCreationsJsonBean {

  /*
  *
  *    info{
    "sort": [

	],
    "title": "我的标题",
    "content": "我的描述",
    "cover": "3"

}
  * */



  private String title;
  private String content;
  private String cover;
  private ArrayList<Integer> sort;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public ArrayList getSort() {
    return sort;
  }

  public void setSort(ArrayList sort) {
    this.sort = sort;
  }


}
