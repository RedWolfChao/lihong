package com.qyw.newdemo.app.entity;

/**
 * Created by Administrator on 2017/8/12 0012.
 */

public class RecruitmentEntity {
/*
*recruitment_Item_Img;
        public TextView recruitment_Item_Post,recuritment_Item_Name,recuritment_Item_City,recuritment_Item_Workdate,recruitment_Item_Pay,recuritment_Item_Date;
*
 */
    private String  recruitment_Post,recruitment_Name,recruitment_City,recruitment_Workdate,recruitment_Pay,recruitment_Date;
    private String  recruitment_Img,recruitment_Id;

    public String getRecruitment_Post() {
        return recruitment_Post;
    }

    public void setRecruitment_Post(String recruitment_Post) {
        this.recruitment_Post = recruitment_Post;
    }

    public String getRecruitment_Name() {
        return recruitment_Name;
    }

    public void setRecruitment_Name(String recruitment_Name) {
        this.recruitment_Name = recruitment_Name;
    }

    public String getRecruitment_City() {
        return recruitment_City;
    }

    public void setRecruitment_City(String recruitment_City) {
        this.recruitment_City = recruitment_City;
    }

    public String getRecruitment_Workdate() {
        return recruitment_Workdate;
    }

    public void setRecruitment_Workdate(String recruitment_Workdate) {
        this.recruitment_Workdate = recruitment_Workdate;
    }

    public String getRecruitment_Pay() {
        return recruitment_Pay;
    }

    public void setRecruitment_Pay(String recruitment_Pay) {
        this.recruitment_Pay = recruitment_Pay;
    }

    public String getRecruitment_Date() {
        return recruitment_Date;
    }

    public void setRecruitment_Date(String recruitment_Date) {
        this.recruitment_Date = recruitment_Date;
    }

    public String getRecruitment_Img() {
        return recruitment_Img;
    }

    public void setRecruitment_Img(String recruitment_Img) {
        this.recruitment_Img = recruitment_Img;
    }

    public String getRecruitment_Id() {
        return recruitment_Id;
    }

    public void setRecruitment_Id(String recruitment_Id) {
        this.recruitment_Id = recruitment_Id;
    }

    public RecruitmentEntity() {
    }

    @Override
    public String toString() {
        return "RecruitmentEntity{" +
                "recruitment_Post='" + recruitment_Post + '\'' +
                ", recruitment_Name='" + recruitment_Name + '\'' +
                ", recruitment_City='" + recruitment_City + '\'' +
                ", recruitment_Workdate='" + recruitment_Workdate + '\'' +
                ", recruitment_Pay='" + recruitment_Pay + '\'' +
                ", recruitment_Date='" + recruitment_Date + '\'' +
                ", recruitment_Img='" + recruitment_Img + '\'' +
                ", recruitment_Id='" + recruitment_Id + '\'' +
                '}';
    }
}
