package com.example.ilibrary.presetners;

import android.database.Cursor;

import androidx.fragment.app.Fragment;

import com.example.ilibrary.interfaces.MyProfile;
import com.example.ilibrary.models.MyProfileModel;

public class MyProfilePresenter implements MyProfile.MyProfilePresneter {


    private MyProfile.MyProfileFragment fragment;
    private MyProfile.MyProfileModel myProfileModel;


    public MyProfilePresenter(MyProfile.MyProfileFragment fragment) {
        this.fragment = fragment;

        myProfileModel = new MyProfileModel(this, fragment);
    }

    @Override
    public Cursor getCurrentUser() {
        return myProfileModel.getUserInfo();
    }

    @Override
    public boolean updateUserInfo(String gender, int age) {
        return myProfileModel.updateUserInfo(gender, age);
    }
}
