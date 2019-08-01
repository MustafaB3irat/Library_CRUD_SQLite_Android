package com.example.ilibrary.interfaces;

import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.models.data.BooksList;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LibraryAPI {

    String BASE_URL = "https://api.myjson.com/bins/";

    @GET("1epyfj")
    Call<BooksList> getBooks();
}

