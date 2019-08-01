package com.example.ilibrary.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.views.SignInActivity;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements com.example.ilibrary.interfaces.DatabaseHelper {

    private final static String USERS = "users";
    private final static String BOOKS = "books";
    private final static String RESERVED_BOOKS = "reservedbooks";

    private SQLiteDatabase db;


    public DatabaseHelper(Context context) {

        super(context, "library.db", null, 1);
    }

    @Override
    public Cursor getBooks() {

        db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + BOOKS, null);
        return res;
    }

    @Override
    public Cursor getReservedBooks(String username) {
        db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + BOOKS + " as a where a.isbn in (select isbn from " + RESERVED_BOOKS + " where username = '" + username + "') ", null);
        return res;
    }

    @Override
    public boolean cancelReservation(String isbn) {
        db = this.getWritableDatabase();
        db.execSQL("delete from " + RESERVED_BOOKS + " where username = '" + SignInActivity.username + "' and isbn = " + isbn);
        return true;
    }

    @Override
    public boolean reserveABook(long isbn, String username) {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("isbn", isbn);
        values.put("username", username);

        return db.insert(RESERVED_BOOKS, null, values) > 0;
    }

    @Override
    public void addBooks(List<Book> books) {

        ContentValues contentValues = new ContentValues();

        for (Book i : books) {

            db = this.getWritableDatabase();

            contentValues.put("title", i.getTitle());
            contentValues.put("publisher", i.getPublisher());
            contentValues.put("author", i.getAuthor());
            contentValues.put("isbn", i.getIsbn());
            contentValues.put("pages", i.getPages());


            db.insert(BOOKS, null, contentValues);
        }

    }

    @Override
    public Cursor getUsers() {

        db = this.getWritableDatabase();
        return db.rawQuery("select * from users", null);
    }

    @Override
    public Cursor getReservations() {

        db = this.getWritableDatabase();
        return db.rawQuery("select * from " + RESERVED_BOOKS, null);
    }

    @Override
    public boolean blockAUser(String username) {
        return false;
    }

    @Override
    public Cursor searchForBooks(int fromPages, int toPages, String publisher) {
        return null;
    }

    @Override
    public Cursor signIn(String username, String password) {
        db = this.getWritableDatabase();

        return db.rawQuery("select * from users where username = '" + username + "' and password = '" + password + "' limit 1", null);
    }

    @Override
    public boolean signUp(String username, String password, String gender, String email, int age) {

        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("gender", gender);
        contentValues.put("age", age);
        return db.insert(USERS, null, contentValues) > 0;
    }

    @Override
    public int countReservations(String username) {

        db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("select count(*) as reservations from " + RESERVED_BOOKS + " where username = '" + username + "'", null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("reservations"));
        }

        return -1;
    }

    @Override
    public boolean isReserved(String isbn, String username) {

        db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select count(*) as reservations from " + RESERVED_BOOKS + " where isbn = " + isbn + " and username = '" + username + "'", null);

        if (cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndex("reservations")) == 0)
                return false;
        }

        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists " + USERS + " (username TEXT PRIMARY KEY, password TEXT NOT NULL , gender TEXT NOT NULL , age INTEGER NOT NULL , email TEXT UNIQUE NOT NULL)");
        sqLiteDatabase.execSQL("create table if not exists " + BOOKS + " (isbn BIGINT PRIMARY KEY, title TEXT NOT NULL , author TEXT NOT NULL , publisher TEXT NOT NULL , pages INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("create table if not exists " + USERS + " (username TEXT PRIMARY KEY, password TEXT NOT NULL , gender TEXT NOT NULL , age INTEGER NOT NULL , email TEXT UNIQUE NOT NULL)");
        sqLiteDatabase.execSQL("create table if not exists " + RESERVED_BOOKS + " (rid INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL , isbn BIGINT NOT NULL ,FOREIGN KEY (username) REFERENCES users (username) \n" +
                " ON DELETE RESTRICT ON UPDATE RESTRICT,\n" +
                " FOREIGN KEY (isbn) REFERENCES books (isbn) \n" +
                " ON DELETE RESTRICT ON UPDATE RESTRICT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BOOKS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESERVED_BOOKS);
        onCreate(sqLiteDatabase);

    }
}
