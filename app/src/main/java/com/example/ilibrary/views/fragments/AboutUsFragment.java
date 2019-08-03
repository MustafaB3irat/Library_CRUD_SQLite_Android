package com.example.ilibrary.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ilibrary.R;
import com.example.ilibrary.databinding.AboutUsBinding;
import com.example.ilibrary.interfaces.AboutUs;

public class AboutUsFragment extends Fragment implements AboutUs.AboutUsView {


    private AboutUsBinding aboutUsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        aboutUsBinding = DataBindingUtil.inflate(inflater, R.layout.about_us, container, false);


        initSendUsEmailButton();
        initiateCallUsButton();


        return aboutUsBinding.getRoot();


    }

    @Override
    public void sendEmail() {

        String[] TO = {"sael@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Library management system");

        try {
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"));

        } catch (android.content.ActivityNotFoundException ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    @Override
    public void initSendUsEmailButton() {

        aboutUsBinding.sendUsAnEmail.setOnClickListener(e -> {

            sendEmail();
        });
    }

    @Override
    public void callUs() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "+8802177690."));
        startActivity(intent);
    }

    @Override
    public void initiateCallUsButton() {

        aboutUsBinding.callUs.setOnClickListener(e -> {
            callUs();
        });

    }


}
