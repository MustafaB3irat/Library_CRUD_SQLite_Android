package com.example.ilibrary.models.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BooksList implements Serializable {

    @SerializedName("books")
    private List<Book> books;

    public BooksList(List<Book> books) {
        this.books = books;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
