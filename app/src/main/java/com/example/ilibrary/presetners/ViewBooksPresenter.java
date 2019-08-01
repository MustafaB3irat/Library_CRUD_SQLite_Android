package com.example.ilibrary.presetners;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ilibrary.interfaces.ViewBooks;
import com.example.ilibrary.models.data.Book;

import java.util.List;

public class ViewBooksPresenter implements ViewBooks.ViewBooksPresenter {

    private ViewBooks.ViewBooksFragment view;
    private ViewBooks.ViewBooksModel model;

    public ViewBooksPresenter(ViewBooks.ViewBooksFragment view) {
        this.view = view;
        model = new com.example.ilibrary.models.ViewBooks(this,  ((Fragment)view).getActivity());
    }

    @Override
    public void setBooks(List<Book> books) {
        view.setViewBooksAdapter(books);
    }

    @Override
    public void getBooks() {
        model.getBooksFromRetrofit();
    }
}
