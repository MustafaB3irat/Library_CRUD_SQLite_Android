package com.example.ilibrary.interfaces;

import android.database.Cursor;

public interface Signin {

    interface SignInView {

        Cursor signin();

        void initSignInButton();

        void onTextChange();

        void initSignUpButton();

        void wrongEmail();

        void wrongPassword();

        void initSharedPreferences();

    }


    interface SignInModel {

        Cursor signin(String username, String password);
        int getUserState(String username);

    }

    interface SignInPresenter {

        Cursor signin(String username, String password);

        int getUserState(String username);
    }
}
