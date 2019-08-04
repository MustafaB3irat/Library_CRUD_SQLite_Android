package com.example.ilibrary.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.views.SignInActivity;

import java.sql.Timestamp;
import java.util.Date;
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

        Cursor res = db.rawQuery("select * from " + BOOKS + " as a where a.isbn not in (select b.isbn from " + RESERVED_BOOKS + " as b )", null);
        return res;
    }

    @Override
    public Cursor getBook(String isbn) {

        db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + BOOKS + "  where isbn = " + isbn, null);
        return res;
    }

    @Override
    public boolean cancelReservationByAdmin(String isbn, String username) {
        db = this.getWritableDatabase();
        db.execSQL("delete from " + RESERVED_BOOKS + " where username = '" + username + "' and isbn = " + isbn);
        return true;
    }

    @Override
    public Cursor getReservedBooks(String username) {
        db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + BOOKS + " as a where a.isbn in (select isbn from " + RESERVED_BOOKS + " where username = '" + username + "') ", null);
        return res;
    }

    @Override
    public boolean cancelAllReservationsForBlockedUser(String username) {
        db = this.getWritableDatabase();
        db.execSQL("delete from " + RESERVED_BOOKS + " where username = '" + username + "'");
        return true;
    }

    @Override
    public boolean cancelReservation(String isbn) {
        db = this.getWritableDatabase();
        db.execSQL("delete from " + RESERVED_BOOKS + " where username = '" + SignInActivity.username + "' and isbn = " + isbn);
        return true;
    }

    @Override
    public int getUserStatus(String username) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select blocked from " + USERS + " where username = '" + username + "'", null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("blocked"));
        }
        return -1;
    }

    @Override
    public boolean reserveABook(long isbn, String username) {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        String reserved_at = ts.toString().split(":")[0] + ":" + ts.toString().split(":")[1];
        values.put("isbn", isbn);
        values.put("username", SignInActivity.username);
        values.put("reserved_at", reserved_at);

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
    public Cursor getUsersByName(String username) {

        db = this.getWritableDatabase();
        return db.rawQuery("select * from users where username like '" + username + "%'", null);

    }

    @Override
    public Cursor getCurrentUser() {
        db = this.getWritableDatabase();
        return db.rawQuery("select * from users where username = '" + SignInActivity.username + "'", null);
    }

    @Override
    public boolean updateUser(String gender, int age) {


        db = this.getWritableDatabase();

        db.execSQL("update users set gender = '" + gender + "' , age =" + age);

        return true;
    }

    @Override
    public Cursor getReservations() {

        db = this.getWritableDatabase();
        return db.rawQuery("select * from " + RESERVED_BOOKS, null);
    }

    @Override
    public boolean blockAUser(String username) {

        db = this.getWritableDatabase();

        db.execSQL("update users set blocked = 1 where username = '" + username + "'");

        if (cancelAllReservationsForBlockedUser(username)) {
            return true;
        } else
            return false;
    }

    @Override
    public boolean unBlockAUser(String username) {

        db = this.getWritableDatabase();
        db.execSQL("update users set blocked = 0 where username = '" + username + "'");
        return true;

    }

    @Override
    public Cursor searchForBooks(int fromPages, int toPages, String publisher) {

        db = this.getWritableDatabase();
        Cursor cursor = null;

        if (fromPages > 0 && toPages > 0 && publisher != null && !TextUtils.isEmpty(publisher)) {

            cursor = db.rawQuery("select * from " + BOOKS + " where pages >= " + fromPages + " and pages <=" + toPages + " and publisher = '" + publisher + "'", null);
        } else if (toPages > 0 && fromPages > 0 && TextUtils.isEmpty(publisher)) {

            cursor = db.rawQuery("select * from " + BOOKS + " where pages >= " + fromPages + " and pages <=" + toPages, null);
        } else if (toPages == 0 && fromPages == 0 && !TextUtils.isEmpty(publisher)) {
            cursor = db.rawQuery("select * from " + BOOKS + " where publisher = '" + publisher + "'", null);
        }

        return cursor;
    }

    @Override
    public Cursor signIn(String username, String password) {
        db = this.getWritableDatabase();

        return db.rawQuery("select * from users where username = '" + username + "' and password = '" + password + "' limit 1", null);
    }

    @Override
    public boolean signUp(String username, String password, String email, String gender, int age) {

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

        sqLiteDatabase.execSQL("create table if not exists " + USERS + " (username TEXT PRIMARY KEY, password TEXT NOT NULL , gender TEXT NOT NULL , age INTEGER NOT NULL , email TEXT UNIQUE NOT NULL , blocked INTEGER NOT NULL DEFAULT 0)");
        sqLiteDatabase.execSQL("create table if not exists " + BOOKS + " (isbn BIGINT PRIMARY KEY, title TEXT NOT NULL , author TEXT NOT NULL , publisher TEXT NOT NULL , pages INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("create table if not exists " + USERS + " (username TEXT PRIMARY KEY, password TEXT NOT NULL , gender TEXT NOT NULL , age INTEGER NOT NULL , email TEXT UNIQUE NOT NULL)");
        sqLiteDatabase.execSQL("create table if not exists " + RESERVED_BOOKS + " (rid INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL , isbn BIGINT NOT NULL,reserved_at TEXT NOT NULL ,FOREIGN KEY (username) REFERENCES users (username) \n" +
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
