package com.example.ilibrary.models;

import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilibrary.interfaces.Signin;

public class SigninModel implements Signin.SignInModel {


    private Signin.SignInPresenter signInPresenter;

    private DatabaseHelper databaseHelper;


    public SigninModel(Signin.SignInPresenter signInPresenter, AppCompatActivity activity) {
        this.signInPresenter = signInPresenter;
        databaseHelper = new DatabaseHelper(activity);
    }

    @Override
    public Cursor signin(String username, String password) {
        return databaseHelper.signIn(username, password);
    }
}
