package com.example.dell.myapp;

/**
 * Created by Dell on 2018/4/27.
 */

public class Account {
    private String imageId;
    private String imageName;
    private float price;
    private String dataStr;

    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getImageNames() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public float getprice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getDataStr() {
        return dataStr;
    }
    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }
    public Account(String imageIds, String imageNames, float price, String dataStr) {
        super();
        this.imageId = imageId;
        this.imageName = imageName;
        this.price = price;
        this.dataStr = dataStr;
    }


}
