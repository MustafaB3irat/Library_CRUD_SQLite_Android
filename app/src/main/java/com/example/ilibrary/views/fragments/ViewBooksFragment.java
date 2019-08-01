package com.example.ilibrary.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.ilibrary.R;
import com.example.ilibrary.adapters.ViewBooksAdapter;
import com.example.ilibrary.databinding.ViewBooksBinding;
import com.example.ilibrary.interfaces.ViewBooks;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.presetners.ViewBooksPresenter;

import java.util.List;

public class ViewBooksFragment extends Fragment implements ViewBooks.ViewBooksFragment {

    private ViewBooksBinding viewBooksBinding;
    private ViewBooksAdapter adapter;
    private ViewBooks.ViewBooksPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewBooksBinding = DataBindingUtil.inflate(inflater, R.layout.view_books, container, false);
        presenter = new ViewBooksPresenter(this);
        presenter.getBooks();
        return viewBooksBinding.getRoot();
    }

    @Override
    public void setViewBooksAdapter(List<Book> books) {

        viewBooksBinding.viewBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ViewBooksAdapter(books, getActivity() , 0);
        viewBooksBinding.viewBooksRecyclerView.setAdapter(adapter);
    }
}
