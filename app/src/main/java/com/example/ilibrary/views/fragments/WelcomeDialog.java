package com.example.ilibrary.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.ilibrary.R;
import com.example.ilibrary.databinding.WlecomeDialogBinding;

public class WelcomeDialog extends DialogFragment {

    private WlecomeDialogBinding wlecomeDialogBinding;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        wlecomeDialogBinding = DataBindingUtil.inflate(inflater, R.layout.wlecome_dialog, null, true);

        builder.setView(wlecomeDialogBinding.getRoot());


        return builder.create();
    }


}
