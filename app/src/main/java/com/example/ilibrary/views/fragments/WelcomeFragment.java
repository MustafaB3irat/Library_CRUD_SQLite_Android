package com.example.ilibrary.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilibrary.R;
import com.example.ilibrary.databinding.WelcomeFragmentBinding;

public class WelcomeFragment extends Fragment {

    private WelcomeFragmentBinding welcomeFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        welcomeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false);
        welcomeFragmentBinding.welcomeLayout.setOnClickListener(e -> {

            YoYo.with(Techniques.SlideInDown).duration(500).playOn(welcomeFragmentBinding.homeLogo);
            YoYo.with(Techniques.SlideInUp).duration(500).playOn(welcomeFragmentBinding.homeText);
            YoYo.with(Techniques.Bounce).duration(100).playOn(welcomeFragmentBinding.homeLogo);
            YoYo.with(Techniques.Bounce).duration(100).playOn(welcomeFragmentBinding.homeText);


        });

        return welcomeFragmentBinding.getRoot();

    }

}
