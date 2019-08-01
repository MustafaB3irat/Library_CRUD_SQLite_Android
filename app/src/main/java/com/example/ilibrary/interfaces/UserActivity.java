package com.example.ilibrary.interfaces;

public interface UserActivity {
    interface UserActivityView {

        void initMenuButton();
        void switchFragments(int fragmentNum);
        void initMenuItems();
    }

    interface UserActivityPresenter {
    }

    interface UserActivityModel {
    }
}
