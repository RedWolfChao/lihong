package com.quanying.app.zhibo;

public class GridListItem {
    private String image;
    private String grif_id;
    private String title;
    private String price;
    private int  isBuy = -1;

    public GridListItem() {
        super();
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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

    public String getGrif_id() {
        return grif_id;
    }

    public void setGrif_id(String grif_id) {
        this.grif_id = grif_id;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }
}