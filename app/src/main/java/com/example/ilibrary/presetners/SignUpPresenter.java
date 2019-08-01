package com.example.ilibrary.presetners;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.RadioButton;

import com.example.ilibrary.interfaces.SignUp;
import com.example.ilibrary.models.SignUpModel;
import com.example.ilibrary.views.SignUpActivity;

import java.util.regex.Pattern;

public class SignUpPresenter implements SignUp.SignUpPresneter {

    private SignUpActivity signUpActivity;
    private SignUp.SignUpModel model;

    public SignUpPresenter(SignUpActivity signUpActivity) {
        this.signUpActivity = signUpActivity;
        model = new SignUpModel(signUpActivity);
    }

    @Override
    public boolean signUp(String username, String password, String email, String gender, String age) {

        return model.signUp(username, password, email, gender, Integer.parseInt(age));
    }

    @Override
    public boolean validate(String username, String password, String email, RadioButton gender, String age) {

        boolean isValid = true;

        if (TextUtils.isEmpty(username)) {
            isValid = false;
            signUpActivity.emptyUsername(true);
        }

        if (TextUtils.isEmpty(password)) {
            isValid = false;
            signUpActivity.emptyPassword(true);
        }

        if (TextUtils.isEmpty(email)) {
            isValid = false;
            signUpActivity.emptyEmail(true);
        }

        if (gender == null) {
            isValid = false;
            signUpActivity.emptyGender(true);
        }

        if (TextUtils.isEmpty(age)) {
            isValid = false;
            signUpActivity.emptyAge(true);
        }

        if (!isValid)
            return isValid;

        if (!Pattern.compile("(?=.*\\d{2,}).{8,}").matcher(password).matches()) {

            signUpActivity.wrongFormatPassword(true);
            isValid = false;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            signUpActivity.wrongFormatEmail(true);
            isValid = false;

        }

        if (Integer.parseInt(age) < 10) {

            signUpActivity.wrongFormatAge(true);
            isValid = false;

        }


        return isValid;

    }
}
