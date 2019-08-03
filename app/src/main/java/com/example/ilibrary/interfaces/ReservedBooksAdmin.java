package com.example.ilibrary.interfaces;

import android.database.Cursor;

import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.models.data.ReservedBook;

import java.util.List;

public interface ReservedBooksAdmin {


    interface ReservedBooksAdminModel {

         void getReservedBooks();
         Cursor getBooksFromDatabase();
    }

    interface ReservedBooksAdminFragment {

        void setReservedBooksAdapter(List<ReservedBook> books);
    }

    interface ReservedBooksAdminPresenter {

         void setBooks(List<ReservedBook> books);
         void getBooks();
    }
}
