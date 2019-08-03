package com.example.ilibrary.interfaces;

import android.database.Cursor;

import com.example.ilibrary.models.data.Book;

import java.util.List;

public interface SearchBooks {

    interface SearchBooksFragment {

        void setSearchBooksAdapter(List<Book> books);

        void initiateSearchButton();

        void showElements(String element);

        void hideElements(String element);

        void initiateArrowButton();

        void initiateSpinner();

        boolean validate();

        void initiateEditTextsOnChangeText();
    }

    interface SearchBooksModel {

        void searchForBooks(int from, int to, String publisher);

        Cursor getBooksFromDatabase(int from, int to, String publisher);
    }

    interface SearchBooksPresenter {

        void setBooks(List<Book> books);

        void searchForBooks(int from, int to, String publisher);
    }
}
