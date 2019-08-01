package com.example.ilibrary.presetners;

import androidx.fragment.app.Fragment;
import com.example.ilibrary.interfaces.MyCart;
import com.example.ilibrary.models.MyCartModel;
import com.example.ilibrary.models.data.Book;

import java.util.List;

public class MyCartPresenter implements MyCart.MyCartPresenter {


    private MyCartModel model;

    private MyCart.MyCartFragment fragment;

    public MyCartPresenter(MyCart.MyCartFragment fragment) {

        this.fragment = fragment;
        model = new MyCartModel(this, ((Fragment) fragment).getActivity());
    }

    @Override
    public void setBooks(List<Book> books) {

        fragment.setViewBooksAdapter(books);

    }

    @Override
    public void getBooks() {

        model.getBooksFromRetrofit();
    }
}
