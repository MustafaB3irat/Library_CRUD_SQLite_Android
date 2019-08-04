package com.example.ilibrary.presetners;

import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilibrary.interfaces.Signin;
import com.example.ilibrary.models.SigninModel;

public class SigninPresenter implements Signin.SignInPresenter {


    private Signin.SignInView view;
    private Signin.SignInModel model;

    public SigninPresenter(Signin.SignInView view) {
        model = new SigninModel(this, (AppCompatActivity) view);
        this.view = view;
    }

    @Override
    public Cursor signin(String username, String password) {
        return model.signin(username, password);
    }

    @Override
    public int getUserState(String username) {
        return model.getUserState(username);
    }
}
