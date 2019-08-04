package com.example.ilibrary.models;

import android.database.Cursor;

import androidx.fragment.app.Fragment;

import com.example.ilibrary.interfaces.BlockUnBlockUsers;
import com.example.ilibrary.interfaces.DatabaseHelper;

public class BlcokUnBlockUsersModel implements BlockUnBlockUsers.BlockUnBlockUsersModel {


    private DatabaseHelper databaseHelper;
    private BlockUnBlockUsers.BlockUnBlockUsersPresenter presenter;


    public BlcokUnBlockUsersModel(BlockUnBlockUsers.BlockUnBlockUsersPresenter presenter, BlockUnBlockUsers.BlockUnBlcokUsersFragment fragment) {
        this.presenter = presenter;
        databaseHelper = new com.example.ilibrary.models.DatabaseHelper(((Fragment) fragment).getActivity());
    }

    @Override
    public Cursor getUserByName(String username) {

        return databaseHelper.getUsersByName(username);

    }
}
