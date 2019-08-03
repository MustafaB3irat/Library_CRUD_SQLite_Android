package com.example.ilibrary.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.ilibrary.R;
import com.example.ilibrary.databinding.WelcomeFragmentBinding;

public class WelcomeFragment extends Fragment {

    private WelcomeFragmentBinding welcomeFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        welcomeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false);
        welcomeFragmentBinding.welcomeLayout.setOnClickListener(e -> {

            WelcomeDialog welcomeDialog = new WelcomeDialog();

            welcomeDialog.show(getFragmentManager(), "iLibrary");

        });

        return welcomeFragmentBinding.getRoot();

    }

}
