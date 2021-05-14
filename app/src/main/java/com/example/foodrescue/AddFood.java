package com.example.foodrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.foodrescue.Data.DatabaseHelper;
import com.example.foodrescue.Model.Food;
import com.example.foodrescue.Utils.Util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;


public class AddFood extends AppCompatActivity {

    String currentPhotoPath;
    ImageView imageThumbnail;
    EditText title, description, pickUpTimes, quantity, location;
    DatePicker date;

    DatabaseHelper db;


    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        imageThumbnail = findViewById(R.id.imageThumbnail);
        title = findViewById(R.id.titleEditText);
        description = findViewById(R.id.descriptionEditText);
        pickUpTimes = findViewById(R.id.pickUpEditText);
        quantity = findViewById(R.id.quantityEditText);
        location = findViewById(R.id.locationEditText);
        date = findViewById(R.id.dateDatePicker);
        db = new DatabaseHelper(this, null);
    }



    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = Util.createImageFile(this);
            currentPhotoPath = photoFile.getAbsolutePath();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.foodrescue",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            takePictureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, "720000");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap imageVal = BitmapFactory.decodeFile(currentPhotoPath, options);
            imageThumbnail.setImageBitmap(imageVal);
        }
    }

    public void addNewFood(View view) {
        String tempTitle, tempDescription, tempPickUpTimes, tempLocation, tempDate;
        int tempQuantity;
        tempTitle = title.getText().toString();
        tempDescription = description.getText().toString();
        tempPickUpTimes = pickUpTimes.getText().toString();
        tempQuantity = Integer.parseInt(quantity.getText().toString());
        tempLocation = location.getText().toString();

        int day = date.getDayOfMonth();
        int month = date.getMonth();
        int year =  date.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        tempDate = String.valueOf(calendar.getTimeInMillis());

        Food food = new Food(-1, currentPhotoPath,tempTitle, tempDescription, tempDate, tempPickUpTimes, tempLocation, tempQuantity);
        db.insertFood(food);
        finish();
    }
}