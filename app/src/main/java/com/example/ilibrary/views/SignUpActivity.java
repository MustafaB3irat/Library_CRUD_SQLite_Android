package com.example.ilibrary.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilibrary.R;
import com.example.ilibrary.customListeners.MyTextWatcher;
import com.example.ilibrary.databinding.SignUpBinding;
import com.example.ilibrary.interfaces.SignUp;
import com.example.ilibrary.interfaces.Signin;
import com.example.ilibrary.presetners.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUp.SignUpView {

    private SignUpBinding signUpBinding;
    private SignUp.SignUpPresneter presneter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding = DataBindingUtil.setContentView(this, R.layout.sign_up);


        presneter = new SignUpPresenter(this);

        initSignUpButton();
        initSignInButton();
        onInputsTextChange();

    }

    @Override
    public boolean signUp() {

        if (presneter.validate(signUpBinding.username.getText().toString(), signUpBinding.password.getText().toString(), signUpBinding.email.getText().toString(), ((RadioButton) findViewById(signUpBinding.gender.getCheckedRadioButtonId())), signUpBinding.age.getText().toString()))
            return presneter.signUp(signUpBinding.username.getText().toString(), signUpBinding.password.getText().toString(), signUpBinding.email.getText().toString(), ((RadioButton) findViewById(signUpBinding.gender.getCheckedRadioButtonId())).getText().toString(), signUpBinding.age.getText().toString());
        else
            return false;
    }

    @Override
    public void initSignInButton() {

        signUpBinding.signin.setOnClickListener(e -> {

            getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);

        });
    }

    @Override
    public void initSignUpButton() {

        signUpBinding.signup.setOnClickListener(e -> {

            if (signUp()) {
                Intent intent = new Intent(this, UserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SignInActivity.username = signUpBinding.username.getText().toString();
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);


            } else {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void emptyUsername(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.usernameError.setText("*Username is required!");

    }

    @Override
    public void emptyPassword(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.passwordError.setText("*Password is required!");

    }

    @Override
    public void emptyEmail(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.emailError.setText("*Email is required!");

    }

    @Override
    public void emptyGender(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.genderError.setText("*Gender is required!");

    }

    @Override
    public void emptyAge(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.ageError.setText("*Age is required!");

    }

    @Override
    public void wrongFormatPassword(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.passwordError.setText("Password must be at least 8 characters with at least 2 numbers!");

    }

    @Override
    public void wrongFormatEmail(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.emailError.setText("Email is not correct!");

    }

    @Override
    public void wrongFormatAge(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.ageText.setText("Age must be more than 10 years");

    }

    @Override
    public void onInputsTextChange() {


        signUpBinding.email.addTextChangedListener(new MyTextWatcher(() -> {

            signUpBinding.emailError.setText("");

        }));

        signUpBinding.password.addTextChangedListener(new MyTextWatcher(() -> {

            signUpBinding.passwordError.setText("");

        }));


        signUpBinding.age.addTextChangedListener(new MyTextWatcher(() -> {

            signUpBinding.ageError.setText("");

        }));

        signUpBinding.gender.setOnCheckedChangeListener((radioGroup, i) -> {

            signUpBinding.genderError.setText("");

        });


        signUpBinding.username.addTextChangedListener(new MyTextWatcher(() -> {

            signUpBinding.usernameError.setText("");

        }));


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            YoYo.with(Techniques.Bounce).duration(500).repeat(2).playOn(signUpBinding.libraryLogo);
        }
    }
}
