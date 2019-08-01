package com.example.ilibrary.models;

import android.database.Cursor;

import androidx.fragment.app.FragmentActivity;

import com.example.ilibrary.interfaces.LibraryAPI;
import com.example.ilibrary.interfaces.MyCart;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.models.data.BooksList;
import com.example.ilibrary.presetners.MyCartPresenter;
import com.example.ilibrary.views.SignInActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCartModel implements MyCart.MyCartModel {


    private MyCart.MyCartPresenter presenter;
    private DatabaseHelper databaseHelper;


    public MyCartModel(MyCartPresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        databaseHelper = new DatabaseHelper(activity);
    }

    private Gson gson = new GsonBuilder().setLenient().create();


    @Override
    public void getBooksFromRetrofit() {

            Cursor cursor = getBooksFromDatabase();

            if (cursor != null) {

                List<Book> books = new ArrayList<>();

                while (cursor.moveToNext()) {
                    books.add(new Book(cursor.getString(cursor.getColumnIndex("isbn")), cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("author")), cursor.getString(cursor.getColumnIndex("publisher")), cursor.getString(cursor.getColumnIndex("pages"))));
                }

                presenter.setBooks(books);
            }

    }

    @Override
    public Cursor getBooksFromDatabase() {
        return databaseHelper.getReservedBooks(SignInActivity.username);
    }
}
