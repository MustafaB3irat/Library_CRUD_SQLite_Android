package com.example.ilibrary.presetners;

import android.database.Cursor;

import com.example.ilibrary.interfaces.BlockUnBlockUsers;
import com.example.ilibrary.models.BlcokUnBlockUsersModel;
import com.example.ilibrary.models.data.User;

import java.util.ArrayList;
import java.util.List;

public class BlockUnBlockUsersPresenter implements BlockUnBlockUsers.BlockUnBlockUsersPresenter {

    private BlockUnBlockUsers.BlockUnBlcokUsersFragment fragment;
    private BlockUnBlockUsers.BlockUnBlockUsersModel model;


    public BlockUnBlockUsersPresenter(BlockUnBlockUsers.BlockUnBlcokUsersFragment fragment) {
        this.fragment = fragment;
        model = new BlcokUnBlockUsersModel(this, fragment);
    }


    @Override
    public void getUserByName(String username) {

        Cursor cursor = model.getUserByName(username);

        List<User> users = new ArrayList<>();

        while (cursor.moveToNext()) {

            User user = new User(cursor.getString(cursor.getColumnIndexOrThrow("username")), "", cursor.getString(cursor.getColumnIndexOrThrow("email")), cursor.getString(cursor.getColumnIndexOrThrow("gender")), cursor.getInt(cursor.getColumnIndexOrThrow("age")), cursor.getInt(cursor.getColumnIndexOrThrow("blocked")));
            users.add(user);
        }

        fragment.initRecyclerView(users);
    }


}
