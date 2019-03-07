package com.qyw.newdemo.app.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class HomeFragmentEntity {

    private List<String> banner_Images;//首页轮播图
    private String[] recommend_Imgs;//推荐企业



//    private List<EventsItemEntity> eventsItemEntities;//活动咨询列表*/

/*    private String recommend_Img;
    private String recommend_Url;*/
    private String eventsItemImgs;    //活动咨询图片地址
    private String eventsItemEventsTitles;// 活动咨询标题
    private String eventsItemDate;//活动咨询发布日期


    public HomeFragmentEntity() {
    }

    public void setRecommend_imgs(String[] recommend_imgs) {
        recommend_Imgs = recommend_imgs;
    }
    public String[] getRecommend_imgs() {
        return recommend_Imgs;
    }

    public List<String> getBanner_Images() {
        return banner_Images;
    }

    public void setBanner_Images(List<String> banner_Images) {
        this.banner_Images = banner_Images;
    }

/*    public String getRecommend_Img() {
        return recommend_Img;
    }

    public void setRecommend_Img(String recommend_Img) {
        this.recommend_Img = recommend_Img;
    }

    public String getRecommend_Url() {
        return recommend_Url;
    }

    public void setRecommend_Url(String recommend_Url) {
        this.recommend_Url = recommend_Url;
    }*/

    public String getEventsItemImgs() {
        return eventsItemImgs;
    }

    public void setEventsItemImgs(String eventsItemImgs) {
        this.eventsItemImgs = eventsItemImgs;
    }

    public String getEventsItemEventsTitles() {
        return eventsItemEventsTitles;
    }

    public void setEventsItemEventsTitles(String eventsItemEventsTitles) {
        this.eventsItemEventsTitles = eventsItemEventsTitles;
    }

    public String getEventsItemDate() {
        return eventsItemDate;
    }

    public void setEventsItemDate(String eventsItemDate) {
        this.eventsItemDate = eventsItemDate;
    }

    @Override
    public String toString() {
        return "HomeFragmentEntity{" +
                "banner_Images=" + banner_Images +
                ", Recommend_imgs=" + Arrays.toString(recommend_Imgs) +
                ", recommend_Img='" + eventsItemEventsTitles + '\'' +
                ", recommend_Url='" + eventsItemEventsTitles + '\'' +
                ", eventsItemImgs='" + eventsItemImgs + '\'' +
                ", eventsItemEventsTitles='" + eventsItemEventsTitles + '\'' +
                ", eventsItemDate='" + eventsItemDate + '\'' +
                '}';
    }


}
