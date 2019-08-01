package com.example.ilibrary.customListeners;

import android.text.Editable;
import android.text.TextWatcher;

public class MyTextWatcher implements TextWatcher {


    private Runnable runnable;

    public MyTextWatcher(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        runnable.run();
    }
}
