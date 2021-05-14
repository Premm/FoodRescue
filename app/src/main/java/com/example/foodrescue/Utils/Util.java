package com.example.foodrescue.Utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "food_rescue_db";
    public class USER {
        public static final String TABLE_NAME = "users";
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String FULL_NAME = "fullName";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";
        public static final String PASSWORD_HASH = "passwordHash";
    }

    public class FOOD {
        public static final String TABLE_NAME = "food";
        public static final String ID = "id";
        public static final String IMAGE_URI = "imageUri";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String PICK_UP_TIMES = "pickUpTimes";
        public static final String QUANTITY = "quantity";
        public static final String LOCATION = "location";
    }

    public class USER_FOOD {
        public static final String TABLE_NAME = "userFood";
        public static final String ID = "id";
        public static final String USER_ID = "userId";
        public static final String FOOD_ID = "foodId";
    }

    //found this hashing method here. https://stackoverflow.com/questions/45279676/how-can-i-hash-the-password-in-my-app
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static File createImageFile(Context context) throws
            IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }
}
