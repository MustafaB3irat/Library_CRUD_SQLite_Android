package com.example.ilibrary.models;

import android.database.Cursor;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.ilibrary.interfaces.MyCart;
import com.example.ilibrary.interfaces.ReservedBooksAdmin;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.models.data.ReservedBook;
import com.example.ilibrary.presetners.MyCartPresenter;
import com.example.ilibrary.views.SignInActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class ReservedBooksAdminModel implements ReservedBooksAdmin.ReservedBooksAdminModel {


    private ReservedBooksAdmin.ReservedBooksAdminPresenter presenter;
    private DatabaseHelper databaseHelper;


    public ReservedBooksAdminModel(ReservedBooksAdmin.ReservedBooksAdminPresenter presenter, FragmentActivity activity) {
        this.presenter = presenter;
        databaseHelper = new DatabaseHelper(activity);
    }

    private Gson gson = new GsonBuilder().setLenient().create();


    @Override
    public void getReservedBooks() {

        Cursor cursor = getBooksFromDatabase();

        if (cursor != null) {

            List<ReservedBook> books = new ArrayList<>();
            Cursor bookInfo;

            while (cursor.moveToNext()) {

                bookInfo = databaseHelper.getBook(cursor.getString(cursor.getColumnIndex("isbn")));
                bookInfo.moveToFirst();

                books.add(new ReservedBook(cursor.getString(cursor.getColumnIndex("isbn")), bookInfo.getString(bookInfo.getColumnIndex("title")), bookInfo.getString(bookInfo.getColumnIndex("author")), bookInfo.getString(bookInfo.getColumnIndex("publisher")), bookInfo.getString(bookInfo.getColumnIndex("pages")), cursor.getString(cursor.getColumnIndex("reserved_at")), cursor.getString(cursor.getColumnIndex("username"))));
                Log.d("Book", books.get(books.size() - 1).getReserved_by());
            }

            presenter.setBooks(books);
        }

    }

    @Override
    public Cursor getBooksFromDatabase() {
        return databaseHelper.getReservations();
    }
}
