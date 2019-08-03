package com.example.ilibrary.models.data;

import com.google.gson.annotations.SerializedName;

public class ReservedBook {


    @SerializedName("isbn")
    private String isbn;

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("pages")
    private String pages;

    @SerializedName("reserved_at")
    private String reserved_at;


    @SerializedName("reserved_by")
    private String reserved_by;


    public ReservedBook() {
    }

    public ReservedBook(String isbn, String title, String author, String publisher, String pages, String reserved_at, String reserved_by) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pages = pages;
        this.reserved_at = reserved_at;
        this.reserved_by = reserved_by;
    }

    public String getReserved_at() {
        return reserved_at;
    }

    public void setReserved_at(String reserved_at) {
        this.reserved_at = reserved_at;
    }

    public String getReserved_by() {
        return reserved_by;
    }

    public void setReserved_by(String reserved_by) {
        this.reserved_by = reserved_by;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }


}
