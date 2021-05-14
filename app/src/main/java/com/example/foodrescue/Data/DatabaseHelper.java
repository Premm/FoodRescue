package com.example.foodrescue.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodrescue.Model.Food;
import com.example.foodrescue.Model.User;
import com.example.foodrescue.Utils.Util;

import java.util.ArrayList;
import java.util.List;

    public class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
            super(context, Util.DATABASE_NAME, factory, Util.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create the user table,
            String CREATE_USER_TABLE = "CREATE TABLE " + Util.USER.TABLE_NAME + "(" + Util.USER.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + Util.USER.EMAIL + " TEXT, " + Util.USER.ADDRESS + " TEXT, " + Util.USER.FULL_NAME + " TEXT, " + Util.USER.PHONE + " TEXT, " + Util.USER.PASSWORD_HASH + " TEXT)";
            db.execSQL(CREATE_USER_TABLE);

            //create the food table
            String CREATE_FOOD_TABLE = "CREATE TABLE " + Util.FOOD.TABLE_NAME + "(" + Util.FOOD.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + Util.FOOD.IMAGE_URI + " TEXT, " + Util.FOOD.TITLE + " TEXT, " + Util.FOOD.DESCRIPTION + " TEXT, " + Util.FOOD.DATE + " TEXT, " + Util.FOOD.LOCATION + " TEXT, " + Util.FOOD.PICK_UP_TIMES + " TEXT, " + Util.FOOD.QUANTITY + " INTEGER)";
            db.execSQL(CREATE_FOOD_TABLE);

            //create the link table between user and food.
           String CREATE_USER_FOOD_TABLE = "CREATE TABLE " + Util.USER_FOOD.TABLE_NAME + "(" + Util.USER_FOOD.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Util.USER_FOOD.USER_ID + " INTEGER, " + Util.USER_FOOD.FOOD_ID + " INTEGER,"
                   + " FOREIGN KEY ("+Util.USER_FOOD.USER_ID+") REFERENCES "+Util.USER.TABLE_NAME+"("+Util.USER.ID+"),"
                   + " FOREIGN KEY ("+Util.USER_FOOD.FOOD_ID+") REFERENCES "+Util.FOOD.TABLE_NAME+"("+Util.FOOD.ID+"))";
            db.execSQL(CREATE_USER_FOOD_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String DROP_TABLE = String.format("DROP TABLE IF EXISTS");
            db.execSQL(DROP_TABLE, new String[]{Util.USER.TABLE_NAME});
            db.execSQL(DROP_TABLE, new String[]{Util.FOOD.TABLE_NAME});
            db.execSQL(DROP_TABLE, new String[]{Util.USER_FOOD.TABLE_NAME});
            onCreate(db);
        }

        public long insertUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.USER.ADDRESS, user.getAddress());
            contentValues.put(Util.USER.EMAIL, user.getEmail());
            contentValues.put(Util.USER.FULL_NAME, user.getFullName());
            contentValues.put(Util.USER.PHONE, user.getPhone());
            contentValues.put(Util.USER.PASSWORD_HASH, user.getPasswordHash());
            long newRowId = db.insert(Util.USER.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;
        }


        public long updateUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.USER.ADDRESS, user.getAddress());
            contentValues.put(Util.USER.EMAIL, user.getEmail());
            contentValues.put(Util.USER.FULL_NAME, user.getFullName());
            contentValues.put(Util.USER.PHONE, user.getPhone());
            contentValues.put(Util.USER.PASSWORD_HASH, user.getPasswordHash());
            long newRowId = db.update(Util.USER.TABLE_NAME, contentValues, Util.USER.ID + "=?", new String[]{String.valueOf(user.getId())});
            db.close();
            return newRowId;
        }

        public User fetchUser(String id) {

            User user = null;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(Util.USER.TABLE_NAME, new String[]{Util.USER.ID}, Util.USER.ID + "=?", new String[]{id}, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast() && user == null) {
                    String userId = cursor.getString(cursor.getColumnIndex(Util.USER.ID));
                    String userFullName = cursor.getString(cursor.getColumnIndex(Util.USER.FULL_NAME));
                    String userEmail = cursor.getString(cursor.getColumnIndex(Util.USER.EMAIL));
                    String userPhone = cursor.getString(cursor.getColumnIndex(Util.USER.PHONE));
                    String userAddress = cursor.getString(cursor.getColumnIndex(Util.USER.ADDRESS));
                    String userPasswordHash = cursor.getString(cursor.getColumnIndex(Util.USER.PASSWORD_HASH));
                    user = new User(Integer.parseInt(userId),userEmail, userFullName, userPhone, userAddress, userPasswordHash);
                    cursor.moveToNext();
                }
            }
            db.close();

            return user;
        }

        public User fetchUserByEmail(String email) {
            User user = null;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(Util.USER.TABLE_NAME, null, Util.USER.EMAIL + "=?", new String[]{email}, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast() && user == null) {
                    String userId = cursor.getString(cursor.getColumnIndex(Util.USER.ID));
                    String userFullName = cursor.getString(cursor.getColumnIndex(Util.USER.FULL_NAME));
                    String userEmail = cursor.getString(cursor.getColumnIndex(Util.USER.EMAIL));
                    String userPhone = cursor.getString(cursor.getColumnIndex(Util.USER.PHONE));
                    String userAddress = cursor.getString(cursor.getColumnIndex(Util.USER.ADDRESS));
                    String userPasswordHash = cursor.getString(cursor.getColumnIndex(Util.USER.PASSWORD_HASH));
                    user = new User(Integer.parseInt(userId),userEmail, userFullName, userPhone, userAddress, userPasswordHash);
                    cursor.moveToNext();
                }
            }
            db.close();

            return user;
        }

        public boolean deleteUser(String id) {

            SQLiteDatabase db = this.getReadableDatabase();
            int rowsDeleted = 0;
            rowsDeleted = db.delete(Util.USER.TABLE_NAME, Util.USER.ID + "=?", new String[]{id});
            db.close();

            if (rowsDeleted > 0) {
                return true;
            } else {
                return false;
            }
        }

        public long insertFood(Food food) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.FOOD.IMAGE_URI, food.getImageUri());
            contentValues.put(Util.FOOD.DATE, food.getDate());
            contentValues.put(Util.FOOD.DESCRIPTION, food.getDescription());
            contentValues.put(Util.FOOD.LOCATION, food.getLocation());
            contentValues.put(Util.FOOD.PICK_UP_TIMES, food.getPickUpTimes());
            contentValues.put(Util.FOOD.TITLE, food.getTitle());
            contentValues.put(Util.FOOD.QUANTITY, food.getQuantity());
            long newRowId = db.insert(Util.FOOD.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;
        }


        public long updateFood(Food food) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.FOOD.IMAGE_URI, food.getImageUri());
            contentValues.put(Util.FOOD.DATE, food.getDate());
            contentValues.put(Util.FOOD.DESCRIPTION, food.getDescription());
            contentValues.put(Util.FOOD.LOCATION, food.getLocation());
            contentValues.put(Util.FOOD.PICK_UP_TIMES, food.getPickUpTimes());
            contentValues.put(Util.FOOD.TITLE, food.getTitle());
            contentValues.put(Util.FOOD.QUANTITY, food.getQuantity());
            long newRowId = db.update(Util.FOOD.TABLE_NAME, contentValues, Util.USER.ID + "=?", new String[]{String.valueOf(food.getId())});
            db.close();
            return newRowId;
        }

        public Food fetchFood(String id) {

            Food food = null;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(Util.FOOD.TABLE_NAME, new String[]{Util.FOOD.ID}, Util.FOOD.ID + "=?", new String[]{id}, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast() && food == null) {
                    String foodId = cursor.getString(cursor.getColumnIndex(Util.FOOD.ID));
                    String foodImageUri = cursor.getString(cursor.getColumnIndex(Util.FOOD.IMAGE_URI));
                    String foodDate = cursor.getString(cursor.getColumnIndex(Util.FOOD.DATE));
                    String foodDescription = cursor.getString(cursor.getColumnIndex(Util.FOOD.DESCRIPTION));
                    String foodLocation = cursor.getString(cursor.getColumnIndex(Util.FOOD.LOCATION));
                    String foodPickUpTimes = cursor.getString(cursor.getColumnIndex(Util.FOOD.PICK_UP_TIMES));
                    String foodTitle = cursor.getString(cursor.getColumnIndex(Util.FOOD.TITLE));
                    String foodQuantity = cursor.getString(cursor.getColumnIndex(Util.FOOD.QUANTITY));
                    food = new Food(Integer.parseInt(foodId),foodImageUri,foodTitle,foodDescription,foodDate,foodPickUpTimes,foodLocation,Integer.parseInt(foodQuantity));
                    cursor.moveToNext();
                }
            }
            db.close();

            return food;
        }

        public boolean deleteFood(String id) {

            SQLiteDatabase db = this.getReadableDatabase();
            int rowsDeleted = 0;
            rowsDeleted = db.delete(Util.FOOD.TABLE_NAME, Util.FOOD.ID + "=?", new String[]{id});
            db.close();

            if (rowsDeleted > 0) {
                return true;
            } else {
                return false;
            }
        }

        public List<Food> fetchAllFood() {

            List<Food> food = new ArrayList<Food>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(Util.FOOD.TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String foodId = cursor.getString(cursor.getColumnIndex(Util.FOOD.ID));
                    String foodImageUri = cursor.getString(cursor.getColumnIndex(Util.FOOD.IMAGE_URI));
                    String foodDate = cursor.getString(cursor.getColumnIndex(Util.FOOD.DATE));
                    String foodDescription = cursor.getString(cursor.getColumnIndex(Util.FOOD.DESCRIPTION));
                    String foodLocation = cursor.getString(cursor.getColumnIndex(Util.FOOD.LOCATION));
                    String foodPickUpTimes = cursor.getString(cursor.getColumnIndex(Util.FOOD.PICK_UP_TIMES));
                    String foodTitle = cursor.getString(cursor.getColumnIndex(Util.FOOD.TITLE));
                    String foodQuantity = cursor.getString(cursor.getColumnIndex(Util.FOOD.QUANTITY));
                    Food tempFood = new Food(Integer.parseInt(foodId),foodImageUri,foodTitle,foodDescription,foodDate,foodPickUpTimes,foodLocation,Integer.parseInt(foodQuantity));
                    food.add(tempFood);
                    cursor.moveToNext();
                }
            }
            db.close();
            return food;
        }


        public long insertUserFood(String userId, String foodId ) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.USER_FOOD.USER_ID, userId);
            contentValues.put(Util.USER_FOOD.FOOD_ID, foodId);
            long newRowId = db.insert(Util.USER_FOOD.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;
        }


        public List<Food> fetchUserFood(String userId) {

            List<Food> food = new ArrayList<Food>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + Util.USER_FOOD.TABLE_NAME + " LEFT JOIN " + Util.FOOD.TABLE_NAME + " ON " + Util.USER_FOOD.TABLE_NAME+"."+Util.USER_FOOD.FOOD_ID
                    +"="+Util.FOOD.TABLE_NAME+"."+Util.FOOD.ID+ " WHERE " + Util.USER_FOOD.USER_ID + "=?", new String[] {userId} );

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String foodId = cursor.getString(cursor.getColumnIndex(Util.FOOD.ID));
                    String foodImageUri = cursor.getString(cursor.getColumnIndex(Util.FOOD.IMAGE_URI));
                    String foodDate = cursor.getString(cursor.getColumnIndex(Util.FOOD.DATE));
                    String foodDescription = cursor.getString(cursor.getColumnIndex(Util.FOOD.DESCRIPTION));
                    String foodLocation = cursor.getString(cursor.getColumnIndex(Util.FOOD.LOCATION));
                    String foodPickUpTimes = cursor.getString(cursor.getColumnIndex(Util.FOOD.PICK_UP_TIMES));
                    String foodTitle = cursor.getString(cursor.getColumnIndex(Util.FOOD.TITLE));
                    String foodQuantity = cursor.getString(cursor.getColumnIndex(Util.FOOD.QUANTITY));
                    Food tempFood = new Food(Integer.parseInt(foodId),foodImageUri,foodTitle,foodDescription,foodDate,foodPickUpTimes,foodLocation,Integer.parseInt(foodQuantity));
                    food.add(tempFood);
                    cursor.moveToNext();
                }
            }
            db.close();
            return food;
        }

    }
