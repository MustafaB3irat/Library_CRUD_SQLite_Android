package com.example.ilibrary.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ilibrary.R;
import com.example.ilibrary.customListeners.MyTextWatcher;
import com.example.ilibrary.databinding.MyProfileBinding;
import com.example.ilibrary.presetners.MyProfilePresenter;


public class MyProfile extends Fragment implements com.example.ilibrary.interfaces.MyProfile.MyProfileFragment {

    private static final int CAPTURE_CAMERA = 1;
    private static final int FROM_GALLERY = 2;
    private MyProfileBinding myProfileBinding;
    private com.example.ilibrary.interfaces.MyProfile.MyProfilePresneter presneter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myProfileBinding = DataBindingUtil.inflate(inflater, R.layout.my_profile, container, false);
        presneter = new MyProfilePresenter(this);

        initiateUpdateButton();
        initiateUserInfo();
        initiateOnValuesChange();

        return myProfileBinding.getRoot();

    }

    @Override
    public void requestAvatarImagePermission() {


        if (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        CAPTURE_CAMERA);

            }
        }

        if (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        FROM_GALLERY);

            }
        }

    }

    @Override
    public boolean validate() {

        boolean isValid = true;

        if (!myProfileBinding.maleUpdate.isChecked() && !myProfileBinding.femaleUpdate.isChecked()) {

            isValid = false;

            myProfileBinding.genderError.setText("* Gender is required!");

        }

        if (TextUtils.isEmpty(myProfileBinding.age.getText())) {

            isValid = false;

            myProfileBinding.ageError.setText("* Age is required!");
        }

        if (Integer.parseInt(myProfileBinding.age.getText().toString()) < 10) {

            isValid = false;

            myProfileBinding.ageError.setText("* Too Young!");
        }

        return isValid;
    }

    @Override
    public void initiateOnValuesChange() {

        myProfileBinding.gender.setOnCheckedChangeListener((radioGroup, i) -> {

            myProfileBinding.genderError.setText("");

        });

        myProfileBinding.age.addTextChangedListener(new MyTextWatcher(() -> {

            myProfileBinding.ageError.setText("");

        }));
    }

    @Override
    public void initiateUserInfo() {


        Cursor cursor = presneter.getCurrentUser();

        if (cursor.moveToFirst()) {

            myProfileBinding.email.setText(cursor.getString(cursor.getColumnIndex("email")));
            myProfileBinding.age.setText(cursor.getString(cursor.getColumnIndex("age")));

            String gender = cursor.getString(cursor.getColumnIndex("gender"));

            if (gender.equals("Male")) {
                myProfileBinding.maleUpdate.setChecked(true);
            } else if (gender.equals("Female")) {
                myProfileBinding.femaleUpdate.setChecked(true);
            }

        }


    }

    @Override
    public void initiateUpdateButton() {


        myProfileBinding.update.setOnClickListener(e -> {

            if (validate())

                if (myProfileBinding.maleUpdate.isChecked()) {
                    if (presneter.updateUserInfo("Male", Integer.parseInt(myProfileBinding.age.getText().toString()))) {

                        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
                        alertDialog.setTitle("Success!");
                        alertDialog.setMessage("User Updated Successfully!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                    }
                } else if (myProfileBinding.femaleUpdate.isChecked()) {

                    if (presneter.updateUserInfo("Female", Integer.parseInt(myProfileBinding.age.getText().toString()))) {

                        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
                        alertDialog.setTitle("Success!");
                        alertDialog.setMessage("User Updated Successfully!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                    }
                }


        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FROM_GALLERY)

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, FROM_GALLERY);
            } else if (requestCode == CAPTURE_CAMERA)

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");

                    startActivityForResult(intent, CAPTURE_CAMERA);
                }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
