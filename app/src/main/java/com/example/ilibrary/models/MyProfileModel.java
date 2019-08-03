package com.example.ilibrary.models;

import android.database.Cursor;

import androidx.fragment.app.Fragment;

import com.example.ilibrary.interfaces.MyProfile;

public class MyProfileModel implements MyProfile.MyProfileModel {

    private MyProfile.MyProfilePresneter presnter;
    private DatabaseHelper databaseHelper;

    public MyProfileModel(MyProfile.MyProfilePresneter presnter, MyProfile.MyProfileFragment fragment) {
        this.presnter = presnter;
        databaseHelper = new DatabaseHelper(((Fragment) fragment).getActivity());

    }

    @Override
    public Cursor getUserInfo() {
        return databaseHelper.getCurrentUser();
    }

    @Override
    public boolean updateUserInfo(String gender, int age) {
        return databaseHelper.updateUser(gender, age);
    }
}
