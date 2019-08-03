package com.example.ilibrary.interfaces;

import android.database.Cursor;

public interface MyProfile {


    interface MyProfileModel {

        Cursor getUserInfo();

        boolean updateUserInfo(String gender, int age);
    }

    interface MyProfilePresneter {

        Cursor getCurrentUser();

        boolean updateUserInfo(String gender, int age);
    }

    interface MyProfileFragment {

        void requestAvatarImagePermission();

        boolean validate();

        void initiateOnValuesChange();

        void initiateUserInfo();

        void initiateUpdateButton();
    }
}
