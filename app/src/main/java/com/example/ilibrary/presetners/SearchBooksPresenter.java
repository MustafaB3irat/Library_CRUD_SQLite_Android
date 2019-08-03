package com.example.ilibrary.presetners;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.ilibrary.interfaces.SearchBooks;
import com.example.ilibrary.models.SearchBook;
import com.example.ilibrary.models.data.Book;

import java.util.List;

public class SearchBooksPresenter implements SearchBooks.SearchBooksPresenter {

    private SearchBooks.SearchBooksModel model;
    private SearchBooks.SearchBooksFragment fragment;


    public SearchBooksPresenter(SearchBooks.SearchBooksFragment fragment) {
        this.fragment = fragment;
        model = new SearchBook(this, ((Fragment) fragment).getActivity());
    }

    @Override
    public void setBooks(List<Book> books) {

        fragment.setSearchBooksAdapter(books);

    }

    @Override
    public void searchForBooks(int from, int to, String publisher) {

        model.searchForBooks(from, to, publisher);

    }
}
