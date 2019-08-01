package com.example.ilibrary.interfaces;

import android.database.Cursor;

import com.example.ilibrary.models.data.Book;

import java.util.List;

public interface DatabaseHelper {

    Cursor getBooks();

    Cursor getReservedBooks(String username);

    boolean cancelReservation(String isbn);

    boolean reserveABook(long isbn, String username);

    void addBooks(List<Book> books);

    Cursor getUsers();

    Cursor getReservations();

    boolean blockAUser(String username);

    Cursor searchForBooks(int fromPages, int toPages, String publisher);

    Cursor signIn(String username, String password);

    boolean signUp(String username, String password, String gender, String email, int age);

    int countReservations(String username);

    boolean isReserved(String isbn, String username);


}
