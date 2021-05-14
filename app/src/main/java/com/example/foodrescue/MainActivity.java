package com.example.foodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodrescue.Data.DatabaseHelper;
import com.example.foodrescue.Model.User;
import com.example.foodrescue.Utils.Util;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;

    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this, null);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
    }


    public void signUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logIn(View view) {
        String tempEmail = email.getText().toString();
        String tempPassword = password.getText().toString();
        User user = db.fetchUserByEmail(tempEmail);
        if(user.getPasswordHash().equals(Util.md5(tempPassword))){
            Intent intent = new Intent(this, FoodActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        }else{
            Toast.makeText(this, "The details you entered didn't match any results in our database.", Toast.LENGTH_LONG).show();
        }

    }
}