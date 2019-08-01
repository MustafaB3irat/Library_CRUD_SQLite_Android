package com.example.ilibrary.interfaces;

import android.database.Cursor;

import com.example.ilibrary.models.data.Book;

import java.util.List;

public interface MyCart {

    interface MyCartFragment {

        void setViewBooksAdapter(List<Book> books);
    }

    interface MyCartPresenter {

        void setBooks(List<Book> books);

        void getBooks();
    }

    interface MyCartModel {

        void getBooksFromRetrofit();

        Cursor getBooksFromDatabase();
    }
}
