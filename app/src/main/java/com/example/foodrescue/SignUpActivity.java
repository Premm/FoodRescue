package com.example.foodrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodrescue.Data.DatabaseHelper;
import com.example.foodrescue.Model.User;
import com.example.foodrescue.Utils.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper db;

    EditText email, fullName, phone, address, password, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DatabaseHelper(this,null );
        email = findViewById(R.id.emailEditText);
        fullName = findViewById(R.id.fullNameEditText);
        phone = findViewById(R.id.phoneEditText);
        address = findViewById(R.id.addressEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
    }




    public void signUpUser(View view) {

        String tempPassword = password.getText().toString();
        String tempConfirmPassword = confirmPassword.getText().toString();
        String tempEmail = email.getText().toString();
        String tempFullName = fullName.getText().toString();
        String tempPhone = phone.getText().toString();
        String tempAddress = address.getText().toString();

        if(tempPassword.equals(tempConfirmPassword)
                && tempPassword.length() > 0
                && tempEmail.length() > 0){

            String passwordHash = Util.md5(password.getText().toString());
            //insert user ignores ID, so can set it to anything.
            User user = new User(-1, tempEmail, tempFullName, tempPhone, tempAddress, passwordHash);
            db.insertUser(user);
            Toast.makeText(getBaseContext(), "Signed Up Successful. Please Log In.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(getBaseContext(), "There was an error. Please make sure all fields are filled out correctly.",
                    Toast.LENGTH_SHORT).show();
        }


    }
}