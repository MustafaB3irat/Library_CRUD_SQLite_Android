package com.example.ilibrary.models;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilibrary.interfaces.DatabaseHelper;
import com.example.ilibrary.interfaces.SignUp;

public class SignUpModel implements SignUp.SignUpModel {


    private DatabaseHelper databaseHelper;


    public SignUpModel(AppCompatActivity activity) {

        databaseHelper = new com.example.ilibrary.models.DatabaseHelper(activity);
    }

    @Override
    public boolean signUp(String username, String password, String email, String gender, int age) {
        return databaseHelper.signUp(username, password, email, gender, age);
    }
}
