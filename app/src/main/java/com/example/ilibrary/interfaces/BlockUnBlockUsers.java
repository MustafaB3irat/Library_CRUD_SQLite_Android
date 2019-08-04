package com.example.ilibrary.interfaces;

import android.database.Cursor;

import com.example.ilibrary.models.data.User;

import java.util.List;

public interface BlockUnBlockUsers {

    interface BlockUnBlcokUsersFragment {

        void initRecyclerView(List<User> userList);

        void initOnEditTextChange();
    }

    interface BlockUnBlockUsersModel {

        Cursor getUserByName(String username);
    }

    interface BlockUnBlockUsersPresenter {

        void getUserByName(String username);


    }
}
