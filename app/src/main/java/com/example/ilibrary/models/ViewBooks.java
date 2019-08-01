package com.example.ilibrary.models;

import android.database.Cursor;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.ilibrary.interfaces.LibraryAPI;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.models.data.BooksList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewBooks implements com.example.ilibrary.interfaces.ViewBooks.ViewBooksModel {

    private com.example.ilibrary.interfaces.ViewBooks.ViewBooksPresenter presenter;
    private DatabaseHelper databaseHelper;


    public ViewBooks(com.example.ilibrary.interfaces.ViewBooks.ViewBooksPresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        databaseHelper = new DatabaseHelper(activity);
    }

    private Gson gson = new GsonBuilder().setLenient().create();


    @Override
    public void getBooksFromRetrofit() {

        if (getBooksFromDatabase().getCount() <= 0) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(LibraryAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            LibraryAPI libraryAPI = retrofit.create(LibraryAPI.class);

            Call<BooksList> call = libraryAPI.getBooks();


            call.enqueue(new Callback<BooksList>() {
                @Override
                public void onResponse(Call<BooksList> call, Response<BooksList> response) {

                    presenter.setBooks(response.body().getBooks());
                    databaseHelper.addBooks(response.body().getBooks());

                }

                @Override
                public void onFailure(Call<BooksList> call, Throwable t) {

                }
            });

        } else {

            Cursor cursor = getBooksFromDatabase();

            List<Book> books = new ArrayList<>();

            while (cursor.moveToNext()) {
                books.add(new Book(cursor.getString(cursor.getColumnIndex("isbn")), cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("author")), cursor.getString(cursor.getColumnIndex("publisher")), cursor.getString(cursor.getColumnIndex("pages"))));
            }

            presenter.setBooks(books);
        }

    }

    @Override
    public Cursor getBooksFromDatabase() {
        return databaseHelper.getBooks();
    }
}
