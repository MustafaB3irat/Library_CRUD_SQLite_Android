package com.example.ilibrary.interfaces;

public interface AboutUs {


    interface AboutUsModel {
    }

    interface AboutUsPresenter {
    }

    interface AboutUsView {
        void sendEmail();
        void initSendUsEmailButton();
        void callUs();
        void initiateCallUsButton();
    }

}
