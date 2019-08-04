package com.example.ilibrary.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilibrary.R;
import com.example.ilibrary.customListeners.MyTextWatcher;
import com.example.ilibrary.databinding.SignInBinding;
import com.example.ilibrary.interfaces.Signin;
import com.example.ilibrary.models.data.User;
import com.example.ilibrary.presetners.SigninPresenter;

public class SignInActivity extends AppCompatActivity implements Signin.SignInView {

    private SignInBinding signInBinding;
    private Signin.SignInPresenter signInPresenter;
    private SharedPreferences sharedPreferences;
    public static String username = "";
    private final static String PREFS = "prefsFile";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = DataBindingUtil.setContentView(this, R.layout.sign_in);
        signInPresenter = new SigninPresenter(this);

        sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        initSharedPreferences();
        initSignInButton();
        initSignUpButton();
        onTextChange();

    }

    @Override
    public Cursor signin() {

        return signInPresenter.signin(signInBinding.username.getText().toString(), signInBinding.password.getText().toString());

    }

    @Override
    public void initSignInButton() {
        signInBinding.signin.setOnClickListener(e -> {


            if (signInBinding.rememberMe.isChecked()) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(USERNAME, signInBinding.username.getText().toString());
                editor.putString(PASSWORD, signInBinding.password.getText().toString());
                editor.putBoolean("isCheckedRememberMe", true);
                editor.apply();

            } else {
                sharedPreferences.edit().clear().apply();
            }

            if (signInBinding.username.getText().toString().equals("sael") && signInBinding.password.getText().toString().equals("advancedproject123")) {

                Intent intent = new Intent(this.getBaseContext(), AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                username = "Sael | Admin";
                startActivity(intent);
                overridePendingTransition(0, 0);

            } else {
                Cursor cursor = signin();

                if (cursor.getCount() == 0) {

                    wrongPassword();

                } else {
                    if (cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex("blocked")) != 1) {
                        Intent intent = new Intent(this, UserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                        User user = new User(cursor.getString(cursor.getColumnIndexOrThrow(USERNAME)), cursor.getColumnName(cursor.getColumnIndexOrThrow(PASSWORD)), cursor.getString(cursor.getColumnIndexOrThrow("email")), cursor.getString(cursor.getColumnIndexOrThrow("gender")), cursor.getInt(cursor.getColumnIndexOrThrow("age")),cursor.getInt(cursor.getColumnIndexOrThrow("blocked")));
                        intent.putExtra("USER", user);
                        username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                    } else {
                        Toast.makeText(this, "Sorry you've been blocked by Admin", Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();
                }

            }
        });

    }

    @Override
    public void onTextChange() {
        signInBinding.password.addTextChangedListener(new MyTextWatcher(() -> {

            signInBinding.passwordError.setText("");

        }));
    }

    @Override
    public void initSignUpButton() {

        signInBinding.signup.setOnClickListener(e -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

    }

    @Override
    public void wrongEmail() {

        signInBinding.emailError.setText("Wrong Email!");

    }

    @Override
    public void wrongPassword() {

        signInBinding.passwordError.setText("Wrong Password or Password!");

    }

    @Override
    public void initSharedPreferences() {

        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        if (preferences.contains(USERNAME)) {
            signInBinding.username.setText(preferences.getString(USERNAME, ""));
        }

        if (preferences.contains(PASSWORD)) {
            signInBinding.password.setText(preferences.getString(PASSWORD, ""));
        }

        if (preferences.contains("isCheckedRememberMe")) {
            signInBinding.rememberMe.setChecked(true);

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            YoYo.with(Techniques.Shake).duration(500).repeat(1).playOn(signInBinding.logo);
        }
    }

}
