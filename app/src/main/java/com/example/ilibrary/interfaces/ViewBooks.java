package com.example.ilibrary.interfaces;

import android.database.Cursor;

import com.example.ilibrary.models.data.Book;

import java.util.List;

public interface ViewBooks {

    interface ViewBooksFragment {

        void setViewBooksAdapter(List<Book> books);
    }

    interface ViewBooksModel {


        void getBooksFromRetrofit();
        Cursor getBooksFromDatabase();
    }

    interface ViewBooksPresenter {

        void setBooks(List<Book> books);
        void getBooks();
    }
}
