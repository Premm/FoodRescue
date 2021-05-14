package com.example.foodrescue.Model;

public class Food {
    private int id;
    private String imageUri;
    private String title;
    private String description;
    private String date;
    private String pickUpTimes;
    private int quantity;
    private String location;

    public Food(int id, String imageUri, String title, String description, String date, String pickUpTimes, String location, int quantity){
        this.id = id;
        this.imageUri = imageUri;
        this.title = title;
        this.description = description;
        this.date = date;
        this.pickUpTimes = pickUpTimes;
        this.location = location;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getLocation() {
        return location;
    }

    public String getPickUpTimes() {
        return pickUpTimes;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPickUpTimes(String pickUpTimes) {
        this.pickUpTimes = pickUpTimes;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
