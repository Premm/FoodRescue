package com.example.foodrescue.Model;

public class User_Food {
    private int id;
    private int userId;
    private int foodId;

    public User_Food(int id, int userId, int foodId){
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
    }

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getUserId() {
        return userId;
    }
}
