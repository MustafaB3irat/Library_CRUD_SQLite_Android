package com.example.ilibrary.interfaces;

import android.widget.RadioButton;

public interface SignUp {

    interface SignUpModel {

        boolean signUp(String username, String password, String email, String gender, int age);
    }

    interface SignUpPresneter {

        boolean signUp(String username, String password, String email, String gender, String age);

        boolean validate(String username, String password, String email, RadioButton gender, String age);
    }

    interface SignUpView {

        boolean signUp();

        void initSignInButton();

        void initSignUpButton();

        void emptyUsername(boolean isEmpty);

        void emptyPassword(boolean isEmpty);

        void emptyEmail(boolean isEmpty);

        void emptyGender(boolean isEmpty);

        void emptyAge(boolean isEmpty);

        void wrongFormatPassword(boolean isEmpty);

        void wrongFormatEmail(boolean isEmpty);

        void wrongFormatAge(boolean isEmpty);

        void onInputsTextChange();


    }
}
