package com.example.ilibrary.models;

import android.database.Cursor;

import androidx.fragment.app.FragmentActivity;

import com.example.ilibrary.interfaces.LibraryAPI;
import com.example.ilibrary.interfaces.SearchBooks;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.models.data.BooksList;
import com.example.ilibrary.presetners.SearchBooksPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchBook implements com.example.ilibrary.interfaces.SearchBooks.SearchBooksModel {

    private SearchBooks.SearchBooksPresenter presenter;
    private DatabaseHelper databaseHelper;


    public SearchBook(SearchBooksPresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        databaseHelper = new DatabaseHelper(activity);
    }

    private Gson gson = new GsonBuilder().setLenient().create();


    @Override
    public void searchForBooks(int from, int to, String publisher) {

        Cursor cursor = getBooksFromDatabase(from, to, publisher);

        List<Book> books = new ArrayList<>();

        while (cursor.moveToNext()) {
            books.add(new Book(cursor.getString(cursor.getColumnIndex("isbn")), cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("author")), cursor.getString(cursor.getColumnIndex("publisher")), cursor.getString(cursor.getColumnIndex("pages"))));
        }

        presenter.setBooks(books);

    }

    @Override
    public Cursor getBooksFromDatabase(int from, int to, String publisher) {
        return databaseHelper.searchForBooks(from, to, publisher);
    }
}
